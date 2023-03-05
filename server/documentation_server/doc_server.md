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


## PusherBehaviour
Der PusherBehaviour erstellt einen neuen MoveIntent und fügt diese in eine MoveIntent-Liste hinzu. Sobald es für alle Roboter dies einmal überprüft wurde, gibt der PusherBehaviour die gesamte Liste wieder zurück. Der PusherBehaviour schiebt den Roboter in die jeweilige Richtung um einen Tile.

## RepairBehaviour


## WallBehaviour

