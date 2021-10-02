package baseball;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

			int[] computerNumbers = Computer.makeRandomNumbers();

			assertThat(computerNumbers[0]).isEqualTo(1);
			assertThat(computerNumbers[1]).isEqualTo(3);
			assertThat(computerNumbers[2]).isEqualTo(5);
		}
	}

	@Test
	void 유저가_정해진_범위_밖의_문자를_입력() {
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
	void 유저가_정해진_범위_안의_문자를_입력() {
		try (final MockedStatic<Console> mockConsole = mockStatic(Console.class)) {
			mockConsole
				.when(Console::readLine)
				.thenReturn("123", "12", "1");

			assertThat(Application.readUserInput()).isEqualTo("123");
			assertThat(Application.readUserInput()).isEqualTo("12");
			assertThat(Application.readUserInput()).isEqualTo("1");
		}
	}

	@Test
	void 유저가_정해진_범위_밖의_숫자를_입력() {
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> Application.toIntArray("012"));
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> Application.toIntArray("102"));
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> Application.toIntArray("560"));
	}

	@Test
	void 유저가_정해진_범위_안의_숫자를_입력() {
		int[] numbers = Application.toIntArray("123");

		assertThat(numbers[0]).isEqualTo(1);
		assertThat(numbers[1]).isEqualTo(2);
		assertThat(numbers[2]).isEqualTo(3);
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
