package me.tamilpp25.server;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Particles implements Listener {

	private static core plugin;

	public Particles(core p) {
		plugin = p;
	}

	public static void heart_particle(Player p) {
		Location location = p.getLocation();
		for (int i = 0; i < 2; i++) {
			Location subloc = p.getLocation().add(0, 1, 0);
			p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(Math.random(), i + Math.random(), Math.random()), 1);
			p.getWorld().spawnParticle(Particle.HEART, subloc.subtract(Math.random(), Math.random(), Math.random()), 1);
		}
	}

	public static void notes_particle(Player p) {
		Location location = p.getLocation();
		for (int i = 0; i < 2; i++) {
			Location subloc = p.getLocation().add(0, 1, 0);
			p.getWorld().spawnParticle(Particle.NOTE, p.getLocation().add(Math.random(), i + Math.random(), Math.random()), 1);
			p.getWorld().spawnParticle(Particle.NOTE, subloc.subtract(Math.random(), Math.random(), Math.random()), 1);
		}
	}

	public static void spiral_particle_loop(Player p,Particle particle,long time) {
		//light = yellow , darkness = purple , electric = yellow , force = blue , mystic = green , Impact = red

		new BukkitRunnable(){
			private double radius = 0.7;
			private double angle = 180;
			//Particle.DustOptions option = new Particle.DustOptions(colour, 1);
			int timer = (int) time;

			@Override
			public void run() {
				if (timer == 0) {
					this.cancel();
				}else {
					double y = (radius * Math.sin(angle));
					double z = (radius * Math.cos(angle));
					angle -= 0.5;
					timer--;

					p.getWorld().spawnParticle(particle, p.getEyeLocation().add(y, 0.5, z), 1);
				}
			}
		}.runTaskTimer(plugin, 0, 2);

	}


	public static void spiral_particle_static(Player p, Color colour,double radius,double angle) {
		//light = yellow , darkness = purple , electric = yellow , force = blue , mystic = green , Impact = red
		// double radius = 0.7;
		// double angle = 180;

		Particle.DustOptions option = new Particle.DustOptions(colour, 1);
		for (int i = 0; i < angle; i++) {
			double y = (radius * Math.sin(angle));
			double z = (radius * Math.cos(angle));
			angle -= 0.5;

			p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().add(y, 0.3, z), 1, option);
		}
	}
}
