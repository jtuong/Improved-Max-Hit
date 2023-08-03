package com.improvedmaxhit;

import net.runelite.api.*;
import net.runelite.api.kit.KitType;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;

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


    public ImprovedMaxHitCalculator(Client client, ClientThread clientThread, ItemManager itemManager) {
        this.client = client;
        this.clientThread = clientThread;
        this.itemManager = itemManager;
    }

    public void calculate() {
        ItemContainer itemContainer = client.getItemContainer(InventoryID.EQUIPMENT);
        printFormattedEquipment(itemContainer);
        System.out.println(getCurrentStrengthBonus(itemContainer));
    }

    public void printFormattedEquipment(ItemContainer itemContainer) {
        StringBuilder currentGear = new StringBuilder();
        for (int i = 0; i < itemContainer.size(); i++) {
            if (i == itemContainer.size() - 1) {
                currentGear.append(getItemName(itemContainer.getItems()[i].getId()));
            } else {
                currentGear.append(getItemName(itemContainer.getItems()[i].getId()) + ", ");
            }
        }
        System.out.println(currentGear);
    }

    public int getCurrentStrengthBonus(ItemContainer itemContainer) {
        int currentStrengthBonus = 0;
        for (Item item : itemContainer.getItems()) {
            currentStrengthBonus += Objects.requireNonNull(itemManager.getItemStats(item.getId(), false)).getEquipment().getStr();
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