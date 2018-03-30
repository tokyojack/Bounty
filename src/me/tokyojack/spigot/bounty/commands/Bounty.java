package me.tokyojack.spigot.bounty.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.tokyojack.spigot.bounty.Core;
import me.tokyojack.spigot.bounty.utils.ItemBuilder;
import me.tokyojack.spigot.bounty.utils.kommand.Kommand;
import me.tokyojack.spigot.bounty.utils.multipage.Multipage;

public class Bounty extends Kommand {

	public Bounty() {
		super("Bounty command", new ArrayList<String>(Arrays.asList("bounties")));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {

		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage("Only players can do this command!");
			return false;
		}

		Map<UUID, Integer> bounties = Core.getPlugin().getBountyManager().getBountyPlayers();

		List<ItemStack> bountiesItems = bounties.keySet().stream()
				.map(uuid -> new ItemBuilder(Material.SKULL_ITEM).setHead(Bukkit.getOfflinePlayer(uuid).getName())
						.setName(Bukkit.getOfflinePlayer(uuid).getName() + " &c$" + bounties.get(uuid)).toItemStack())
				.collect(Collectors.toList());

		new Multipage("Bounties: Pg %page%", bountiesItems).openInventory(((Player) commandSender));

		return false;
	}

}
