package de.daslixou.lioxumining;

import de.daslixou.lioxumining.events.Events;
import de.daslixou.lioxumining.items.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main ins;

    @Override
    public void onEnable() {
        ins = this;

        this.getServer().getPluginManager().registerEvents(new Events(), this);

        LItemWoodenHammer.createRecipe();
        LItemStoneHammer.createRecipe();
        LItemIronHammer.createRecipe();
        LItemGoldHammer.createRecipe();
        LItemDiamondHammer.createRecipe();
        LItemNetheriteHammer.createRecipe();
    }

    @Override
    public void onDisable() {

    }

    public static Main getInstance() {
        return ins;
    }
}
