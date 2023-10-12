package it.easymc.easyfish.utils;

import de.tr7zw.nbtapi.NBTItem;
import it.easymc.easyfish.EasyFish;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Econ {
    public static boolean addMoney(OfflinePlayer player, double money){
        EconomyResponse res = EasyFish.INS.getEconomy().depositPlayer(player, money);
        return res.transactionSuccess();
    }

    public static void addMoney(OfflinePlayer player, double money, String string){
        if (addMoney(player, money)){
            if (player.isOnline()){
                Msg.send((Player) player, string);
            }
        }
    }

    public static void addMoneyFromInv(OfflinePlayer player){
        if (player.isOnline()){
            double price = 0.00;
            int quantity = 0;
            Player playerOnline = player.getPlayer();
            for (ItemStack itemStack : playerOnline.getInventory().getContents()){
                if (itemStack != null){
                    NBTItem nbtItem = new NBTItem(itemStack);
                    if (nbtItem.hasTag(EasyFish.INS.getNbtTagPrice())){
                        price += nbtItem.getDouble(EasyFish.INS.getNbtTagPrice()) * itemStack.getAmount();
                        quantity += itemStack.getAmount();
                        playerOnline.getInventory().remove(itemStack);
                    }
                }
            }
            if (price > 0.00)
                addMoney(player, price, EasyFish.INS.getConfig().getString("msg.successfully-sell")
                        .replace("{price}", formatValue(price))
                        .replace("{quantity}", String.valueOf(quantity))
                );
            else
                Msg.send(playerOnline, EasyFish.INS.getConfig().getString("msg.no-fish-sell"));
        }
    }

    public static String formatValue(double value){
        double m = 1000000.00;
        double k = 1000.00;
        if (value / m > 1){
            return String.format("%.2fm", value/m);
        }
        if (value / k > 1){
            return String.format("%.2fk", value/k);
        }
        return String.format("%.2f", value);
    }
}
