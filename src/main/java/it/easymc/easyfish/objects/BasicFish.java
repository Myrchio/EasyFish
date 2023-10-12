package it.easymc.easyfish.objects;

import de.tr7zw.nbtapi.NBTItem;
import it.easymc.easyfish.objects.enums.Rarity;
import it.easymc.easyfish.utils.Msg;
import it.easymc.easyfish.utils.Ranger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class BasicFish {
    private final String name;
    private final Rarity rarity;
    private final ArrayList<String> defaultLore;
    private final Material material;
    private final short durability;
    private final boolean glow;
    private final double defaultPrice;
    private final Ranger weightRange;

    public BasicFish(String name, Rarity rarity, ArrayList<String> defaultLore, Material material, short durability, boolean glow, double defaultPrice, Ranger weightRange) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.rarity = rarity;
        this.defaultLore = Msg.translateList(defaultLore);
        this.material = material;
        this.durability = durability;
        this.glow = glow;
        this.defaultPrice = defaultPrice;
        this.weightRange = weightRange;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ArrayList<String> getDefaultLore() {
        return defaultLore;
    }

    public Material getMaterial() {
        return material;
    }

    public short getDurability() {
        return durability;
    }

    public boolean isGlow() {
        return glow;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public Ranger getWeightRange() {
        return weightRange;
    }

    public double calculateWeight(){
        return (double) Math.round(weightRange.random()*100)/100;
    }

    public NBTItem createCustomItem() {
        return (new SellableFish(this)).createNBTItem();
    }

}
