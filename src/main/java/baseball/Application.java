package baseball;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import nextstep.utils.Console;

public class Application {
	public static List<Integer> COMPUTER_NUMBER_LIST;
	public static boolean IS_PLAYING;

	public static void main(String[] args) {
		COMPUTER_NUMBER_LIST = Computer.makeRandomNumbers();
		IS_PLAYING = true;
		while (IS_PLAYING) {
			playGame(COMPUTER_NUMBER_LIST);
		}
	}

	private static void playGame(List<Integer> computerNumberList) {
		List<Integer> userNumberList = getUserNumberList();
		int strike = countStrike(computerNumberList, userNumberList);
		int ball = countBall(computerNumberList, userNumberList);
		printResult(strike, ball);
		if (strike == 3) {
			System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 끝");
			decideToPlayNewGame();
		}
	}

	private static List<Integer> getUserNumberList() {
		System.out.print("숫자를 입력해 주세요:");
		try {
			String inputLine = readUserInput();
			return makeUserNumberList(inputLine);
		} catch (IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
			return getUserNumberList();
		}
	}

	public static List<Integer> makeUserNumberList(String inputLine) {
		List<Integer> userNumberList = new ArrayList<>();
		for (int index = 0; index < inputLine.length(); index++) {
			int number = Character.digit(inputLine.charAt(index), 10);
			validateNumber(number, userNumberList);
			userNumberList.add(number);
		}
		return userNumberList;
	}

	private static void printResult(int strike, int ball) {
		if (hasStrikeOrBall(strike, ball))
			return;
		printNothing(strike, ball);
	}

	private static void printNothing(int strike, int ball) {
		if (strike == 0 && ball == 0) {
			System.out.println("낫싱");
		}
	}

	private static boolean hasStrikeOrBall(int strike, int ball) {
		if (strike > 0)
			System.out.print(strike + "스트라이크 ");
		if (ball > 0)
			System.out.print(ball + "볼");
		if (strike + ball > 0) {
			System.out.print("\n");
			return true;
		}
		return false;
	}

	public static void decideToPlayNewGame() {
		int inputNumber = 0;
		while (inputNumber != 1 && inputNumber != 2) {
			inputNumber = readEndStatus();
		}
		if (inputNumber == 1)
			COMPUTER_NUMBER_LIST = Computer.makeRandomNumbers();
		if (inputNumber == 2)
			IS_PLAYING = false;
	}

	private static int readEndStatus() {
		int number = 0;
		try {
			System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
			String inputLine = readEndStatusUserInput();
			number = getEndStatusToInt(inputLine);
		} catch (IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
		}
		return number;
	}

	public static int getEndStatusToInt(String inputLine) {
		int number = Character.digit(inputLine.charAt(0), 10);
		if (number != 1 && number != 2) {
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
		}

		return number;
	}

	public static String readEndStatusUserInput() {
		String inputLine = Console.readLine();
		if (inputLine.length() != 1) {
			throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
		}
		return inputLine;
	}

	public static int countStrike(List<Integer> computerNumberList, List<Integer> userNumberList) {
		int strike = 0;
		for (int index = 0; index < userNumberList.size(); index++) {
			strike += compareNumberIsStrike(computerNumberList, userNumberList, index);
		}
		return strike;
	}

	private static int compareNumberIsStrike(List<Integer> computerNumberList, List<Integer> userNumberList,
		int index) {
		if (Objects.equals(computerNumberList.get(index), userNumberList.get(index))) {
			return 1;
		}
		return 0;
	}

	public static int countBall(List<Integer> computerNumberList, List<Integer> userNumberList) {
		int ball = 0;
		for (int index = 0; index < userNumberList.size(); index++) {
			ball += compareNumberIsBall(computerNumberList, userNumberList, index);
		}
		return ball;
	}

	private static int compareNumberIsBall(List<Integer> computerNumberList, List<Integer> userNumberList, int index) {
		int computerNumber = computerNumberList.get(index);
		int userNumber = userNumberList.get(index);
		if (computerNumber != userNumber && computerNumberList.contains(userNumber)) {
			return 1;
		}
		return 0;
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
