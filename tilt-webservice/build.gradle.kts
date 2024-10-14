plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	war
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "org.heathtech"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
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
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.+")
	implementation("com.fasterxml.jackson.core:jackson-core:2.18.+")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.18.+")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.+")
	implementation("org.jetbrains.exposed:exposed-core:0.55.+")
	implementation("org.jetbrains.exposed:exposed-dao:0.55.+")
	implementation("org.jetbrains.exposed:exposed-jdbc:0.55.+")
	implementation("org.jetbrains.exposed:exposed-java-time:0.55.+")
	implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.55.+")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.6.0")

	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	runtimeOnly("org.xerial:sqlite-jdbc")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
