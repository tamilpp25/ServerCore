package me.tamilpp25.server;

import me.enesmelda.CustomItems.playerstats;
import me.tamilpp25.server.core;
import me.tamilpp25.testboss.DragonFight.DragonStats;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ChatStarGUI implements Listener {

	private static core plugin;

	public ChatStarGUI(core p) {
		plugin = p;
	}

	public void starGui(Player p) {


		Inventory inv = plugin.getServer().createInventory(null, 54, ChatColor.DARK_GRAY + "Glory Badge");

		ItemStack AdminInv = new ItemStack(Material.BLACK_BANNER);
		ItemMeta adminmeta = AdminInv.getItemMeta();
		;
		adminmeta.setDisplayName(ChatColor.RED + "Glory Badge");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "Click to select a badge!");
		adminmeta.setLore(lore);
		adminmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		AdminInv.setItemMeta(adminmeta);
		AdminInv.addUnsafeEnchantment(Enchantment.DURABILITY, 1);


		inv.setItem(4, AdminInv);
		inv.setItem(19, createColorItem(p, ChatColor.RED, "Red", Material.RED_DYE, 10, checklevel(p, 10)));
		inv.setItem(20, createColorItem(p, ChatColor.GOLD, "Gold", Material.ORANGE_DYE, 20, checklevel(p, 20)));
		inv.setItem(28, createColorItem(p, ChatColor.GREEN, "Green", Material.LIME_DYE, 30, checklevel(p, 30)));
		inv.setItem(29, createColorItem(p, ChatColor.YELLOW, "Yellow", Material.YELLOW_DYE, 40, checklevel(p, 40)));
		inv.setItem(30, createColorItem(p, ChatColor.LIGHT_PURPLE, "Pink", Material.PINK_DYE, 50, checklevel(p, 50)));
		inv.setItem(37, createColorItem(p, ChatColor.WHITE, "White", Material.WHITE_DYE, 60, checklevel(p, 60)));
		inv.setItem(38, createColorItem(p, ChatColor.AQUA, "Aqua", Material.LIGHT_BLUE_DYE, 70, checklevel(p, 70)));
		inv.setItem(24, createColorItem(p, ChatColor.DARK_GREEN, "Dark Green", Material.GREEN_DYE, 80, checklevel(p, 80)));
		inv.setItem(25, createColorItem(p, ChatColor.DARK_RED, "Dark Red", Material.REDSTONE, 90, checklevel(p, 90)));
		inv.setItem(32, createColorItem(p, ChatColor.DARK_AQUA, "Dark Aqua", Material.CYAN_DYE, 100, checklevel(p, 100)));
		inv.setItem(33, createColorItem(p, ChatColor.DARK_PURPLE, "Dark Purple", Material.PURPLE_DYE, 110, checklevel(p, 110)));
		inv.setItem(34, createColorItem(p, ChatColor.DARK_BLUE, "Dark Blue", Material.BLUE_DYE, 120, checklevel(p, 120)));
		inv.setItem(42, createColorItem(p, ChatColor.DARK_GRAY, "Dark Gray", Material.GRAY_DYE, 130, checklevel(p, 130)));
		inv.setItem(43, createColorItem(p, ChatColor.BLACK, "Black", Material.INK_SAC, 150, checklevel(p, 150)));

		ItemStack reset = new ItemStack(Material.PAPER);
		ItemMeta meta = reset.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Reset Badge");
		ArrayList<String> rlore = new ArrayList<>();
		rlore.add(ChatColor.GRAY + "Click this to reset your Badge!");
		meta.setLore(rlore);
		reset.setItemMeta(meta);

		inv.setItem(40,reset);


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
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (plugin.getConfig().contains("glory." + p.getUniqueId())) { //CHECK FOR GLORY UNLOCK
			if (plugin.getConfig().contains("glory." + p.getUniqueId() + ".color") && !(plugin.getConfig().getString("glory." + p.getUniqueId() + ".color").equalsIgnoreCase("None"))) {
				if (plugin.getConfig().contains("glory." + p.getUniqueId() + ".title")&& !(plugin.getConfig().getString("glory." + p.getUniqueId() + ".title").equalsIgnoreCase("None"))) {
					String star_color = plugin.getConfig().getString("glory." + p.getUniqueId() + ".color");
					String title = plugin.getConfig().getString("glory." + p.getUniqueId() + ".title");
					String color_name = plugin.getConfig().getString("glory." + p.getUniqueId() + ".title_color");
					if (!(title.equalsIgnoreCase("None")) && !(star_color.equalsIgnoreCase("None"))) {
						switch (plugin.getConfig().getString("glory." + p.getUniqueId() + ".color")) {
							case "Red":
								if (p.isOp()) {
									e.setFormat(ChatColor.RED + ""  + ChatColor.BOLD +  "[✰] " + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.RED + ""  + ChatColor.BOLD +  "[✰] "+ getTitleColor(color_name) + title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Gold":
								if (p.isOp()) {
									e.setFormat(ChatColor.GOLD + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.GOLD + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Green":
								if (p.isOp()) {
									e.setFormat(ChatColor.GREEN + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.GREEN + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Yellow":
								if (p.isOp()) {
									e.setFormat(ChatColor.YELLOW + ""  + ChatColor.BOLD +  "[✰] " + getTitleColor(color_name) +  title + " - "  + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.YELLOW + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Pink":
								if (p.isOp()) {
									e.setFormat(ChatColor.LIGHT_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + getTitleColor(color_name) +  title + " - "  + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.LIGHT_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + getTitleColor(color_name) +  title + " - "  + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Dark Green":
								if (p.isOp()) {
									e.setFormat(ChatColor.DARK_GREEN + ""  + ChatColor.BOLD +  "[✰] " + getTitleColor(color_name) +  title + " - "  + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.DARK_GREEN + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Dark Red":
								if (p.isOp()) {
									e.setFormat(ChatColor.DARK_RED + ""  + ChatColor.BOLD +  "[✰] " + getTitleColor(color_name) +  title + " - "  + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.DARK_RED + ""  + ChatColor.BOLD +  "[✰] " + getTitleColor(color_name) +  title + " - "  + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Dark Aqua":
								if (p.isOp()) {
									e.setFormat(ChatColor.DARK_AQUA + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.DARK_AQUA + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Dark Purple":
								if (p.isOp()) {
									e.setFormat(ChatColor.DARK_PURPLE + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.DARK_PURPLE + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Dark Blue":
								if (p.isOp()) {
									e.setFormat(ChatColor.DARK_BLUE + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.DARK_BLUE + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Dark Gray":
								if (p.isOp()) {
									e.setFormat(ChatColor.DARK_GRAY + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.DARK_GRAY + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Black":
								if (p.isOp()) {
									e.setFormat(ChatColor.BLACK + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.BLACK + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "White":
								if (p.isOp()) {
									e.setFormat(ChatColor.WHITE + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.WHITE + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
							case "Aqua":
								if (p.isOp()) {
									e.setFormat(ChatColor.AQUA + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								} else {
									e.setFormat(ChatColor.AQUA + ""  + ChatColor.BOLD +  "[✰] "  + getTitleColor(color_name) +  title + " - " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
								}
								break;
						}
					} else {
							switch (plugin.getConfig().getString("glory." + p.getUniqueId() + ".color")) {
								case "Red":
									if (p.isOp()) {
										e.setFormat(ChatColor.RED + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.RED + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Gold":
									if (p.isOp()) {
										e.setFormat(ChatColor.GOLD + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.GOLD + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Green":
									if (p.isOp()) {
										e.setFormat(ChatColor.GREEN + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.GREEN + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Yellow":
									if (p.isOp()) {
										e.setFormat(ChatColor.YELLOW + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.YELLOW + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Pink":
									if (p.isOp()) {
										e.setFormat(ChatColor.LIGHT_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.LIGHT_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Dark Green":
									if (p.isOp()) {
										e.setFormat(ChatColor.DARK_GREEN + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.DARK_GREEN + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Dark Red":
									if (p.isOp()) {
										e.setFormat(ChatColor.DARK_RED + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.DARK_RED + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Dark Aqua":
									if (p.isOp()) {
										e.setFormat(ChatColor.DARK_AQUA + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.DARK_AQUA + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Dark Purple":
									if (p.isOp()) {
										e.setFormat(ChatColor.DARK_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.DARK_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Dark Blue":
									if (p.isOp()) {
										e.setFormat(ChatColor.DARK_BLUE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.DARK_BLUE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Dark Gray":
									if (p.isOp()) {
										e.setFormat(ChatColor.DARK_GRAY + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.DARK_GRAY + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Black":
									if (p.isOp()) {
										e.setFormat(ChatColor.BLACK + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.BLACK + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "White":
									if (p.isOp()) {
										e.setFormat(ChatColor.WHITE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.WHITE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
								case "Aqua":
									if (p.isOp()) {
										e.setFormat(ChatColor.AQUA + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									} else {
										e.setFormat(ChatColor.AQUA + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
									}
									break;
							}
					}
				}else{
					switch (plugin.getConfig().getString("glory." + p.getUniqueId() + ".color")) {
						case "Red":
							if (p.isOp()) {
								e.setFormat(ChatColor.RED + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.RED + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Gold":
							if (p.isOp()) {
								e.setFormat(ChatColor.GOLD + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.GOLD + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Green":
							if (p.isOp()) {
								e.setFormat(ChatColor.GREEN + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.GREEN + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Yellow":
							if (p.isOp()) {
								e.setFormat(ChatColor.YELLOW + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.YELLOW + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Pink":
							if (p.isOp()) {
								e.setFormat(ChatColor.LIGHT_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.LIGHT_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Dark Green":
							if (p.isOp()) {
								e.setFormat(ChatColor.DARK_GREEN + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.DARK_GREEN + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Dark Red":
							if (p.isOp()) {
								e.setFormat(ChatColor.DARK_RED + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.DARK_RED + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Dark Aqua":
							if (p.isOp()) {
								e.setFormat(ChatColor.DARK_AQUA + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.DARK_AQUA + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Dark Purple":
							if (p.isOp()) {
								e.setFormat(ChatColor.DARK_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.DARK_PURPLE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Dark Blue":
							if (p.isOp()) {
								e.setFormat(ChatColor.DARK_BLUE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.DARK_BLUE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Dark Gray":
							if (p.isOp()) {
								e.setFormat(ChatColor.DARK_GRAY + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.DARK_GRAY + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Black":
							if (p.isOp()) {
								e.setFormat(ChatColor.BLACK + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.BLACK + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "White":
							if (p.isOp()) {
								e.setFormat(ChatColor.WHITE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.WHITE + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
						case "Aqua":
							if (p.isOp()) {
								e.setFormat(ChatColor.AQUA + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							} else {
								e.setFormat(ChatColor.AQUA + ""  + ChatColor.BOLD +  "[✰] " + ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
							}
							break;
					}
				}
			}else{
				if (p.isOp()) {
					e.setFormat(ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
				} else {
					e.setFormat(ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
				}
			}
		} else { //NO PREFIX / TITLE
			if (p.isOp()) {
				e.setFormat(ChatColor.RED + "[ADMIN] " + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
			} else {
				e.setFormat(ChatColor.AQUA + p.getName() + ": " + ChatColor.WHITE + e.getMessage());
			}
		}
	}


	@EventHandler
	public void onclick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		InventoryView view = e.getView();
		ItemStack item = e.getCurrentItem();
		if (view.getTitle().equals(ChatColor.DARK_GRAY + "Glory Badge")) {
			e.setCancelled(true);
			if (item!=null &&  item.hasItemMeta() && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() == 4) {
				if (item.getItemMeta().getLore().get(3).equalsIgnoreCase(ChatColor.GREEN + "Click to select!")) {
					String name = item.getItemMeta().getDisplayName();
					switch (ChatColor.stripColor(name)) {
						case "Red star color":
							p.sendMessage(ChatColor.GREEN + "Selected Red star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Red");
							break;
						case "Gold star color":
							p.sendMessage(ChatColor.GREEN + "Selected Gold star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Gold");
							break;
						case "Green star color":
							p.sendMessage(ChatColor.GREEN + "Selected Green star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Green");
							break;
						case "Yellow star color":
							p.sendMessage(ChatColor.GREEN + "Selected Yellow star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Yellow");
							break;
						case "Pink star color":
							p.sendMessage(ChatColor.GREEN + "Selected Pink star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Pink");
							break;
						case "Dark Green star color":
							p.sendMessage(ChatColor.GREEN + "Selected Dark Green star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Dark Green");
							break;
						case "Dark Red star color":
							p.sendMessage(ChatColor.GREEN + "Selected Dark Red star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Dark Red");
							break;
						case "Dark Aqua star color":
							p.sendMessage(ChatColor.GREEN + "Selected Dark Aqua star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Dark Aqua");
							break;
						case "Dark Purple star color":
							p.sendMessage(ChatColor.GREEN + "Selected Dark Purple star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Dark Purple");
							break;
						case "Dark Blue star color":
							p.sendMessage(ChatColor.GREEN + "Selected Dark Blue star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Dark Blue");
							break;
						case "Dark Gray star color":
							p.sendMessage(ChatColor.GREEN + "Selected Dark Gray star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Dark Gray");
							break;
						case "Black star color":
							p.sendMessage(ChatColor.GREEN + "Selected Black star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Black");
							break;
						case "White star color":
							p.sendMessage(ChatColor.GREEN + "Selected White star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "White");
							break;
						case "Aqua star color":
							p.sendMessage(ChatColor.GREEN + "Selected Aqua star color!");
							plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "Aqua");
							break;
					}
					plugin.saveConfig();
				} else if (item!=null && item.getItemMeta().getLore().get(3).startsWith(ChatColor.RED + "Requires")) {
					p.sendMessage(ChatColor.RED + "You have not unlocked this yet!");
				}
			} else if (item!=null && item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Close")) {
				p.closeInventory();
			}
			else if (item!=null && item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Reset Badge")) {
				p.sendMessage(ChatColor.RED + "Successfully reset badge!");
				plugin.getConfig().set("glory." + p.getUniqueId() + ".color", "None");
				plugin.saveConfig();
			}
		}
	}


	public boolean checklevel(Player p, int level_required) {
		playerstats stats = new playerstats();
		int player_level = stats.getLevel(p);
		if (player_level >= level_required) {
			return true;
		} else {
			return false;
		}
	}


	public ItemStack createColorItem(Player p, ChatColor color, String color_name, Material material, int level, boolean has_unlocked) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Changes the color of the star in Chat prefix");
		lore.add(ChatColor.GRAY + "to " + color_name + " turning it into " + color + "[✰]");
		lore.add("");
		if (has_unlocked) {
			lore.add(ChatColor.GREEN + "Click to select!");
		} else {
			lore.add(ChatColor.RED + "Requires level " + level + " to unlock!");
		}
		meta.setLore(lore);
		meta.setDisplayName(color + color_name + " star color");
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack createTitleItem(Player p, String title, ChatColor color, String color_name, Material material, String condition, boolean has_unlocked) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Changes your title in the chat");
		lore.add(ChatColor.GRAY + "to " + color + title);
		lore.add("");
		if (has_unlocked) {
			lore.add(ChatColor.GREEN + "Click to select!");
		} else {
			lore.add(ChatColor.RED + "Requires " + condition + " to unlock!");
		}
		meta.setLore(lore);
		meta.setDisplayName(color + title + " Title");
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}


	public void titleGui(Player p) {
		Inventory inv = plugin.getServer().createInventory(null, 54, ChatColor.DARK_GRAY + "Ranking of the Gods");

		ItemStack AdminInv = new ItemStack(Material.BLACK_BANNER);
		ItemMeta adminmeta = AdminInv.getItemMeta();
		;
		adminmeta.setDisplayName(ChatColor.RED + "Ranking of the Gods");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.DARK_GRAY + "Click to set a title!");
		adminmeta.setLore(lore);
		adminmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		AdminInv.setItemMeta(adminmeta);
		AdminInv.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

		ItemStack reset = new ItemStack(Material.PAPER);
		ItemMeta meta = reset.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Reset Title");
		ArrayList<String> rlore = new ArrayList<>();
		rlore.add(ChatColor.GRAY + "Click this to reset your title!");
		meta.setLore(rlore);
		reset.setItemMeta(meta);


		inv.setItem(4, AdminInv);
		inv.setItem(19, createTitleItem(p, "Reaper", ChatColor.DARK_GRAY, "Dark Gray", Material.NETHERITE_HOE, "10,000 Mob Kills",checkTitleCondition(p,"10,000 Mob Kills")));
		inv.setItem(20, createTitleItem(p, "The Giver", ChatColor.GOLD, "Gold", Material.GOLD_INGOT, "500 presents", checkTitleCondition(p,"500 presents")));
		inv.setItem(21, createTitleItem(p, "Speeeeed", ChatColor.WHITE, "White", Material.FEATHER, "300% base speed", checkTitleCondition(p,"300% base speed")));
		inv.setItem(22, createTitleItem(p, "Powerful", ChatColor.AQUA, "Aqua", Material.DIAMOND_SWORD, "level 25", checkTitleCondition(p,"level 25")));
		inv.setItem(23, createTitleItem(p, "Mighty", ChatColor.DARK_RED, "Dark Red", Material.EXPERIENCE_BOTTLE, "level 50",checkTitleCondition(p,"level 50")));
		inv.setItem(24, createTitleItem(p, "Healthy", ChatColor.RED, "Red", Material.APPLE, "200 base health", checkTitleCondition(p,"200 base health")));
		inv.setItem(25, createTitleItem(p, "Dragon Slayer", ChatColor.DARK_PURPLE, "Dark Purple", Material.BOW, "30 Ancient Dragon kills", checkTitleCondition(p,"30 Ancient Dragon kills")));
		inv.setItem(40, reset);


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


	public ChatColor getTitleColor(String name) {
		switch (name) {
			case "Dark Gray":
				return ChatColor.DARK_GRAY;
			case "Gold":
				return ChatColor.GOLD;
			case "White":
				return ChatColor.WHITE;
			case "Dark Red":
				return ChatColor.DARK_RED;
			case "Aqua":
				return ChatColor.AQUA;
			case "Red":
				return ChatColor.RED;
			case "Dark Purple":
				return ChatColor.DARK_PURPLE;
			default:
				return ChatColor.RESET;
		}
	}

	public boolean checkTitleCondition(Player p, String condition){
		OfflinePlayer op = Bukkit.getPlayer(p.getName());
		playerstats stats = new playerstats();
		switch(condition){
			case "10,000 Mob Kills":
				if(op.getStatistic(Statistic.MOB_KILLS)>=10000) {
					return true;
				}else{
					return false;
				}
			case "500 presents":
				if(plugin.getConfig().getBoolean("admin_titles." + p.getUniqueId() + ".presents")){
					return true;
				}else {
					return false;
				}
			case "300% base speed":
				if(stats.getPlayerSpeed(p)>=0.6) {
					return true;
				}else{
					return false;
				}
			case "level 25":
				if(stats.getLevel(p)>=25) {
					return true;
				}else{
					return false;
				}
			case "level 50":
				if(stats.getLevel(p)>=50) {
					return true;
				}else{
					return false;
				}
			case "200 base health":
				if(stats.getPlayerMaxHealth(p)>=200.0) {
					return true;
				}else{
					return false;
				}
			case "30 Ancient Dragon kills":
				if(DragonStats.getDragonKills(p)>=30) {
					return true;
				}else{
					return false;
				}
			default:
				return false;
		}
	}

	@EventHandler
	public void ontitleclick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		InventoryView view = e.getView();
		ItemStack item = e.getCurrentItem();
		if (view.getTitle().equals(ChatColor.DARK_GRAY + "Ranking of the Gods")) {
			e.setCancelled(true);
			if (item!=null &&item.hasItemMeta() && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() == 4) {
				if (item.getItemMeta().getLore().get(3).equalsIgnoreCase(ChatColor.GREEN + "Click to select!")) {
					String name = item.getItemMeta().getDisplayName();
					if(plugin.getConfig().contains("glory." + p.getUniqueId() + ".color") && !(plugin.getConfig().getString("glory." + p.getUniqueId() + ".color").equalsIgnoreCase("None"))) {
						switch (ChatColor.stripColor(name)) {
							case "The Giver Title":
								p.sendMessage(ChatColor.GREEN + "Selected The Giver Title!");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title", "The Giver");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title_color", "Gold");
								break;
							case "Reaper Title":
								p.sendMessage(ChatColor.GREEN + "Selected Reaper Title!");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title", "Reaper");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title_color", "Dark Gray");
								break;
							case "Speeeeed Title":
								p.sendMessage(ChatColor.GREEN + "Selected Speeeeed Title!");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title", "Speeeeed");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title_color", "White");
								break;
							case "Powerful Title":
								p.sendMessage(ChatColor.GREEN + "Selected Powerful Title!");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title", "Powerful");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title_color", "Aqua");
								break;
							case "Mighty Title":
								p.sendMessage(ChatColor.GREEN + "Selected Mighty Title!");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title", "Mighty");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title_color", "Dark Red");
								break;
							case "Healthy Title":
								p.sendMessage(ChatColor.GREEN + "Selected Healthy Title!");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title", "Healthy");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title_color", "Red");
								break;
							case "Dragon Slayer Title":
								p.sendMessage(ChatColor.GREEN + "Selected Dragon Slayer Title!");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title", "Dragon Slayer");
								plugin.getConfig().set("glory." + p.getUniqueId() + ".title_color", "Dark Purple");
								break;
						}
						plugin.saveConfig();
					}else{
						p.sendMessage(ChatColor.RED + "You need to select a Badge first!");
					}
				} else if (item.getItemMeta().getLore().get(3).startsWith(ChatColor.RED + "Requires")) {
					p.sendMessage(ChatColor.RED + "You have not unlocked this yet!");
				}
			} else if (item!=null &&item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Close")) {
				p.closeInventory();
			} else if (item!=null &&item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Reset Title")) {
				p.sendMessage(ChatColor.RED + "Successfully reset title!");
				plugin.getConfig().set("glory." + p.getUniqueId() + ".title", "None");
				plugin.getConfig().set("glory." + p.getUniqueId() + ".title_color", "None");
			}
		}
	}
}
