import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.jpa") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
}

group = "com.kakaopay.assignment"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web:2.5.4")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.4")
	implementation("com.h2database:h2:1.4.200")
	implementation("com.zaxxer:HikariCP:5.0.0")
	implementation("commons-codec:commons-codec:1.15")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.4")
	testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
