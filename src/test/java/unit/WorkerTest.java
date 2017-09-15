package unit;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.google.api.services.calendar.Calendar;

import app.CalendarApi;
import app.CalendarUtils;
import workers.PPTQFinder;

public class WorkerTest {

	@Before
	public void init() {
		CalendarUtils.CALENDAR_ID = "khva8hs40c91hb4dkj1nok98is@group.calendar.google.com";
	}

	@Test
	public void simpleRun() throws IOException {
		new PPTQFinder("http://locator.wizards.com/#brand=magic&a=location&loc=351780&addrid=351780&p=Romania").run();
		Calendar service = CalendarApi.getCalendarService();
		CalendarUtils.insertAllEventsFromMemory(service);
	}
}
