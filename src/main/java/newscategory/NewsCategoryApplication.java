package newscategory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NewsCategoryApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(NewsCategoryApplication.class, args);
	}
}
