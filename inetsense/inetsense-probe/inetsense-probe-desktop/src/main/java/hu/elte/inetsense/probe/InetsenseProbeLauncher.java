
package hu.elte.inetsense.probe;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hu.elte.inetsense.probe.controller.InetsenseProbeController;
import hu.elte.inetsense.probe.view.ProbeView;

public class InetsenseProbeLauncher {

	// !!!!!  WARNING !!!!! 
	// logger cannot be used before spring context is created!!!
	// !!!!!!!!!!!!!!!!!!!!
	
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
