# 1. Jelenlegi helyzet
A póker az egyik legnépszerűbb kártyajáték a világon. Minden esetben a játék lényege, hogy a játékosok a  kártyáikból a legjobbat 
kihozva elvigyék az asztal közepén lévő kasszát. Ezt a legjobb lap kombinációval tehetik meg, tehát minden játékosnak 
arra kell törekednie, hogy a saját kezében vagy a saját kézben tartott és a lenti kártyák összessége legyen a legjobb.

A Texas Hold'Em a közössági pókerjátékok legismertebb változata, amin 2-10 játékos vesz részt. A játékot 52 lapos francia
kártyával játszák a joker lapok nélkül. 

Ahogy sok minden más kártya játékot úgy ezt is utól érte a technológia, így már 
ez a kártya játék is játszható online környezetben vagy épp mobilos környezetben is. Aki rendelkezik okos eszközzel annak 
egy pár kattintás és már élvezheti is a játékot, viszont ezek tele vannak reklámmal, amiknek java része nem is kapcsolódik 
a témahoz ezzel sok embert zavarva, illetve mikrotranzakcióval amivel arra az útra probálják terelni a játékost, hogy zsetont
vegyen, vagy olyan dolgokra költsön amiket a játékmenet előrehaladásával amúgy is megszerezne.
# 2. Vágyálom rendszezr
Az általunk megalkotott póker játék bárki számra elérhető lesz aki rendelkezik Adndroid operációs rendszerrel rendelkező
készülékkel. Biztosítjuk a játszani vágyó szemálynek hogy 100%-ban reklámmentes játékélményben részesül majd, illtve azt is,
hogy nincs semmi szüksége valódi pénzt belefektésnek a játékba mivel, minden mérföldkő és jutalom megszerezhető lesz e nélkül.

A játékot egy személy fogja tudni játszani egy számítógép ellen. A játék kezdésekor mindketten ugyan annyi számú
zsetonnal kezdenek, a végén  pedig az nyer akinek sikerül elnyernie ellenfele összes zsetonját. A játékmenet során kártya 
skineket lehet feloldani. Ezek külömböző kártya kombinációk(pl. két pár) elérésekot fognak megnyílni majd számára.  
# 3. Jelenlegi üzleti folyamatok

4.1 A játékos kiosztja a kártyákat.

4.2 Játékszabály szerint kisvagy és nagyvak eldöntése.

4.3 Négy Licit kör kijátszása:

4.3.1 A játékos tetszés szerint emel, tart vagy dob.

4.3.2 Pre-flop, Flop, Turn, River kártyaosztás.

4.4 Showdown, a játékosok lapjainak mutatása.  A játékos nyer vagy veszít.

4.5 A játékos felírja az eredményt magának.

4.6 A játékos összegyűji és újrakeveri a kártyát.

4.7 A játékos újrakezdi a folyamatot előrehaladási élmény nélkül.
# 4. Igényelt üzleti folyamatok
- Beállítások => Hang, Pot méret, Nehézség, Adattörlés
- Pakli => Jutalom kártyák megjelenítése => Jutalom kártyák aktiválása
- Játék => Játék folyamat => Győzelem esetén jutalom


<br/><img src="res/folyamat.png" width="500"><br/>
# 5. Rendszerre vonatkozó szabályok:
- A szoftver megvalósítása Java programozási nyelvben kell hogy történjen.
- Az UI megvalósításához xml-t kell használni.
- Mentési fájl JSON formátumban.
- wav kiterjesztésű hangfájlok kezelése.
- Gradle fejlesztői projektautomatizációs eszköz használata.
- Képfájlok jpg/png formátumúak legyenek.
# 6. Követelménylista
- K01 A játék kövesse a hivatalos nemzetközi  Texas Hold'Em játékszabályokat.
- K02 A játéknak jutalmaznia kell a győzelmet és jeleznie az előrehaladást.
- K03 A program legalább az android rendszerek 95%-án működjön.
- K04 A szoftver megvalósítása Java programozási nyelvben kell hogy történjen.
- K05 Az UI megvalósításához xml-t kell használni.
- K06 A mentéseket titkosítva kell lokálisan tárolni.
- K07 Képfájlok jpg/png formátumúak legyenek.
- K08 A hangfájlokat wav kiterjesztéssel kell kezeléni.
# 7. Fogalomszótár
- mikrotranzakció: játékon belüli valós pénzért történő vásárlás
- zseton: kaszinókban használt érme
- skin: Kinézet, textúra 
- kártyakombinációk:
  - Rojálflös (royal flush) : Ez a legjobb az összes pókerlap közül, a royal flös egy olyan színsor, amely tízest, bubit, dámát, királyt és ászt tartalmaz.
  - Színsor (straight flush): Egy sor (lejjebb definiált), amelyben az összes kártya azonos színű.
  - Póker (four of a kind): Négy ugyanolyan értéku kártya, például négy dáma.
  - Full (full house): Egy pár és egy drill kombinációja.
  - Flös (flush): Öt ugyanolyan színu kártya, például öt kör.
  - Sor (straight): Öt egymást követo értéku kártya, az ászt alacsony és magas kártyának is lehet használni.
  - Drill (Three of a kind): Három azonos értéku kártya, például három dáma.
  - Két pár: Kétszer két ugyanolyan értéku kártya, például két hármas és két kilences.
  - Pár: Bármilyen két azonos értéku kártya, például két kilences. A párok rangsora tükrözi a kártyák rangsorát, tehát a legerosebb pár az ász pár, a leggyengébb a kettes pár.
  - Magas kártya: A legmagasabb értéku kártya az ász, a legalacsonyabb a kettes.
