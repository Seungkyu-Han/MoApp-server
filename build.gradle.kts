import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	war
	id("org.springframework.boot") version "2.7.17"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "knu"
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	//SWAGGER
	implementation("io.springfox:springfox-swagger2:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.swagger:swagger-annotations:1.5.21")
	implementation("io.swagger:swagger-models:1.5.21")

	//LOMBOK
	compileOnly("org.projectlombok:lombok")

	//DB
	runtimeOnly("com.mysql:mysql-connector-j")

	//JWT
	implementation ("io.jsonwebtoken:jjwt:0.9.1")
//	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")

	//KAKAO gson
	implementation ("com.google.code.gson:gson:2.10.1")

	annotationProcessor("org.projectlombok:lombok")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
