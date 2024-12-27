package Apache.tika.examples.ApacheTicaDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.*"})
public class ApacheTicaDemo {
public static void main(String[] args) {
	SpringApplication.run(ApacheTicaDemo.class, args);
	System.out.println("HEllo world");
}
}
