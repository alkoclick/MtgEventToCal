package unit;

import java.io.IOException;

import org.junit.Test;

import com.google.api.services.calendar.Calendar;

import app.CalendarApi;
import app.CalendarUtils;

public class CalendarUtilsTest {

	@Test
	public void testDelete() throws IOException {
		Calendar service = CalendarApi.getCalendarService();
		CalendarUtils.deleteAllFutureEvents(service);
	}
}
