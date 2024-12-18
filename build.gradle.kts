plugins {
	kotlin("jvm") version "1.9.0"
	kotlin("plugin.spring") version "1.9.0"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("kapt") version "1.9.0"
	id("io.gitlab.arturbosch.detekt") version "1.23.1"
}

group = "com.kp2"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	google()
	maven { url = uri("https://repo.spring.io/milestone") }
}


dependencies {
	// Стартовые зависимости для веб-приложений и Thymeleaf
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Безопасность
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	testImplementation("org.springframework.security:spring-security-test")

	// Для работы с Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Для работы с JPA и PostgreSQL
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql")

	// Для валидации
	implementation("jakarta.validation:jakarta.validation-api:3.0.2")
	implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")

	// Для работы с OpenAPI (Swagger)
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// Ломбок
	implementation("org.projectlombok:lombok")
	kapt("org.projectlombok:lombok")

	// Тестирование
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Devtools для разработки
	developmentOnly("org.springframework.boot:spring-boot-devtools")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

detekt {
	toolVersion = "1.23.1"
	config = files("detekt-config.yml")
	buildUponDefaultConfig = true
	allRules = false
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
	jvmTarget = "17"
}

//tasks.withType<Jar> {
//	archiveBaseName.set("myapp")
//	archiveVersion.set("0.0.1")
//}

tasks.withType<Test> {
	useJUnitPlatform()
}


