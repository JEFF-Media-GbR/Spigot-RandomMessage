package de.jeffclan.RandomMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

public class JeffRandomMessagePlugin extends JavaPlugin {

	private boolean usingMatchingConfig = true;
	private int currentConfigVersion = 1;

	private int currentMessage = 0;

	private ArrayList<String[]> broadcasts;

	@Override
	public void onEnable() {

		createConfig();
		loadMessages();

		getCommand("randommessage").setExecutor(new JeffRandomMessageCommandExecutor(this));

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				String[] message = getNextMessage();
				if (message != null) {
					broadcastMessage(message);
				}

			}
		}, 0, getConfig().getLong("delay") * 20);

	}

	public String[] getNextMessage() {
		if (broadcasts.size() == 0) {
			return null;
		}
		if (currentMessage >= broadcasts.size()) {
			currentMessage = 0;
			if (getConfig().getBoolean("random-order")) {
				Collections.shuffle(broadcasts);
			}
		}
		currentMessage++;
		return broadcasts.get(currentMessage - 1);

	}

	public void broadcastMessage(String[] text) {
		for (String line : text) {
			getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', line));
		}
	}

	protected void loadMessages() {

		broadcasts = new ArrayList<String[]>();

		currentMessage = 0;

		File messagesFolder = new File(
				getDataFolder().getAbsolutePath() + File.separator + "messages" + File.separator);

		File[] listOfMessageFiles = messagesFolder.listFiles();

		Arrays.sort(listOfMessageFiles);

		for (File file : listOfMessageFiles) {
			if (file.isFile()) {

				try {
					broadcasts.add(JeffRandomMessageUtils.getStringArrayFile(file));
				} catch (FileNotFoundException e) {
					getLogger().warning("Could not load message file: " + file.getName());
					// e.printStackTrace();
				}
			}
		}

		if (getConfig().getBoolean("random-order")) {
			Collections.shuffle(broadcasts);
		}

		getLogger().info(String.format("%d messages loaded. Broadcasting started with %d seconds delay.",
				broadcasts.size(), getConfig().getLong("delay")));

	}

	private void createConfig() {
		this.saveDefaultConfig();

		if (getConfig().getInt("config-version", 0) < 1) {
			getLogger().warning("========================================================");
			getLogger().warning("You are using a config file that has been generated");
			getLogger().warning("prior to RandomMessage version 1.0.");
			getLogger().warning("To allow everyone to use the new features, your config");
			getLogger().warning("has been renamed to config.old.yml and a new one has");
			getLogger().warning("been generated. Please examine the new config file to");
			getLogger().warning("see the new possibilities and adjust your settings.");
			getLogger().warning("========================================================");

			File configFile = new File(getDataFolder().getAbsolutePath() + File.separator + "config.yml");
			File oldConfigFile = new File(getDataFolder().getAbsolutePath() + File.separator + "config.old.yml");
			if (oldConfigFile.getAbsoluteFile().exists()) {
				oldConfigFile.getAbsoluteFile().delete();
			}
			configFile.getAbsoluteFile().renameTo(oldConfigFile.getAbsoluteFile());
			saveDefaultConfig();
			try {
				getConfig().load(configFile.getAbsoluteFile());
			} catch (IOException | InvalidConfigurationException e) {
				getLogger().warning("Could not load freshly generated config file!");
				e.printStackTrace();
			}
		} else if (getConfig().getInt("config-version", 0) != currentConfigVersion) {
			getLogger().warning("========================================================");
			getLogger().warning("YOU ARE USING AN OLD CONFIG FILE!");
			getLogger().warning("This is not a problem, as RandomMessage will just use the");
			getLogger().warning("default settings for unset values. However, if you want");
			getLogger().warning("to configure the new options, please go to");
			getLogger().warning("<missing link>");
			getLogger().warning("and replace your config.yml with the new one. You can");
			getLogger().warning("then insert your old changes into the new file.");
			getLogger().warning("========================================================");
			usingMatchingConfig = false;
		}

		File playerDataFolder = new File(getDataFolder().getPath() + File.separator + "messages");
		if (!playerDataFolder.getAbsoluteFile().exists()) {
			playerDataFolder.mkdir();
		}

		getConfig().addDefault("delay", 300);
		getConfig().addDefault("check-for-updates", "true");
		getConfig().addDefault("random-order", true);
	}

}
