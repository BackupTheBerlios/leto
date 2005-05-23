package org.eu.leto.samples.useradmin;


import org.eu.leto.core.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
    public static void main(String[] args) {
        final ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/org/eu/leto/samples/useradmin/applicationContext.xml");
        final Application application = (Application) context.getBean(
                "application", Application.class);

        try {
            application.init();
            application.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
