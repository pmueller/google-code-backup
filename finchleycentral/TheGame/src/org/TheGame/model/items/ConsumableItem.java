package org.TheGame.model.items;

import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.model.elements.factories.types.ElementGraphicDescription;
import org.TheGame.model.elements.factories.types.ItemElementTypeDescription;

public class ConsumableItem extends Item
{
	public ConsumableItem(ElementGraphicDescription _elementGraphicDescription,
			ItemElementTypeDescription _itemElementTypeDescription)
{
	super(_elementGraphicDescription, _itemElementTypeDescription);
}
	public boolean pickUp()
	{
		Inventory i = InventoryManager.getInstance().getPlayerInventory("normal");
		if(!i.isFull()&&!i.hasObject(this))
		{
			i.AddItem(this);
			return true;
		}
		return false;
	}
	
	public boolean useItem()
	{
		// Here we would apply the attributes in the ItemElementTypeDescription right away.
		InventoryManager.getInstance().getPlayerInventory("normal").removeItem(this);
		return true;
	}
	
	public Item createItem()
	{
		return new ConsumableItem(elementGraphicDescription,itemElementTypeDescription);
	}
	public String getToolTip() {
		return super.getName();
	}
}
