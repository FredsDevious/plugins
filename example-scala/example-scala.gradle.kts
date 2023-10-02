version = "0.0.1"

project.extra["PluginName"] = "Scala Example Plugin"
project.extra["PluginDescription"] = ""

plugins{
    scala
}

dependencies {
    implementation("org.scala-lang:scala3-library_3:3.3.1")
}

tasks {
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
