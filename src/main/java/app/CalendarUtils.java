package app;

import java.io.IOException;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import util.MainMemory;

public class CalendarUtils {
	private static final String EVENT_INSERT = "Inserting event ";
	private static final String EVENT_DELETE = "Deleting events:";
	public static String CALENDAR_ID = "";

	public static void deleteAllFutureEvents(Calendar service) throws IOException {
		DateTime now = new DateTime(System.currentTimeMillis());
		Events futureEvents = service.events().list(CALENDAR_ID).setTimeMin(now).execute();

		futureEvents.getItems().forEach(event -> {
			try {
				System.out.println(EVENT_DELETE + event.getSummary());
				service.events().delete(CALENDAR_ID, event.getId()).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void insertAllEventsFromMemory(Calendar service) {
		MainMemory.allEvents.values().forEach(event -> {
			try {
				System.out.println(EVENT_INSERT);
				service.events().insert(CALENDAR_ID, event.toGoogleEvent()).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
