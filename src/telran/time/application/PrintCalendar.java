package telran.time.application;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {
private static final int TITLE_OFFSET = 8;

static DayOfWeek[] daysOfWeek = DayOfWeek.values();
	public static void main(String[] args)  {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

	}

	private static void printCalendar(RecordArguments recordArguments) {
		
		if(recordArguments.localeStr() != null)
			Locale.setDefault(new Locale(recordArguments.localeStr(), recordArguments.localeStr()));
	 
		String localSpace = "";
		if ((Locale.getDefault().getCountry() == "RU") || (Locale.getDefault().getCountry() == "UK") )
			 localSpace = " ";   // Russian letters are narrow
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstWeekDay(),  localSpace);
		printDays(recordArguments.month(), recordArguments.year(), recordArguments.firstWeekDay().getValue());
		
	}

	private static void printDays(int month, int year, int firstday) {
		int nDays = getNumberOfDays(month, year);
		int currentWeekDay = getFirstWeekDay(month, year);
		printOffset(currentWeekDay, firstday);
		for (int day = 1; day <= nDays; day++) {
			System.out.printf("%4d",day);
			currentWeekDay = currentWeekDay  % 7 +1;	
			if(currentWeekDay == (firstday)) {
				System.out.println();
				
			
			}
			
		}
		
	}

	private static void printOffset(int currentWeekDay, int firstday) {
		int shift = currentWeekDay - firstday ;
		if(shift <0)
			shift +=7;
		System.out.printf("%s", " ".repeat(4 * shift));
		
	}

	private static int getFirstWeekDay(int month, int year) {
		int weekDayNumber = LocalDate.of(year, month, 1)
				.get(ChronoField.DAY_OF_WEEK);
		return weekDayNumber;
	}

	private static int getNumberOfDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static void printWeekDays(DayOfWeek firstDay, String localSpace) {
		System.out.print("  ");
		DayOfWeek[] days = new DayOfWeek[7];
		int num = firstDay.getValue() -1 ;
		
		System.arraycopy(daysOfWeek, num, days, 0, 7 -num );
		if(num !=0) {
			System.arraycopy(daysOfWeek,0, days, 7- num, num );
		}
		Arrays.stream(days).forEach(dw ->
		System.out.printf("%s " + localSpace,
				dw.getDisplayName(TextStyle.SHORT, Locale.getDefault())));
		System.out.println();
		
		
		
	}

	private static void printTitle(int monthNumber, int year) {
		// <year>, <month name>
		Month month = Month.of(monthNumber);
		String monthName = month.getDisplayName(TextStyle.FULL,
				Locale.getDefault());
		System.out.printf("%s%s  %d\n", " ".repeat(TITLE_OFFSET),monthName, year);
		
	}

	private static RecordArguments getRecordArguments(String[] args) throws Exception {
		LocalDate ld = LocalDate.now();
		int month = args.length == 0 ? ld.get(ChronoField.MONTH_OF_YEAR) :
			getMonth(args[0]);
		int year = args.length > 1 ? getYear(args[1]) :
			ld.get(ChronoField.YEAR);
		DayOfWeek firstday = args.length > 2 ? getDay(args[2]):  DayOfWeek.of(1);
		String locale = args.length > 3 ? args[3]:  null;
		
		return new RecordArguments(month, year, firstday, locale);
	}

	private static DayOfWeek getDay(String dayString) throws Exception{
		int day=1;
		String message = "";
		try {
			day = Integer.parseInt(dayString);
			if(day < 1 || day > 7) {
				message = "day must be in the range [1-7]";
			}
			
		} catch (NumberFormatException e) {
			message = "day must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		
		return  DayOfWeek.of(day);
		
	}

	private static int getYear(String yearStr) throws Exception {
		String message = "";
		int year = 0;
		try {
			year = Integer.parseInt(yearStr);
			if(year < 0) {
				message = "year must be a positive number";
			}
			
		} catch (NumberFormatException e) {
			message = "year must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return year;
	}

	private static int getMonth(String monthStr) throws Exception {
		String message = "";
		int month = 0;
		try {
			month = Integer.parseInt(monthStr);
			if(month < 1 || month > 12) {
				message = "month must be in the range [1-12]";
			}
			
		} catch (NumberFormatException e) {
			message = "month must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return month;
	}

}
