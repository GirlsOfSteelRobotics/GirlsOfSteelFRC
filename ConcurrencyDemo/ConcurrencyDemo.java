
public class ConcurrencyDemo implements Runnable {
	// Toggle to show synchronized vs. unsynchronized operation
	static final boolean SYNCED = false;

	// Global counter to be incremented by both threads
	static int counter = 0;

	static synchronized void incrementCounter() {
		counter++;
		// System.out.println(Thread.currentThread().getName() + ": " + counter);
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			if (SYNCED)
				incrementCounter();
			else
				counter++;
		}
	}

	public static void main(String[] args) {
		ConcurrencyDemo demo = new ConcurrencyDemo();
		Thread thread1 = new Thread(demo);
		Thread thread2 = new Thread(demo);

		thread1.start();
		thread2.start();

		// Wait up to 5 seconds for each thread to finish
		try {
			thread1.join(5000);
			thread2.join(5000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		// Print the final value of the counter
		System.out.println("Counter: " + counter);
	}
}
