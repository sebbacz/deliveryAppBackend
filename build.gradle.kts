plugins {
	java
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "be.kdg"
version = "0.0.1-SNAPSHOT"
description = "Keep Dishes Going"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.modulith:spring-modulith-api:1.4.1")
	implementation("org.springframework.modulith:spring-modulith-events-core:1.4.1")
	implementation("org.springframework.modulith:spring-modulith-starter-core:1.4.1")
	implementation("org.springframework.modulith:spring-modulith-core:1.4.1")
	implementation("org.springframework.amqp:spring-rabbit:3.2.7")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
