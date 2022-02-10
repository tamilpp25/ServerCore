package me.tamilpp25.server;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpecialStuff implements Listener, CommandExecutor {
	core plugin;
	public SpecialStuff(core plugin1) {
		this.plugin = plugin1;
	}

	private void fishoflove(Player p, String from, String to){
		ItemStack item = new ItemStack(Material.SALMON);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Fish of love");
		lore.add(ChatColor.GRAY +"This item was given to players ");
		lore.add(ChatColor.GRAY +"who helped test the game! ");
		lore.add(ChatColor.GRAY + "Thank you " + ChatColor.RED + "❤");
		lore.add("");
		lore.add(ChatColor.DARK_GRAY +"To: " + ChatColor.translateAlternateColorCodes('&',"&2" +to));
		lore.add(ChatColor.DARK_GRAY +"From: "+ChatColor.translateAlternateColorCodes('&',"&c[ADMIN] " +from));
		lore.add("");
		lore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "SPECIAL ITEM");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL,2);
		p.getInventory().addItem(item);
	}

	private void createCreativeMind(Player p,String from,String to,int edition){
		ItemStack item = new ItemStack(Material.MAP);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Creative Vision");
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC +"Given to players who's ideas");
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC +"were implemented in the game!");
		lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "thank you for the wonderful ideas " + ChatColor.RED + "❤");
		lore.add("");
		lore.add(ChatColor.DARK_GRAY +"To: " + ChatColor.translateAlternateColorCodes('&', "&b"+to));
		lore.add(ChatColor.DARK_GRAY +"From: "+ChatColor.translateAlternateColorCodes('&',"&c[ADMIN] " +from));
		lore.add(ChatColor.DARK_GRAY + "Edition: #" + edition);
		lore.add("");
		lore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "SPECIAL ITEM");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL,2);
		p.getInventory().addItem(item);
	}

	private void BugHunter(Player p, String from, String to) {
		ItemGenerator item = new ItemGenerator(Material.DIAMOND_HOE,ChatColor.LIGHT_PURPLE + "Bug Hunter",
				List.of(Utils.color("&7This item was given to a player")
				,Utils.color("&7who reported a game breaking bug!"),
				Utils.color("&7What an amazing guy! &6⭐")
				,"",Utils.color("&8To: &b" + to)
				,Utils.color("&8From: &c[ADMIN] " + from),"",Utils.color("&d&lSPECIAL ITEM")),1,true,false);

		p.getInventory().addItem(item);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player)sender;
		if(p.isOp()){
			if(args[0].equalsIgnoreCase("beta_tester")){
				Player target = plugin.getServer().getPlayer(args[1]);
				if(target!=null) {
					fishoflove(target, p.getName(), args[1]);
					p.sendMessage(ChatColor.GREEN + "Gave Fish of love to " + target.getName());
					target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,0.8f);
					target.sendMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + " gave you a " + ChatColor.LIGHT_PURPLE + "Fish of love" + ChatColor.YELLOW + " Special item!");
				}else{
					p.sendMessage(ChatColor.RED + "CANT_FIND_PLAYER");
				}
			}else if(args[0].equalsIgnoreCase("creativemind")) {
				Player target = plugin.getServer().getPlayer(args[1]);
				if (target != null) {
					createCreativeMind(target, p.getName(), args[1], Integer.parseInt(args[2]));
					p.sendMessage(ChatColor.GREEN + "Gave Creative Vision to " + target.getName());
					target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,0.8f);
					target.sendMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + " gave you a " + ChatColor.LIGHT_PURPLE + "Creative Vision " + ChatColor.DARK_GRAY + "(Edition #" + args[2] + ")");
				}else{
					p.sendMessage(ChatColor.RED + "CANT_FIND_PLAYER");
				}
			}else if(args[0].equalsIgnoreCase("bughunter")) {
				Player target = plugin.getServer().getPlayer(args[1]);
				if (target != null) {
					BugHunter(target, p.getName(), args[1]);
					p.sendMessage(ChatColor.GREEN + "Gave Bug Hunter to " + target.getName());
					target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,0.8f);
					target.sendMessage(ChatColor.RED + p.getName() + ChatColor.YELLOW + " gave you a " + ChatColor.LIGHT_PURPLE + "Bug Hunter" + ChatColor.YELLOW + " Special item!");
				}else{
					p.sendMessage(ChatColor.RED + "CANT_FIND_PLAYER");
				}
			}else{                                                 //0        1      2
				p.sendMessage(ChatColor.RED + "/createspecialitem (type) (to) (edition)");
			}
		}
		return true;
	}




	@EventHandler
	public void onCreativemind(InventoryClickEvent e){
		if(e.getInventory().getType().equals(InventoryType.CARTOGRAPHY)){
			if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Creative Vision")){
				e.setResult(Event.Result.DENY);
			}
		}
	}

	@EventHandler
	public void onBugHunter(InventoryClickEvent e){
		if(e.getInventory().getType().equals(InventoryType.SMITHING)){
			if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Bug Hunter")){
				e.setResult(Event.Result.DENY);
			}
		}
	}

	@EventHandler
	public void disableRename(PrepareAnvilEvent e){
		ItemStack item = e.getInventory().getItem(0);
		if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
			if (e.getResult()!= null && e.getResult().hasItemMeta() && !(item.getItemMeta().getDisplayName().equalsIgnoreCase(e.getResult().getItemMeta().getDisplayName()))) {
				e.setResult(new ItemStack(Material.AIR));
			}
		}
	}

	@EventHandler
	public void disableGrindstone(InventoryClickEvent e){
		if(e.getInventory().getType().equals(InventoryType.GRINDSTONE)){
			if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getLore()
					.contains(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "SPECIAL ITEM")){
				e.setResult(Event.Result.DENY);
			}
		}
	}

	@EventHandler
	public void disableRename(PrepareSmithingEvent e){
		ItemStack item = e.getInventory().getItem(0);
		if(item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
			if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Bug Hunter")){
				e.setResult(new ItemStack(Material.AIR));
			}
		}
	}

	@EventHandler
	public void onclick(PlayerInteractEvent e){
		ItemStack item = e.getItem();
		if(item!=null) {
			if (item.hasItemMeta()) {
				if(Objects.requireNonNull(item.getItemMeta()).hasLore()) {
					if (Objects.requireNonNull(item.getItemMeta().getLore()).contains(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "SPECIAL ITEM")) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

}
