# Drawing App Demo

> Piirrossovellus on tarkoitettu TOL:n Ohjelmointi 4 -kurssin demoksi yksinkertaisen grafiikan piirtämiseen, hiiritapahtumien käsittelyyn ja yksinkertaisen sovellusarkkitehtuurin rakentamiseen.

Tässä demossa esitellään lyhyesti ja askeettisesti miten Javalla Swingissä:

1. view -luokassa (`DrawingPanel`) käsitellään hiiritapahtumia (mouse down, mouse up, mouse drag),
2. view -luokka kertoo hiiritapahtumista  controller -oliolle (`DrawingDocument`), jossa luodaan hiiritapahtumien perustella piirros-olioita (`DrawingShape`) jotka jemmataan dokumenttiolion sisältämään listaan (model), sekä
3. päivitetään view -luokassa tilanne hakemalla dokumentista piirrosolioita piirrettäväksi näytölle.
4. toinen view -luokka (`ListPanel`) näyttää piirroselementtien tiedot yksinkertaisella listalla. Listan avulla voidaan valita piirroselementti sekä poistaa niitä.
5. kolmas view -luokka (`Statuspanel`) näyttää montako erilaista piirroselementtiä dokumentissa tällä hetkellä on.

Sovellus noudattaa siis [Model-View-Controller](https://fi.wikipedia.org/wiki/MVC-arkkitehtuuri) -arkkitehtuuria, jossa:

* **Model** eli malli on sovelluksen piirroselementit (`DrawingShape`) listalla (`List`),
* **Controller**, joka kontrolloi sovelluksen logiikkaa, on `DrawingDocument` -luokka, sekä
* useampia näkymiä (**View**) jotka näyttävät controllerin avulla mallin tilaa eri tavoin näytöllä:
  * `DrawingPanel` näyttää piirroselementit graafisina elementteinä, ja
  * `ListPanel` näyttää piirroselementit tekstinä listalla,
  * `StatusPanel` näyttää "tilastoja" dokumentista, sekä
  * joista kaksi ensin mainittua tarjoavat käyttäjälle tapoja muuttaa controllerin avulla mallin tilaa (lisätä ja poistaa piirroselementtejä).

`DrawingDocument`ja näkymät toteuttavat myös [Observer](https://en.wikipedia.org/wiki/Observer_pattern) -suunnittelumallin (*design pattern*). Dokumentti ilmoittaa muutoksista tilassaan tarkkailijoilleen (näkymät). Näkymät taas tarjoavat käyttäjälle tapoja muuttaa mallin tilaa, siten että muutkin näkymät saavat ilmoituksia mallin tilamuutoksista.

## Miten demo toimii?

Suorita ohjelma vaikkapa VS Codesta, ja ala piirtämään ikkunan **vasemmanpuoleisella** valkoisella alueella, painamalla hiiren (vasen) painike alas, raahaamalla hiirtä ja nostamalla painike ylös. **Oikeanpuoleiselle** listalle ilmestyy kuvaus piirretyistä elementeistä.

Ohjelma vaihtaa satunnaisesti piirrettävää muotoa (viiva, ellipsi, laatikko), viivan paksuutta, väriä sekä täyttöväriä.

Listalta voit valita (hiirellä ja nuoli ylös/alas -näppäimillä) mikä piirroselementti on aktiivinen. Aktiivinen elementti piirretään vasemmalla puolella ns. *tartuntakahvojen* kanssa. 

> *Jos* ohjelma tukisi tartuntakahvoihin tarttumista ja siten elementin muokkaamista, piirrettyjä elementtejä voitaisiin siirtää ja muokata. Tällaista ominaisuutta ohjelmassa ei nyt ainakaan vielä ole.

Voit poistaa listalta valitun piirroselementin `ctrl-backspace` -näppäimistöoikotiellä. Koska molemmat näkymät näyttävät saman modelin sisällön, myös vasen näkymä päivittyy kun piirroselementti poistetaan, observer -suunnittelumallin ansiosta. 

Voit piirtää sovelluksella tällaisia abstrakteja teoksia:

![Teoskynnys tuskin ylittyy](screenshot.png)

Vasemmalla piirrosalue jolla hiiritapahtumat käsitellään. Oikealla lista, jolla voit valita (hiirellä tai nuolinäppäimillä) aktiivisen piirroselementin ja poistaa sen `ctrl-backspace` -näppäinoikotiellä. Listalla valittu piirtoelementti (vihreäreunainen oranssilla värillä täytetty nelikulmio) on piirrosalueella korostettu nurkistaan mustilla pienillä neliöillä, ns. tartuntakahvoilla. Statusnäkymä alhaalla näyttää tilannetietoa piirrosdokumentista, päivittäen tietoja sitä mukaa kun piirroselementtejä luodaan ja/tai poistetaan.

## Toteutuksen yksityiskohtia

Tässä karkea luokkamalli sovelluksen arkkitehtuurista:

![UML-luokkamalli](classes.png)

Toteutuksessa käytettyjä olennaisia luokkia AWT:stä ovat mm.:

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
* `java.awt.event.InputEvent/KeyEvent/KeyListener` -- näppäimistotapahtumien käsittely listanäkymässä;

Toteutuksessa käytettyjä olennaisia luokkia Swing:stä ovat mm.:

* `javax.swing.JPanel` -- ikkunan sisälle voidaan sijoittaa vierekkäin tai sisäkkäin paneeleita, jotka kukin sisältävät käyttöliittymän elementtejä.
* `javax.swing.JFrame` -- ikkuna, jonka sisällä voi olla yksi tai useampi `JPanel`.
* `javax.swing.JList` -- yksinkertaisen listan toteuttaminen käyttöliittymään.
* `javax.swing.JScrollPane` -- listat sisältävät usein paljon elementtejä, joten ne kannattaa saman tien tehdä skrollattavaksi (vieritettäväksi) tämän luokan avulla.
* `javax.swing.AbstractListModel` -- listan varsinainen tietosisältö kannattaa erottaa erikseen käyttöliittymästä (panel, lista itse) vaikkapa tällaiseen list model -olioon. Tämän lisäksi on muitakin keinoja, mutta tällä se ainakin suhteellisen helposti onnistuu. Kuten sana "model" tässä kertoo, tässäkin on model-view -tyyppinen rakenne. Listan sisältö on model:ssa, ja itse lista on se view.
* `javax.swing.ListSelectionModel/ListDataEvent/ListDataListener/ListSelectionEvent/ListSelectionListener` -- Swingin listojen (`JList`) datan muutoksiin liittyvät rajapinnat ja tapahtumaoliot auttavat listojen toiminnallisuuden toteuttamisessa.

Muita demossa käytettyjä luokkia:

* `java.util.ArrayList` -- on `DrawingDocument`:n jäsenmuuttujana, pitää sisällään piirrosoliot (`DrawingShape`).
* `java.util.concurrent.ThreadLocalRandom` -- käytetään valitsemaan satunnaisesti mikä on piirrettävä muoto, käytetty viivan paksuus ja väri sekä muodon täyttöväri.

## Demon teki

* (c) Antti Juustila 2025
* Lehtori, INTERACT Research Group
* Oulun yliopisto