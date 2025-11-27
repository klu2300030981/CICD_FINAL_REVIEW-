package klu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SfFeedbackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SfFeedbackServiceApplication.class, args);
    }

    /**
     * Configures RestTemplate as a Bean for making synchronous HTTP requests 
     * to other microservices (StudentService, FacultyService).
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}