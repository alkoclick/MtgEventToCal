package unit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import app.CalendarApi;
import model.MTGEvent;

public class CalendarApiTest {

	@Test
	public void postEvent() throws IOException {
		Calendar service = CalendarApi.getCalendarService();

		service.calendarList().list().execute().getItems().forEach(System.out::println);

		service.events()
				.insert("khva8hs40c91hb4dkj1nok98is@group.calendar.google.com",
						new Event().setSummary("PPTQ Kaissa")
								.setStart(new EventDateTime().setDateTime(new DateTime(new Date().from(Instant.now()))))
								// .setEndTimeUnspecified(true))

								.setEnd(new EventDateTime()
										.setDateTime(new DateTime(new Date().from(Instant.now().plusSeconds(3600))))))
				.execute();

		MTGEvent pptq = new MTGEvent();
		pptq.setFormat("Modern");
		pptq.setLink("http://youtube.com");
		pptq.setName("PPTQ Albuquerque");
		try {
			pptq.setDate(new SimpleDateFormat("yyyy/MM/dd").parse("2017/08/18"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		service.events().insert("khva8hs40c91hb4dkj1nok98is@group.calendar.google.com", pptq.toGoogleEvent()).execute();
	}
}
