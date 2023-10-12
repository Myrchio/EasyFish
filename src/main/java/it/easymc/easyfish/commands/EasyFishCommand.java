package it.easymc.easyfish.commands;

import it.easymc.easyfish.EasyFish;
import it.easymc.easyfish.objects.BasicFish;
import it.easymc.easyfish.utils.Econ;
import it.easymc.easyfish.utils.Msg;
import it.easymc.easyfish.utils.WeightedObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EasyFishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("easyfish")){
            //easyfish sellall
            if (sender instanceof Player){
                if (args[0].equalsIgnoreCase("sellall")){
                    if(sender.hasPermission("easyfish.sellall")){
                        Econ.addMoneyFromInv((Player) sender);
                        return true;
                    }
                }
            }
            //easyfish give [player] [quantity]
            if (args[0].equalsIgnoreCase("give") && args.length == 3){
                if (sender.hasPermission("easyfish.admin.give")){
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null){
                        Msg.send(sender, EasyFish.INS.getConfig().getString("msg.not-player"));
                        return true;
                    }
                    int quantity;
                    try{
                        quantity = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e){
                        Msg.send(sender, EasyFish.INS.getConfig().getString("msg.not-integer"));
                        return true;
                    }

                    BasicFish fishChosen = (BasicFish) WeightedObject.choice(EasyFish.INS.getWeightedObjects(), EasyFish.INS.getRandom());
                    if (fishChosen != null){
                        ItemStack itemFish = fishChosen.createCustomItem().getItem();
                        itemFish.setAmount(quantity);
                        if (player.getInventory().firstEmpty() != -1) {
                            player.getInventory().addItem(itemFish);
                        } else {
                            player.getWorld().dropItem(player.getLocation(), itemFish);
                        }

                    }
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("reload")){
                EasyFish.INS.reloadConfig();
                EasyFish.INS.start();
                Msg.send(sender, EasyFish.INS.getConfig().getString("msg.successfully-reloaded"));
                return true;
            }
        }
        Msg.send(sender, EasyFish.INS.getConfig().getString("msg.not-permission"));
        return true;
    }
}
