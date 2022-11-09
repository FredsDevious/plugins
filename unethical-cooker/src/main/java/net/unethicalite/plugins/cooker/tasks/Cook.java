package net.unethicalite.plugins.cooker.tasks;

import net.runelite.api.AnimationID;
import net.runelite.api.Item;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.TileObject;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.chat.ChatColorType;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Production;
import net.unethicalite.api.widgets.Widgets;
import net.unethicalite.plugins.cooker.CookerPlugin;
import net.unethicalite.plugins.cooker.Meat;

import java.util.function.Predicate;

public class Cook extends CookerTask
{
	public Cook(CookerPlugin context)
	{
		super(context);
	}

	@Override
	public boolean validate()
	{
		return true;
	}

	@Override
	public int execute()
	{
		Meat meat = getConfig().item();
		Item raw = Inventory.getFirst(meat.getRawId());
		if (raw == null)
		{
			if (Bank.isOpen())
			{
				Predicate<Item> rawPredicate = x -> x.getId() == meat.getRawId();
				if (Inventory.contains(rawPredicate.negate()))
				{
					Bank.depositInventory();
					return -2;
				}

				Bank.withdrawAll(meat.getRawId(), Bank.WithdrawMode.ITEM);
				return -2;
			}

			NPC banker = NPCs.getNearest(x -> x.hasAction("Collect"));
			if (banker != null)
			{
				banker.interact("Bank");
				return -3;
			}

			MessageUtils.addMessage("Bank NPC not found.", ChatColorType.HIGHLIGHT);
			return -1;
		}

		Player local = Players.getLocal();
		if (local.getAnimation() == AnimationID.COOKING_RANGE || local.getAnimation() == AnimationID.COOKING_FIRE)
		{
			taskCooldown = getClient().getTickCount() + meat.getCookTicks();
			return -1;
		}

		if (getClient().getTickCount() < taskCooldown)
		{
			return -1;
		}

		TileObject cookingObject = TileObjects.getFirstSurrounding(
				local.getWorldLocation(),
				10,
				x -> x.hasAction("Cook")
		);
		if (cookingObject == null)
		{
			MessageUtils.addMessage("Fire/cooking range not found.", ChatColorType.HIGHLIGHT);
			return -1;
		}


		if (Production.isOpen())
		{
			Widgets.get(WidgetID.MULTISKILL_MENU_GROUP_ID, 14 + meat.getProductionIndex()).interact(0);
			return -meat.getCookTicks();
		}

		cookingObject.interact("Cook");
		return 1000;
	}

	@Override
	public boolean subscribe()
	{
		return true;
	}
}
