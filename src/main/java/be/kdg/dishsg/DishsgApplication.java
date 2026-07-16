package be.kdg.dishsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableScheduling;

// Entry point for the Spring Modulith application; logs discovered module structure on startup.
@Modulith
@EnableScheduling
public class DishsgApplication {
	private static final Logger log = LoggerFactory.getLogger(DishsgApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DishsgApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	void onApplicationStarted(){
		ApplicationModules modules = ApplicationModules.of(DishsgApplication.class);
		modules.forEach(module -> log.info("\n{}", module));
	}

}
