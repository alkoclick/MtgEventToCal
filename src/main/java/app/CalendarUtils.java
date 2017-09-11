package app;

import java.io.IOException;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import util.MainMemory;

public class CalendarUtils {

	private static final String CALENDAR_ID = "1625666ov36rdj4uo3pgjb6qls@group.calendar.google.com";

	public static void deleteAllFutureEvents(Calendar service) {
		try {
			DateTime now = new DateTime(System.currentTimeMillis());
			Events futureEvents = service.events().list(CALENDAR_ID).setTimeMin(now).execute();

			futureEvents.getItems().forEach(event -> {
				try {
					System.out.println("Deleting " + event.getSummary());
					service.events().delete(CALENDAR_ID, event.getId()).execute();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void insertAllEventsFromMemory(Calendar service) {
		MainMemory.allEvents.values().forEach(event -> {
			try {
				service.events().insert(CALENDAR_ID, event.toGoogleEvent()).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
