import ProjectVersions.unethicaliteVersion

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    `java-library`
    checkstyle
    // 'com.github.johnrengelman.shadow' version '7.1.2'
//    kotlin("jvm") version "1.6.21"
//    kotlin("kapt") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

project.extra["GithubUrl"] = "https://github.com/FredsDevious/plugins"
project.extra["GithubUserName"] = "FredsDevious"
project.extra["GithubRepoName"] = "plugins"

apply<BootstrapPlugin>()

allprojects {
    group = "net.unethicalite"

    project.extra["PluginProvider"] = "fred4106"
    project.extra["ProjectSupportUrl"] = ""
    project.extra["PluginLicense"] = "3-Clause BSD License"

    apply<JavaPlugin>()
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")
//    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        annotationProcessor(Libraries.lombok)
        annotationProcessor(Libraries.pf4j)

        compileOnly("net.unethicalite:http-api:$unethicaliteVersion+")
        compileOnly("net.unethicalite:runelite-api:$unethicaliteVersion+")
        compileOnly("net.unethicalite:runelite-client:$unethicaliteVersion+")
        compileOnly("net.unethicalite.rs:runescape-api:$unethicaliteVersion+")

        compileOnly(Libraries.guice)
        compileOnly(Libraries.javax)
        compileOnly(Libraries.lombok)
        compileOnly(Libraries.pf4j)
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType<AbstractArchiveTask> {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
            dirMode = 493
            fileMode = 420
        }
        shadowJar {
            archiveClassifier.set("shaded")
            manifest.inheritFrom(jar.get().manifest) //will make your shadowJar (produced by jar task) runnable
            // from(sourceSets.main.getOutput)
            // from(project.configurations.runtimeClasspath)
            exclude("**/*.kotlin_metadata")
            exclude("**/*.kotlin_module")
            // exclude("META-INF/maven/**")
            exclude("META-INF/versions/**")
            // exclude("/META-INF/versions/*")
        }
        register<Copy>("releaseToExternalManager"){
            into("${System.getProperty("user.home")}/.openosrs/sideloaded-plugins/")
            from(shadowJar)
            include("*.jar")
        }

        register<Copy>("copyDeps") {
            into("./build/deps/")
            from(configurations["runtimeClasspath"])
        }
    }
}
