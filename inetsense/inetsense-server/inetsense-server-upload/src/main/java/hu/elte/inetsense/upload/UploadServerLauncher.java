
package hu.elte.inetsense.upload;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hu.elte.inetsense.upload.controller.UploadServerController;

public class UploadServerLauncher {

	public static void main(final String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				UploadServerConfiguration.class);
		context.registerShutdownHook();
		registerShutdownHook(context);
		UploadServerController app = context.getBean(UploadServerController.class);
		app.start();
	}

	private static void registerShutdownHook(AnnotationConfigApplicationContext context) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (context != null) {
					context.close();
				}
			}
		});
	}

}
