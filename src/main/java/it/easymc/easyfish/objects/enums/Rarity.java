package it.easymc.easyfish.objects.enums;

import it.easymc.easyfish.EasyFish;
import org.bukkit.ChatColor;

public enum Rarity {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY;

    public String getColor(){
        switch (this) {
            case COMMON:
                return ChatColor.translateAlternateColorCodes('&', EasyFish.INS.getConfig().getString("rarity.common.color"));
            case RARE:
                return ChatColor.translateAlternateColorCodes('&', EasyFish.INS.getConfig().getString("rarity.rare.color"));
            case EPIC:
                return ChatColor.translateAlternateColorCodes('&', EasyFish.INS.getConfig().getString("rarity.epic.color"));
            case LEGENDARY:
                return ChatColor.translateAlternateColorCodes('&', EasyFish.INS.getConfig().getString("rarity.legendary.color"));
        }
        return ChatColor.translateAlternateColorCodes('&', EasyFish.INS.getConfig().getString("rarity.common.color"));  // if rarity doesn't recognize, "COMMON" as default
    }

    public double getPercentage(){
        switch (this) {
            case COMMON:
                return EasyFish.INS.getConfig().getDouble("rarity.common.percentage");
            case RARE:
                return EasyFish.INS.getConfig().getDouble("rarity.rare.percentage");
            case EPIC:
                return EasyFish.INS.getConfig().getDouble("rarity.epic.percentage");
            case LEGENDARY:
                return EasyFish.INS.getConfig().getDouble("rarity.legendary.percentage");
        }
        return EasyFish.INS.getConfig().getDouble("rarity.common.percentage");  // if rarity doesn't recognize, "COMMON" as default
    }

    public String getName(){
        switch (this) {
            case COMMON:
                return EasyFish.INS.getConfig().getString("rarity.common.display-name");
            case RARE:
                return EasyFish.INS.getConfig().getString("rarity.rare.display-name");
            case EPIC:
                return EasyFish.INS.getConfig().getString("rarity.epic.display-name");
            case LEGENDARY:
                return EasyFish.INS.getConfig().getString("rarity.legendary.display-name");
        }
        return EasyFish.INS.getConfig().getString("rarity.common.display-name");  // if rarity doesn't recognize, "COMMON" as default
    }

    public static Rarity getRarity(String string){
        string = string.toUpperCase();
        switch (string) {
            case "COMMON":
                return Rarity.COMMON;
            case "RARE":
                return Rarity.RARE;
            case "EPIC":
                return Rarity.EPIC;
            case "LEGENDARY":
                return Rarity.LEGENDARY;
        }
        return Rarity.COMMON;  // if rarity doesn't recognize, "COMMON" as default
    }
}
