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
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class LItemWoodenHammer {

    private static AdvancedItemStack itemStack;

    private static List<Material> possibleBreaks;

    public static AdvancedItemStack getItem() {
        if(itemStack == null) {
            itemStack = new AdvancedItemStack(Material.WOODEN_PICKAXE);
            itemStack.setName("daslixou.lixoumining.litem.wooden_hammer");
            itemStack.setNameAsTranslate("daslixou.lixoumining.litem.wooden_hammer");
            itemStack.setMainID("daslixou.lixoumining.litem.wooden_hammer");
            itemStack.setCustomID("daslixou.lixoumining.litem.wooden_hammer");
            itemStack.setCustomModelData(15);
            itemStack.setAttackDamage(3);

        }
        return itemStack;
    }

    public static void createRecipe() {
        NamespacedKey key = new NamespacedKey(Main.getInstance(), "wooden_hammer");
        ShapedRecipe recipe = new ShapedRecipe(key, getItem());
        recipe.shape("WWW", "WWW", " S ");
        recipe.setIngredient('W', new RecipeChoice.MaterialChoice(Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.CRIMSON_PLANKS, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.SPRUCE_PLANKS, Material.WARPED_PLANKS));
        recipe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe);
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.discoverRecipe(key);
        }
    }

    public static String getBrokenName() {
        return "daslixou.lixoumining.litem.wooden_hammer_broken";
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
