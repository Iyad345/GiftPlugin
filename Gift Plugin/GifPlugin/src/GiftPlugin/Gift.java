package GiftPlugin;


import org.bukkit.inventory.ItemStack;

public class Gift
{
	public String Target;
	public ItemStack Items;
	public Gift(String Target,ItemStack Items) 
	{
		this.Target = Target;
		this.Items = Items;
	}
}
