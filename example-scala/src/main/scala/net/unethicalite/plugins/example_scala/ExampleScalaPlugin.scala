package net.unethicalite.plugins.example_scala

import lombok.extern.slf4j.Slf4j
import net.runelite.api.ItemID

import scala.Option
import scala.util.{Either, Try}
import scala.util.chaining.*
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.language.postfixOps
import net.runelite.client.plugins.{Plugin, PluginDescriptor}
import net.unethicalite.api.items.Inventory
import net.unethicalite.api.plugins.Script
import net.unethicalite.api.query.items.ItemQuery
import net.unethicalite.api.widgets.Widgets
import org.pf4j.Extension

@PluginDescriptor(name = "Example Scala Plugin")
@Extension
class ExampleScalaPlugin extends Plugin {
	private val log = org.slf4j.LoggerFactory.getLogger(classOf[ExampleScalaPlugin])
	val wateringCanQuery: ItemQuery = Inventory.query.ids(
		ItemID.WATERING_CAN1,
		ItemID.WATERING_CAN2,
		ItemID.WATERING_CAN3,
		ItemID.WATERING_CAN4,
		ItemID.WATERING_CAN5,
		ItemID.WATERING_CAN6,
		ItemID.WATERING_CAN7,
		ItemID.WATERING_CAN8)

	val seedsQuery: ItemQuery = Inventory.query.ids(ItemID.LOGAVANO_SEED)

	//override def loop(): Int = 2000
	//
	//override def onStart(scriptArgs: String *): Unit = {
	//	Widgets.get(458, 4).interact("Build")
	//}

	override def startUp(): Unit = {
		wateringCanQuery.results().asScala.foreach(i => {
			log.debug("item: {}", i)
		})
		seedsQuery.results().asScala.foreach(i => {
			log.debug("item: {}", i)
		})
	}

	override def shutDown(): Unit = super.shutDown()
}
