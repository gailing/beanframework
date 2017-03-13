package ro.szzsa.flashair.application;

/**
 * Entry point of the application
 */
public class Launcher implements Runnable {

	public static void main(String[] args) {
		new Downloader().start();
	}

	@Override
	public void run() {
		new Downloader().start();
	}
}
