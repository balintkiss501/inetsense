
package hu.elte.inetsense.probe;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hu.elte.inetsense.probe.controller.InetsenseProbeController;
import hu.elte.inetsense.probe.view.ProbeView;

public class InetsenseProbeLauncher {

    public static void main(final String[] args) throws InvocationTargetException, InterruptedException {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ProbeConfiguration.class)) {
            context.registerShutdownHook();
            SwingUtilities.invokeAndWait(() -> {
                ProbeView view = context.getBean(ProbeView.class);
                view.setVisible(true);
            });
            InetsenseProbeController app = context.getBean(InetsenseProbeController.class);
            app.start();
            while(true){}
        }

    }

}
