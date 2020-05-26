import org.jfugue.player.*;
// import javafx.scene.paint.Color;
import java.awt.Color;
import java.io.File;
import javax.swing.*;
import java.awt.event.*;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.*;

public class App {
    static Player player = new Player();
    static JFrame f = new JFrame();
    static Color muted = new Color(132, 146, 171);
    public static Pattern loadMIDI(String filename) {
        try {
            // final Player player = new Player();
            final Pattern pattern = MidiFileManager.loadPatternFromMidi(new File("src/assets/" + filename + ".mid"));
            return pattern;
        } catch (final Exception e) {
            e.printStackTrace();
            return new Pattern("C");
        }
    }
    public static void playVivaldi() {
        // Vivaldi's Spring
        Pattern spring = new Pattern("T[Allegro]");
        spring.add(
                "V0 C5q | E5q+C5q E5q+C5q E5q+C5q D5i C5i | Eh.+Gh. G5i F5i | E5q+C5q E5q+C5q E5q+C5q D5i C5i | Eh.+Gh. G5i | F5i | E5q G5q F5q E5q | D5h");
        spring.add("V1 Rq | C4h C4h | C4h C4h | C4h C4h | C4h C4h | C4h C4h | R ");
        player.play(spring);
    }
    public static boolean render(int numKeys) { // exits when x'ed
        String[] letters = new String[]{"C", "D", "E", "F", "G", "A", "B"};
        
        int letterIndex = 0;
        int xPos = 10;
        int yPos = 10;
        int octave = 4;

        for (int i = 0; i < numKeys; i++) {
            JButton b = new JButton(letters[letterIndex] + octave);
            b.setBounds(xPos, yPos, 40, 100);
            f.add(b);
            final int letI = letterIndex;
            final int play = octave;
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    player.play(letters[letI] + play);
                }
            });
            b.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    b.setForeground(muted);
                    b.setOpaque(true);
                    player.play(letters[letI] + play);
                }
                public void mouseExited(MouseEvent ect) {
                    b.setForeground(Color.black);
                }
            });
            letterIndex++;
            if (letterIndex == letters.length) {
                octave++;
                letterIndex = 0;
            }
            xPos += 50;
        }
        f.setSize(700, 400);
        f.setLayout(null);
        f.setVisible(true);
        return true;
    }
    public static void main(String[] args) {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        render(15);
        // player.play("Bbmin13q");
    }
}