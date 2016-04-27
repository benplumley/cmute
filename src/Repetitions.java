/*
 * This class represents the days of the week a ride will repeat on, and gives
 * a convenient way to store these in the database by converting to integer.
 */

public class Repetitions {

	Boolean[] days = {false, false, false, false, false, false, false};

	public Repetitions() {}

	public Repetitions(int n) {
		// stores the int as a bit array
		for (int i = 6; i >= 0; i--) {
	        days[6 - i] = (n & (1 << i)) != 0;
	    }
	}

	public Repetitions(Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
		days[0] = monday;
		days[1] = tuesday;
		days[2] = wednesday;
		days[3] = thursday;
		days[4] = friday;
		days[5] = saturday;
		days[6] = sunday;
	}

	public Boolean[] getDays() {
		return days;
	}

	public int toInteger() {
		// converts the bit array back to an int
		int n = 0;
		for (Boolean day : days) {
    		n = (n << 1) + (day ? 1 : 0);
		}
		return n;
	}

	public Boolean getByIndex(int i) {
		return days[i];
	}

}
