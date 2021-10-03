package baseball;

import java.util.ArrayList;
import java.util.List;

import nextstep.utils.Console;

public class Application {
	public static void main(String[] args) {
		// TODO 숫자 야구 게임 구현

		List<Integer> computerNumberList = Computer.makeRandomNumbers();
		while (true) {
			System.out.print("숫자를 입력해 주세요:");
			try {
				String inputLine = readUserInput();
				List<Integer> userNumberList = toList(inputLine);
				System.out.println(userNumberList.get(0) + "" + userNumberList.get(1) + "" + userNumberList.get(2));

			} catch (IllegalArgumentException exception) {
				System.out.println(exception.getMessage());
			}

		}

	}

	public static List<Integer> toList(String inputLine) {
		List<Integer> userNumberList = new ArrayList<>();
		for (int index = 0; index < inputLine.length(); index++) {
			int number = Character.digit(inputLine.charAt(index), 10);
			validateNumber(number, userNumberList);
			userNumberList.add(number);
		}

		return userNumberList;
	}

	public static String readUserInput() {
		String inputLine = Console.readLine();

		if (inputLine.length() != 3) {
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
		}

		return inputLine;
	}

	private static void validateNumber(int number, List<Integer> userNumberList) {
		if (number < 1 || number > 9) {
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
		}

		if (userNumberList.contains(number)) {
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
		}
	}
}
