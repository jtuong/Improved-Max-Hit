package com.improvedmaxhit;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ImprovedMaxHitPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ExamplePlugin.class);
		RuneLite.main(args);
	}
}