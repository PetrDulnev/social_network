plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.practice"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework:spring-messaging:6.1.10")
	implementation("org.springframework:spring-websocket:6.1.10")
	implementation("io.minio:minio:8.5.10")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	testImplementation("org.testcontainers:testcontainers:1.19.7")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
	implementation("org.springframework.security:spring-security-oauth2-core:6.2.3")
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	runtimeOnly("io.micrometer:micrometer-registry-prometheus")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("io.swagger:swagger-annotations:1.6.14")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
