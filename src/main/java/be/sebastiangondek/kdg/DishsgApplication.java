package be.sebastiangondek.kdg;

import org.springframework.boot.SpringApplication;
import org.springframework.modulith.Modulith;
import org.springframework.scheduling.annotation.EnableScheduling;


@Modulith
@EnableScheduling
public class DishsgApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishsgApplication.class, args);
	}

}
