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
	public void playerKill(PlayerKilledPlayerEvent event) {
		BountyManager bountyManager = Core.getPlugin().getBountyManager();

		Player victim = event.getVictim();

		UUID victimUUID = victim.getUniqueId();

		if (!bountyManager.getBountyPlayers().containsKey(victimUUID))
			return;

		int bountyAmount = bountyManager.getBounty(victimUUID);
		Player killer = event.getKiller();

		killer.sendMessage("You've claimed " + victim.getName() + " bounty of $" + bountyAmount);

		Core.getPlugin().getEconomy().depositPlayer(killer, bountyAmount);
		bountyManager.getBountyPlayers().remove(victimUUID);
	}

}
