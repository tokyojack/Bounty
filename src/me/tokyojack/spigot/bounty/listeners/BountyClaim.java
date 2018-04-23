package me.tokyojack.spigot.bounty.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.tokyojack.spigot.bounty.Core;
import me.tokyojack.spigot.bounty.managers.BountyManager;
import me.tokyojack.spigot.bounty.utils.PlayerKilledPlayerEvent;

public class BountyClaim implements Listener {

	@EventHandler
	public void onPlayerKill(PlayerKilledPlayerEvent event) {
		BountyManager bountyManager = Core.getPlugin().getBountyManager();

		Player victim = event.getVictim();

		UUID victimUUID = victim.getUniqueId();

		// Checks if the player has a bounty
		if (!bountyManager.getBountyPlayers().containsKey(victimUUID))
			return;

		// Gets the bounty amount
		int bountyAmount = bountyManager.getBounty(victimUUID);
		
		Player killer = event.getKiller();

		killer.sendMessage("You've claimed " + victim.getName() + " bounty of $" + bountyAmount);

		// Gives the killer the bounty amount
		Core.getPlugin().getEconomy().depositPlayer(killer, bountyAmount);
		
		// Removes the bounty from the killed player
		bountyManager.getBountyPlayers().remove(victimUUID);
	}

}
