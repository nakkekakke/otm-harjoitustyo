# Vaatimusmäärittely

## Sovelluksen tarkoitus

Tämän sovelluksen avulla käyttäjä pystyy pitämään kirjaa omasta kryptovaluuttaportfoliostaan ja seuraamaan sen kokonaisarvoa euroissa (euroarvo haetaan coinmarketcap.com:in [API:sta](https://coinmarketcap.com/api/). Sovelluksella näkee myös portfolion historiallisen arvonkehityksen. Sovelluksella voi olla monta rekisteröitynyttä käyttäjää, ja kirjautumalla sisään jokainen käyttäjä näkee oman portfolionsa.

## Käyttäjät

Aluksi sovelluksella on vain yksi käyttäjäryhmä, _normaali käyttäjä_. Lisäoikeuksilla varustettu _pääkäyttäjä_ saatetaan lisätä tulevaisuudessa.

## Suunnitellut päätoiminnallisuudet

Lisätään ennen lisätoiminnallisuuksia.

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
  - jos portfoliossa on jo valuuttaa, jota sinne lisätään, tämän uuden ostoerän arvo lisätään entisen arvoon

- käyttäjä voi poistaa kryptovaluuttoja portfoliostaan
  - jokaisen valuutan kohdalla on poistonäppäin

## Suunnitellut lisätoiminnallisuudet

Lisätään päätoiminnallisuuksien jälkeen.

- käyttäjätunnuksen luonnin (ja sisäänkirjautumisen) yhteydessä vaaditaan myös salasana
- valuuttoja lisättäessä syötetään ostokurssi sekä valuutta, jolla ostos tehtiin
  - valuutta joko euro tai toinen kryptovaluutta
    - jos kryptovaluutta, vähennetään vastaava määrä lähtövaluuttaa portfoliosta (valuutanvaihto)
- valuuttaomistusten määrää voi muokata
- jokaisen valuuttaomistuksen kohdalla näkyy omistuksen arvo euroina

## Jatkokehitys

- valuuttaomistusten ostohintaa voi muokata
- valuuttaomistusta lisättäessä liitetään ostoerään ostopäivämäärä
- jokaisen valuuttaomistuksen kohdalla näkyy lista ostoeristä
  - yksittäiset ostoerät muokattavissa
