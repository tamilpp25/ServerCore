package me.tamilpp25.server;

import gui.MenuItems;
import gui.smpmenu;
import me.enesmelda.CustomItems.playerstats;
import me.tamilpp25.server.TabComplete.warpTabComplete;
import me.tamilpp25.server.TabComplete.sendPacketTabComplete;
import me.tamilpp25.server.TabComplete.setStatTabComplete;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xp.XPItems;
import xp.XpEvents;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class core extends JavaPlugin implements Listener {
	public int count = 0;
	Plugin plugin = this;
	FileConfiguration config = this.getConfig();
	boolean maintenance = false;
	String maintenance_msg = "";
	String maintenance_time = "";

	@Override
	public void onEnable() {
		config.options().copyDefaults(true);
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[CORE] Loaded Config!");
		getServer().getPluginManager().registerEvents(new SpecialStuff(this), this);
		getServer().getPluginManager().registerEvents(new Particles(this), this);
		getServer().getPluginManager().registerEvents(new Events(this), this);
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new WarpMenu(this), this);
		getServer().getPluginManager().registerEvents(new ProfileViewer(this), this);
		getServer().getPluginManager().registerEvents(new ChatStarGUI(this), this);
		getCommand("warp").setExecutor(new WarpMenu(this));

		getCommand("warp").setTabCompleter(new warpTabComplete());
		getCommand("setstat").setTabCompleter(new setStatTabComplete());
		getCommand("sendpacket").setTabCompleter(new sendPacketTabComplete());

		//new WorldCreator("world_true_end").environment(World.Environment.THE_END).createWorld();
		//new WorldCreator("moon2").environment(World.Environment.NORMAL).createWorld();
		//new WorldCreator("buildtest").environment(World.Environment.NORMAL).createWorld();

		Objects.requireNonNull(this.getCommand("createspecialitem")).setExecutor(new SpecialStuff(this));


	}

	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.RED + "[CORE] unloaded Config!");
	}

	@EventHandler
	public void on(ServerListPingEvent event) {
		if(getServer().getPort() == 25573) {
			event.setMotd(ChatColor.RED + "Maintenance");
		}else{
			if (maintenance) {
				event.setMotd(ChatColor.RED + "Maintenance Mode" + "\n" + ChatColor.RED + "ETA: " + ChatColor.GOLD + maintenance_time);
			} else {
				event.setMotd(ChatColor.translateAlternateColorCodes('&', "&6&l[&5Patch 0.2&6]&f~&7&l| &3LostKidsSMP-RPG&7&l |\n&b&lTwitter:&r&b @LostKidsSMP"));
			}
		}
	}

	public void restarter() {
		(new BukkitRunnable() {
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					if (!(p.isOp())) {
						p.kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + "Important" + "\n" +
								"\n" + ChatColor.GRAY + "Server is currently in maintenance mode!" + "\n" +
								"\n" + ChatColor.GRAY + "Reason: " + ChatColor.GOLD + maintenance_msg +
								"\n" + ChatColor.GRAY + "ETA : " + ChatColor.GOLD + maintenance_time +
								"\n" +
								"\n" + ChatColor.GRAY + "For latest updates:  " + ChatColor.DARK_GRAY + "#❀-announcements" +
								ChatColor.translateAlternateColorCodes('&', "&7 on &9Discord!"));
					}
				}
			}
		}).runTaskLater((Plugin) this, 320L);
	}

	public void restarter_stop() {
		(new BukkitRunnable() {
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					p.kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + "Important" +
							"\n\n" + ChatColor.GRAY + "Server is currently restarting");

				}
				getServer().dispatchCommand((CommandSender) getServer().getConsoleSender(), "stop");
			}

		}).runTaskLater((Plugin) this, 320L);
	}

	@EventHandler
	public void onconnect(PlayerLoginEvent e) {
		if (maintenance) {
			if (!e.getPlayer().isOp()) {
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Important" +
						"\n" +
						"\n" + ChatColor.RED + "Server is currently in maintenance mode!" + "\n" +
						"\n" + ChatColor.GRAY + "Reason: " + ChatColor.GOLD + maintenance_msg +
						"\n" + ChatColor.GRAY + "ETA : " + ChatColor.GOLD + maintenance_time +
						"\n" +
						"\n" + ChatColor.GRAY + "For latest updates:  " + ChatColor.DARK_GRAY + "#❀-announcements" +
						ChatColor.translateAlternateColorCodes('&', "&7 on &9Discord!"));
			}
		}
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gmc")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage(ChatColor.GREEN + "Gamemode set to CREATIVE");
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("gms")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(ChatColor.GREEN + "Gamemode set to SURVIVAL");
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("gmsp")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				p.setGameMode(GameMode.SPECTATOR);
				p.sendMessage(ChatColor.GREEN + "Gamemode set to SPECTATOR");
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("gmadv")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				p.setGameMode(GameMode.ADVENTURE);
				p.sendMessage(ChatColor.GREEN + "Gamemode set to ADVENTURE");
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}

		if (cmd.getName().equalsIgnoreCase("count")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				p.sendMessage(ChatColor.GRAY + "Displaying...");
				for (Player all : getServer().getOnlinePlayers()) {
					counter(all, Integer.parseInt(args[0]));
				}
				new BukkitRunnable() {

					@Override
					public void run() {
						for (Player all : getServer().getOnlinePlayers()) {
							Firework fw = (Firework) p.getWorld().spawnEntity(all.getLocation(), EntityType.FIREWORK);
							FireworkMeta meta = fw.getFireworkMeta();
							meta.setPower(2);
							meta.addEffect(FireworkEffect.builder()
									.withColor(Color.BLUE).flicker(true)
									.withFade(Color.GREEN).flicker(true)
									.withTrail()
									.with(FireworkEffect.Type.BALL)
									.build());
							fw.setFireworkMeta(meta);
						}
					}
				}.runTaskLater(this, Long.parseLong(args[0]) * 20);
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that");
			}
		}

		if (cmd.getName().equalsIgnoreCase("settings")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("silentjoin")) {
						if (config.getBoolean("Silent_join." + p.getUniqueId())) {
							p.sendMessage(ChatColor.RED + "Silent join has been disabled!");
							config.set("Silent_join." + p.getUniqueId(), false);
							saveConfig();
						} else {
							p.sendMessage(ChatColor.GREEN + "Silent join has been enabled!");
							config.set("Silent_join." + p.getUniqueId(), true);
							saveConfig();
						}
					} else {
						p.sendMessage(ChatColor.RED + "Invalid Usage! /settings (silentjoin)");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Missing Arguments!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}

		}

		if (cmd.getName().equalsIgnoreCase("refreshmana")) {
			if (sender.isOp()) {
				playerstats stats = new playerstats();
				stats.setPlayerCurrentElementalPower((Player) sender, stats.getPlayerElementalPower((Player) sender));
				sender.sendMessage(ChatColor.GREEN + "Refreshed Mana");
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("skillpoints")) {
			Player p = (Player) sender;
			playerstats stats = new playerstats();
			int skillpts = stats.getSkillpoints(p);
			p.sendMessage(ChatColor.GREEN + "Your current skill points: " + ChatColor.GOLD + skillpts);
		}
		if (cmd.getName().equalsIgnoreCase("setpoints")) {
			Player p = (Player) sender;
			if (sender.isOp()) {
				playerstats stats = new playerstats();
				int skillpts = stats.getSkillpoints(p);
				int newpoint = skillpts + Integer.parseInt(args[0]);
				p.sendMessage("New skill pts = " + newpoint);
				stats.setSkillpoints(p, newpoint);
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("resetstats")) {
			Player p = (Player) sender;
			if (sender.isOp()) {
				playerstats stats = new playerstats();
				stats.setPlayerMaxHealth(p, 20.0D);
				stats.setPlayerElementalPower(p, 100.0D);
				stats.setPlayerStrength(p, 0);
				stats.setLevel(p, 1);
				stats.setPreviousLevel(p, 1);
				stats.setXP(p, 0L);
				stats.setSkillpoints(p, 0);
				stats.setFame(p, 0);
				stats.setXpLightClass(p, 0L);
				stats.setLevelLight(p, 1);
				stats.setXpDarknessClass(p, 0L);
				stats.setLevelDarkness(p, 1);
				stats.setXpElectricClass(p, 0L);
				stats.setLevelElectric(p, 1);
				stats.setXpForceClass(p, 0L);
				stats.setLevelForce(p, 1);
				stats.setXpMysticClass(p, 0L);
				stats.setLevelMystic(p, 1);
				stats.setXpImpactClass(p, 0L);
				stats.setLevelImpact(p, 1);
				stats.setLoyalty(p, 0);
				p.setWalkSpeed(0.2F);
				p.sendMessage(ChatColor.GREEN + "RESET_STATS_SUCCESS");
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("addexp")) {
			Player p = (Player) sender;
			if (sender.isOp()) {
				playerstats stats = new playerstats();

				XpEvents xp = new XpEvents();
				xp.addXp(p, Long.parseLong(args[0]));

				p.sendMessage(ChatColor.GREEN + "Current EXP: " + stats.getXP(p));
				p.sendMessage(ChatColor.GREEN + "Current Level: " + stats.getLevel(p));
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("resetxp")) {
			Player p = (Player) sender;
			if (sender.isOp()) {
				playerstats stats = new playerstats();

				stats.setXP(p, 1);
				stats.setLevel(p, 1);
				p.sendMessage(ChatColor.GREEN + "Current EXP: " + stats.getXP(p));
				p.sendMessage(ChatColor.GREEN + "Current Level: " + stats.getLevel(p));
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("setstat")) {
			playerstats stats = new playerstats();
			Player p = (Player) sender;
			if (p.isOp()) {
				Player target = getServer().getPlayer(args[2]);
				String methods = args[0];
				if (args[1] != null) {
					switch (methods.toUpperCase()) {
						case "VITALITY":
							stats.setPlayerMaxHealth(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set Max HP to " + args[1] + " for " + target.getName());
							break;
						case "SPEED":
							int multiplier = Integer.parseInt(args[1]);
							float speed = (float) (0.2 * multiplier / 100);
							stats.setPlayerSpeed(target, speed);
							p.sendMessage(ChatColor.GREEN + "Set speed to " + args[1] + "%" + " for " + target.getName());
							break;
						case "ELEMENTAL_POWER":
							stats.setPlayerElementalPower(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set Max Elemental power to " + args[1] + " for " + target.getName());
							break;
						case "MANA":
							stats.setPlayerCurrentElementalPower(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set Current mana to " + args[1] + " for " + target.getName());
							break;
						case "STRENGTH":
							stats.setPlayerStrength(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set Strength to " + args[1] + " for " + target.getName());
							break;
						case "FAME":
							stats.setFame(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set Fame to " + args[1] + " for " + target.getName());
							break;
						case "LOYALTY":
							stats.setLoyalty(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set Loyalty to " + args[1] + " for " + target.getName());
							break;
						case "SKILL_POINT":
							stats.setSkillpoints(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set Skill point to " + args[1] + " for " + target.getName());
							break;
						case "XP":
							stats.setXP(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set XP to " + args[1] + " for " + target.getName());
							break;
						case "LEVEL":
							stats.setLevel(target, Integer.parseInt(args[1]));
							p.sendMessage(ChatColor.GREEN + "Set Level to " + args[1] + " for " + target.getName());
							break;
						case "CLASS_LEVEL":
							switch(args[3].toUpperCase()){
								case "LIGHT"->{
									stats.setLevelLight(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set LIGHT Class Level to " + args[1] + " for " + target.getName());
								}
								case "DARKNESS"->{
									stats.setLevelDarkness(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set DARKNESS Class Level to " + args[1] + " for " + target.getName());
								}
								case "ELECTRIC"->{
									stats.setLevelElectric(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set ELECTRIC Class Level to " + args[1] + " for " + target.getName());
								}
								case "FORCE"->{
									stats.setLevelForce(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set FORCE Class Level to " + args[1] + " for " + target.getName());
								}
								case "MYSTIC"->{
									stats.setLevelMystic(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set MYSTIC Class Level to " + args[1] + " for " + target.getName());
								}
								case "IMPACT"->{
									stats.setLevelLight(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set IMPACT Class Level to " + args[1] + " for " + target.getName());
								}
								default-> {
									p.sendMessage(ChatColor.RED + "Usage: /setstat CLASS_LEVEL (amount) (player) (CLASS_NAME)");
								}
							}
							break;
						case "CLASS_XP":
							switch(args[3].toUpperCase()){
								case "LIGHT"->{
									stats.setXpLightClass(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set LIGHT Class XP to " + args[1] + " for " + target.getName());
								}
								case "DARKNESS"->{
									stats.setXpDarknessClass(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set DARKNESS Class XP to " + args[1] + " for " + target.getName());
								}
								case "ELECTRIC"->{
									stats.setXpElectricClass(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set ELECTRIC Class XP to " + args[1] + " for " + target.getName());
								}
								case "FORCE"->{
									stats.setXpForceClass(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set FORCE Class XP to " + args[1] + " for " + target.getName());
								}
								case "MYSTIC"->{
									stats.setXpMysticClass(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set MYSTIC Class XP to " + args[1] + " for " + target.getName());
								}
								case "IMPACT"->{
									stats.setXpImpactClass(target, Integer.parseInt(args[1]));
									p.sendMessage(ChatColor.GREEN + "Set IMPACT Class XP to " + args[1] + " for " + target.getName());
								}
								default-> {
									p.sendMessage(ChatColor.RED + "Usage: /setstat CLASS_XP (amount) (player) (CLASS_NAME)");
								}
							}

							break;
					}
				}
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that");
			}
		}
		if (cmd.getName().equalsIgnoreCase("calcrng")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				//light = yellow , darkness = purple , electric = yellow , force = blue , mystic = green , Impact = red
				//Particles.spiral_particle_static(p, Color.RED, 0.8, 90);
				/*
				Particles.spiral_particle_loop(p, Particle.valueOf(args[0].toUpperCase()), Integer.parseInt(args[1]));
				ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(),EntityType.ARMOR_STAND);
				stand.setSmall(true);
				stand.setCollidable(true);
				p.getWorld().getBlockAt(p.getLocation()).setType(Material.BARRIER);
				stand.setPassenger(p);

				 */
				p.sendMessage(ChatColor.DARK_GRAY + "Running RNG Calculations for fishing...");
				int greatcatch = 0;
				int goodcatch = 0;
				int legendarycatch = 0;
				int normalcatch = 0;
				int enter_pot_rng = 0;

				int counting = Integer.parseInt(args[0]);
				for (int i = 0; i < counting; i++) {
					int rng = new Random().nextInt(100);
					if (rng < Integer.parseInt(args[1])) {
						enter_pot_rng++;
						int rng_internal = new Random().nextInt(10000);
						if (rng_internal < 100) {
							greatcatch++;
						} else if (rng_internal > 651 && rng_internal < 2652) {
							goodcatch++;
						} else if (rng_internal > 600 && rng_internal < 651) {
							legendarycatch++;
						} else {
							normalcatch++;
						}
					}
				}
				int total_rng = goodcatch + greatcatch + legendarycatch;
				p.sendMessage(ChatColor.YELLOW + "-----Fishing RNG Simulation Success-----");
				p.sendMessage("");
				p.sendMessage(ChatColor.GREEN + "Total Simulation: " + ChatColor.GOLD + counting);
				p.sendMessage(ChatColor.GREEN + "Chance for Pot Drop: " + ChatColor.DARK_AQUA + args[1] + "%");
				p.sendMessage("");
				p.sendMessage(ChatColor.BLUE + "Nice Catch Rng: " + ChatColor.GOLD + goodcatch + ChatColor.DARK_GRAY + " / " + 100 * goodcatch / enter_pot_rng + "%");
				p.sendMessage(ChatColor.DARK_PURPLE + "Great Catch Rng: " + ChatColor.GOLD + greatcatch + ChatColor.DARK_GRAY + " / " + 100 * greatcatch / enter_pot_rng + "%");
				p.sendMessage(ChatColor.GOLD + "Legendary Catch Rng: " + ChatColor.GOLD + legendarycatch + ChatColor.DARK_GRAY + " / " + 100 * legendarycatch / enter_pot_rng + "%");
				p.sendMessage(ChatColor.WHITE + "Normal Catch Rng: " + ChatColor.GOLD + normalcatch + ChatColor.DARK_GRAY + " / " + 100 * normalcatch / (enter_pot_rng) + "%");
				p.sendMessage("");
				p.sendMessage(ChatColor.GREEN + "Total rng count: " + ChatColor.GOLD + total_rng);
				p.sendMessage(ChatColor.GREEN + "Total rng count %: " + ChatColor.DARK_PURPLE + 100 * (total_rng) / counting);
				p.sendMessage(ChatColor.GREEN + "Total Pots Dropped: " + ChatColor.BLUE + (total_rng + normalcatch));
				p.sendMessage("");
				p.sendMessage(ChatColor.RED + "-----End of Analytics-----");
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}

		if (cmd.getName().equalsIgnoreCase("players")) {
			if (sender.isOp() || sender instanceof ConsoleCommandSender) {
				CommandSender pl = (CommandSender) sender;
				pl.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");
				pl.sendMessage(ChatColor.GOLD + "      Players List " + ChatColor.RED + "(Debug)");
				pl.sendMessage("");
				for (Player all : getServer().getOnlinePlayers()) {
					if (all.isOp()) {
						pl.sendMessage(ChatColor.RED + all.getName() + " " + ChatColor.YELLOW + "is in " + all.getWorld().getName());
					} else {
						pl.sendMessage(ChatColor.AQUA + all.getName() + " " + ChatColor.YELLOW + "is in " + all.getWorld().getName());
					}
				}
				pl.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "--------------------------------------");
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("smpclear")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				if (args.length == 0) {
					p.getInventory().clear();
					p.getInventory().setItem(8, MenuItems.MagicalGlobe);
					p.sendMessage(ChatColor.GREEN + "Successfully cleared your Inventory");
				} else {
					Player target = getServer().getPlayer(args[0]);
					if (target != null) {
						target.getInventory().clear();
						target.getInventory().setItem(8, MenuItems.MagicalGlobe);
						p.sendMessage(ChatColor.GREEN + "Cleared Inventory of " + target.getName());
					} else {
						p.sendMessage(ChatColor.RED + "Cant find player by the name " + args[0]);
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}

		if (cmd.getName().equalsIgnoreCase("maintenance")) {
			if (sender.isOp() || sender instanceof ConsoleCommandSender) {
				CommandSender p = sender;
				if (args.length != 0) {
					if (args[0].equalsIgnoreCase("enable")) {
						if (!(args[1].isEmpty()) && !(args[2]).isEmpty()) {
							p.sendMessage(ChatColor.RED + "Enabling Maintenance mode...");
							for (Player all : getServer().getOnlinePlayers()) {
								all.sendTitle(ChatColor.GREEN + "Server Reboot", ChatColor.YELLOW + "For a Game Update!" + ChatColor.RED + " (in 15s)", 20, 80, 20);
								all.sendMessage(ChatColor.RED + "[IMPORTANT] " + ChatColor.GREEN + " Server Rebooting: " + ChatColor.YELLOW + "For a game Update!" + ChatColor.RED + " (in 15s)");
								maintenance_msg = args[2];
								maintenance_time = args[1];
								restarter();
							}
							maintenance = true;
						} else {
							p.sendMessage(ChatColor.RED + "Usage: /maintenance enable (time) (reason)");
						}
					} else {
						if (args[0].equalsIgnoreCase("disable")) {
							p.sendMessage(ChatColor.RED + "Maintenance mode disabled!");
							maintenance = false;
							maintenance_msg = "";
							maintenance_time = "";
						} else if (args[0].equalsIgnoreCase("restart")) {
							for (Player all : getServer().getOnlinePlayers()) {
								all.sendTitle(ChatColor.GREEN + "Server Reboot", ChatColor.YELLOW + "For a Game Update!" + ChatColor.RED + " (in 15s)", 20, 80, 20);
								all.sendMessage(ChatColor.RED + "[IMPORTANT] " + ChatColor.GREEN + " Server Rebooting: " + ChatColor.YELLOW + "For a game Update!" + ChatColor.RED + " (in 15s)");
								restarter_stop();
							}
						}
					}
				} else {
					p.sendMessage(ChatColor.RED + "Error! Missing Arguments");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("smpmenu")) {
			smpmenu menu = new smpmenu();
			menu.smp_gui((Player) sender);
		}
		if (cmd.getName().equalsIgnoreCase("suicide")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(ChatColor.RED + "You took your own life...");
				p.setHealth(0);
			}
		}
		if (cmd.getName().equalsIgnoreCase("getxppot")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				p.getInventory().addItem(XPItems.decent_xp_pot);
				p.getInventory().addItem(XPItems.giant_xp_pot);
				p.getInventory().addItem(XPItems.ultimate_xp_pot);
				//p.damage(10,p);
			}
		}
		if (cmd.getName().equalsIgnoreCase("serversettings")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				if (args.length != 0) {
					if (args[0].equalsIgnoreCase("end")) {
						if (getConfig().contains("settings.disabled")) {
							if (getConfig().getBoolean("settings.disabled")) {
								getConfig().set("settings.disabled", false);
								p.sendMessage(ChatColor.GREEN + "End Island Enabled");
								saveConfig();
							} else {
								getConfig().set("settings.disabled", true);
								p.sendMessage(ChatColor.RED + "End Island Disabled");
								saveConfig();
							}
						} else {
							getConfig().set("settings.disabled", false);
							saveConfig();
						}
					}
				} else {
					p.sendMessage(ChatColor.RED + "Error Missing Arguments");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("resetskilltree")) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
				/*
				playerstats stats = new playerstats();
				stats.setPlayerMaxHealth(p, 20.0D);
				stats.setPlayerElementalPower(p, 100.0D);
				stats.setPlayerStrength(p, 0);
				stats.setPlayerSpeed(p,0.2F);
				stats.setSkillpoints(p,stats.getLevel(p));
				p.sendMessage(ChatColor.GREEN + "Your skill tree has been reset! you have gained all your skill points back");

				 */

			} else {
				if (p.isOp()) {
					Player target = getServer().getPlayer(args[0]);
					if (target != null) {
						playerstats stats = new playerstats();
						stats.setPlayerMaxHealth(target, 20.0D);
						stats.setPlayerElementalPower(target, 100.0D);
						stats.setPlayerStrength(target, 0);
						stats.setPlayerSpeed(target, 0.2f);
						stats.setSkillpoints(target, stats.getLevel(target));
						p.sendMessage(ChatColor.GREEN + "Successfully reset skill tree for " + target.getName());
					} else {
						p.sendMessage(ChatColor.RED + "Cant find player by name " + args[0] + "!");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Error Missing Arguments!");
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("profile")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				if (args.length != 0) {
					Player clicked = getServer().getPlayer(args[0]);
					if (clicked != null) {
						p.sendMessage(ChatColor.GREEN + "Showing profile of " + clicked.getName());
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
						profilelore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + (int) clicked.getHealth());
						profilelore.add(ChatColor.GRAY + "Hunger: " + ChatColor.GOLD + (int) clicked.getSaturation());
						profilelore.add(ChatColor.GRAY + "Vitality: " + ChatColor.GREEN + clicked.getMaxHealth());
						DecimalFormat df = new DecimalFormat("###");
						profilelore.add(ChatColor.GRAY + "Speed: " + ChatColor.AQUA + df.format((clicked.getWalkSpeed() * 500.0F)));
						profilelore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + stats.getPlayerStrength(clicked));
						profilelore.add(ChatColor.GRAY + "Elemental Power: " + ChatColor.YELLOW + stats.getPlayerElementalPower(clicked));
						profilelore.add("");

						meta.setLore(profilelore);
						skull.setItemMeta((ItemMeta) meta);
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

					} else {
						p.sendMessage(ChatColor.RED + "Error! Cant find player by name " + args[0]);
					}
				}
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("playerlog")) {
			if (sender instanceof ConsoleCommandSender || sender.isOp()) {
				if (args.length != 0) {
					if (args[0].equalsIgnoreCase("player")) {
						CommandSender p = sender;
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						if (target.getFirstPlayed() != 0) {

							Date date = new java.util.Date(target.getFirstPlayed());
							SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
							sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
							String formatted_fj = sdf.format(date);

							Date date2 = new java.util.Date(target.getLastPlayed());
							SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
							sdf2.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
							String formatted_lj = sdf2.format(date2);

							p.sendMessage(ChatColor.DARK_GRAY + "First Login: " + ChatColor.GOLD + formatted_fj);
							p.sendMessage(ChatColor.DARK_GRAY + "Last Seen: " + ChatColor.GOLD + formatted_lj);
						} else {
							p.sendMessage(ChatColor.RED + "Error! " + args[1] + " has never joined the game!");
						}
					} else {
						sender.sendMessage(ChatColor.GREEN + "Checking playerdata...");
						getConfig().getStringList("server.uuids").forEach(uuid -> {
							UUID uniqueId = UUID.fromString(uuid);
							OfflinePlayer target = Bukkit.getOfflinePlayer(uniqueId);
							if (target.getFirstPlayed() != 0) {

								Date date = new java.util.Date(target.getFirstPlayed());
								SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
								sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
								String formatted_fj = sdf.format(date);

								Date date2 = new java.util.Date(target.getLastPlayed());
								SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
								sdf2.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
								String formatted_lj = sdf2.format(date2);
								sender.sendMessage(ChatColor.GREEN + "Player data for " + target.getName() + ":");
								sender.sendMessage(ChatColor.DARK_GRAY + "First Login: " + ChatColor.GOLD + formatted_fj);
								sender.sendMessage(ChatColor.DARK_GRAY + "Last Seen: " + ChatColor.GOLD + formatted_lj);
							} else {
								sender.sendMessage(ChatColor.RED + "Error! " + target.getName() + " has never joined the game!");
							}
						});
						sender.sendMessage(ChatColor.GREEN + "Playerdata checking done!");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid Syntax!");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("addplayerlog")) {
			if (sender instanceof ConsoleCommandSender || sender.isOp()) {
				if (getConfig().contains("server.uuids")) {
					if (!(getConfig().getStringList("server.uuids").contains(args[0]))) {
						UUID uniqueId = UUID.fromString(args[0]);
						List<String> list = getConfig().getStringList("server.uuids");
						list.add(args[0]);
						getConfig().set("server.uuids", list);
						saveConfig();
						sender.sendMessage(ChatColor.GREEN + "Added UUID: " + ChatColor.RED + args[0] + ChatColor.GREEN + " of player: " + ChatColor.GOLD + Bukkit.getOfflinePlayer(uniqueId).getName() + ChatColor.GREEN + " to the database!");
					} else {
						sender.sendMessage(ChatColor.RED + args[0] + " is already present!");
					}
				} else {
					ArrayList<String> initlist = new ArrayList<>();
					sender.sendMessage(ChatColor.GREEN + args[0] + "Data defined successfully! now use the command again!");
					getConfig().set("server.uuids", initlist);
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("setPresentsConfig")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				Player Target = Bukkit.getPlayer(args[0]);
				if (Target != null) {
					getConfig().set("admin_titles." + Target.getUniqueId() + ".presents", true);
					p.sendMessage(ChatColor.GREEN + "Set Presents Title for " + Target.getName() + " to true");
					Target.sendMessage(ChatColor.GRAY + "You received " + ChatColor.GOLD + "" + ChatColor.BOLD + "The Giver" + ChatColor.GRAY + " title from " + ChatColor.RED + "[ADMIN] " + p.getName() + "!");
					Bukkit.getOnlinePlayers().forEach(player -> {
						player.sendMessage("");
						player.sendMessage(ChatColor.AQUA + Target.getName() + ChatColor.GRAY + " received " + ChatColor.GOLD + "" + ChatColor.BOLD + "The Giver" + ChatColor.GRAY + " title from " +
								ChatColor.RED + "[ADMIN] " + p.getName() + "!");
						player.sendMessage("");
					});
				}
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("glory")) {
			Player p = (Player) sender;
			ChatStarGUI gui = new ChatStarGUI(this);
			gui.starGui(p);
		}
		if (cmd.getName().equalsIgnoreCase("rankingofthegods")) {
			Player p = (Player) sender;
			ChatStarGUI gui = new ChatStarGUI(this);
			gui.titleGui(p);
		}
		if(cmd.getName().equalsIgnoreCase("deathcount")){
			Player p = (Player) sender;
			if(args.length!=0) {
				OfflinePlayer op = (OfflinePlayer) getServer().getOfflinePlayer(args[0]);
				if(op.getFirstPlayed()!=0) {
					p.sendMessage(ChatColor.GRAY + "Death count of " + op.getName() + ": " + ChatColor.GOLD + op.getStatistic(Statistic.DEATHS));
				}else{
					p.sendMessage(ChatColor.RED + "An error occurred while fetching the player!" + ChatColor.GRAY + " (ERR_UNKNOWN_PLAYER)");
				}
			}else{
				OfflinePlayer op = (OfflinePlayer) p;
				p.sendMessage(ChatColor.GRAY + "Death count: " + ChatColor.GOLD + op.getStatistic(Statistic.DEATHS));
			}
		}
		if (cmd.getName().equalsIgnoreCase("sendpacket")) {
			Player p = (Player) sender;
			Player target = getServer().getPlayer(args[1]);
			if (p.isOp()) {
				if (target != null) {
					PlayerConnection connection = ((CraftPlayer) target).getHandle().b;
					switch (args[0].toUpperCase()) {
						case "RAIN_START":
							PacketPlayOutGameStateChange rainstart = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.c, 0);
							connection.sendPacket(rainstart);
							p.sendMessage(ChatColor.GREEN + "Successfully sent packet to " + args[1]);
							break;
						case "RAIN_STOP":
							PacketPlayOutGameStateChange rainstop = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.b, 0);
							connection.sendPacket(rainstop);
							p.sendMessage(ChatColor.GREEN + "Successfully sent packet to " + args[1]);
							break;

						case "GUARDIAN_EFFECT":
							PacketPlayOutGameStateChange guardian = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.k, 0);
							connection.sendPacket(guardian);
							p.sendMessage(ChatColor.GREEN + "Successfully sent packet to " + args[1]);
							break;

						case "DEMO":
							PacketPlayOutGameStateChange demo = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.f, 0);
							connection.sendPacket(demo);
							p.sendMessage(ChatColor.GREEN + "Successfully sent packet to " + args[1]);
							break;

						case "SKY_LEVEL":
							PacketPlayOutGameStateChange skychange = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.h, Float.parseFloat(args[2]));
							connection.sendPacket(skychange);
							p.sendMessage(ChatColor.GREEN + "Successfully sent packet to " + args[1]);
							break;

						case "THUNDER_LEVEL":
							PacketPlayOutGameStateChange rainchange = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.i, Float.parseFloat(args[2]));
							connection.sendPacket(rainchange);
							p.sendMessage(ChatColor.GREEN + "Successfully sent packet to " + args[1]);
							break;

						case "CREDITS":
							PacketPlayOutGameStateChange credits = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.e, 1);
							connection.sendPacket(credits);
							p.sendMessage(ChatColor.GREEN + "Successfully sent packet to " + args[1]);
							break;
					}
				} else {
					p.sendMessage(ChatColor.RED + "Target Not Found!");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You need to be ADMIN or higher to do that!");
			}
		}
		return true;
	}


	public static boolean hasAdvancement(Player player, String achname) {
		Advancement ach = null;
		for (Iterator<Advancement> iter = Bukkit.getServer().advancementIterator(); iter.hasNext(); ) {
			Advancement adv = iter.next();
			if (adv.getKey().getKey().equalsIgnoreCase(achname)) {
				ach = adv;
				break;
			}
		}
		AdvancementProgress prog = player.getAdvancementProgress(ach);
		if (prog.isDone()) {
			return true;
		}
		return false;
	}

	@EventHandler
	public void staffjoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.isOp()) {
			if (!(config.isSet("Silent_join." + p.getUniqueId()))) {
				new BukkitRunnable() {
					@Override
					public void run() {
						p.sendMessage(ChatColor.YELLOW + "You can now change silent join settings ");
						p.sendMessage(ChatColor.YELLOW + "with the following command: ");
						p.sendMessage(ChatColor.AQUA + "/settings silentjoin ");
						config.set("Silent_join." + p.getUniqueId(), false);

						saveConfig();
					}
				}.runTaskLater(this, 30);
			}
		}
	}

	@EventHandler
	public void onjoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.isOp()) {
			if (config.getBoolean("Silent_join." + p.getUniqueId())) {
				for (Player staff : getServer().getOnlinePlayers()) {
					e.setJoinMessage(null);
					if (staff.isOp()) {
						staff.sendMessage(ChatColor.DARK_AQUA + "[STAFF] " + ChatColor.RED + "[ADMIN] " + p.getName() + ChatColor.YELLOW + " joined.");
						getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[STAFF] " + ChatColor.RED + "[ADMIN] " + p.getName() + " joined.");
					}
				}
			} else {
				for (Player staff : getServer().getOnlinePlayers()) {
					if (staff.isOp()) {
						staff.sendMessage(ChatColor.DARK_AQUA + "[STAFF] " + ChatColor.RED + "[ADMIN] " + p.getName() + ChatColor.YELLOW + " joined.");
						getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[STAFF] " + ChatColor.RED + "[ADMIN] " + p.getName() + " joined.");
					}
				}
			}
		}
	}


	@EventHandler
	public void onleave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (p.isOp()) {
			if (config.getBoolean("Silent_join." + p.getUniqueId())) {
				for (Player staff : getServer().getOnlinePlayers()) {
					e.setQuitMessage(null);
					if (staff.isOp()) {
						staff.sendMessage(ChatColor.DARK_AQUA + "[STAFF] " + ChatColor.RED + "[ADMIN] " + p.getName() + ChatColor.YELLOW + " disconnected.");
						getServer().getLogger().severe(ChatColor.DARK_AQUA + "[STAFF] " + ChatColor.RED + "[ADMIN] " + p.getName() + " disconnected.");
					}
				}
			} else {
				for (Player staff : getServer().getOnlinePlayers()) {
					if (staff.isOp()) {
						staff.sendMessage(ChatColor.DARK_AQUA + "[STAFF] " + ChatColor.RED + "[ADMIN] " + p.getName() + ChatColor.YELLOW + " disconnected.");
						getServer().getLogger().severe(ChatColor.DARK_AQUA + "[STAFF] " + ChatColor.RED + "[ADMIN] " + p.getName() + " disconnected.");
					}
				}
			}
		}
	}


	public void counter(Player p, long seconds) {
		BukkitTask task = new BukkitRunnable() {
			int count = (int) seconds;

			public void run() {

				if (count == 0) {
					this.cancel();
				} else {
					if (count >= 30) {
						p.sendTitle(ChatColor.RED + String.valueOf(count) + "", ChatColor.GRAY + "Launching in...", 20, 10, 10);
						p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1.0f, 1.0f);
						count--;
					} else if (count > 10) {
						p.sendTitle(ChatColor.YELLOW + String.valueOf(count) + "", ChatColor.GRAY + "Launching in...", 20, 10, 10);
						p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1.0f, 1.0f);
						count--;
					} else if (count > 1) {
						p.sendTitle(ChatColor.GREEN + String.valueOf(count) + "", ChatColor.GRAY + "Launching in...", 20, 10, 10);
						p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1.0f, 1.0f);
						count--;
					} else {
						count--;
						p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Lost Kids SMP", ChatColor.RED + "" + ChatColor.BOLD + "Welcome");
						//p.sendTitle(ChatColor.GREEN+ String.valueOf(count) + "", ChatColor.GRAY + "Launching in...", 20, 10, 10);
						p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
						new BukkitRunnable() {
							@Override
							public void run() {
								p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
							}
						}.runTaskLater(plugin, 5);
					}
				}
			}
		}.runTaskTimer(this, 20, 20);
	}
}
