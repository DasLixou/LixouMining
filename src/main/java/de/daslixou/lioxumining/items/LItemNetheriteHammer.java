package de.daslixou.lioxumining.items;

import de.daslixou.lioxumining.Main;
import de.daslixou.lioxumining.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class LItemNetheriteHammer {

    private static AdvancedItemStack itemStack;

    public static AdvancedItemStack getItem() {
        if(itemStack == null) {
            itemStack = new AdvancedItemStack(Material.NETHERITE_PICKAXE);
            itemStack.setName("daslixou.lixoumining.litem.netherite_hammer");
            itemStack.setNameAsTranslate("daslixou.lixoumining.litem.netherite_hammer");
            itemStack.setMainID("daslixou.lixoumining.litem.netherite_hammer");
            itemStack.setCustomID("daslixou.lixoumining.litem.netherite_hammer");
            itemStack.setCustomModelData(15);
            itemStack.setAttackDamage(7);

        }
        return itemStack;
    }

    public static void createRecipe() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(), "netherite_hammer");
        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape("NNN", "NNN", " S ");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe);
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.discoverRecipe(key);
        }
    }

    public static String getBrokenName() {
        return "daslixou.lixoumining.litem.netherite_hammer_broken";
    }

    public static void breakBlocks(BlockFace blockFace, Block block, ItemStack item) {
        switch(blockFace) {
            case UP:
                Utils.BreakNaturally(block.getWorld(), block.getLocation().add(-1, 0, -1), block.getLocation().add(1, 0, 1), item, getBrokenName());
                break;
            case DOWN:
                Utils.BreakNaturally(block.getWorld(), block.getLocation().add(-1, 0, -1), block.getLocation().add(1, 0, 1), item, getBrokenName());
                break;
            case SOUTH:
                Utils.BreakNaturally(block.getWorld(), block.getLocation().add(-1, 1, 0), block.getLocation().add(1, -1, 0), item, getBrokenName());
                break;
            case NORTH:
                Utils.BreakNaturally(block.getWorld(), block.getLocation().add(1, 1, 0), block.getLocation().add(-1, -1, 0), item, getBrokenName());
                break;
            case EAST:
                Utils.BreakNaturally(block.getWorld(), block.getLocation().add(0, 1, -1), block.getLocation().add(0, -1, 1), item, getBrokenName());
                break;
            case WEST:
                Utils.BreakNaturally(block.getWorld(), block.getLocation().add(0, 1, 1), block.getLocation().add(0, -1, -1), item, getBrokenName());
                break;
        }
    }

}
