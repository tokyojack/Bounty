package me.tokyojack.spigot.bounty;

package me.tokyojack.spigot.bounty.commands.subcommands;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.tokyojack.spigot.bounty.Core;
import me.tokyojack.spigot.bounty.utils.ItemBuilder;
import me.tokyojack.spigot.bounty.utils.subkommand.SubKommand;

public class Top extends SubKommand {

	public Top() {
		super("Get top bounties");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean execute(CommandSender commandSender, String[] args) {

		LinkedHashMap<UUID, Integer> bountyPlayers = Core.getPlugin().getBountyManager().getBountyPlayers();

		// Sorts by the highest
		Object[] organizedBounties = bountyPlayers.entrySet().toArray();
		Arrays.sort(organizedBounties, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Map.Entry<UUID, Integer>) o2).getValue().compareTo(((Map.Entry<UUID, Integer>) o1).getValue());
			}
		});

		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GREEN + "Top 9");

		for (int i = 0; i < organizedBounties.length; i++) {
			String name = Bukkit.getOfflinePlayer(((Map.Entry<UUID, Integer>) a[i]).getKey()).getName();
			int amount = ((Map.Entry<UUID, Integer>) a[i]).getValue();

			if (commandSender instanceof Player) {
				Player player = (Player) commandSender;

				inv.addItem(new ItemBuilder(Material.SKULL_ITEM).setHead(name).setName(name + " | " + amount)
						.toItemStack());
			} else {
				commandSender.sendMessage(name + " | " + amount);
			}
		}

		if (commandSender instanceof Player)
			player.openInventory(inv);

		return true;
	}
}
