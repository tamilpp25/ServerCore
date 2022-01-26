package me.tamilpp25.server;

import io.netty.util.collection.ByteCollections;
import me.enesmelda.CustomItems.playerstats;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xp.XpEvents;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileViewer implements Listener {

	private static core plugin;
	public HashMap<Player, String> clickmenu = new HashMap<>();

	public ProfileViewer(core p) {
		plugin = p;
	}

	@EventHandler
	public void onclickk(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player && !(CitizensAPI.getNPCRegistry().isNPC(e.getRightClicked()))) {
			Player clicked = (Player) e.getRightClicked();
			Inventory I = plugin.getServer().createInventory(null, 54, ChatColor.DARK_GRAY + clicked.getName() + " | Profile");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(" ");
			lore.add(ChatColor.RED + "Empty!");
			ItemStack iteminhand = clicked.getInventory().getItemInMainHand();
			ItemStack iteminoffhand = clicked.getInventory().getItemInOffHand();
			ItemStack helmet = clicked.getEquipment().getHelmet();
			ItemStack chestplate = clicked.getEquipment().getChestplate();
			ItemStack leggings = clicked.getEquipment().getLeggings();
			ItemStack boots = clicked.getEquipment().getBoots();
			ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
			ItemStack no_boots = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
			ItemMeta bootmeta = no_boots.getItemMeta();
			bootmeta.setDisplayName(ChatColor.YELLOW + "Boots ");
			bootmeta.setLore(lore);
			no_boots.setItemMeta(bootmeta);
			ItemStack no_helmet = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
			ItemMeta helmetmeta = no_helmet.getItemMeta();
			helmetmeta.setDisplayName(ChatColor.YELLOW + "Helmet ");
			helmetmeta.setLore(lore);
			no_helmet.setItemMeta(helmetmeta);
			ItemStack no_chestplate = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
			ItemMeta chestmeta = no_chestplate.getItemMeta();
			chestmeta.setLore(lore);
			chestmeta.setDisplayName(ChatColor.YELLOW + "Chestplate ");
			no_chestplate.setItemMeta(chestmeta);
			ItemStack no_leggings = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
			ItemMeta legmeta = no_leggings.getItemMeta();
			legmeta.setDisplayName(ChatColor.YELLOW + "Leggings ");
			legmeta.setLore(lore);
			no_leggings.setItemMeta(legmeta);
			ItemStack no_iteminhand = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
			ItemMeta inhandmeta = no_iteminhand.getItemMeta();
			inhandmeta.setLore(lore);
			inhandmeta.setDisplayName(ChatColor.YELLOW + "Item in Hand ");
			no_iteminhand.setItemMeta(inhandmeta);
			ItemStack no_iteminohand = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
			ItemMeta inohandmeta = no_iteminohand.getItemMeta();
			inohandmeta.setLore(lore);
			inohandmeta.setDisplayName(ChatColor.YELLOW + "Item in Off Hand");
			no_iteminohand.setItemMeta(inohandmeta);
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			meta.setOwner(clicked.getName());
			meta.setDisplayName(ChatColor.GREEN + clicked.getName());
			ArrayList<String> profilelore = new ArrayList<>();
			playerstats stats = new playerstats();
			XpEvents xp = new XpEvents();

			profilelore.add("");
			profilelore.add(ChatColor.GRAY + "Class: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") +
					stats.getPlayerClass(clicked));
			profilelore.add(ChatColor.GRAY + "Player Level: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") +
					stats.getLevel(clicked));
			profilelore.add(ChatColor.GRAY + "Player Skillpoints: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") +
					stats.getSkillpoints(clicked));
			profilelore.add(ChatColor.GRAY + "Player XP: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") +
					stats.getXP(clicked) + "/" + xp.xpNeededForLevel(stats.getLevel(clicked)));
			String str;
			switch ((str = stats.getPlayerClass(clicked)).hashCode()) {
				case -2100942490:
					if (!str.equals("Impact"))
						break;
					profilelore.add(ChatColor.GRAY + "Class Level: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") +
							stats.getLevelImpact(clicked));
					profilelore.add(ChatColor.GRAY + "Class XP: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") +
							stats.getXpImpactClass(clicked) + "/" + xp.xpNeededForLevel(stats.getLevelImpact(clicked)));
					break;
				case -1975235833:
					if (!str.equals("Mystic"))
						break;
					profilelore.add(ChatColor.GRAY + "Class Level: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getLevelMystic(clicked));
					profilelore.add(ChatColor.GRAY + "Class XP: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getXpMysticClass(clicked) + "/" + xp.xpNeededForLevel(stats.getLevelMystic(clicked)));
					break;
				case 47520061:
					if (!str.equals("Electric"))
						break;
					profilelore.add(ChatColor.GRAY + "Class Level: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getLevelElectric(clicked));
					profilelore.add(ChatColor.GRAY + "Class XP: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getXpElectricClass(clicked) + "/" + xp.xpNeededForLevel(stats.getLevelElectric(clicked)));
					break;
				case 68065995:
					if (!str.equals("Force"))
						break;
					profilelore.add(ChatColor.GRAY + "Class Level: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getLevelForce(clicked));
					profilelore.add(ChatColor.GRAY + "Class XP: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getXpForceClass(clicked) + "/" + xp.xpNeededForLevel(stats.getLevelForce(clicked)));
					break;
				case 73417974:
					if (!str.equals("Light"))
						break;
					profilelore.add(ChatColor.GRAY + "Class Level: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getLevelLight(clicked));
					profilelore.add(ChatColor.GRAY + "Class XP: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getXpLightClass(clicked) + "/" + xp.xpNeededForLevel(stats.getLevelLight(clicked)));
					break;
				case 1806447341:
					if (!str.equals("Darkness"))
						break;
					profilelore.add(ChatColor.GRAY + "Class Level: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getLevelDarkness(clicked));
					profilelore.add(ChatColor.GRAY + "Class XP: " + net.md_5.bungee.api.ChatColor.of("#2E4FE1") + stats.getXpDarknessClass(clicked) + "/" + xp.xpNeededForLevel(stats.getLevelDarkness(clicked)));
					break;
			}
			profilelore.add(ChatColor.GRAY + "Fame: " + ChatColor.LIGHT_PURPLE + stats.getFame(clicked));
			profilelore.add(ChatColor.GRAY + "Loyalty: " + ChatColor.GREEN + stats.getLoyalty(clicked));
			profilelore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + (int)clicked.getHealth());
			profilelore.add(ChatColor.GRAY + "Hunger: " + ChatColor.GOLD + (int)clicked.getSaturation());
			profilelore.add(ChatColor.GRAY + "Vitality: " + ChatColor.GREEN + clicked.getMaxHealth());
			DecimalFormat df = new DecimalFormat("###");
			profilelore.add(ChatColor.GRAY + "Speed: " + ChatColor.AQUA + df.format((clicked.getWalkSpeed() * 500.0F)));
			profilelore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + stats.getPlayerStrength(clicked));
			profilelore.add(ChatColor.GRAY + "Elemental Power: " + ChatColor.YELLOW + stats.getPlayerElementalPower(clicked));
			profilelore.add("");

			meta.setLore(profilelore);
			skull.setItemMeta((ItemMeta) meta);
			this.clickmenu.put(p, clicked.getName());
			if (p.isSneaking()) {
				if (clicked.getEquipment().getBoots() != null) {
					I.setItem(37, boots);
				} else {
					I.setItem(37, no_boots);
				}
				if (clicked.getEquipment().getLeggings() != null) {
					I.setItem(28, leggings);
				} else {
					I.setItem(28, no_leggings);
				}
				if (clicked.getEquipment().getChestplate() != null) {
					I.setItem(19, chestplate);
				} else {
					I.setItem(19, no_chestplate);
				}
				if (clicked.getEquipment().getHelmet() != null) {
					I.setItem(10, helmet);
				} else {
					I.setItem(10, no_helmet);
				}
				if (!(clicked.getEquipment().getItemInMainHand().getType().equals(Material.AIR))) {
					I.setItem(1, iteminhand);
				} else {
					I.setItem(1, no_iteminhand);
				}
				if (!(clicked.getEquipment().getItemInOffHand().getType().equals(Material.AIR))) {
					I.setItem(46, iteminoffhand);
				} else {
					I.setItem(46, no_iteminohand);
				}
				ItemStack Close = new ItemStack(Material.BARRIER, 1);
				ItemMeta closemeta = Close.getItemMeta();
				closemeta.setDisplayName(ChatColor.RED + "Close");
				Close.setItemMeta(closemeta);
				I.setItem(22, skull);
				I.setItem(49, Close);
				for (int slot = 0; slot < 54; slot++) {
					ItemStack i = I.getItem(slot);
					if (i == null) {
						ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
						ItemMeta glassmeta = glass.getItemMeta();
						glassmeta.setDisplayName(" ");
						glass.setItemMeta(glassmeta);
						I.setItem(slot, glass);
					}
				}
				p.openInventory(I);
			}
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Inventory i = e.getClickedInventory();
		Player p = (Player)e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if (e.getView().getTitle().contains("| Profile")) {
			String pname = this.clickmenu.get(p);
			e.setCancelled(true);
			if (item!=null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Close")) {
				p.closeInventory();
				this.clickmenu.clear();
			}
		}
	}

	@EventHandler
	public void onclose(InventoryCloseEvent e) {
		if (e.getView().getTitle().contains("| Profile"))
			this.clickmenu.clear();
	}

}
