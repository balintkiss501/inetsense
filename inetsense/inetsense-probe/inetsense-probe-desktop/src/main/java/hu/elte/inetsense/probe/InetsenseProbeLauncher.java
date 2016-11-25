
package hu.elte.inetsense.probe;

import hu.elte.inetsense.probe.controller.InetsenseProbeController;
import hu.elte.inetsense.probe.view.ProbeView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class InetsenseProbeLauncher {

    private static final Logger log = LogManager.getLogger();

    public static void main(final String[] args) throws InvocationTargetException, InterruptedException {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ProbeConfiguration.class)) {
            context.registerShutdownHook();
            InetsenseProbeController app = context.getBean(InetsenseProbeController.class);
            ProbeView view = context.getBean(ProbeView.class);

            if (view == null) {
                app.setGuiEnabled(false);
            } else {
                app.setGuiEnabled(true);
                SwingUtilities.invokeAndWait(() -> {
                    view.setVisible(true);
                });
            }

            app.start();
        }
    }
}
