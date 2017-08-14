package util;

import java.util.concurrent.ConcurrentHashMap;

import model.Event;

public class MainMemory {

	public static ConcurrentHashMap<Integer, String> storeLinks = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<Integer, Event> allEvents = new ConcurrentHashMap<>();
}
