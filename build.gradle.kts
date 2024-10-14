plugins {
	java
	id("java")
	id("jacoco")
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "tech.wendel.swap"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Doc
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
}

jacoco {
	toolVersion = "0.8.10"
	reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
		html.setDestination(file("${buildDir}/jacocoHtml"))
	}

	classDirectories.setFrom(
		files(classDirectories.files.map {
			fileTree(it) {
				exclude("tech/wendel/swap/github/issue/finder/advicers/**")
				exclude("tech/wendel/swap/github/issue/finder/infrastructure/**")
				exclude("tech/wendel/swap/github/issue/finder/domain/exceptions/**")
				exclude("tech/wendel/swap/github/issue/finder/domain/model/**")
			}
		})
	)
}
