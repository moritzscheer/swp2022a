# BITTE BEACHTEN
**<span style="color:red">
Bitte schreibt in dem jeweiligen Dokument eure Dokumentation rein, damit wir das alles sortiert haben und nicht kreuz und quer.
Sonst könnt ihr mich gerne fragen, wenn ihr Fragen zum Markdown habt oder zur Dokumentation.
Wenn ihr die Dokumentation geschrieben habt, dann fügt Tommy bitte als Reviewer hinzu.
</span>**

**<span style="color:red">
In diesem Abschnitt sollen alle Dokumentationen bezüglich des Servers reins.
Das bedeutet die Communication, Lobby, Messages, den UserManagement, ServerApp und alles was zukünftig noch dazu kommen könnte.
</span>**

# Communication


# Lobby
19.01 Moritz Scheer

### LobbyService

Im LobbyService werden alle Requests aufgefangen und dementsprechend behandelt, ob die ausführung erfolgreich war oder nicht. Zuerst wird im LobbyManagement eine Methode aufgerufen, um die gewünschte Aktion auszuführen. Wenn eine Exception geworfen wurde beim Aufrufen der Methode, dann wird eine ExceptionResponse zurückgeschickt. Falls der Methodenaufruf erfolgreich war, wird eine SuccessfulResponse zurückgeschickt und falls das multiplayer Attribut true ist auch eine Message abgeschickt  

```java
public class LobbyService {
    @Subscribe
    public void onCreateLobbyRequest(CreateLobbyRequest createLobbyRequest) { }
}
```
Wenn eine Lobby erstellt werden soll, wird erst die createLobby() Methode im LobbyManagement aufgerufen, welche die LobbyID zurückgibt. Wenn keine Exception geworfen wurde, dann wird eine LobbyCreatedSuccessfulResponse zurückgeschickt und eine UserCreatedLobbyMessage, falls das lobby Attribut multiplayer true ist. Wenn schon wird eine LobbyCreateException zurückgeschickt.
```java
public class LobbyService {
    @Subscribe
    public void onLobbyJoinUserRequest(LobbyJoinUserRequest lobbyJoinUserRequest) { }
}
```
Wenn einer bestehenden Lobby beigetreten werden soll, dann wird zuerst die Lobby aus der HashMap geholt mithilfe der getLobby() Methode. Wenn die Lobby vorhanden ist, dann wird zuerst die joinUser() Methode im LobbyDTO aufgerufen und dementsprechend eine Response und Message zurückgeschickt anhand der oben beschriebenen Prozedur. Falls nicht, wird eine ExceptionResponse abgeschickt.
```java
public class LobbyService {
    @Subscribe
    public void onLobbyLeaveUserRequest(LeaveLobbyRequest leaveLobbyRequest) { }
}
```
Wenn eine Lobby verlassen werden soll, dann wird zuerst die Lobby aus der HashMap geholt mithilfe der getLobby() Methode. Wenn die Lobby vorhanden ist, dann wird zuerst die leaveUser() Methode im LobbyDTO aufgerufen. Wenn nur ein Spieler in der Lobby ist, dann wird eine Exception geworfen und es wird automatisch die Lobby aus der Hashmap gelöscht. Wenn keine Exception geworfen wurde, dann wird die Lobby verlassen. Falls die Lobby nicht vorhanden ist, dann wird eine ExceptionResponse abgeschickt.

### LobbyManagement

Im LobbyManagement werden alle Lobbies in einer HashMap in der Virtual Machine gespeichert. Wenn eine Request an dem Server ankommt, um eine Lobby zu erstellen, wird ein neuer Integer Wert generiert und zusammen mit einem neuen LobbyDTO Objekt in die HashMap hineingelegt. Der Integer Wert wird dabei als Schlüssel klassifiziert und das LobbyDTO Objekt als Wert. Wenn eine Request an dem Server geschickt wird um eine Lobby zu schließen, wird die der Eintrag mit dem Schlüssel aus der HashMap gelöscht. Ebenso gibt es eine getLobby() Methoden, die nach einer Lobby suchen mit der jeweiligen lobbyID und zwei Methoden die eine Liste an Lobbies zurückgibt. Zum einen die getLobbies() Methode, welche alle gespeicherten Lobbies zurückgibt und die getMultiplayerLobbies() Methode, die nur die Lobbies zurückgibt, bei dem das boolesche Attribut multiplayer auf true gesetzt wurde.


# Messages


# UserManagement


# ServerApp
