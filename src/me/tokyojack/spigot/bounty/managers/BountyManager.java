package me.tokyojack.spigot.bounty.managers;

import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

import lombok.Getter;

@Getter
public class BountyManager {

	private LinkedHashMap<UUID, Integer> bountyPlayers;

	public BountyManager() {
		this.bountyPlayers = new LinkedHashMap<UUID, Integer>();
	}

	public void addBounty(UUID playerUUID, int amount) {
		
		// If the player already has a bounty, adds that value to it
		if (bountyPlayers.containsKey(playerUUID)) {
			int pastAmount = this.bountyPlayers.get(playerUUID);
			this.bountyPlayers.put(playerUUID, pastAmount + amount);
		} else
			this.bountyPlayers.put(playerUUID, amount);
	}

	public boolean hasBounty(Player player) {
		return this.bountyPlayers.containsKey(player.getUniqueId());
	}

	public boolean hasBounty(UUID playerUUID) {
		return this.bountyPlayers.containsKey(playerUUID);
	}

	public int getBounty(UUID playerUUID) {
		return this.bountyPlayers.get(playerUUID);
	}

	public void removeBounty(Player player) {
		this.bountyPlayers.remove(player.getUniqueId());
	}

	// Just for testing out the scrolling and what not
	public void test() {
		for (int i = 0; i < 20; i++) {
			this.bountyPlayers.put(UUID.randomUUID(), RandomNumber(1000));
		}
	}

	private int RandomNumber(int randomNumber) {
		return new Random().nextInt(randomNumber) + 1;
	}
}
