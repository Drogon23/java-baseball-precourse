package baseball;

import nextstep.utils.Console;

public class Application {
	public static void main(String[] args) {
		// TODO 숫자 야구 게임 구현

		int[] computerNumbers = Computer.makeRandomNumbers();

		System.out.print("숫자를 입력해 주세요:");
		String inputLine = readUserInput();
		int[] userNumbers = toIntArray(inputLine);

		System.out.println(userNumbers[0]+""+userNumbers[1]+""+userNumbers[2]);

	}

	public static int[] toIntArray(String inputLine) {
		int[] userNumbers = new int[10];
		for (int index = 0; index < 3; index++) {
			int number = Character.digit(inputLine.charAt(index), 10);
			validateNumber(number, userNumbers);
			userNumbers[number] = 1;
		}

		return userNumbers;
	}

	public static String readUserInput() {
		String inputLine = Console.readLine();

		if (inputLine.length() != 3) {
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
		}

		return inputLine;
	}

	private static void validateNumber(int number, int[] userNumbers) {
		if (number < 1 || number > 9) {
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
		}

		if (userNumbers[number] == 1) {
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
		}
	}
}
