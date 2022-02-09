package me.tamilpp25.server.TabComplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class setStatTabComplete implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()) {
			ArrayList<String> complete = new ArrayList<>();
			ArrayList<String> tabcomplete = new ArrayList<>();
			tabcomplete.add("VITALITY");
			tabcomplete.add("SPEED");
			tabcomplete.add("ELEMENTAL_POWER");
			tabcomplete.add("MANA");
			tabcomplete.add("STRENGTH");
			tabcomplete.add("FAME");
			tabcomplete.add("LOYALTY");
			tabcomplete.add("SKILL_POINT");
			tabcomplete.add("LEVEL");
			tabcomplete.add("XP");
			tabcomplete.add("CLASS_LEVEL");
			tabcomplete.add("CLASS_XP");

			ArrayList<String> LKSMP_class = new ArrayList<>();
			LKSMP_class.add("LIGHT");
			LKSMP_class.add("DARKNESS");
			LKSMP_class.add("MYSTIC");
			LKSMP_class.add("FORCE");
			LKSMP_class.add("ELECTRIC");
			LKSMP_class.add("IMPACT");


			if(args[0].equalsIgnoreCase("CLASS_LEVEL") || args[0].equalsIgnoreCase("CLASS_XP")){
				if(args.length>3) {
					if (!args[3].isBlank()) {
						LKSMP_class.forEach(item -> {
							if (item.toString().contains(args[3]))
								complete.add(item.toString());
						});
					} else {
						complete.addAll(LKSMP_class);
					}
					return complete;
				}
			}else{
				if (args.length < 2) {
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
		}
		return null;
	}
}
