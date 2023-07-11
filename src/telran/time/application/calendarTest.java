package telran.time.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class calendarTest {

	@Test
	void test() {
		String[] arg = {"7","2023", "1"};
		PrintCalendar.main(arg);
		System.out.println();
		String[] arg1 = {"8","2024", "5", "UK"};
		PrintCalendar.main(arg1);
		System.out.println();
		String[] arg2 = {"02","2024","7", "en"};
		PrintCalendar.main(arg2);
		System.out.println();
		String[] arg3 = {"10","2023"};
		PrintCalendar.main(arg3);
		System.out.println();
	}

}
