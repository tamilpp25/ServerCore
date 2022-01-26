package me.tamilpp25.server;

import me.tamilpp25.testboss.Recipe.CustomRecipe;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.minecraft.network.protocol.game.PacketPlayOutOpenBook;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.EnumHand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.libs.org.eclipse.sisu.Priority;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;


public class Events implements Listener {
	private static core plugin;

	public Events(core p) {
		plugin = p;
	}

	@EventHandler
	public void onSwitch(PlayerPortalEvent e){
		if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
			if (!(plugin.getConfig().getBoolean("settings.disabled"))) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.DARK_GRAY + "Warping to End...");
				e.getPlayer().teleport(plugin.getServer().getWorld("world_true_end").getSpawnLocation());
			}else{
				if(!e.getPlayer().isOp()) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(ChatColor.RED + "The End island is currently disabled!");
				}else{
					e.getPlayer().sendMessage(ChatColor.DARK_GRAY + "Warping to End...");
					e.getPlayer().teleport(plugin.getServer().getWorld("world_true_end").getSpawnLocation());
				}
			}
		}
	}


	@EventHandler
	public void ArchangelImmunity(EntityDamageEvent e){
		Entity ent = e.getEntity();
		if(ent instanceof Player) {
			Player p = (Player) ent;
			if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
				if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta()
						.getDisplayName().equalsIgnoreCase(CustomRecipe.archangel.getItemMeta().getDisplayName())){
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void oncmd(PlayerCommandPreprocessEvent e){
		if(e.getMessage().startsWith("/help")){
			if(e.getPlayer().isOp()){
				if(e.getMessage().equalsIgnoreCase("/helpgui")) {
					HelpBook(e.getPlayer());
				}
			}else{
				e.setCancelled(true);
				HelpBook(e.getPlayer());
			}
		}
	}

	public void HelpBook(Player p){
		PlayerConnection connection = ((CraftPlayer)p).getHandle().b;
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		bookMeta.addPage( );


		BaseComponent[] page =  new ComponentBuilder(ChatColor.RED + "" + ChatColor.BOLD + "    LostKidsSMP \n\n" + ChatColor.DARK_GREEN + "Click to select an option: \n\n")
				.create();

		BaseComponent[] bug = new ComponentBuilder(ChatColor.GOLD + "" + ChatColor.BOLD + "Found a Bug\n")
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/channels/902137142422175774/925956766469091368"))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.LIGHT_PURPLE + "Click to report a Bug / Issue!").create()))
				.create();
		BaseComponent[] contact  = new ComponentBuilder(ChatColor.GOLD + "" + ChatColor.BOLD + "Support\n")
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://lustrecrew.net/Contact-Us.html"))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.LIGHT_PURPLE + "Click to contact Admins!").create()))
				.create();
		BaseComponent[] twitch = new ComponentBuilder(ChatColor.GOLD + "" + ChatColor.BOLD + "Twitch Support\n")
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/channels/902137142422175774/915997319869104178/929009500021739540"))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.LIGHT_PURPLE + "Click to view!").create()))
				.create();
		BaseComponent[] wiki = new ComponentBuilder(ChatColor.GOLD + "" + ChatColor.BOLD + "LostKidpedia!\n")
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/LustreCrew/LostKidpedia/wiki/"))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.LIGHT_PURPLE + "Click to visit the Wiki!").create()))
				.create();

		BaseComponent[] twitter = new ComponentBuilder(ChatColor.BLUE + "" + ChatColor.BOLD + "\n\n       Twitter")
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitter.com/LostKidsSMP/"))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Click to get Twitter link!").create()))
				.create();



		BaseComponent[] pagefinal = new ComponentBuilder().append(page)
				.append(contact)
				.append(bug)
				.append(twitch)
				.append(wiki)
				.append(twitter)
				.create();

		bookMeta.spigot().addPage(pagefinal);

		bookMeta.setTitle("Help Menu");
		bookMeta.setAuthor("Lost Kids Creators");
		book.setItemMeta(bookMeta);
		openBook(book,p);
	}

	public void openBook(final ItemStack book, final Player player)
	{
		final int slot = player.getInventory().getHeldItemSlot();
		final ItemStack old = player.getInventory().getItem(slot);
		player.getInventory().setItem(slot, book);
		((CraftPlayer) player).getHandle().b.sendPacket(new PacketPlayOutOpenBook(EnumHand.a));
		player.getInventory().setItem(slot, old);
	}
}
