package com.improvedmaxhit;

import net.runelite.api.*;
import net.runelite.api.kit.KitType;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.http.api.item.ItemStats;

import javax.inject.Inject;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ImprovedMaxHitCalculator {
    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private ItemManager itemManager;

    private ItemContainer[] currentEquippedItems;

    public ImprovedMaxHitCalculator(Client client, ClientThread clientThread, ItemManager itemManager) {
        this.client = client;
        this.clientThread = clientThread;
        this.itemManager = itemManager;
    }

    public void calculate() {
        ItemContainer itemContainer = client.getItemContainer(InventoryID.EQUIPMENT);
        printFormattedEquipment(itemContainer);

    }

    public void printFormattedEquipment(ItemContainer itemContainer) {
        StringBuilder currentGear = new StringBuilder();
        for (int i = 0; i < itemContainer.size(); i++) {
            String itemName = getItemName(itemContainer.getItems()[i].getId());
            if (!itemName.equals("null")) {
                if (i == itemContainer.size() - 1) {
                    currentGear.append(itemName);
                } else {
                    currentGear.append(itemName + ", ");
                }
            }

        }
        System.out.println(currentGear);
        System.out.println(getCurrentStrengthBonus(itemContainer));
    }

    public int getCurrentStrengthBonus(ItemContainer itemContainer) {
        int currentStrengthBonus = 0;
        for (Item item : itemContainer.getItems()) {
            ItemStats itemStats = itemManager.getItemStats(item.getId(), false);
            if (item != null && itemStats != null) {
                int currItemStr = itemStats.getEquipment().getStr();
                if (currItemStr > 0) {
                    System.out.println(currItemStr + " added from " + getItemName(item.getId()));
                }
                currentStrengthBonus += currItemStr;
            }
        }
        return currentStrengthBonus;
    }

    public String getItemName(int id) {
        return client.getItemDefinition(id).getName();
    }
//    public void calculate() {
//        clientThread.invoke(() -> System.out.println(client.getItemDefinition(client.getLocalPlayer().getPlayerComposition().getEquipmentId(KitType.WEAPON)).getName()));
//    }

}