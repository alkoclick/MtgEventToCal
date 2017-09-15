package unit;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.api.services.calendar.Calendar;

import app.CalendarApi;
import app.CalendarUtils;
import model.MTGEvent;
import util.MainMemory;

public class CalendarUtilsTest {

	@Before
	public void init() {
		CalendarUtils.CALENDAR_ID = "khva8hs40c91hb4dkj1nok98is@group.calendar.google.com";
	}

	@Test
	public void testDelete() throws IOException {
		Calendar service = CalendarApi.getCalendarService();
		CalendarUtils.deleteAllFutureEvents(service);
	}

	@Test
	public void testUpload() throws IOException {
		Calendar service = CalendarApi.getCalendarService();
		MTGEvent event = new MTGEvent();
		event.setCountry("Greece");
		event.setFormat("Modern");
		event.setType("PPTQ");
		event.setDate(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)));
		event.setOrganizer(Arrays
				.asList(new String[] { "Alex's Event Runners", "Random Street 1", "51237, Thessaloniki", "Greece" }));
		MainMemory.allEvents.put(event.hashCode(), event);
		CalendarUtils.insertAllEventsFromMemory(service);
	}
}
