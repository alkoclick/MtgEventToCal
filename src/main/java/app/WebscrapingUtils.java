package app;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.Messages;
import util.MainMemory;
import workers.PPTQFinder;

public class WebscrapingUtils {
	private static final long TIMEOUT = Messages.getInt("Timeout.Executor");
	private static final int MAX_THREADS = Messages.getInt("Threads.Max");

	public static void addEventsFromLinks() {
		runOnExecutor(MainMemory.storeLinks.stream().map(link -> new PPTQFinder(link)).collect(Collectors.toSet()),
				MAX_THREADS, TIMEOUT);
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
