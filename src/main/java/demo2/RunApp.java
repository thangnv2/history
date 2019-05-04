package demo2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import demo2.app.App;

public class RunApp {

	public static void main(String []args) {
		try {
			new App().run();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
