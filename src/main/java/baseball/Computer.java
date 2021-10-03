package baseball;

import nextstep.utils.Randoms;

public class Computer {

	private static final int START_NUMBER = 1;
	private static final int END_NUMBER = 9;

	private Computer() {

	}

	public static int[] makeRandomNumbers() {
		int[] computerNumbers = new int[10];
		int count = 0;
		while (count < 3) {
			int randomNumber = Randoms.pickNumberInRange(START_NUMBER, END_NUMBER);
			count = increaseCountIfNumberIsUnique(computerNumbers, count, randomNumber);
		}

		return computerNumbers;
	}

	private static int increaseCountIfNumberIsUnique(int[] computerNumbers, int count, int randomNumber) {
		if (computerNumbers[randomNumber] == 0) {
			computerNumbers[randomNumber] = 1;
			count++;
		}
		return count;
	}
}
