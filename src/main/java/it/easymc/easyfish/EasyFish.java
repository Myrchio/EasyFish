package it.easymc.easyfish;

import com.avaje.ebeaninternal.server.lib.util.NotFoundException;
import it.easymc.easyfish.commands.EasyFishCommand;
import it.easymc.easyfish.objects.BasicFish;
import it.easymc.easyfish.objects.enums.Rarity;
import it.easymc.easyfish.utils.Ranger;
import it.easymc.easyfish.utils.WeightedObject;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public final class EasyFish extends JavaPlugin {

    public static EasyFish INS;
    private Random random;
    private String nbtTagPrice;
    private Economy economy;
    private ArrayList<BasicFish> basicFishes;
    private ArrayList<WeightedObject> weightedObjects;

    public EasyFish(){
        INS = this;
    }

    @Override
    public void onEnable() {
        loadConfig();
        saveConfig();
        getDependencies();
        getCommands();
        this.random = new Random();
        this.nbtTagPrice = getConfig().getString("nbt.price");
        initialBasicFishes(getConfig().getConfigurationSection("fishes"));
        initialWeightedObjects();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadConfig(){
        saveDefaultConfig();
        getConfig().options().copyHeader(true);
        getConfig().options().copyDefaults(true);
    }

    public void getDependencies(){
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            throw new UnknownDependencyException("Vault does not found");
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            throw new NotFoundException("Economy with Vault does not found!");
        }
        if (rsp.getProvider() == null){
            throw new NotFoundException("Economy with Vault does not found!");
        }
        this.economy = rsp.getProvider();
    }

    public void getCommands(){
        this.getCommand("easyfish").setExecutor(new EasyFishCommand());
    }

    public Random getRandom() {
        return random;
    }

    public String getNbtTagPrice() {
        return nbtTagPrice;
    }

    public Economy getEconomy() {
        return economy;
    }

    public ArrayList<BasicFish> getBasicFishes() {
        return basicFishes;
    }

    public ArrayList<WeightedObject> getWeightedObjects() {
        return weightedObjects;
    }

    private void initialBasicFishes(ConfigurationSection fishesConfig){
        this.basicFishes = new ArrayList<>();
        for (String fishName: fishesConfig.getKeys(false)){
            BasicFish basicFish = new BasicFish(
                    fishesConfig.getString(fishName + ".display-name"),
                    Rarity.getRarity(fishesConfig.getString(fishName + ".rarity")),
                    new ArrayList<>(fishesConfig.getStringList(fishName + ".display-lore")),
                    Material.getMaterial(fishesConfig.getString(fishName + ".material")),
                    fishesConfig.getBoolean(fishName + ".glow"),
                    fishesConfig.getDouble(fishName + ".start-sell-price"),
                    new Ranger(
                            fishesConfig.getDouble(fishName + ".weight.min"),
                            fishesConfig.getDouble(fishName + ".weight.max")
                    )
            );
            this.basicFishes.add(basicFish);
        }
    }
    private void initialWeightedObjects(){
        this.weightedObjects = new ArrayList<>();
        this.weightedObjects = basicFishes.stream().map(
                basicFish -> new WeightedObject(basicFish, basicFish.getRarity().getPercentage())
        ).collect(Collectors.toCollection(ArrayList::new));
    }
}
