package de.daslixou.lioxumining.items;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagByte;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagString;

public class AdvancedItemStack extends ItemStack {

    private static ItemStack WATER_BOTTLE;

    private String mainID = "";

    public static ItemStack getWaterBottle() {
        if(WATER_BOTTLE == null) {
            WATER_BOTTLE = new ItemStack(Material.POTION, 1);

            PotionMeta meta = (PotionMeta) WATER_BOTTLE.getItemMeta();
            meta.setDisplayName("§bBotte Of Water §f§l0.25L");
            meta.clearCustomEffects();
            meta.setBasePotionData(new PotionData(PotionType.WATER));
            WATER_BOTTLE.setItemMeta(meta);
        }
        return WATER_BOTTLE;

    }

    public AdvancedItemStack(ItemStack stack) {
        super(stack);
    }

    public AdvancedItemStack(Material type) {
        super(type);
    }

    public AdvancedItemStack(Material type, int amount) {
        super(type, amount);
    }

    @SuppressWarnings("deprecation")
    public AdvancedItemStack(Material type, int amount, int durability) {
        super(type, amount, (short) durability);
    }

    public AdvancedItemStack setName(String name) {
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);
        return this;
    }

    public AdvancedItemStack setNameAsTranslate(String tName) {
        NBTTagCompound display = AdvancedItemStack.JSONtoNBT("{Name:'{\"translate\":\"" + tName + "\",\"italic\":false}'}");
        this.setNBTTag("display", display);
        return this;
    }

    public String getName() {
        return this.getItemMeta().getDisplayName();
    }

    @SuppressWarnings("deprecation")
    public static AdvancedItemStack getSkullOfPlayer(String player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta smeta = (SkullMeta) item.getItemMeta();
        smeta.setOwner(player);
        item.setItemMeta(smeta);
        return new AdvancedItemStack(item);
    }

    public static AdvancedItemStack getSkull(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        if(url.isEmpty())return new AdvancedItemStack(head);

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return new AdvancedItemStack(head);
    }

    public static AdvancedItemStack getColoredLeatherHelmet(org.bukkit.Color c) {
        ItemStack item = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(c);
        item.setItemMeta(meta);
        return new AdvancedItemStack(item);
    }

    public static AdvancedItemStack getColoredLeatherChestplate(org.bukkit.Color c) {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(c);
        item.setItemMeta(meta);
        return new AdvancedItemStack(item);
    }

    public AdvancedItemStack setFromJsom(String json) {
        NBTTagCompound nbtc = AdvancedItemStack.JSONtoNBT(json);
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);

        nmsItem.setTag(nbtc);

        ItemMeta meta = CraftItemStack.getItemMeta(nmsItem);
        this.setItemMeta(meta);
        return this;
    }

    public AdvancedItemStack setNBTTag(String key, NBTBase value) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);

        NBTTagCompound comp = nmsItem.getTag();
        comp.set(key, value);

        nmsItem.setTag(comp);

        ItemMeta meta = CraftItemStack.getItemMeta(nmsItem);
        this.setItemMeta(meta);
        return this;
    }

    public void setNBTTag(String key, String value) {
        setNBTTag(key, NBTTagString.a(value));
    }

    public void removeNBTTag(String key) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);

        NBTTagCompound comp = nmsItem.getTag();

        comp.remove(key);

        nmsItem.setTag(comp);

        ItemMeta meta = CraftItemStack.getItemMeta(nmsItem);
        this.setItemMeta(meta);
    }

    public NBTBase getNBTTag(String key) {
        try {
            net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
            NBTTagCompound comp = nmsItem.getTag();
            return comp.get(key);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNBTTagString(String key) {
        try {
            if(this.hasItemMeta()) {
                net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
                NBTTagCompound comp = nmsItem.getTag();
                String value = comp.getString(key);
                return value.isEmpty() ? null : value;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getNBTTagString(ItemStack i, String key) {
        try {
            if(i.hasItemMeta()) {
                net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
                NBTTagCompound comp = nmsItem.getTag();
                String value = comp.getString(key);
                return value.isEmpty() ? null : value;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public int getNBTTagInt(String key) {
        try {
            if(this.hasItemMeta()) {
                net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
                NBTTagCompound comp = nmsItem.getTag();
                return comp.getInt(key);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public short getNBTTagShort(String key) {
        try {
            if(this.hasItemMeta()) {
                net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
                NBTTagCompound comp = nmsItem.getTag();
                return comp.getShort(key);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public NBTTagCompound getNBTTags() {
        try {
            if(this.hasItemMeta()) {
                net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
                NBTTagCompound comp = nmsItem.getTag();
                return comp;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NBTTagCompound JSONtoNBT(String json) {
        try {
            return MojangsonParser.parse(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AdvancedItemStack setCustomModelData(int i) {
        ItemMeta meta = this.getItemMeta();
        meta.setCustomModelData(i);
        this.setItemMeta(meta);
        return this;
    }

    public int getCustomModelData() {
        if(!this.hasItemMeta()) {
            return 0;
        }
        ItemMeta meta = this.getItemMeta();
        if(!meta.hasCustomModelData()) {
            return 0;
        }
        return meta.getCustomModelData();
    }

    public AdvancedItemStack clearLore() {
        ItemMeta meta = this.getItemMeta();
        List<String> lore = new ArrayList<>();
        meta.setLore(lore);
        this.setItemMeta(meta);
        return this;
    }

    public AdvancedItemStack addLore(String s) {
        ItemMeta meta = this.getItemMeta();
        List<String> lore = meta.getLore();
        if(lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(s);
        meta.setLore(lore);
        this.setItemMeta(meta);
        return this;
    }

    public AdvancedItemStack addSeperatorLine() {
        this.addLore("§r§7------------");

        return this;
    }

    public AdvancedItemStack hideAttributes() {
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.setItemMeta(meta);

        return this;
    }

    public AdvancedItemStack hidePotionEffects() {
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        this.setItemMeta(meta);

        return this;
    }

    public AdvancedItemStack hideUnbreakable() {
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        this.setItemMeta(meta);

        return this;
    }

    public AdvancedItemStack hideDye() {
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        this.setItemMeta(meta);

        return this;
    }

    public AdvancedItemStack removeGlow() {
        net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(this);
        NBTTagCompound tag = null;
        if (nmsStack.hasTag()) {
            tag = nmsStack.getTag();
            tag.remove("ench");
            nmsStack.setTag(tag);
            this.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
        }
        return this;
    }

    public AdvancedItemStack setUnbreakable() {
        ItemMeta meta = this.getItemMeta();
        meta.setUnbreakable(true);
        this.setItemMeta(meta);

        return this;
    }

    public AdvancedItemStack setItemAmount(int amount) {
        this.setAmount(amount);
        return this;
    }

    public AdvancedItemStack setIllegalItemAmount(int amount) {
        System.out.println(this.getNBTTagInt("Count"));
        return this;
    }

    public AdvancedItemStack setLocationOfCompass(Location loc) {
        ItemMeta iMeta = this.getItemMeta();
        CompassMeta cMeta = (CompassMeta) iMeta;
        cMeta.setLodestone(loc);
        this.setItemMeta(cMeta);
        return this;
    }

    public AdvancedItemStack addKey(String key) {
        this.setNBTTag(key, NBTTagByte.a(true));
        return this;
    }

    public AdvancedItemStack setCustomID(String cid) {
        this.setNBTTag("CustomIdentifier", NBTTagString.a(cid));

        return this;
    }

    public String getCustomID() {
        return this.getNBTTagString("CustomIdentifier");
    }

    public static String getCustomID(ItemStack i) {
        return AdvancedItemStack.getNBTTagString(i, "CustomIdentifier");
    }

    public AdvancedItemStack removeCustomID() {
        this.removeNBTTag("CustomIdentifier");

        return this;
    }

    public AdvancedItemStack removeKey(String key) {
        this.setNBTTag(key, NBTTagByte.a(false));
        return this;
    }

    public static boolean hasSimilarKey(ItemStack i1, ItemStack i2, String key) {
        if(i1 == null || i2 == null) {
            return false;
        }
        AdvancedItemStack ai1 = new AdvancedItemStack(i1);
        AdvancedItemStack ai2 = new AdvancedItemStack(i2);
        if(ai1.getNBTTagShort(key) == 1 && ai2.getNBTTagShort(key) == 1) {
            return true;
        }
        return false;
    }

	/*public AdvancedItemStack addToItemList(String id) {
		GetCommand.addItem(id, this);
		return this;
	}*/

    public AdvancedItemStack setMainID(String id) {
        this.mainID = id;

        return this;
    }

    public AdvancedItemStack setAttackDamage(int attackDamage) {
        ItemMeta meta = this.getItemMeta();
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", attackDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        this.setItemMeta(meta);

        return this;
    }

    public int getSlot(Inventory i) {
        for(int s = 0; s < i.getSize(); s++) {
            if(i.getItem(s) != null) {
                AdvancedItemStack ai = new AdvancedItemStack(i.getItem(s));
                if(ai.getNBTTagShort(mainID) == 1) {
                    return s;
                }
            }
        }
        return -1;
    }

    public void setMaxDurability(int maxDurability) {
        try {
            Method h = CraftItemStack.asNMSCopy(this).getItem().getClass().getDeclaredMethod("setMaxDurability", int.class);
            h.setAccessible(true);
            h.invoke(CraftItemStack.asNMSCopy(this).getItem(), maxDurability);
            h.setAccessible(false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void doNothing() {

    }

}
