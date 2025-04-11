# Drawing App Demo

> Piirrossovellus on tarkoitettu TOL:n Ohjelmointi 4 -kurssin demoksi.

Tässä demossa esitellään *hyvin* lyhyesti ja askeettisesti miten Javalla Swingissä:

1. view -luokassa (`DrawingPanel`) käsitellään hiiritapahtumia (mouse down, mouse up, mouse drag),
2. view -luokka kertoo hiiritapahtumista  model -oliolle (`DrawingDocument`), jossa luodaan hiiritapahtumien perustella piirros-olioita (`DrawingShape`) jotka jemmataan dokumenttiolion sisältämään listaan, sekä
3. päivitetään view -luokassa tilanne hakemalla dokumentista piirrosolioita piirrettäväksi näytölle.

Älä siis katso tätä esimerkkinä siitä miten Java Swing -sovellus tehdään, miten layoutit ja ulkoasu toimii ja saadaan siistiksi. Keskity vain siihen miten hiiritapahtumia käsitellään, ja niiden perusteella saadaan jotain graafista aikaiseksi.

## Miten demo toimii?

Suorita ohjelma vaikkapa VS Codesta, ja ala piirtämään ikkunan valkoisella alueella, painamalla hiiren (vasen) painike alas, raahaamalla hiirtä ja nostamalla painike ylös.

Ohjelma vaihtaa satunnaisesti piirrettävää muotoa (viiva, ellipsi, laatikko), viivan paksuutta, väriä sekä täyttöväriä.

Esimerkiksi, voit piirtää sovelluksella tällaisia abstrakteja teoksia:

![Teoskynnys tuskin ylittyy](screenshot.png)

## Toteutuksen yksityiskohtia

Toteutuksessa olennaisia luokkia AWT:stä ovat mm.:

* `java.awt.Shape` -- rajapinta (`interface`) kaikenlaisille geometrisille piirrettäville muodoille (neliöt, ellipsit, jne)).
* `java.awt.Stroke` -- rajapinta jonka avulla voidaan määritellä miten muodon rajat piirretään (esim. `BasicStroke`).
* `java.awt.BasicStroke` -- piirrettävän viivan ominaisuuksien yksinkertainen määrittely voidaan tehdä tällä luokalla, on eräänlainen `Stroke`.
* `java.awt.Color` -- viivan, tekstien ja elementin täyttövärin määrittelyyn.
* `java.awt.Graphics2D` -- grafiikkakonteksti, jonka avulla piirrosoperaatiot varsinaisesti suoritetaan.
* `java.awt.geom.Point2D` -- kaksiulotteisen geometrian pisteen määrittely.
* `java.awt.geom.Rectangle2D` -- kaksiulotteisen geometrian nelikulmion määrittely.
* `java.awt.event.MouseEvent` -- hiiritapahtumien käsittelyyn, olion sisältö kertoo mitä ja missä tapahtui.
* `java.awt.event.MouseListener` -- rajapinta jonka hiiritapahtumien "kuuntelija" (paneeli) toteuttaa.
* `java.awt.event.MouseMotionListener` -- jos halutaan tietää myös hiiren liikkeistä paneelin alueella, kuunnellaan myös tämän rajapinnan kautta kyseisiä tapahtumia.

Toteutuksessa olennaisia luokkia Swing:stä ovat mm.:

* `javax.swing.JPanel` -- ikkunan sisälle voidaan sijoittaa vierekkäin tai sisäkkäin paneeleita, jotka kukin sisältävät käyttöliittymän elementtejä.
* `javax.swing.JFrame` -- ikkuna, jonka sisällä voi olla yksi tai useampi `JPanel`.

Muita demossa käytettyjä luokkia:

* `java.util.ArrayList` -- on `DrawingDocument`:n jäsenmuuttujana, pitää sisällään piirrosoliot (`DrawingShape`).
* `java.util.concurrent.ThreadLocalRandom` -- käytetään valitsemaan satunnaisesti mikä on piirrettävä muoto, käytetty viivan paksuus ja väri sekä muodon täyttöväri.

## Demon teki

* (c) Antti Juustila 2025
* Lehtori, INTERACT Research Group
* Oulun yliopisto