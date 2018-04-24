# Arkkitehtuurikuvaus

## Ohjelman rakenne

Alla oleva luokka-/pakkauskaavio kuvaa sovelluksen eri osien suhteita toisiinsa. CryptoService-luokka käyttää kaikkia domain-pakkauksen luokkia sisäänkirjautuneen käyttäjän (User) kautta. Huom! CryptoBatchDao-luokka puuttuu vielä toistaiseksi!

<img src="https://raw.githubusercontent.com/nakkekakke/CryptoTracker/master/dokumentointi/kuvat/Luokkapakkausdiagrammi.png">

## Päätoiminnallisuudet

Kuvataan ohjelman keskeisimpiä toimintoja ja niiden suoritusta sekvenssikaavioina.

### Sisäänkirjautuminen

Sisäänkirjautuminen tapahtuu ohjelman aloitusnäkymässä. Ohjelman käyttäjä voi kirjautua sisään, kun hän syöttää aloitusnäkymässä olevaan tekstikenttään käyttäjänimensä ja painaa "Login"-näppäintä.

<img src="https://raw.githubusercontent.com/nakkekakke/CryptoTracker/master/dokumentointi/kuvat/sekvenssikaavio_login.png">

Tapahtumakäsittelijä kutsuu sovelluslogiikasta vastaavan luokan _CryptoService_ metodia [login](https://github.com/nakkekakke/CryptoTracker/blob/5eec2a01380550264e897e71b4b0ec71380c8977/src/main/java/cryptotracker/domain/CryptoService.java#L88) parametrinaan käyttäjän syöttämä käyttäjänimi. Luokan _UserDao_ avulla selvitetään, onko kyseisellä käyttäjänimellä rekisteröidytty sovellukseen. Tietokantakysely tuottaa lopulta vastauksen; jos saatu ResultSet on epätyhjä, niin käyttäjä löytyi. Tällöin metodi login palauttaa arvon true, jolloin sovelluslogiikka kirjaa käyttäjän sisään. Lopulta käyttäjä siirretään uuteen näkymään.


