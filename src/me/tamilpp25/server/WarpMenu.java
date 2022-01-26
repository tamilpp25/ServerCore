package me.tamilpp25.server;


import Tools.ToolItems;
import magic.MagicItems;
import me.tamilpp25.testboss.DragonFight.DragonLoot;
import me.tamilpp25.testboss.Recipe.CustomRecipe;
import me.tamilpp25.testboss.main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import weapons.WeaponItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WarpMenu implements Listener, CommandExecutor {

	private static core plugin;
	Map<UUID, Long> cooldown = new HashMap<>();

	public WarpMenu(core p) {
		plugin = p;
	}

	@EventHandler
	public void InCombat(EntityDamageEvent e) {
		EntityDamageEvent.DamageCause cause = e.getCause();
		if (e.getEntity() instanceof Player) {
			if (cause != EntityDamageEvent.DamageCause.FALL && cause != EntityDamageEvent.DamageCause.FALLING_BLOCK) {
				Player p = (Player) e.getEntity();
				cooldown.put(p.getUniqueId(), System.currentTimeMillis() + 5000);
			}
		}
	}

	public void warpMenu(Player p) {
		Inventory inv = plugin.getServer().createInventory(null, 54, ChatColor.DARK_GRAY + "Fast Travel");

		ItemStack AdminInv = new ItemStack(Material.ELYTRA);
		ItemMeta adminmeta = AdminInv.getItemMeta();
		;
		adminmeta.setDisplayName(ChatColor.RED + "Warp Menu");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "Click a destination to warp to!");
		adminmeta.setLore(lore);
		adminmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		AdminInv.setItemMeta(adminmeta);
		AdminInv.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

		ItemStack world = new ItemStack(Material.GRASS_BLOCK);
		ItemMeta world_meta = world.getItemMeta();
		world_meta.setDisplayName(ChatColor.GREEN + "World");
		ArrayList<String> world_lore = new ArrayList<>();
		world_lore.add(ChatColor.DARK_GRAY + "/warp world");
		world_lore.add("");
		world_lore.add(ChatColor.WHITE + "Location: " + ChatColor.GREEN + "World");
		world_lore.add(ChatColor.GRAY + "Takes you to the overworld!");
		world_lore.add("");
		world_lore.add(ChatColor.YELLOW + "Click to warp!");
		world_meta.setLore(world_lore);
		world.setItemMeta(world_meta);

		ItemStack End = new ItemStack(Material.OBSIDIAN);
		ItemMeta end_meta = End.getItemMeta();
		end_meta.setDisplayName(ChatColor.DARK_PURPLE + "Dragon's Lair");
		ArrayList<String> end_lore = new ArrayList<>();
		end_lore.add(ChatColor.DARK_GRAY + "/warp drag");
		end_lore.add("");
		end_lore.add(ChatColor.WHITE + "Location: " + ChatColor.DARK_PURPLE + "Dragon's Lair");
		end_lore.add(ChatColor.GRAY + "Takes you to the Dragon's Lair!");
		end_lore.add("");
		end_lore.add(ChatColor.YELLOW + "Click to warp!");
		end_meta.setLore(end_lore);
		End.setItemMeta(end_meta);

		ItemStack End_true = new ItemStack(Material.END_STONE_BRICKS);
		ItemMeta end_true_meta = End_true.getItemMeta();
		end_true_meta.setDisplayName(ChatColor.DARK_PURPLE + "End");
		ArrayList<String> end_true_lore = new ArrayList<>();
		end_true_lore.add(ChatColor.DARK_GRAY + "/warp end");
		end_true_lore.add("");
		end_true_lore.add(ChatColor.WHITE + "Location: " + ChatColor.DARK_PURPLE + "End");
		end_true_lore.add(ChatColor.GRAY + "Takes you to the End Dimension!");
		end_true_lore.add("");
		end_true_lore.add(ChatColor.YELLOW + "Click to warp!");
		end_true_meta.setLore(end_true_lore);
		End_true.setItemMeta(end_true_meta);

		ItemStack Nether = new ItemStack(Material.NETHER_WART_BLOCK);
		ItemMeta Nether_meta = Nether.getItemMeta();
		Nether_meta.setDisplayName(ChatColor.RED + "Nether");
		ArrayList<String> Nether_lore = new ArrayList<>();
		Nether_lore.add(ChatColor.DARK_GRAY + "/warp nether");
		Nether_lore.add("");
		Nether_lore.add(ChatColor.WHITE + "Location: " + ChatColor.RED + "Nether");
		Nether_lore.add(ChatColor.GRAY + "Takes you to the Nether Dimension!");
		Nether_lore.add("");
		Nether_lore.add(ChatColor.YELLOW + "Click to warp!");
		Nether_meta.setLore(Nether_lore);
		Nether.setItemMeta(Nether_meta);

		ItemStack home = new ItemStack(Material.RED_BED);
		ItemMeta home_meta = home.getItemMeta();
		home_meta.setDisplayName(ChatColor.YELLOW + "Your Home");
		ArrayList<String> home_lore = new ArrayList<>();
		home_lore.add(ChatColor.DARK_GRAY + "/warp home");
		home_lore.add("");
		home_lore.add(ChatColor.WHITE + "Location: " + ChatColor.YELLOW + "Your Spawn point");
		home_lore.add(ChatColor.GRAY + "Takes you to your spawn point");
		home_lore.add("");
		home_lore.add(ChatColor.YELLOW + "Click to warp!");
		home_meta.setLore(home_lore);
		home.setItemMeta(home_meta);

		ItemStack moon = new ItemStack(Material.END_STONE);
		ItemMeta moon_meta = moon.getItemMeta();
		moon_meta.setDisplayName(ChatColor.GOLD + "Ancient Moon");
		ArrayList<String> moon_lore = new ArrayList<>();
		moon_lore.add(ChatColor.DARK_GRAY + "/warp moon");
		moon_lore.add("");
		moon_lore.add(ChatColor.WHITE + "Location: " + ChatColor.GOLD + "Ancient Moon");
		moon_lore.add(ChatColor.GRAY + "Takes you to the Ancient Moon!");
		moon_lore.add("");
		moon_lore.add(ChatColor.RED + "Currently Closed!");
		moon_meta.setLore(moon_lore);
		moon.setItemMeta(moon_meta);

		ItemStack coming_soon = new ItemStack(Material.BEDROCK);
		ItemMeta coming_soon_meta = coming_soon.getItemMeta();
		coming_soon_meta.setDisplayName(ChatColor.RED + "Coming Soon!");
		ArrayList<String> coming_soon_lore = new ArrayList<>();
		coming_soon_lore.add("");
		coming_soon_lore.add(ChatColor.RED + "More warps will be added in the future!");
		coming_soon_meta.setLore(coming_soon_lore);
		coming_soon.setItemMeta(coming_soon_meta);


		inv.setItem(4, AdminInv);
		inv.setItem(19, world);
		inv.setItem(21, End);
		inv.setItem(20, End_true);
		inv.setItem(22, moon);
		inv.setItem(23, Nether);
		inv.setItem(24, coming_soon);
		inv.setItem(40, home);

		ItemStack Close = new ItemStack(Material.BARRIER, 1);
		ItemMeta closemeta = Close.getItemMeta();
		closemeta.setDisplayName(ChatColor.RED + "Close");
		Close.setItemMeta(closemeta);
		inv.setItem(49, Close);

		for (int slot = 0; slot < 54; slot++) {
			ItemStack i = inv.getItem(slot);
			if (i == null) {
				ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
				ItemMeta glassmeta = glass.getItemMeta();
				glassmeta.setDisplayName(" ");
				glass.setItemMeta(glassmeta);
				inv.setItem(slot, glass);
			}
		}

		p.openInventory(inv);
	}

	@EventHandler
	public void onclick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		InventoryView view = e.getView();
		ItemStack item = e.getCurrentItem();
		if (view.getTitle().equals(ChatColor.DARK_GRAY + "Fast Travel")) {
			e.setCancelled(true);
			if (item != null &&
					item.hasItemMeta() &&
					item.getItemMeta().hasDisplayName()) {
				if (cooldown.containsKey(p.getUniqueId())) {
					if (cooldown.get(p.getUniqueId()) > System.currentTimeMillis()) {
						p.sendMessage(ChatColor.RED + "Cannot warp while in combat!");
					} else {
						switch (ChatColor.stripColor(item.getItemMeta().getDisplayName())) {
							case "World":
								p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
								p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
								p.sendMessage(ChatColor.DARK_GRAY + "Warping to World...");
								break;
							case "Dragon's Lair":
								p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
								if (!plugin.getConfig().getBoolean("settings.disabled")) {
									if (core.hasAdvancement(p, "end/root")) {
										p.teleport(plugin.getServer().getWorld("world_the_end").getSpawnLocation());
										p.sendMessage(ChatColor.DARK_GRAY + "Warping to Dragon's Lair...");
									} else {
										p.sendMessage(ChatColor.RED + "You have not been to that place before!");
									}
								} else {
									if (p.isOp()) {
										p.teleport(plugin.getServer().getWorld("world_the_end").getSpawnLocation());
										p.sendMessage(ChatColor.DARK_GRAY + "Warping to Dragon's Lair...");
									} else {
										p.sendMessage(ChatColor.RED + "The End island is currently disabled!");
									}
								}
								break;
							case "End":
								if (!plugin.getConfig().getBoolean("settings.disabled")) {
									if (core.hasAdvancement(p, "end/root")) {
										p.teleport(plugin.getServer().getWorld("world_true_end").getSpawnLocation());
										p.sendMessage(ChatColor.DARK_GRAY + "Warping to End...");
									} else {
										p.sendMessage(ChatColor.RED + "You have not been to that place before!");
									}
								} else {
									if (p.isOp()) {
										p.teleport(plugin.getServer().getWorld("world_true_end").getSpawnLocation());
										p.sendMessage(ChatColor.DARK_GRAY + "Warping to End...");
									} else {
										p.sendMessage(ChatColor.RED + "The End island is currently disabled!");
									}
								}
								break;
							case "Ancient Moon":
								p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
								if (p.isOp()) {
									p.teleport(plugin.getServer().getWorld("moon2").getSpawnLocation());
									p.sendMessage(ChatColor.DARK_GRAY + "Warping to Ancient Moon...");
								} else {
									p.sendMessage(ChatColor.RED + "You have not been to that place before!");
								}
								break;
							case "Nether":
								p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
								p.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
								p.sendMessage(ChatColor.DARK_GRAY + "Warping to Nether...");
								break;
							case "Your Home":
								if (p.getBedSpawnLocation() != null) {
									p.sendMessage(ChatColor.DARK_GRAY + "Warping to your home...");
									p.teleport(p.getBedSpawnLocation());
								} else {
									p.sendMessage(ChatColor.RED + "Cant find your bed location :(");
									p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
								}
								break;
							case "Coming Soon!":
								p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
								p.sendMessage(ChatColor.RED + "More warps coming soon!");
								break;
							case "Close":
								p.closeInventory();
								break;
						}
					}

				} else {
					switch (ChatColor.stripColor(item.getItemMeta().getDisplayName())) {
						case "World":
							p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
							p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
							p.sendMessage(ChatColor.DARK_GRAY + "Warping to World...");
							break;
						case "Dragon's Lair":
							p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
							if (!plugin.getConfig().getBoolean("settings.disabled")) {
								if (core.hasAdvancement(p, "end/root")) {
									p.teleport(plugin.getServer().getWorld("world_the_end").getSpawnLocation());
									p.sendMessage(ChatColor.DARK_GRAY + "Warping to Dragon's Lair...");
								} else {
									p.sendMessage(ChatColor.RED + "You have not been to that place before!");
								}
							} else {
								if (p.isOp()) {
									p.teleport(plugin.getServer().getWorld("world_the_end").getSpawnLocation());
									p.sendMessage(ChatColor.DARK_GRAY + "Warping to Dragon's Lair...");
								} else {
									p.sendMessage(ChatColor.RED + "The End island is currently disabled!");
								}
							}
							break;
						case "End":
							if (!plugin.getConfig().getBoolean("settings.disabled")) {
								if (core.hasAdvancement(p, "end/root")) {
									p.teleport(plugin.getServer().getWorld("world_true_end").getSpawnLocation());
									p.sendMessage(ChatColor.DARK_GRAY + "Warping to End...");
								} else {
									p.sendMessage(ChatColor.RED + "You have not been to that place before!");
								}
							} else {
								if (p.isOp()) {
									p.teleport(plugin.getServer().getWorld("world_true_end").getSpawnLocation());
									p.sendMessage(ChatColor.DARK_GRAY + "Warping to End...");
								} else {
									p.sendMessage(ChatColor.RED + "The End island is currently disabled!");
								}
							}
							break;
						case "Ancient Moon":
							p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
							if (p.isOp()) {
								p.teleport(plugin.getServer().getWorld("moon2").getSpawnLocation());
								p.sendMessage(ChatColor.DARK_GRAY + "Warping to Ancient Moon...");
							} else {
								p.sendMessage(ChatColor.RED + "You have not been to that place before!");
							}
							break;
						case "Nether":
							p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
							p.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
							p.sendMessage(ChatColor.DARK_GRAY + "Warping to Nether...");
							break;
						case "Your Home":
							if (p.getBedSpawnLocation() != null) {
								p.sendMessage(ChatColor.DARK_GRAY + "Warping to your home...");
								p.teleport(p.getBedSpawnLocation());
							} else {
								p.sendMessage(ChatColor.RED + "Cant find your bed location :(");
								p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
							}
							break;
						case "Coming Soon!":
							p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
							p.sendMessage(ChatColor.RED + "More warps coming soon!");
							break;
						case "Close":
							p.closeInventory();
							break;
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void WarpCommandCooldown(PlayerCommandPreprocessEvent e){
		if(e.getMessage().startsWith("/warp")){
			if(!(e.getPlayer().getName().equalsIgnoreCase("tamilpp25"))) {
				if (cooldown.containsKey(e.getPlayer().getUniqueId()) && cooldown.get(e.getPlayer().getUniqueId()) > System.currentTimeMillis()) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "Cannot warp while in combat!");
				}
			}
		}
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (args.length != 0) {
			switch (args[0].toUpperCase()) {
				case "END":
					if (!plugin.getConfig().getBoolean("settings.disabled")) {
						if (core.hasAdvancement(p, "end/root")) {
							p.teleport(plugin.getServer().getWorld("world_true_end").getSpawnLocation());
							p.sendMessage(ChatColor.DARK_GRAY + "Warping to End...");
						} else {
							p.sendMessage(ChatColor.RED + "You have not been to that place before!");
						}
					} else {
						if (p.isOp()) {
							p.teleport(plugin.getServer().getWorld("world_true_end").getSpawnLocation());
							p.sendMessage(ChatColor.DARK_GRAY + "Warping to End...");
						} else {
							p.sendMessage(ChatColor.RED + "The End island is currently disabled!");
						}
					}
					break;
				case "DRAG":
					if (!plugin.getConfig().getBoolean("settings.disabled")) {
						if (core.hasAdvancement(p, "end/root")) {
							p.teleport(plugin.getServer().getWorld("world_the_end").getSpawnLocation());
							p.sendMessage(ChatColor.DARK_GRAY + "Warping to Dragon's Lair...");
						} else {
							p.sendMessage(ChatColor.RED + "You have not been to that place before!");
						}
					} else {
						if (p.isOp()) {
							p.teleport(plugin.getServer().getWorld("world_the_end").getSpawnLocation());
							p.sendMessage(ChatColor.DARK_GRAY + "Warping to Dragon's Lair...");
						} else {
							p.sendMessage(ChatColor.RED + "The End island is currently disabled!");
						}
					}
					break;
				case "WORLD":
					p.teleport(plugin.getServer().getWorld("world").getSpawnLocation());
					p.sendMessage(ChatColor.DARK_GRAY + "Warping to World...");
					break;
				case "HOME":
					if (p.getBedSpawnLocation() != null) {
						p.sendMessage(ChatColor.DARK_GRAY + "Warping to your home...");
						p.teleport(p.getBedSpawnLocation());
					} else {
						p.sendMessage(ChatColor.RED + "Cant find your bed location :(");
						p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
					}
					break;
				case "NETHER":
					p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.8f);
					p.teleport(plugin.getServer().getWorld("world_nether").getSpawnLocation());
					p.sendMessage(ChatColor.DARK_GRAY + "Warping to Nether...");
					break;
				case "MOON":
					if (p.isOp()) {
						p.teleport(plugin.getServer().getWorld("moon2").getSpawnLocation());
						p.sendMessage(ChatColor.DARK_GRAY + "Warping to Ancient Moon...");
					} else {
						p.sendMessage(ChatColor.RED + "You have not been to that place before!");
					}
					break;

			}
		} else {
			warpMenu(p);
		}
		return true;
	}

	public static String getPlayerTitle(UUID uuid){
		if(main.getPlugin(main.class).getConfig().contains("glory." + uuid + ".title")){
			return main.getPlugin(main.class).getConfig().getString("glory." + uuid + ".title");
		}else{
			return "None";
		}
	}

	public static String getPlayerTitleColor(UUID uuid){
		if(main.getPlugin(main.class).getConfig().contains("glory." + uuid + ".title_color")){
			return main.getPlugin(main.class).getConfig().getString("glory." + uuid + ".title_color");
		}else{
			return "None";
		}
	}

	public static String getGloryColor(UUID uuid){
		if(main.getPlugin(main.class).getConfig().contains("glory." + uuid + ".color")){
			return main.getPlugin(main.class).getConfig().getString("glory." + uuid + ".color");
		}else{
			return "None";
		}
	}

}
