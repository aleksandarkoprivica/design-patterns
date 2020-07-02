# Example Screenshot
![alt text](src/screenshot/screenshot.png)

# Opis zahteva za projekat iz predmeta Dizajnerski zadaci

Korišćenjem Java programskog jezika implementirati desktop aplikaciju za rad sa 2D grafikom. Aplikacija
mora podržavati funkcionalnosti koje su rađene u projektnom zadatku na predmetu Objektno orijentisane
informacione tehnologije.

Izmene/Dodatne funkcionalnosti:

1. nazivi klasa, metoda i promenljivih moraju biti na engleskom jeziku,
2. aplikacija mora biti realizovan u skladu sa MVC obrascem,
3. dodavanje, brisanje i modifikacija š estougla (hexagon) koristeći adapter obrazac za hexagon.jar,
4. poništavanje izvršenih komandi (undo funkcionalnost) – Command obrazac,
    ponovno izvršenje poništenih komandi (redo funkcionalnost) – Command obrazac,
5. generisanje i prikaz loga izvršenih komandi u okviru aplikacije ,
6. zapis u tekstualnu datoteku loga izvršenih komandi na eksterni memorijski medijum,
    zapis kompletnog crteža (Serialization) na eksterni memorijski medijum, - Strategy obrazac,
7. učitavanje tekstualne datoteke koja sadrži log izvršenih komandi i na osnovu sadržaja, kreiranje
    odgovarajućeg crteža, komandu po komandu,
    učitavanje kompletnog crteža,
8. promenu pozicije oblika po Z osi, ToFront (pozicija po pozicija), ToBack (pozicija po pozicija),
    BringToFront (na najvišu poziciju), BringToBack (na najnižu poziciju),
9. prikazati trenutno aktivne boje za crtanje ivice i popunjavanje oblika, klikom na boje otvara se
    dijalog sa mogućnošću promene trenutno aktivne boje,
10. omogućiti selekciju više oblika,
11. JButton za brisanje treba da bude dostupan samo u slučaju da postoje selektovani objekti – Observer
    obrazac,
12. JButton za modifikaciju treba da bude dostupan samo u slučaju kada je selektovan samo jedan oblik - Observer obrazac.
