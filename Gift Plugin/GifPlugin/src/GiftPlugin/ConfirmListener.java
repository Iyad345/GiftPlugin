package GiftPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ConfirmListener implements Listener
{
	public ConfirmMenu confirmMenu;
	public Inventory i;
	public Material Confirm;
	public Material Cancel;
	public ConfirmListener(ConfirmMenu confirmMenu,Inventory i,Material Confirm,Material Cancel) 
	{
		this.confirmMenu = confirmMenu;
		this.i = i;
		this.Confirm = Confirm;
		this.Cancel = Cancel;
	}
	@EventHandler
	public void onClickEvent(InventoryClickEvent event) 
	{
		
		Player p = (Player)event.getWhoClicked();
		if(event.getInventory().equals(i)) 
		{
			if(event.getCurrentItem().getType() == Confirm) 
			{
				p.closeInventory();
				confirmMenu.SendConfirmed();
			}
			if(event.getCurrentItem().getType() == Cancel)
			{
				p.closeInventory();
			}
		}
		event.setCancelled(true);
	}
}
