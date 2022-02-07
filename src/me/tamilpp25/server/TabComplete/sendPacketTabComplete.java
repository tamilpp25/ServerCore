package me.tamilpp25.server.TabComplete;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sendPacketTabComplete implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()) {
			ArrayList<String> complete = new ArrayList<>();
			ArrayList<String> tabcomplete = new ArrayList<>();
			tabcomplete.add("RAIN_START");
			tabcomplete.add("RAIN_STOP");
			tabcomplete.add("GUARDIAN_EFFECT");
			tabcomplete.add("DEMO");
			tabcomplete.add("SKY_LEVEL");
			tabcomplete.add("THUNDER_LEVEL");
			tabcomplete.add("CREDITS");
			if (args[0] != null && args.length < 2) {
				if (!args[0].isBlank()) {
					tabcomplete.forEach(item -> {
						if (item.toString().contains(args[0]))
							complete.add(item.toString());
					});
				} else {
					complete.addAll(tabcomplete);
				}
				return complete;
			}
		}
		return null;
	}
}
