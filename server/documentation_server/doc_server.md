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

**<span style="color:DodgerBlue">
**Verwendete Klassen und Interfaces**
</span>**

Das GearBehaviour verwendet folgende Klassen und Interfaces:

    Robot: ist ein Klasse, die den Zustand und das Verhalten eines Roboters repräsentiert
    Block: ist eine Klasse, die ein Objekt eines Behaviours beinhaltet / aufruft und zurückgibt
    Position: ist eine Klasse, die die Position eines Objekts auf dem Spielbrett darstellt
    MoveIntent: ist eine Klasse, die einem Roboter eine einmalige sowie eindeutige ID zuweist
    und die Reihenfolge des Spielablaufs koordiniert
    CardinalDirection: ist eine Enumeration, die festgelegte Variablen, in diesem Fall die vier
    Himmelsrichtungen aufzählt / vorgibt

**<span style="color:DodgerBlue">
**Vererbungshierarchie**
</span>**

Das GearBehaviour erbt ihre Methode ```OnRotatorStage``` von der abstrakten Oberklasse AbstractTileBehaviour
und besitzt ein weiteres Attribut ```turnClockwise```, welches die Rotationsausrichtung des Zahnrads bestimmt.

**<span style="color:DodgerBlue">
**Konstruktor**
</span>**

Der Konstruktor des GearBehaviours ist folgendermaßen aufgebaut:

    Robot[] robotStates: ist ein Array von Roboterzuständen
    Block[][] board: ist ein zweidimensionales Array von Blöcken
    Position blockPos: ist eine Position des Zahnrads auf dem Spielbrett
    boolean turnC: ist ein Operator, der die Rotationsausrichtung bei "true" mit
    dem Uhrzeigersinn und bei "false", gegen den Uhrzeigersinn bestimmt

Der Konstruktor ruft wie alle anderen Behaviours den Konstruktor der Oberklasse AbstractTileBehaviour auf.

**<span style="color:DodgerBlue">
**Methode**
</span>**

Die Methode ```OnRotatorStage``` besitzt einen Parameter namens ```int prgramStep``` und
gibt entweder eine Liste von ```List<MoveIntent>```, oder den Wert ```Null``` zurück.
Innerhalb der Methode werden alle Roboter, die in der Liste ```Robot[] robotStates``` sich befinden
iteriert. Falls ein Roboter dieselbe Position wie ```Position blockPos``` des GearBehaviours besitzt,
wird der Roboter bei einem positiven "turnC-Wert" um 180° mit dem Uhrzeigersinn gedreht. Fällt der "turnC-Wert" auf "false", 
wird eine Ausrichtung gegen den Uhrzeigersinn vorgenommen.



## LaserBehaviour

**<span style="color:DodgerBlue">
**Verwendete Klassen und Interfaces**
</span>**

Das LaserBehaviour verwendet diese Klassen und Interfaces:

    Robot: ist ein Klasse, die den Zustand und das Verhalten eines Roboters repräsentiert
    Block: ist eine Klasse, die ein Objekt eines Behaviours beinhaltet / aufruft und zurückgibt
    Position: ist eine Klasse, die die Position eines Objekts auf dem Spielbrett darstellt
    MoveIntent: ist eine Klasse, die einem Roboter eine einmalige sowie eindeutige ID zuweist
    und die Reihenfolge des Spielablaufs koordiniert
    CardinalDirection: ist eine Enumeration, die festgelegte Variablen, in diesem Fall die vier
    Himmelsrichtungen aufzählt / vorgibt

**<span style="color:DodgerBlue">
**Vererbungshierarchie**
</span>**

Das LaserBehaviour erbt ihre Methode ```onLaserStage``` von der abstrakten Oberklasse AbstractTileBehaviour.
Sie besitzt drei weitere Attribute: ```private CardinalDirection direction```, ```private int[] activeInProgramSteps```
und```private int laserBeam```.

**<span style="color:DodgerBlue">
**Konstruktor**
</span>**

Der Konstruktor ist folgendermaßen aufgebaut:

    Robot[] robotStates: ist ein Array von Roboterzuständen
    Block[][] board: ist ein zweidimensionales Array von Blöcken
    Position blockPos: ist eine Position des Lasers auf dem Spielbrett
    int[] activeInProgramSteps: ist ein Array von Programmschritten in denen
    der Laser aktiv ist
    CardinalDirection laserDir: ist die Ausrichtung des Lasers
    int laserBeam: ist die Anzahl an Laser, die sich auf dem Block befinden

Der Konstruktor ruft wie alle anderen Behaviours den Konstruktor der Oberklasse AbstractTileBehaviour auf.

**<span style="color:DodgerBlue">
**Methoden**
</span>**

Die Methode ```onLaserStage``` wird aktiviert, wenn ein Roboter sich auf dem Laserblock befindet. Hierbei ist zu beachten, dass der Parameter ```int programStep```, zum Abgleich dient.
Falls der gesuchte Programmschritt eintritt und sich der Roboter auf dem Feld befindet, erhält dieser einen Schaden in Höhe der Laseranzahl.

Mit der Methode ```getLaserDirection``` wird die Ausrichtung der Laser ermittelt.
Die Methode ```getLaserBeam``` dient zur Ermittlung der Laseranzahl.


## PitBehaviour


**<span style="color:DodgerBlue">
**Verwendete Klassen und Interfaces**
</span>**

Das PitBehaviour verwendet folgende Klassen und Interfaces:

    Robot: ist ein Klasse, die den Zustand und das Verhalten eines Roboters repräsentiert
    Block: ist eine Klasse, die ein Objekt eines Behaviours beinhaltet / aufruft und zurückgibt
    Position: ist eine Klasse, die die Position eines Objekts auf dem Spielbrett darstellt
    MoveIntent: ist eine Klasse, die einem Roboter eine einmalige sowie eindeutige ID zuweist
    und die Reihenfolge des Spielablaufs koordiniert
    List: ist eine Schnittstelle, die eine geordnete Sammlung von Objekten enthält
    Objects: ist eine Hilfsklasse, die nützliche Methoden zum Vergleichen von Objekten bereitstellt.

**<span style="color:DodgerBlue">
**Vererbungshierarchie**
</span>**

Das PitBehaviour erbt ihre Methode ```onRobotEntered``` von der abstrakten Oberklasse AbstractTileBehaviour.
Sie besitzt keine weiteren Attribute.

**<span style="color:DodgerBlue">
**Konstruktor**
</span>**

Der Konstruktor ist folgendermaßen aufgebaut:

    Robot[] robotStates: ist ein Array von Roboterzuständen
    Block[][] board: ist ein zweidimensionales Array von Blöcken
    Position blockPos: ist eine Position der Grube auf dem Spielbrett

Der Konstruktor ruft wie alle anderen Behaviours den Konstruktor der Oberklasse AbstractTileBehaviour auf.

**<span style="color:DodgerBlue">
**Methode**
</span>**

Das PitBehaviour besitzt eine öffentliche Methode namens ```onRobotEntered (int indexMoveRobot)```, diese wird ausgelöst, sobald ein Roboter sich auf dem Feld / Block des PitBehaviours befindet. Hierbei wird die Position des Roboters mit dem übergebenden Parameter der Methode verglichen. Falls eine Übereinstimmung eintrifft, werden die Lebenspunkte des Roboters auf Null gesetzt. Abschließend gibt die Methode den Wert null zurück, da keine MoveIntent-Objekte zurückgegeben werden müssen.

## PressorBehaviour

**<span style="color:DodgerBlue">
**Verwendete Klassen und Interfaces**
</span>**

Das PressorBehaviour verwendet folgende Klassen und Interfaces:

    Robot: ist ein Klasse, die den Zustand und das Verhalten eines Roboters repräsentiert
    Block: ist eine Klasse, die ein Objekt eines Behaviours beinhaltet / aufruft und zurückgibt
    Position: ist eine Klasse, die die Position eines Objekts auf dem Spielbrett darstellt
    List: ist eine Schnittstelle, die eine geordnete Sammlung von Objekten enthält
    Objects: ist eine Hilfsklasse, die nützliche Methoden zum Vergleichen von Objekten bereitstellt.

**<span style="color:DodgerBlue">
**Vererbungshierarchie**
</span>**

Die Klasse erbt ihre Methode ```onPressorStage``` von der abstrakten Oberklasse AbstractTileBehaviour.
Sie besitzt ein Attribut namens ```private int[] activeInProgramSteps```.

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

Die Klasse hat eine öffentliche Methode ```onPressorStage(int programStep)```, die eine Liste von MoveIntent-Objekten zurückgibt und einen Parameter programStep akzeptiert. Die Methode wird überschrieben und implementiert das Verhalten des PressorBehaviours, wenn ein Roboter sich unter dem Pressor befindet.


Die Methode nimmt den Parameter programStep an, der den aktuellen Programmschritt darstellt und prüft, ob diese Stufe im Array activeInProgramSteps enthalten ist. Falls das der Fall ist, wird eine weitere Bedingung überprüft. Befindet sich ein Roboter auf dem Pressor-Block, so wird der Roboterzustand geändert, die Lebenspunkte werden auf Null gesetzt. Abschließend gibt die Methode den Wert null zurück, da keine MoveIntent-Objekte zurückgegeben werden müssen.

## PusherBehaviour
Der PusherBehaviour erstellt einen neuen MoveIntent und fügt diese in eine MoveIntent-Liste hinzu. Sobald es für alle Roboter dies einmal überprüft wurde, gibt der PusherBehaviour die gesamte Liste wieder zurück. Der PusherBehaviour schiebt den Roboter in die jeweilige Richtung um einen Tile.

## RepairBehaviour

**<span style="color:DodgerBlue">
**Verwendete Klassen und Interfaces**
</span>**

Das RepairBehaviour verwendet folgende Klassen und Interfaces:

    Robot: ist ein Klasse, die den Zustand und das Verhalten eines Roboters repräsentiert
    Block: ist eine Klasse, die ein Objekt eines Behaviours beinhaltet / aufruft und zurückgibt
    Position: ist eine Klasse, die die Position eines Objekts auf dem Spielbrett darstellt

**<span style="color:DodgerBlue">
**Vererbungshierarchie**
</span>**

Die Klasse erbt ihre Methode ```onCardEnding``` von der abstrakten Oberklasse AbstractTileBehaviour.

**<span style="color:DodgerBlue">
**Konstruktor**
</span>**

Der Konstruktor der Klasse ist folgendermaßen aufgebaut:

    Robot[] robotStates: ist ein Array von Roboterzuständen
    Block[][] board: ist ein zweidimensionales Array von Blöcken
    Position blockPos: ist eine Position des Pressor-Blocks auf dem Spielbrett
    int repairSiteKey: ist ein Paramter, der die Unterscheidung der Reperaturstation ermöglicht

Der Konstruktor ruft den Konstruktor der Oberklasse AbstractTileBehaviour auf.

**<span style="color:DodgerBlue">
**Methoden**
</span>**

Das RepairBehaviour hat eine Methode namens ```getCheckPointPosition```, welche als Rückgabewert die Position der Reparaturstation zurückgibt.
Des Weiteren besitzt die Klasse eine öffentliche Methode ```onCardEnding```, die eine Liste von MoveIntent als Rückgabewert zurückgibt und als Parameter einen Integer-Wert "programmStep" nutzt.
Sobald der vierte Programmschritt ausgeführt wurde und ein Roboter sich auf dem RepairBehaviour befindet, wird die Methode "onCardEnding" ausgeführt und die Schadenpunkte um einen oder zwei Marken / Werte reduziert.

(Weitere Methode muss no
## WallBehaviour
