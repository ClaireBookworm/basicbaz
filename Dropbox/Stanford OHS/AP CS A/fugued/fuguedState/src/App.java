import org.jfugue.player.*;
import java.awt.Color;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.*;
import java.lang.String;

// named App because that's the default name and I don't want to mess it up!
public class App {
    static Player player = new Player();
    static JFrame f = new JFrame();
    static Color muted = new Color(132, 146, 171);
    static int keyboardSize = 15; //default
    public static int measure = 4;
    // set up an arrayList of notes, it's the music score
    public static ArrayList<Note> score = new ArrayList<>(20);

    public static void scoreAdd(String letter, int octave) {
        // add a note to the score
        score.add(new Note(letter, octave, 3));
    }
    public static void toMIDI(ArrayList<Note> arr) {
        // export the score to a midi file, readable by jFugue
        // converting to mp3 is much more complicated
        String pattern = "";
        for (Note key : arr) {
            pattern.concat(key.getLetter() + key.getOctave() + " ");
        }
        Pattern notes = new Pattern(pattern);
        try {
            MidiFileManager.savePatternToMidi(notes, new File("src/assets/fugue-score.mid"));
        } catch (IOException ex) {
        }
    }
    public static void toFile(ArrayList<Note> arr, int measure) {
        // this prints the score into a file
        try {
            FileWriter writer = new FileWriter("score.txt");
            // these separate it into measures and lines
            int rowC = 0;
            int counter = 0;
            for (Note key : arr) {
                counter++;
                rowC++;
                writer.write(" " + key.toString() + " ");
                if (rowC > measure) {
                    writer.write(" | ");
                    rowC = 0;
                }
                if (counter > (measure * 3)) {
                    writer.write(System.lineSeparator());
                    counter = 0;
                }
            }
            writer.close();
        } catch (IOException ioException) {
            // not very applicable, but required
            ioException.fillInStackTrace();
        }
    }
    public static void displayScore(int measure) {
        // this displays the score in the console -> not that needed since we have print to .txt file
        System.out.println("Time Signature: " + measure + "/4");
        int rowCounter = 0;
        int lineCount = 0;
        for (Note note : score) {
            rowCounter++;
            lineCount++;
            System.out.print(" " + note.toString() + " ");
            if (lineCount > (measure * 3)) {
                System.out.println();
                lineCount = 0;
            } 
            else if (rowCounter > measure) {
                System.out.print("|");
                rowCounter = 0;
            }
        }
    }
    public static Pattern loadMIDI(String filename) {
        try {
            // final Player player = new Player();
            final Pattern pattern = MidiFileManager.loadPatternFromMidi(new File("src/assets/" + filename));
            return pattern;
        } catch (final Exception e) {
            e.printStackTrace();
            return new Pattern("C");    
        }
    }
    public static void playVivaldi() {
        // Vivaldi's Spring 
        // just for fun!
        Pattern spring = new Pattern("T[Allegro]");
        // this is in music string!
        spring.add(
                "V0 C5q | E5q+C5q E5q+C5q E5q+C5q D5i C5i | Eh.+Gh. G5i F5i | E5q+C5q E5q+C5q E5q+C5q D5i C5i | Eh.+Gh. G5i | F5i | E5q G5q F5q E5q | D5h");
        spring.add("V1 Rq | C4h C4h | C4h C4h | C4h C4h | C4h C4h | C4h C4h | R ");
        player.play(spring);
    }
    public static boolean render(int numKeys) { // exits when x'ed
        String[] letters = new String[]{"C", "D", "E", "F", "G", "A", "B"};
        
        int letterIndex = 0;
        int xPos = 20;
        int yPos = 100;
        int octave = 4;

        int rowCounter = 0;

        for (int i = 0; i < numKeys; i++) {
            rowCounter++;
            JButton b = new JButton(letters[letterIndex] + octave);
            b.setBounds(xPos, yPos, 40, 100);
            b.setBackground(Color.WHITE);
            b.setOpaque(true);
            f.add(b);
            
            final int letI = letterIndex;
            final int play = octave;
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    player.play(letters[letI] + play);
                    scoreAdd(letters[letI], play);
                }
            });
            letterIndex++;
            if (letterIndex == letters.length) {
                octave++;
                letterIndex = 0;
            }
            xPos += 50;
            if (rowCounter > 10) {
                yPos += 130;
                xPos = 20;
                rowCounter = 0;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        f.setLayout(null); // self-layout, don't need layout manager
        f.setSize(700, 400); // allows resizing, hopefully not too big of a keyboard

        // keys are 40 by 100
        // 10 keys per row
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // generator button and exit
        Color altGreen = new Color(92, 219, 149);
        JButton display = new JButton("Exit");
        display.setBounds(20, 20, 100, 40);
        display.setBorderPainted(false);
        display.setBackground(altGreen);
        display.setVisible(true);
        Scanner input = new Scanner(System.in);
        display.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayScore(measure);
                toFile(score, measure);
                toMIDI(score);
                System.out.println("Playback? (y/n):");
                char playback = input.next().charAt(0);
                if (playback == 'y' || playback == 'Y') {
                    player.play(loadMIDI("fugue-score.mid"));
                }
                input.close();
                System.exit(0);
            }
        });
        f.add(display);
        render(keyboardSize);
        f.setVisible(true);
    }
}