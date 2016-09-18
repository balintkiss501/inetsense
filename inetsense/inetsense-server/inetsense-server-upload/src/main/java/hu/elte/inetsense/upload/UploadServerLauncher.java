
package hu.elte.inetsense.upload;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hu.elte.inetsense.upload.controller.UploadServerController;

public class UploadServerLauncher {

    public static void main(final String[] args) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                UploadServerConfiguration.class)) {
            context.registerShutdownHook();
            UploadServerController app = context.getBean(UploadServerController.class);
            app.start();
            while (true) {
            }
        }
    }

}
