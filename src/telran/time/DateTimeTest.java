package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.time.*;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;

class DateTimeTest {

	@Test
	void test() {
		LocalDate birthAS = LocalDate.of(1799, 6, 6);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM YYYY, EEEE");
		System.out.println(birthAS.format(dtf));
		LocalDate barMizva  = birthAS.plusYears(13);
		System.out.printf("Bar Mizva of AS %s \n",barMizva.format(dtf));
		assertEquals(barMizva, birthAS.with(new BarMizvaAdjuster()));
		assertThrowsExactly(UnsupportedTemporalTypeException.class,
			() ->	LocalTime.now().with(barMizva));
		}

	@Test
	void nextFriday13Test() {
		TemporalAdjuster nextFr = new NextFriday13();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime fr13 = ZonedDateTime.of(2023, 10, 13,0,0,0,0,ZoneId.systemDefault());
		assertEquals(fr13.toLocalDate(), zdt.with(nextFr).toLocalDate());
		LocalDate fr13_next = LocalDate.of(2024, 9,13);
		LocalDate ld = LocalDate.of(2024, 9,13);
		assertEquals(fr13_next, ld.with(nextFr));
		assertThrowsExactly(UnsupportedTemporalTypeException.class,
				() ->	LocalTime.now().with(nextFr));
		
	}
	
	@Test
	void tempCanadaZoneTest() {
	//	ZoneId.getAvailableZoneIds().forEach(System.out::println);
		displayCurrentTime("canada");
		//ZoneRulesException
	}
	void displayCurrentTime(String zoneName) {
		ZonedDateTime zdt =  ZonedDateTime.now();
		ZoneId.getAvailableZoneIds().stream()
						.filter(zone -> zone.toLowerCase().contains(zoneName.toLowerCase()))
						.map(zone -> zdt.withZoneSameInstant( ZoneId.of(zone)))
						.forEach(System.out::println);//(dt -> displayCurrentTime(dt));
				
	}
}
