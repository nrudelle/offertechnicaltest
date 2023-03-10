package fr.nrude.offertechnicaltest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"fr.nrude.offertechnicaltest"})
@EnableJpaRepositories(basePackages = "fr.nrude.offertechnicaltest.dao")
public class UserRegistrationSpringBootApplication
{
    public static void main( String[] args )
    {
        final SpringApplication mainApplication = new SpringApplication(UserRegistrationSpringBootApplication.class);
        mainApplication.run();
    }
}
