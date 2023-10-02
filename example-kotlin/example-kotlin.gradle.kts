version = "0.0.1"

project.extra["PluginName"] = "Kotlin Example Plugin"
project.extra["PluginDescription"] = ""

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("kapt") version "1.6.21"
}

//apply(plugin = "kotlin")
dependencies {
    kapt(Libraries.pf4j)
}
tasks {
    compileKotlin {
            kotlinOptions.jvmTarget = "11"
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
    }
}
