# FuguedState

*Disclaimer: this is by no means done! still working on it... but still shippable in some form.*

## jFugue
jFugue ([jfugue.org](https://jfugue.org)) is an awesome music library for java that uses an ingenious way of playing music, called the MusicString.

This player generates a keyboard (with 20 keys, but you can change it...) and generates a .txt file and .midi file once you're done. Just click exit

### Example

```java
Player player = new Player(); // creates a new player
Pattern spring = new Pattern("T[Allegro]"); // tempo set to allegro
// this is in music string!
spring.add(
		"V0 C5q | E5q+C5q E5q+C5q E5q+C5q D5i C5i | Eh.+Gh. G5i F5i | E5q+C5q E5q+C5q E5q+C5q D5i C5i | Eh.+Gh. G5i | F5i | E5q G5q F5q E5q | D5h"); // right hand
spring.add("V1 Rq | C4h C4h | C4h C4h | C4h C4h | C4h C4h | C4h C4h | R "); // left hand
player.play(spring); // play both!
```

## Todo
- [ ] Get note length differences working
- [ ] Allow for chords
- [ ] Allow for two hands 
- [ ] Smoother interface/keyboard input
- [ ] Switch through octaves