package GiftPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
public class MailNotifier implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		Player p = event.getPlayer();
		String Name = p.getName();
		if(Main.CheckForGifts(Name)) 
		{
			new BukkitRunnable() 
			{
				@Override
				public void run() {
					p.sendMessage(ChatColor.GOLD + "You have unclaimed gifts. Do /maildelivery to redeem them!");
				}
				
			}.runTaskLater(Main.main,60);
		}
	}
}
