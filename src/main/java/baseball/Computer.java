package baseball;

import java.util.ArrayList;
import java.util.List;

import nextstep.utils.Randoms;

public class Computer {

	private static final int START_NUMBER = 1;
	private static final int END_NUMBER = 9;

	private Computer() {

	}

	public static List<Integer> makeRandomNumbers() {
		List<Integer> computerNumberList = new ArrayList<>();
		int count = 0;

		while (count < 3) {
			int randomNumber = Randoms.pickNumberInRange(START_NUMBER, END_NUMBER);
			count += addNumberIfNotExists(computerNumberList, randomNumber);
		}

		return computerNumberList;
	}

	private static int addNumberIfNotExists(List<Integer> computerNumberList, int randomNumber) {
		if (!computerNumberList.contains(randomNumber)) {
			computerNumberList.add(randomNumber);
			return 1;
		}
		return 0;
	}

}
