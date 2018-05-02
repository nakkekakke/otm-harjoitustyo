# Arkkitehtuurikuvaus

## Ohjelman rakenne

Ohjelman rakenne koostuu kolmesta peruskerroksesta, joita ovat **käyttöliittymä**, **sovelluslogiikka** ja **pysyväistalletus**. Ohjelman koodissa näitä kerroksia vastaavat pakkaukset **ui**, **domain** ja **dao**.

Alla oleva luokka-/pakkauskaavio kuvaa sovelluksen eri osien suhteita toisiinsa. Sovelluslogiikasta vastaava CryptoService-luokka käyttää kaikkia domain-pakkauksen luokkia sisäänkirjautuneen käyttäjän (User) kautta.

<img src="https://raw.githubusercontent.com/nakkekakke/CryptoTracker/master/dokumentointi/kuvat/Luokkapakkausdiagrammi.png">

Tarkastellaan seuraavaksi kunkin kerroksen toimintaa ja rakennetta vielä tarkemmin.

## Käyttöliittymä 
Vastaava pakkaus [cryptotracker.ui](https://github.com/nakkekakke/CryptoTracker/tree/master/src/main/java/cryptotracker/ui).

Ohjelman käyttöliittymä on toteutettu JavaFX:llä.
Käyttöliittymässä on neljä eri näkymää
- sisäänkirjautuminen ja rekisteröityminen
- kryptovaluuttalista
- kryptovaluutan/ostoerän lisääminen
- kryptovaluutan ostoerälista
Jokainen näistä näkymästä on toteutettu sovelluksessa omana [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html)-olionaan. Ohjelmassa näkyy kerrallaan vain yksi ikkuna ([Stage](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html)), johon näytettävä näkymä (Scene) sijoitetaan. Käyttöliittymä rakennetaan luokassa [cryptotracker.ui.CryptoUI](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/ui/CryptoUI.java).

Käyttöliittymä on pyritty eristämään sovelluslogiikasta, joten se vain kutsuu sovelluslogiikkaluokan olion metodeja kun käyttäjä tekee jotain sovelluksessa.

## Sovelluslogiikka
Vastaava pakkaus [cryptotracker.domain](https://github.com/nakkekakke/CryptoTracker/tree/master/src/main/java/cryptotracker/domain).

Sovelluksen ydin muodostuu "**dataluokkien**" [User](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/domain/User.java), [Portfolio](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/domain/Portfolio.java), [Cryptocurrency](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/domain/Cryptocurrency.java) ja [CryptoBatch](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/domain/CryptoBatch.java) olioista. Näimä oliot toimivat väliaikaisena talletuskohteena kaikelle datalle, mikä halutaan tallettaa pysyvästi, tai vastaavasti näyttää ohjelman käyttäjälle.

Ohjelmassa tapahtuvat toiminnalliset kokonaisuudet kuuluvat luokan [CryptoService](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/domain/CryptoService.java) ainoalle oliolle. Luokka tarjoaa metodeita käyttöliittymän toiminnoille. Näitä ovat esimerkiksi:
- boolean login(String username)
- boolean createUser(String username)
- CryptoBatch createCrypto(String name, int amount, int totalPaid, LocalDate date)
- void deleteBatch(int id)

_CryptoService_ tallentaa ja hakee dataa pysyväistalletuksesta vastaavassa pakkauksessa _cryptotracker.dao_ olevien, Dao-rajapinnan toteuttavien luokkien kautta. Jokaista dao-oliota on vain yksi kappale, ja ne injektoidaan _CryptoService_-oliolle [konstruktorissa](https://github.com/nakkekakke/CryptoTracker/blob/82215b1097a1064cfb681fcdb84f21cdc785bf2c/src/main/java/cryptotracker/domain/CryptoService.java#L24).

## Pysyväistallennus
Vastaava pakkaus [cryptotracker.dao](https://github.com/nakkekakke/CryptoTracker/tree/master/src/main/java/cryptotracker/dao).

Luokkien [UserDao](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/dao/UserDao.java), [PortfolioDao](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/dao/CryptocurrencyDao.java), [CryptocurrencyDao](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/dao/PortfolioDao.java) ja [CryptoBatchDao](https://github.com/nakkekakke/CryptoTracker/blob/master/src/main/java/cryptotracker/dao/CryptoBatchDao.java) oliot vastaavat datan tallentamisesta tietokantaan ja datan hakemisesta tietokannasta. Näitä olioita on ohjelmassa vain yksi jokaista. Nämä luokat noudattavat [DAO-mallia](https://en.wikipedia.org/wiki/Data_access_object), minkä takia tietokantatallennus voitaisiin suhteellisen helposti vaihtaa johonkin toiseen talletusmuotoon; tämä tosin vaatisi muutoksia kaikkiin dao-luokkiin.

### Tietokanta

Sovellus tallentaa dataluokkia (User, Portfolio, Cryptocurrency, CryptoBatch) vastaavat tiedot tietokantaan. Tietokannan sijainti määritellään tiedostossa [config.properties](https://github.com/nakkekakke/CryptoTracker/blob/master/config.properties).

Tietokannanhallintajärjestelmänä käytetään [SQLiteä](https://www.sqlite.org/about.html). Apuna tietokannan kanssa kommunikoimiseen käytetään Javan [JDBC](https://en.wikipedia.org/wiki/Java_Database_Connectivity)-rajapintaa.

Tietokantataulut ja niiden sarakkeet selviävät Database-luokan metodissa [initializeTables()](https://github.com/nakkekakke/CryptoTracker/blob/82215b1097a1064cfb681fcdb84f21cdc785bf2c/src/main/java/cryptotracker/dao/Database.java#L23) määritellyistä "CREATE TABLE"-lauseista.

## Päätoiminnallisuudet

Kuvataan ohjelman keskeisimpiä toimintoja ja niiden suoritusta sekvenssikaavioina.

### Sisäänkirjautuminen

Sisäänkirjautuminen tapahtuu ohjelman aloitusnäkymässä. Ohjelman käyttäjä voi kirjautua sisään, kun hän syöttää aloitusnäkymässä olevaan tekstikenttään käyttäjänimensä ja painaa "Login"-näppäintä.

<img src="https://raw.githubusercontent.com/nakkekakke/CryptoTracker/master/dokumentointi/kuvat/sekvenssikaavio_login.png">

Tapahtumakäsittelijä kutsuu sovelluslogiikasta vastaavan luokan _CryptoService_ metodia [login](https://github.com/nakkekakke/CryptoTracker/blob/5eec2a01380550264e897e71b4b0ec71380c8977/src/main/java/cryptotracker/domain/CryptoService.java#L88) parametrinaan käyttäjän syöttämä käyttäjänimi. Luokan _UserDao_ avulla selvitetään, onko kyseisellä käyttäjänimellä rekisteröidytty sovellukseen. Tietokantakysely tuottaa lopulta vastauksen; jos saatu ResultSet on epätyhjä, niin käyttäjä löytyi. Tällöin metodi login palauttaa arvon true, jolloin sovelluslogiikka kirjaa käyttäjän sisään. Lopulta käyttäjä siirretään uuteen näkymään.


