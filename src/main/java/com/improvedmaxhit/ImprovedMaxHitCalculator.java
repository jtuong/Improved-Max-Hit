package com.improvedmaxhit;

import net.runelite.api.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.http.api.item.ItemStats;

import javax.inject.Inject;
import java.util.HashMap;

public class ImprovedMaxHitCalculator {
    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private ItemManager itemManager;

    private ItemContainer[] currentEquippedItems;

    private HashMap<Prayer, Double> prayerBonusMap;

    public ImprovedMaxHitCalculator(Client client, ClientThread clientThread, ItemManager itemManager) {
        this.client = client;
        this.clientThread = clientThread;
        this.itemManager = itemManager;
        prayerBonusMap = new HashMap<>();
        loadPrayerBonuses();
    }

    public void calculate() {
        ItemContainer itemContainer = client.getItemContainer(InventoryID.EQUIPMENT);
        //printFormattedEquipment(itemContainer);
        int currentStrengthLevel = getStrengthLevel();
        double currentPrayerBonus = getPrayerBonus();
        double voidBonus = getVoidBonus(itemContainer);
        System.out.println(voidBonus);
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

    private void loadPrayerBonuses() {
        prayerBonusMap.put(Prayer.BURST_OF_STRENGTH, 1.05);
        prayerBonusMap.put(Prayer.SUPERHUMAN_STRENGTH, 1.1);
        prayerBonusMap.put(Prayer.ULTIMATE_STRENGTH, 1.15);
        prayerBonusMap.put(Prayer.CHIVALRY, 1.18);
        prayerBonusMap.put(Prayer.PIETY, 1.23);
    }

    public double getPrayerBonus() {
        for (Prayer p : prayerBonusMap.keySet()) {
            if (client.isPrayerActive(p)) {
                return prayerBonusMap.get(p);
            }
        }
        return 1;
    }
    public int getStrengthLevel() {
        return client.getBoostedSkillLevel(Skill.STRENGTH);
    }

    public double getVoidBonus(ItemContainer itemContainer) {
        boolean helmet = false;
        boolean top = false;
        boolean bottom = false;
        boolean gloves = false;

        for (Item item : itemContainer.getItems()) {
            int itemId = item.getId();
            if (itemId == 11665) {
                helmet = true;
                continue;
            }
            if (itemId == 8839 || itemId == 13072) {
                top = true;
                continue;
            }
            if (itemId == 8840 || itemId == 13073) {
                bottom = true;
                continue;
            }
            if (itemId == 8842) {
                gloves = true;
            }
        }

        return (helmet && top && bottom && gloves) ? 1.1 : 1.0;
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