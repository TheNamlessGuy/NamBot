package nambot.helpers;

public class Number {
	public static boolean isInt(String s) {
		return s.matches("[0-9]+");
	}

	public static int clamp(int min, int i, int max) {
		if (i < min)
			return min;
		if (i > max)
			return max;
		return i;
	}

	public static int lclamp(int min, int i) {
		if (i < min)
			return min;
		return i;
	}
}