# Vaatimusmäärittely

## Sovelluksen tarkoitus

Tämän sovelluksen avulla käyttäjä pystyy pitämään kirjaa omista kryptovaluuttasijoituksistaan lisäämällä niiden tiedot portfolioon. Käyttäjä pystyy seuraamaan sekä koko portfolionsa että yksittäisten portfolion kryptovaluuttojen arvoa euroissa (euroarvo haetaan coinmarketcap.com:in [API:sta](https://coinmarketcap.com/api/)). Sovelluksella voi olla monta rekisteröitynyttä käyttäjää, ja kirjautumalla sisään jokainen käyttäjä näkee oman portfolionsa.

## Käyttäjät

Aluksi sovelluksella on vain yksi käyttäjäryhmä, _normaali käyttäjä_. Lisäoikeuksilla varustettu _pääkäyttäjä_ saatetaan lisätä tulevaisuudessa.

## Suunnitellut perustoiminnallisuudet

Tässä vaiheessa ei euro-arvoja oteta vielä mukaan.

### Ennen sisäänkirjautumista

- käyttäjä voi luoda käyttäjätunnuksen
  - käyttäjätunnuksen on oltava uniikki
  - käyttäjätunnus vähintään neljä merkkiä pitkiä

- käyttäjä voi kirjautua sisään
  - jos kirjautuminen epäonnistuu, ilmoitetaan käyttäjälle

### Sisäänkirjautumisen jälkeen

- käyttäjä näkee oman portfolionsa
  - portfoliossa näkyy siihen lisätyt kryptovaluutat sekä jokaisen valuutan määrä
  - portfolio näkyy vain sisäänkirjautuneelle käyttäjälle

- käyttäjä voi lisätä kryptovaluuttoja omaan portfolioonsa
  - lisättäessä syötetään valuutan nimi ja lisättävä määrä
  - jos portfoliossa on jo valuuttaa, jota sinne lisätään, tämän uuden ostoerän määrä lisätään entisen määrään

- käyttäjä voi poistaa kryptovaluuttoja portfoliostaan
  - jokaisen valuutan kohdalla on poistonäppäin

## Suunnitellut lisätoiminnallisuudet

Lisätään perustoiminnallisuuksien jälkeen.

### Sisäänkirjautuminen

- käyttäjätunnuksen luonnin (ja sisäänkirjautumisen) yhteydessä käyttäjältä vaaditaan myös salasana

### Portfolionhallinta

- jokainen valuuttaomistuksen lisäys käsitellään erillisenä **ostoeränä**
  - jokaisen ostoerän kohdalla näkyy valuutan määrä, ostopäivämäärä ja tämänhetkinen arvo euroina
  - ostoerät poistettavissa yksittäisesti
  
- ostoerää lisättäessä käyttäjältä vaaditaan ostopäivämäärä, ostohinta (kurssi) sekä valuutta, jolla ostos tehtiin
  - valuutta joko euro tai toinen kryptovaluutta
    - jos kryptovaluutta, vähennetään vastaava määrä lähtövaluuttaa portfoliosta (valuutanvaihto)

### Portfolion tietojen esitys

- jokaisen valuuttaomistuksen kohdalla näkyy koko omistuksen arvo euroina, sekä lista ostoeristä
  - koko omistuksen arvo saadaan ostoerien arvojen summasta

- sovelluksessa näkyy portfolion kokonaisarvo euroina

- sovelluksessa näkyy euromäärä, jonka käyttäjä on sijoittanut portfolioonsa, sekä voiton/tappion suuruus

- sovelluksessa kuvataan portfolion arvonmuutos viimeisen tunnin, päivän ja viikon aikana
  - koko portfolion arvonmuutos sekä jokaisen kryptovaluutan arvonmuutos erikseen

#### Seuraava toteutetaan, jos jää aikaa

- sovelluksessa on (esim.) taulukko, jossa kuvataan portfolion arvonkehitystä
  - tarkkuus alustavasti kuukausittainen
  - seuraa portfolion absoluuttista arvoa ja prosentuaalista kehitystä

## Ideoita jatkokehitykseen

- käyttäjä voi muokata ostoerän ostohintaa ja ostetun valuutan määrää
  
- sovelluksessa on ympyrädiagrammi, joka kuvaa portfolion eri kryptovaluuttojen osuutta koko portfoliosta

- sovelluksessa on viivadiagrammi, joka kuvaa portfolion arvonkehitystä
