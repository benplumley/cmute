public class Repetitions {

	Boolean[] days = {false, false, false, false, false, false, false};

	public Repetitions() {

	}

	public Boolean[] getDays() {
		return days;
	}

	public int toInteger() {
		int n = 0;
		for (Boolean day : days) {
    		n = (n << 1) + (day ? 1 : 0);
		}
		return n;
	}

}
