package net.unethicalite.plugins.example_scala

import scala.Option
import scala.util.{Try, Either}
import scala.util.chaining._
import scala.jdk.CollectionConverters._
import scala.jdk.OptionConverters._

import scala.language.postfixOps
import net.runelite.client.plugins.PluginDescriptor
import net.unethicalite.api.plugins.Script
import net.unethicalite.api.widgets.Widgets
import org.pf4j.Extension

@PluginDescriptor(name = "Example Scala Plugin")
@Extension
class ExampleScalaPlugin extends Script {

	override def loop(): Int = 2000

	override def onStart(scriptArgs: String *): Unit = {
		Widgets.get(458, 4).interact("Build")
	}
}
