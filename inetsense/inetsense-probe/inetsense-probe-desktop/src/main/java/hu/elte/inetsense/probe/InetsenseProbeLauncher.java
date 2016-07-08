
package hu.elte.inetsense.probe;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hu.elte.inetsense.probe.controller.InetsenseProbeController;

public class InetsenseProbeLauncher {

    public static void main(final String[] args) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ProbeConfiguration.class)) {
            context.registerShutdownHook();
            InetsenseProbeController app = context.getBean(InetsenseProbeController.class);
            app.start();
            while(true){}
        }

    }

}
