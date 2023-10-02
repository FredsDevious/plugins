version = "0.0.1"

project.extra["PluginName"] = "Kotlin Example Plugin"
project.extra["PluginDescription"] = ""

plugins {
    kotlin("jvm") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.lombok") version "1.6.21"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
}
tasks {
    compileKotlin {
            kotlinOptions.jvmTarget = "1.8"
//            kotlinOptions.j
    }
    jar {
        manifest {
            attributes(mapOf(
                "Plugin-Version" to project.version,
                "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                "Plugin-Provider" to project.extra["PluginProvider"],
//                "Plugin-Dependencies" to nameToId("example-utils"),
                "Plugin-Description" to project.extra["PluginDescription"],
                "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
        exclude("META-INF/versions")
    }
}
