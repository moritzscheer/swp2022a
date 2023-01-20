# BITTE BEACHTEN
**<span style="color:red">
Bitte schreibt in dem jeweiligen Dokument eure Dokumentation rein, damit wir das alles sortiert haben und nicht kreuz und quer.
Sonst könnt ihr mich gerne fragen, wenn ihr Fragen zum Markdown habt oder zur Dokumentation.
Wenn ihr die Dokumentation geschrieben habt, dann fügt Tommy bitte als Reviewer hinzu.
</span>**

**<span style="color:red">
In diesem Abschnitt sollen alle Dokumentationen bezüglich des Commons reins.
Das bedeutet die Exceptions, Lobby, Messages, den User und alles was zukünftig noch dazu kommen könnte.
</span>**

# Exceptions


# Lobby
19.01 Moritz Scheer

1. ### LobbyDTO
   2. Attribute  
   **lobbyID: Integer** ist der Identifier der Klasse  
   **name: String** ist der zugewiesene lobby Name (ist Singleplayer bei, wenn das multiplayer Attribut false ist)  
   **owner: User** ist der momentan zugewiesene Besitzer der Lobby  
   **users: Set<User>** beinhaltet alle Spieler in der Lobby  
   **password: String** ist das zugewiesene Password (ist null, wenn das multiplayer Attribut false ist)  
   **multiplayer: Boolean** ist true, wenn die Lobby öffentlich sein soll und false, wenn nicht
   **playerSlot: Integer** stellt die maximale Anzahl an Spielern fest
   3. Methoden  
   **createWithoutPassword():** verschleiert alle Passwörter im LobbyDTO Objekt. Zum einen die Passwörter der User und zum anderen das passwort der Lobby. Das ist notwendig für die Liste an Lobbies in der JoinOrCreateView, da dort die Passwörter für andere Nutzer nicht sichtbar sein sollen.  
   **createWithoutUserPassword():** verschleiert nur die User Passwörter. Dient zur allgemeinen Datenrückgabe vom Server an den Client.  
   **joinUser():** Methode um einen Spieler zu einer Lobby hinzuzufügen. Eine Exception wird geworfen, wenn der **playerSlot** erreicht wurde, das **passwort** falsch ist oder **multiplayer** false ist.  
   **leaveUser():** Methode um einen Spieler aus einer Lobby zu entfernen. Eine Exception wird geworfen, wenn nur ein Spieler in der Lobby ist. Falls der Besitzer der Lobby die Lobby verlässt, wird die **updateOwner()** Methode aufgerufen.  
   **updateOwner():** Methode um einen neuen Spieler als Besitzer einzutragen. Das ist wichtig, falls der bisherige Besitzer die Lobby verlässt.  

2. ### Exception  
    Lobby Exception Responses werden an den Client zurückgeschickt, falls die Operation auf dem Server fehlgeschlagen hat oder die Verbindung unterbrochen wurde
3. ### Requests  
    Lobby Requests werden an den Server geschickt, wenn eine bestimmte Operation ausgeführt werden soll auf dem Server
4. ### Messages  
    Lobby Messages werden nur an den Client geschickt, wenn das multiplayer Attribut true ist.
5. ### Response  
    Lobby Responses werden an den Client zurückgeschickt, falls die Operation auf dem Server erfolgreich waren
# Messages


# User
