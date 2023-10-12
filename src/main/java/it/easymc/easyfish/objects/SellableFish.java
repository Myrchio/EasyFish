package it.easymc.easyfish.objects;

import de.tr7zw.nbtapi.NBTItem;
import it.easymc.easyfish.EasyFish;
import it.easymc.easyfish.utils.Msg;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SellableFish {
    private final BasicFish basicFish;
    private final double weight;

    public SellableFish(BasicFish basicFish) {
        this.basicFish = basicFish;
        this.weight = this.basicFish.calculateWeight();
    }

    public BasicFish getBasicFish() {
        return basicFish;
    }

    public ArrayList<String> customLore(){
        ArrayList<String> loreList = Msg.translateList(new ArrayList<>(EasyFish.INS.getConfig().getStringList("default-lore.display-lore")));
        loreList = this.replaceStringsWithRarityAndWeight(loreList);
        if (EasyFish.INS.getConfig().getBoolean("default-lore.first")){
            loreList.addAll(basicFish.getDefaultLore());
        } else {
            int i=0;
            for (String lore : basicFish.getDefaultLore()){
                loreList.add(i, lore);
                i++;
            }
        }
        return loreList;
    }

    public ArrayList<String> replaceStringsWithRarityAndWeight(ArrayList<String> strings){
        for(int i=0; i<strings.size(); i++){
            if (strings.get(i).contains("{rarity}"))
                strings.set(i, strings.get(i).replace("{rarity}", basicFish.getRarity().getColor() + basicFish.getRarity().getName()));
            if (strings.get(i).contains("{weight}"))
                strings.set(i, strings.get(i).replace("{weight}", basicFish.getRarity().getColor() + this.weight));
        }
        return strings;
    }

    public double calculatePrice(){
        return (double) Math.round(basicFish.getDefaultPrice() * (this.weight / basicFish.getWeightRange().getMin())*100)/100;
    }

    public NBTItem createNBTItem(){
        ItemStack itemStack = new ItemStack(basicFish.getMaterial());
        itemStack.setDurability(basicFish.getDurability());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(basicFish.getName());
        itemMeta.setLore(this.customLore());
        if (basicFish.isGlow()){
            itemMeta.addEnchant(Enchantment.LUCK, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setDouble(EasyFish.INS.getNbtTagPrice(), this.calculatePrice());
        return nbtItem;
    }

    public static boolean isValid(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.hasTag(EasyFish.INS.getNbtTagPrice());
    }
}
