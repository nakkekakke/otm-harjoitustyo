# CryptoTracker

Tämä sovellus on harjoitustyö kurssille Ohjelmistotekniikan menetelmät. Sovellukseen voi luoda oman portfolion, johon käyttäjä voi tallentaa tiedot omista kryptovaluuttasijoituksistaan. Näin käyttäjä voi seurata eri kryptovaluuttasijoituksiaan helposti ja nopeasti. **Tällä hetkellä sovellukseen voi rekisteröityä, kirjautua sisään ja ulos, lisätä kryptovaluuttoja ja ostoeriä.** Sovellus on englanninkielinen.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/nakkekakke/CryptoTracker/blob/master/dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/nakkekakke/CryptoTracker/blob/master/dokumentointi/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/nakkekakke/CryptoTracker/blob/master/dokumentointi/arkkitehtuuri.md)

## Komentorivitoiminnot

### Testaaminen

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Tätä kattavuusraporttia voi tarkastella avaamalla selaimella repositorion juuresta löytyvän tiedoston _target/site/jacoco/index.html_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/nakkekakke/CryptoTracker/blob/master/checkstyle.xml) määritellyt tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Voit tarkastella Checkstyle-raporttia avaamalla selaimella repositorion juuresta löytyvän tiedoston _target/site/checkstyle.html_

### Suoritettavan jarin generointi

Jar-tiedosto generoidaan komennolla

```
mvn package
```

Syntynyt jar-tiedosto _CryptoTracker-1.0-SNAPSHOT.jar_ löytyy kansiosta _target_. HUOM! Sovellus on riippuvainen repositorion juuresta löytyvästä tiedostosta config.properties! Tiedoston täytyy siis olla samassa kansiossa kuin suoritettava jar-tiedosto. Voit esimerkiksi siirtää jar-tiedoston repositorion juureen komennolla

```
mv CryptoTracker-1.0-SNAPSHOT.jar ..
```
