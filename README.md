# CryptoTracker

Tämä sovellus on harjoitustyö kurssille Ohjelmistotekniikan menetelmät. Sovellukseen voi luoda oman portfolion, johon käyttäjä voi tallentaa tiedot omista kryptovaluuttasijoituksistaan. Näin käyttäjä voi seurata eri kryptovaluuttasijoituksiaan helposti ja nopeasti. Tällä hetkellä sovellukseen voi vain rekisteröityä, kirjautua sisään ja kirjautua ulos. Sovellus on englanninkielinen.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/nakkekakke/CryptoTracker/blob/master/dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/nakkekakke/CryptoTracker/blob/master/dokumentointi/tyoaikakirjanpito.md)

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
