package me.tokyojack.spigot.bounty;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.tokyojack.spigot.bounty.commands.Bounty;
import me.tokyojack.spigot.bounty.commands.subcommands.Add;
import me.tokyojack.spigot.bounty.commands.subcommands.Top;
import me.tokyojack.spigot.bounty.listeners.BountyClaim;
import me.tokyojack.spigot.bounty.managers.BountyManager;
import me.tokyojack.spigot.bounty.utils.Gependecy;
import me.tokyojack.spigot.bounty.utils.PlayerKilledPlayerEvent;
import me.tokyojack.spigot.bounty.utils.multipage.Multipage;
import me.tokyojack.spigot.bounty.utils.subkommand.SubKommandManager;
import net.milkbowl.vault.economy.Economy;

@Getter
public class Core extends JavaPlugin {

	@Getter
	private static Core plugin;

	private BountyManager bountyManager;

	private Economy economy;

	public void onEnable() {
		plugin = this;
		
		this.bountyManager = new BountyManager();

		new SubKommandManager(new Bounty(), false).addSubCommand(new Add()).addSubCommand(new Top()).build();
		
		new PlayerKilledPlayerEvent().registerListener(this);
		Multipage.registerListener(this);

		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new BountyClaim(), this);
		
		new Gependecy("Vault", true, this) {

			@Override
			public void ifFound() {
				setupEconomy();
			}

			@Override
			public void ifNotFound() {
				economy = null;
			}
		}.check();
	}

	// From Vault's WIKI if I'm correct
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
			this.economy = economyProvider.getProvider();

		return (this.economy != null);
	}

}