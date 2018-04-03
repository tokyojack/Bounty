package me.tokyojack.spigot.bounty.commands.subcommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.tokyojack.spigot.bounty.Core;
import me.tokyojack.spigot.bounty.managers.BountyManager;
import me.tokyojack.spigot.bounty.utils.Chat;
import me.tokyojack.spigot.bounty.utils.subkommand.SubKommand;
import net.milkbowl.vault.economy.Economy;

public class Add extends SubKommand {

	public Add() {
		super("Adds a bounty to a player", new ArrayList<String>(Arrays.asList("set")));
	}

	@Override
	public boolean execute(CommandSender commandSender, String[] args) {
		if (args.length == 0) {
			commandSender.sendMessage("/bounty add <amount> <player>");
			return false;
		}

		if (!isNumber(args[0])) {
			commandSender.sendMessage("You must put a number for the amount.");
			return false;
		}

		int amount = Integer.parseInt(args[0]);

		if (amount <= 0) {
			commandSender.sendMessage(Chat.color("The number must be over 0"));
			return false;
		}

		UUID targetedPlayerUUID = getUUIDFromPlayername(args[1]);

		BountyManager bountyManager = Core.getPlugin().getBountyManager();

		if ((!bountyManager.hasBounty(targetedPlayerUUID)) && Bukkit.getPlayer(args[1]) == null) {
			commandSender.sendMessage("That player must be online to add a bounty!");
			return false;
		}

		Economy economy = Core.getPlugin().getEconomy();

		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

			if (economy.getBalance(player) < amount) {
				commandSender.sendMessage("You don't have enough money!");
				return false;
			}

			economy.withdrawPlayer(player, amount);
		}

		commandSender.sendMessage("You've added " + amount + " to " + args[1]);
		bountyManager.addBounty(targetedPlayerUUID, amount);

		return true;
	}

	@SuppressWarnings("deprecation")
	private UUID getUUIDFromPlayername(String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		return player != null ? player.getUniqueId() : Bukkit.getOfflinePlayer(playerName).getUniqueId();
	}

	private boolean isNumber(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}
	}
}
