package de.daslixou.lioxumining.utils;

import de.daslixou.lioxumining.items.AdvancedItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Utils {

    public static BlockFace getBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 100);
        if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding()) return null;
        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);
        return targetBlock.getFace(adjacentBlock);
    }

    public static void BreakNaturally(World w, Location l1, Location l2, ItemStack i, String brokenName) {
        int fromX = smaller(l1.getBlockX(), l2.getBlockX());
        int fromY = smaller(l1.getBlockY(), l2.getBlockY());
        int fromZ = smaller(l1.getBlockZ(), l2.getBlockZ());
        int toX = bigger(l1.getBlockX(), l2.getBlockX());
        int toY = bigger(l1.getBlockY(), l2.getBlockY());
        int toZ = bigger(l1.getBlockZ(), l2.getBlockZ());


        for(int x = fromX; x <= toX; x++) {
            for(int y = fromY; y <= toY; y++) {
                for(int z = fromZ; z <= toZ; z++) {
                    Block b = w.getBlockAt(x, y, z);
                    if(isUsableTool(i, b.getType())) {
                        b.breakNaturally(i);
                        i.setDurability((short) (i.getDurability() + 1));
                        if(i.getDurability() >= i.getType().getMaxDurability()) {
                            AdvancedItemStack ai = new AdvancedItemStack(i);
                            ai.setName(brokenName);
                            ai.setNameAsTranslate(brokenName);
                            ai.setCustomModelData(16);
                            ai.setCustomID(brokenName);
                            w.playSound(b.getLocation(), Sound.ENTITY_ITEM_BREAK, 1F, 1F);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static int smaller(int i, int i2) {
        if(i > i2) {
            return i2;
        }
        return i;
    }

    public static int bigger(int i, int i2) {
        if(i >= i2) {
            return i;
        }
        return i2;
    }

    public static boolean isUsableTool(ItemStack tool, Material block) {
        net.minecraft.server.v1_16_R3.Block nmsBlock = org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers.getBlock(block);
        if (nmsBlock == null) {
            return false;
        }
        net.minecraft.server.v1_16_R3.IBlockData data = nmsBlock.getBlockData();
        return tool != null && tool.getType() != Material.AIR
                && org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers.getItem(tool.getType()).canDestroySpecialBlock(data);
    }

}
