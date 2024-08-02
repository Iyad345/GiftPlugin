package GiftPlugin;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public static Main main;
	public static File configFile;
	public static YamlConfiguration config;
    @Override
    public void onEnable() {
    	main = this;
    	
        if (!getDataFolder().exists())
        {
            getDataFolder().mkdirs();
        }
        configFile = new File(getDataFolder(), "gifts.yml");
        if (!configFile.exists()) 
        {
            try 
            {
                configFile.createNewFile();
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    	
        this.getCommand("gift").setExecutor(new GiftCommand());
        this.getCommand("maildelivery").setExecutor(new MailDelivery());
        
        
        
        this.getServer().getPluginManager().registerEvents(new MailNotifier(),this);
        
    }
    @Override
    public void onDisable() {
    	
    }
    
    public static void RegisterGift(CommandSender sender,Gift gift,Player player) 
    {    	
    	List<Gift> giftsOwned = GetGifts(gift.Target);
    	
    	
    	if(giftsOwned.size() >= 20) 
    	{
    		sender.sendMessage(ChatColor.RED + gift.Target + "'s mail is full!");
    		return;
    	}
    	
    	String senderName = sender.getName();
    	if(gift.Target.equals(senderName)) 
    	{
    		sender.sendMessage(ChatColor.RED + "You can't gift yourself!");
    		return;
    	}
    	
    	if(gift.Items.getType() == Material.AIR) 
    	{
    		sender.sendMessage(ChatColor.RED + "You can only gift an item!");
    		return;
    	}
    	
    	ConfirmMenu confirmMenu = new ConfirmMenu(sender,gift,player);
    }
	public static List<Gift> GetGift(CommandSender sender,int nbOfEmptySlots) 
    {
    	List<Gift> giftsOwned = GetGifts(sender.getName());
    	
    	if(giftsOwned.size() == 0) {
    		sender.sendMessage(ChatColor.GOLD + "Your mail is empty!");
    		return null;
    	}
    	if(giftsOwned.size() > nbOfEmptySlots) {
    		sender.sendMessage(ChatColor.RED + "You need " + String.valueOf(giftsOwned.size() - nbOfEmptySlots) + " more empty slots in your inventory!" );
    		return null;
    	}
    	
    	removeAllGifts(sender.getName());
    	sender.sendMessage(ChatColor.GREEN + "Successfully Withdrawn all gifts!" );
    	return giftsOwned;
    }
    public static boolean CheckForGifts(String name)
    {
    	List<Gift> giftsOwned = GetGifts(name);
    	
    	if(!giftsOwned.isEmpty()) 
    	{
    		return true;
    	}
    	
		return false;
    	
    }
    public static void saveGift(Gift gift) 
    {
        String path = "gifts." + gift.Target;
        List<ItemStack> items = (List<ItemStack>) config.getList(path);
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(gift.Items);
        config.set(path, items);
        
        try 
        {
            config.save(configFile);
        } 
        catch (IOException e) 
        {
        	return;
        }
    }
    public static List<Gift> GetGifts(String ownerName)
    {
        String path = "gifts." + ownerName;
        List<ItemStack> items = (List<ItemStack>) config.getList(path);
        ArrayList<Gift> gifts = new ArrayList<>();
        if (items != null) {
            for (ItemStack item : items) {
                gifts.add(new Gift(ownerName, item));
            }
        }
        return gifts;
    	
    }
    public static void removeAllGifts(String targetName) 
    {
        String path = "gifts." + targetName;
        config.set(path, null);
        try {
            config.save(configFile);
        } catch (IOException e) {
            return;
        }
    }
}
