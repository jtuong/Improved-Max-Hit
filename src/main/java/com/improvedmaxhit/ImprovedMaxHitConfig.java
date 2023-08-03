package com.improvedmaxhit;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import javax.inject.Inject;

@ConfigGroup("example")
public interface ImprovedMaxHitConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Welcome Greeting",
		description = "The message to show to the user when they login"
	)

	default String greeting()
	{
		return "Hello";
	}

}
