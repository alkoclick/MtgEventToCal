package app;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.FileHandler;
import util.MainMemory;
import workers.PPTQFinder;

public class Application {
	static String MAIN_URL = Messages.getString("EventLocator.Main");

	public static void main(String[] args) {

		// Read store links
		Collection<String> storeLinks = FileHandler.reader("storeLinks.txt");

		runOnExecutor(storeLinks.stream().map(link -> new PPTQFinder(link)).collect(Collectors.toSet()), 8, 5);

		MainMemory.allEvents.values().forEach(System.out::println);

		// Google calendar upload
	}

	protected static void runOnExecutor(Collection<Runnable> runnables, int maxThreads, long timeout) {
		// Over 4 threads hit connection reset from server
		ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
		runnables.forEach(runnable -> executor.execute(runnable));

		executor.shutdown();
		try {
			executor.awaitTermination(timeout, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
