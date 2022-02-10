package me.tamilpp25.server;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.enesmelda.CustomItems.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ItemGenerator extends ItemStack {
	public ItemGenerator(Material material, String name, List<String> lore, int amount,boolean glowing,boolean isRecipeItem) {
		super(new ItemStack(material, amount));
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		if(isRecipeItem){
			PersistentDataContainer container = meta.getPersistentDataContainer();
			container.set(new NamespacedKey(Main.getPlugin(Main.class),"RECIPE"), PersistentDataType.STRING,"true");
		}
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		if(glowing){
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			this.setItemMeta(meta);
			this.addUnsafeEnchantment(Enchantment.PROTECTION_FALL,1);
		}else{
			this.setItemMeta(meta);
		}
	}

	public ItemGenerator(String name, List<String> lore, String texture ,String signature, int amount,boolean isRecipeItem) {
		super(new ItemStack(Material.PLAYER_HEAD,amount));
		SkullMeta meta = (SkullMeta) this.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		if(isRecipeItem){
			PersistentDataContainer container = meta.getPersistentDataContainer();
			container.set(new NamespacedKey(Main.getPlugin(Main.class),"RECIPE"), PersistentDataType.STRING,"true");
		}
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", texture,signature));
		Field field;
		try {
			field = meta.getClass().getDeclaredField("profile");
			field.setAccessible(true);
			field.set(meta, profile);
			this.setItemMeta(meta);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			Bukkit.getServer().getLogger().log(Level.SEVERE, "Error! unable to apply texture",e);
		}
	}

}
