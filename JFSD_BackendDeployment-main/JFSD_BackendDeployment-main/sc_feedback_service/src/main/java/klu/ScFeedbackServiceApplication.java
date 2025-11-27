package klu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ScFeedbackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScFeedbackServiceApplication.class, args);
    }

    /**
     * Configures RestTemplate as a Bean for making synchronous HTTP requests 
     * to other microservices (StudentService, CourseService).
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}