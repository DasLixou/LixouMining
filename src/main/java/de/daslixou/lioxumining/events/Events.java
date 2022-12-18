package de.daslixou.lioxumining.events;

import de.daslixou.lioxumining.items.*;
import de.daslixou.lioxumining.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class Events implements Listener {

    @EventHandler
    public void on(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(AdvancedItemStack.getCustomID(p.getInventory().getItemInMainHand()) != null) {
            if(AdvancedItemStack.getCustomID(p.getInventory().getItemInMainHand()).equals("daslixou.lixoumining.litem.wooden_hammer")) {
                LItemWoodenHammer.breakBlocks(Utils.getBlockFace(p), e.getBlock(), p.getInventory().getItemInMainHand());
            } else if(AdvancedItemStack.getCustomID(p.getInventory().getItemInMainHand()).equals("daslixou.lixoumining.litem.stone_hammer")) {
                LItemStoneHammer.breakBlocks(Utils.getBlockFace(p), e.getBlock(), p.getInventory().getItemInMainHand());
            } else if(AdvancedItemStack.getCustomID(p.getInventory().getItemInMainHand()).equals("daslixou.lixoumining.litem.iron_hammer")) {
                LItemIronHammer.breakBlocks(Utils.getBlockFace(p), e.getBlock(), p.getInventory().getItemInMainHand());
            } else if(AdvancedItemStack.getCustomID(p.getInventory().getItemInMainHand()).equals("daslixou.lixoumining.litem.golden_hammer")) {
                LItemGoldHammer.breakBlocks(Utils.getBlockFace(p), e.getBlock(), p.getInventory().getItemInMainHand());
            } else if(AdvancedItemStack.getCustomID(p.getInventory().getItemInMainHand()).equals("daslixou.lixoumining.litem.diamond_hammer")) {
                LItemDiamondHammer.breakBlocks(Utils.getBlockFace(p), e.getBlock(), p.getInventory().getItemInMainHand());
            } else if(AdvancedItemStack.getCustomID(p.getInventory().getItemInMainHand()).equals("daslixou.lixoumining.litem.netherite_hammer")) {
                LItemNetheriteHammer.breakBlocks(Utils.getBlockFace(p), e.getBlock(), p.getInventory().getItemInMainHand());
            }
        }
    }

    @EventHandler
    public void on(CraftItemEvent e) {
        e.getWhoClicked().sendMessage("YEAH!");
    }

}
