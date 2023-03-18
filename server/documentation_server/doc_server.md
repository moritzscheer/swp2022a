# BITTE BEACHTEN
**<span style="color:red">
Bitte schreibt in dem jeweiligen Dokument eure Dokumentation rein, damit wir das alles sortiert haben und nicht kreuz und quer.
Sonst könnt ihr mich gerne fragen, wenn ihr Fragen zum Markdown habt oder zur Dokumentation.
Wenn ihr die Dokumentation geschrieben habt, dann fügt Tommy bitte als Reviewer hinzu.
</span>**

**<span style="color:red">
In diesem Abschnitt sollen alle Dokumentationen bezüglich des Servers reins.
Das bedeutet die Communication, Lobby, Messages, den Usermanagement, ServerApp und alles was zukünftig noch dazu kommen könnte.
</span>**

# Communication


# Lobby


# Messages


# Usermanagement


# ServerApp


# Tiles


# TilesBehaviour


## AbstractTileBehaviour


## CheckPointBehaviour
Der CheckPointBehaviour funktioniert so, dass überprüft wird, ob sich ein Roboter auf dem jeweiligen CheckPoint Tile befindet. Wenn dies der Fall ist, dann wird im Anschluss überprüft, ob der letzte CheckPointNummer größer ist als das neue CheckPointNummer. Ist dies der Fall, wird der letzte CheckPoint mit dem neuen CheckPoint sowie die Position übernommen. So kann der Roboter an dieser Stelle wieder spawnen, wenn vorher zerstört wurde.

## ConveyorBeltBehaviour


## GearBehaviour


## LaserBehaviour


## PitBehaviour


## PressorBehaviour

**<span style="color:DodgerBlue">
**Verwendete Klassen und Interfaces**
</span>**

Die Klasse verwendet folgende Klassen und Interfaces:

    Robot: ist ein Klasse, die den Zustand und das Verhalten eines Roboters repräsentiert
    Block: ist eine Klasse, die ein Objekt eines Behaviours beinhaltet / aufruft und zurückgibt
    Position: ist eine Klasse, die die Position eines Objekts auf dem Spielbrett darstellt
    List: ist eine Schnittstelle, die eine geordnete Sammlung von Objekten enthält
    Objects: ist eine Hilfsklasse, die nützliche Methoden zum Vergleichen von Objekten bereitstellt.

**<span style="color:DodgerBlue">
**Vererbungshierarchie**
</span>**

Die Klasse erbt ihre Methode ```java OnPressorStage``` von der abstrakten Oberklasse AbstractTileBehaviour.
Sie besitzt ein Attribut namens ```java private int[] activeInProgramSteps```.

**<span style="color:DodgerBlue">
**Konstruktor**
</span>**

Die Klasse hat einen Konstruktor, welcher folgende Parameter benötigt:

    Robot[] robotStates: ist ein Array von Roboterzuständen
    Block[][] board: ist ein zweidimensionales Array von Blöcken
    int[] activeInProgramSteps: ist ein Array von Programm-Schritten,
    in denen der Pressor aktiviert wird
    Position blockPos: ist eine Position des Pressor-Blocks auf dem Spielbrett

Der Konstruktor ruft den Konstruktor der Oberklasse AbstractTileBehaviour auf.

**<span style="color:DodgerBlue">
**Methode**
</span>**

Die Klasse erbt ihre Methode ```java OnPressorStage``` von der abstrakten Oberklasse AbstractTileBehaviour.
Sie besitzt ein Attribute names ```java private int[] activeInProgramSteps```.


Die Klasse hat eine öffentliche Methode ```java OnPressorStage(int programStep)```, die eine Liste von MoveIntent-Objekten zurückgibt und einen Parameter programStep akzeptiert. Die Methode wird überschrieben und implementiert das Verhalten des Pressors, wenn ein Roboter sich unter dem Pressor befindet.


Die Methode nimmt den Parameter programStep an, der den aktuellen Programmschritt darstellt und prüft, ob diese Stufe im Array activeInProgramSteps enthalten ist. Falls das der Fall ist, wird eine weitere Bedingung überprüft. Befindet sich ein Roboter auf dem Pressor-Block, so wird der Roboterzustand geändert, die Lebenspunkte werden auf Null gesetzt. Abschließend git die Methode den Wert null zurück, da keine MoveIntent-Objekte zurückgegeben werden müssen.

## PusherBehaviour
Der PusherBehaviour erstellt einen neuen MoveIntent und fügt diese in eine MoveIntent-Liste hinzu. Sobald es für alle Roboter dies einmal überprüft wurde, gibt der PusherBehaviour die gesamte Liste wieder zurück. Der PusherBehaviour schiebt den Roboter in die jeweilige Richtung um einen Tile.

## RepairBehaviour


## WallBehaviour
