#Crashkurs
Das ist ein Dokument für einen Crashkurs wie man mit dem markdown in IntelliJ umgeht. Hier findet ihr alle nötigen Informationen, die benötigt werden um ein Dokument zu schreiben. 

======================================

# GANZ WICHTIG
**<span style="color:red">
Bitte schreibt in dem jeweiligen Dokument eure Dokumentation rein, damit wir das alles sortiert haben und nicht kreuz und quer.
Sonst könnt ihr mich gerne fragen, wenn ihr Fragen zum Markdown habt oder zur Dokumentation.  
Wenn ihr die Dokumentation geschrieben habt, dann fügt mich bitte als Reviewer hinzu.
</span>**

======================================

# Beispiel: Überschriften
Mit einem "#" könnt ihr Überschriften angeben. Je mehr "#" ihr habt, desto kleiner wird sie.
Beispiel: 

# Überschrift 1
## Überschrift 2
### Überschrift 3
#### Überschrift 4
##### Überschrift 5
###### Überschrift 6

Bitte beachte, dass es nur bis 6 von "#" geht und er automatisch Absätze einfügt.

======================================

# Textabschnitte
Texte können zwar in mehrere Zeilen geschrieben werden, aber ich würde sagen, dass wir ein Text - egal wie lang dieser ist - in eine Zeile schreiben.
Natürlich könnt ihr in einer zweiten Zeile weiter schreiben, allerdings weiß ich nicht, wie das später mit LaTeX bei der Konvertierung funktionieren wird.

======================================

# Absatz und Zeilenumbruch
### Absatz
Absätze könnt ihr ganz einfach erzeugen, in dem ihr eine Zeile frei lässt.

### Zeilenumbruch
Wenn ihr nur einen Zeilenumbruch machen wollt,  
dann macht hier das am Ende der Zeile mit zwei "Leerzeichen"

======================================

# Aufzählung
Es gibt zwei Arten von Aufzählungen - mit und ohne Nummerierung

### Mit Nummerierung
1. Test01
   1. Test01.1
   2. Test01.2
2. Test02
3. Test03

### Ohne Nummerierung
- Test01
  - Test01.1
  - Test01.2
- Test02
- Test03

======================================

# Schriftart Bold und Kursiv
### Bold
Text ohne bold  
**Text in bold**

======================================

# Trennlinie
In einer Zeile drei dieser folgenden Zeichen nutzen

---

***

___

======================================

# Link
Ihr könnt im Dokument auch Links erstellen, sei es auf eine Internetseite oder im Dokument selbst.
Das Wort, welches den Link haben soll, markiert ihr in [], dann im Klammern den Link oder Überschrift.

[Absatz](#Absatz) Beispiel mit Überschrift  
[Crashkurs](https://www.markdownguide.org/basic-syntax/#overview) Beispiel mit Link

======================================

# Bilder
Um Bilder in das Dokument hinzuzufügen, könnt ihr diesen Befehl nutzen.  
![Name des Bildes](Quelle des Bildes.png)
Beispiel
![Name of Pic](../src/main/java/de/uol/swp/client/main/diagrams/AccountOptionsDiagram.png)

Wichtig ist, dass ihr immer den Datentyp des Bildes mit angibt.

======================================

# Blockzitate
Um ein Blockzitat zu erstellen, fügt ihr ganz vorne ein ">" hinzu. Ihr könnt auch diese miteinander verschachteln
Beispiel  
> Zitat Anfang
> 
>> Zitat Verschachtelt
> 
> Zitat Ende

======================================

# Code zitieren
Ihr könnt auch ein Stück von eurem Code hier zitieren.
Dabei müsst ihr nur für den Anfang und Ende des Codezitates "```" hinzufügen.
Wichtig ist, dass ihr beim Anfang "java" dahinter schreibt, damit Markdown weiß,
dass es sich um Java handelt  
Beispiel

```java
public class kuchen{
    public void essen();
}
``` 

======================================

# Befehle markieren
Wenn ihr irgendwelche Befehle markieren wollt, wie zum Beispiel für das Terminal, dann könnt ihr das so machen:  
Beispiel:  

`git pull`

======================================

# Sonstiges
Wenn ihr weitere Informationen zum Markdown lesen wollt, dann ist hier der Link dazu:  
https://www.markdownguide.org/basic-syntax/#code

