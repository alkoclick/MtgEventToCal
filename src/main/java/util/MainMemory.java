package util;

import java.util.concurrent.ConcurrentHashMap;

import model.MTGEvent;

public class MainMemory {
	public static ConcurrentHashMap<Integer, String> storeLinks = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<Integer, MTGEvent> allEvents = new ConcurrentHashMap<>();
}
