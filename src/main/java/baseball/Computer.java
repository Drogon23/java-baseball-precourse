package baseball;

import nextstep.utils.Randoms;

public class Computer {

	private static final int START_NUMBER = 1;
	private static final int END_NUMBER = 9;

	private Computer() {

	}

	public static int[] makeRandomNumbers() {
		int[] computerNumbers = new int[3];

		for (int index = 0; index < 3; index++) {
			computerNumbers[index] = Randoms.pickNumberInRange(START_NUMBER, END_NUMBER);
		}

		return computerNumbers;
	}
}
