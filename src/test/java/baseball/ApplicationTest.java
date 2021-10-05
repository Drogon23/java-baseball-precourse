package baseball;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import nextstep.test.NSTest;
import nextstep.utils.Console;
import nextstep.utils.Randoms;

public class ApplicationTest extends NSTest {
	@BeforeEach
	void beforeEach() {
		super.setUp();
	}

	@Test
	void 컴퓨터_랜덤숫자_생성() {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms
				.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(1, 3, 5);

			List<Integer> computerNumberList = Computer.makeRandomNumbers();

			assertThat(computerNumberList.get(0)).isEqualTo(1);
			assertThat(computerNumberList.get(1)).isEqualTo(3);
			assertThat(computerNumberList.get(2)).isEqualTo(5);
		}
	}

	@Test
	void 유저가_세자리가_아닌_문자를_입력() {
		try (final MockedStatic<Console> mockConsole = mockStatic(Console.class)) {
			mockConsole
				.when(Console::readLine)
				.thenReturn("1234", "");

			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(Application::readUserInput);

			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(Application::readUserInput);
		}
	}

	@Test
	void 유저가_세자리_문자를_입력() {
		try (final MockedStatic<Console> mockConsole = mockStatic(Console.class)) {
			mockConsole
				.when(Console::readLine)
				.thenReturn("123");

			assertThat(Application.readUserInput()).isEqualTo("123");
		}
	}

	@Test
	void 유저_숫자_리스트_생성_실패_정해진_범위_밖의_문자를_입력() {
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> Application.makeUserNumberList("012"));

		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> Application.makeUserNumberList("asb"));
	}

	@Test
	void 유저_숫자_리스트_생성_성공() {
		List<Integer> list = Application.makeUserNumberList("135");
		assertThat(list.get(0)).isEqualTo(1);
		assertThat(list.get(1)).isEqualTo(3);
		assertThat(list.get(2)).isEqualTo(5);
	}

	@Test
	void 스트라이크_카운트() {
		List<Integer> computerNumberList = Lists.newArrayList(1, 3, 5);

		int strike = Application.countStrike(computerNumberList, Lists.list(1, 2, 3));
		assertThat(strike).isEqualTo(1);

		int strike1 = Application.countStrike(computerNumberList, Lists.list(1, 3, 4));
		assertThat(strike1).isEqualTo(2);

		int strike2 = Application.countStrike(computerNumberList, Lists.list(1, 3, 5));
		assertThat(strike2).isEqualTo(3);
	}

	@Test
	void 볼_카운트() {
		List<Integer> computerNumberList = Lists.newArrayList(1, 3, 5);

		int ball = Application.countBall(computerNumberList, Lists.list(1, 2, 3));
		assertThat(ball).isEqualTo(1);

		int ball1 = Application.countBall(computerNumberList, Lists.list(5, 2, 3));
		assertThat(ball1).isEqualTo(2);

		int ball2 = Application.countBall(computerNumberList, Lists.list(5, 1, 3));
		assertThat(ball2).isEqualTo(3);
	}

	@Test
	void 종료시_유저가_한자리가_아닌_문자를_입력() {
		try (final MockedStatic<Console> mockConsole = mockStatic(Console.class)) {
			mockConsole
				.when(Console::readLine)
				.thenReturn("1234", "");

			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(Application::readEndStatusUserInput);

			assertThatExceptionOfType(IllegalArgumentException.class)
				.isThrownBy(Application::readEndStatusUserInput);
		}
	}

	@Test
	void 종료시_유저가_한자리_문자를_입력() {
		try (final MockedStatic<Console> mockConsole = mockStatic(Console.class)) {
			mockConsole
				.when(Console::readLine)
				.thenReturn("1", "2");

			String input = Application.readEndStatusUserInput();
			assertThat(input).isEqualTo("1");

			input = Application.readEndStatusUserInput();
			assertThat(input).isEqualTo("2");
		}
	}

	@Test
	void 종료시_유저가_허용되지_않은_문자를_입력() {
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> Application.getEndStatusToInt("a"));

		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> Application.getEndStatusToInt("3"));
	}

	@Test
	void 종료시_유저가_허용된_문자를_입력() {
		int number = Application.getEndStatusToInt("1");
		assertThat(number).isEqualTo(1);

		number = Application.getEndStatusToInt("2");
		assertThat(number).isEqualTo(2);
	}

	@Test
	void 유저가_게임_종료_후_재시작_요청() {
		try (final MockedStatic<Console> mockConsole = mockStatic(Console.class);
			 final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockConsole
				.when(Console::readLine)
				.thenReturn("1");
			mockRandoms
				.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(1, 3, 5);

			Application.decideToPlayNewGame();

			assertThat(Application.COMPUTER_NUMBER_LIST.get(0)).isEqualTo(1);
			assertThat(Application.COMPUTER_NUMBER_LIST.get(1)).isEqualTo(3);
			assertThat(Application.COMPUTER_NUMBER_LIST.get(2)).isEqualTo(5);
		}
	}

	@Test
	void 유저가_게임_종료_후_종료_요청() {
		try (final MockedStatic<Console> mockConsole = mockStatic(Console.class)) {
			mockConsole
				.when(Console::readLine)
				.thenReturn("2");

			Application.decideToPlayNewGame();

			assertThat(Application.IS_PLAYING).isEqualTo(false);
		}
	}

	@Test
	void 유저_잘못된_문자_입력_시_에러_메세지_출력() {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms
				.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(1, 3, 5);
			running("666");
			verify("[ERROR]", "숫자를 입력해 주세요");
		}
	}

	@Test
	void 게임_종료_시_잘못된_문자_입력() {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms
				.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(1, 3, 5);
			running("135", "5");
			verify("3스트라이크", "게임 끝", "[ERROR]");
		}
	}

	@Test
	void 낫싱() {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms
				.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(1, 3, 5);
			running("246");
			verify("낫싱");
		}
	}

	@Test
	void 게임종료_후_재시작() {
		try (final MockedStatic<Randoms> mockRandoms = mockStatic(Randoms.class)) {
			mockRandoms.when(() -> Randoms.pickNumberInRange(anyInt(), anyInt()))
				.thenReturn(7, 1, 3)
				.thenReturn(5, 8, 9);
			run("713", "1", "597", "589", "2");
			verify("3스트라이크", "게임 끝", "1스트라이크 1볼");
		}
	}

	@AfterEach
	void tearDown() {
		outputStandard();
	}

	@Override
	public void runMain() {
		Application.main(new String[] {});
	}
}
