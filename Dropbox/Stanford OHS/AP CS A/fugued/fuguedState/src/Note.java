public class Note {
	String letter;
	int octave;
	Time length;
	public Note(String letter, int octave) {
		length = new Time(3); // default to quarter note
		this.letter = letter;
		this.octave = octave;
	}
	public Note(String letter, int octave, int times) {
		length = new Time(times);
		this.letter = letter;
		this.octave = octave;
	}
	public String getLetter() {
		return letter;
	}
	public int getOctave() {
		return octave;
	}
	public boolean equals(Note other) {
		return (other.letter == letter) && (other.octave == this.octave);
	}
	@Override
	public String toString() {
		return letter + octave + " (" + length.getShort() + ")";
	}
}

class Time {
	public String[] signatures = new String[]{"sixteenth", "quarter", "half", "whole"};
	String elem;
	public Time(int num) {
		elem = signatures[num]; // set the value of the length of the note
	}
	public Time (String elem) {
		this.elem = elem;
	}
	public void setLength(String elem) {
		this.elem = elem;
	}
	public String timeString() {
		return elem;
	}
	public int timeInt() {
		int counter = 0;
		for (String index : signatures) {
			if (elem.equals(index)) {
				return counter;
			}
			counter++;
		}
		return 0;
	}
	public String getShort() {
		if (elem.equals("sixteenth")) { return "1/16"; }
		else if (elem.equals("quarter")) { return "1/4"; }
		else if (elem.equals("half")) { return "1/2"; }
		else if (elem.equals("whole")) { return "1"; }
		else { return "0"; }
	}
}