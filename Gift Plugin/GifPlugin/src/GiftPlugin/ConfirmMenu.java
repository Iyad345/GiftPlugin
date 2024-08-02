package GiftPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmMenu 
{
	public CommandSender sender;
	public Gift gift;
	public Player player;
	public ConfirmMenu(CommandSender sender,Gift gift,Player player) 
	{
		this.sender = sender;
		this.gift = gift;
		this.player = player;
		
		Inventory myInventory = Bukkit.createInventory(null, 27,ChatColor.RED + "" + ChatColor.BOLD + "Send gift to " + gift.Target + "?!");
		
		Material Confirm = Material.LIME_CONCRETE;
		Material Cancel = Material.RED_CONCRETE;
		
		ItemStack confirmItem = new ItemStack(Confirm,1);
		ItemMeta itemMeta = confirmItem.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD  + "Confirm");
		confirmItem.setItemMeta(itemMeta);
		
		ItemStack cancelItem = new ItemStack(Cancel,1);
		ItemMeta itemMeta2 = cancelItem.getItemMeta();
		itemMeta2.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD  + "Cancel");
		cancelItem.setItemMeta(itemMeta2);
		
		myInventory.setItem(11, confirmItem);
		myInventory.setItem(15, cancelItem);
		
		player.openInventory(myInventory);
		
		ConfirmListener l = new ConfirmListener(this,myInventory,Confirm,Cancel);
		Bukkit.getServer().getPluginManager().registerEvents(l,Main.main);
	}
    public void SendConfirmed() 
    {
    	/*if(!player.getInventory().contains(gift.Items)) 
    	{
    		sender.sendMessage(ChatColor.RED + "You have lost the gift from your inventory!");
    		return;
    	}*/
		Main.saveGift(gift);
		
		player.getInventory().removeItem(gift.Items);		
		sender.sendMessage(ChatColor.GREEN + "Successfully sent the gift to " + gift.Target + "!");
		
		Player p = Bukkit.getPlayerExact(gift.Target);
		if(p != null) 
		{
			p.sendMessage(ChatColor.GOLD + "You have been sent a gift by " + sender.getName() + ". Do /maildelivery to redeem it!");
		}
    }
}
