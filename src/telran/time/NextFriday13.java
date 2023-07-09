package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;

public class NextFriday13 implements TemporalAdjuster {

	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal;
		boolean good = res.isSupported(ChronoField.DAY_OF_MONTH) && res.isSupported(ChronoUnit.MONTHS) 
				&& res.isSupported(ChronoField.DAY_OF_WEEK);
		if(!good)
			throw new UnsupportedTemporalTypeException("wrong format");
		res = next13(res);
		System.out.println(res.toString());
		res = nextFriday(res);
	
		return res;
	}
	
	
	
	private Temporal next13(Temporal temporal) {
		Temporal res = temporal;
		System.out.println(res.toString());
		int data = temporal.get(ChronoField.DAY_OF_MONTH);
		if(data > 13)
			res = res.plus(1, ChronoUnit.MONTHS);
		return res.with(ChronoField.DAY_OF_MONTH, 13);
		
	}
	
	private Temporal nextFriday(Temporal temporal) {
		Temporal res = temporal;
		while (res.get(ChronoField.DAY_OF_WEEK) != 5) {
			res = res.plus(1, ChronoUnit.MONTHS);
			System.out.println(res.toString());
		}
		return res;
		
	}

}
