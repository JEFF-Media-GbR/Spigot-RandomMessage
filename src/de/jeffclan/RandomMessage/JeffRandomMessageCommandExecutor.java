package de.jeffclan.RandomMessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JeffRandomMessageCommandExecutor implements CommandExecutor {
	
	JeffRandomMessagePlugin plugin;
	
	JeffRandomMessageCommandExecutor(JeffRandomMessagePlugin plugin) {
		this.plugin=plugin;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(!arg0.hasPermission("randommessage.reload")) {
			arg0.sendMessage(plugin.getCommand("randommessage").getPermissionMessage());
			return true;
		}
		
		plugin.loadMessages();
		
		arg0.sendMessage("Message files have been reloaded.");
		
		return true;
	}

}
