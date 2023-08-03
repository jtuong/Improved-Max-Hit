package com.improvedmaxhit;

import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.kit.KitType;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

public class ImprovedMaxHitOverlay extends Overlay {
    private ImprovedMaxHitPlugin plugin;
    private ImprovedMaxHitConfig config;
    private Client client;
    private TooltipManager toolTipManager;
    private ClientThread clientThread;

    private PanelComponent panelComponent = new PanelComponent();
    private ImprovedMaxHitCalculator improvedMaxHitCalculator;

    @Inject
    public ImprovedMaxHitOverlay(ImprovedMaxHitPlugin plugin, ImprovedMaxHitConfig config, Client client, TooltipManager tooltipManager, ClientThread clientThread, ItemManager itemManager) {
        super(plugin);
        this.plugin = plugin;
        this.config = config;
        this.client = client;
        this.clientThread = clientThread;
        this.toolTipManager = tooltipManager;
        getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Improved Max Hit Overlay"));
        this.improvedMaxHitCalculator = new ImprovedMaxHitCalculator(client, clientThread, itemManager);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        this.improvedMaxHitCalculator.calculate();

        return panelComponent.render(graphics);
    }
}
