package GiftPlugin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MailDelivery implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(args.length != 0) 
		{
			sender.sendMessage(ChatColor.GOLD + "Proper Usage: " + ChatColor.GREEN + "/maildelivery");	
			return true;
		}
		
		if(sender instanceof Player) 
		{
			Player p = (Player) sender;
			int emptySlots = getEmptySlots(p);
			List<Gift> MyGifts = Main.GetGift(sender,emptySlots);
			if(MyGifts != null)
			{
				for(Gift g : MyGifts) 
				{
					p.getInventory().addItem(g.Items);
				}
			}
		}
		
		return true;
	}
    public int getEmptySlots(Player p) {
        PlayerInventory inventory = p.getInventory();
        ItemStack[] cont = inventory.getContents();
        int i = 0;
        for (ItemStack item : cont)
          if (item != null && item.getType() != Material.AIR) {
            i++;
          }
        return 36 - i;
    }

}