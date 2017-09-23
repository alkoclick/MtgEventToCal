package util;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import model.MTGEvent;

public class MainMemory {
	public static Collection<String> storeLinks = new HashSet<>();
	public static ConcurrentHashMap<Integer, MTGEvent> allEvents = new ConcurrentHashMap<>();
}
