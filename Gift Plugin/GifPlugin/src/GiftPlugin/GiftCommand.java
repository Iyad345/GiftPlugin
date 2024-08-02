package GiftPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiftCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(args.length > 1) 
		{
			sender.sendMessage(ChatColor.GOLD + "Proper Usage: " + ChatColor.GREEN + "/gift <name of player>");
			return true;
		}
		
		if(sender instanceof Player) 
		{
				Player player = (Player) sender;
				ItemStack stack = player.getItemInHand();
				Gift gift = new Gift(args[0],stack);
				Main.RegisterGift(sender,gift,player); 
		}

		return true;
	}
}