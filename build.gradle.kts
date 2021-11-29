plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.6.0"
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.14.0"
    id("com.adarshr.test-logger") version "3.1.0"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // third party plugins we want to be propergated to users of ok-bro
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0")
    implementation("com.adarshr:gradle-test-logger-plugin:3.1.0")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.38.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("io.strikt:strikt-core:0.33.0")
}

gradlePlugin {
    val okBro by plugins.creating {
        id = "tech.draeger.ok-bro"
        implementationClass = "tech.draeger.OkBroPlugin"
    }
}

val functionalTestSourceSet = sourceSets.create("functionalTest") {}

gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations.getByName("functionalTestImplementation").extendsFrom(configurations.getByName("testImplementation"))

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    val functionalTest by creating(Test::class) {
        testClassesDirs = functionalTestSourceSet.output.classesDirs
        classpath = functionalTestSourceSet.runtimeClasspath
    }

    val check by getting(Task::class) {
        dependsOn(functionalTest)
    }
}
