package org.TheGame.model.items;


import org.TheGame.main.GameCore;
import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.model.elements.factories.types.ElementGraphicDescription;
import org.TheGame.model.elements.factories.types.ItemElementTypeDescription;

public class EquipableItem extends Item
{
	public EquipableItem(ElementGraphicDescription _elementGraphicDescription,
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
		return GameCore.getInstance().getPlayer().getElementType().getCharacteristics().wearItem(this);
		//Inventory i = InventoryManager.getInstance().getPlayerInventory("normal");
		//i.removeItem(this);
	}
	
	public void unequipItem() {
		GameCore.getInstance().getPlayer().getElementType().getCharacteristics().takeItemOff(this.getSlot());
	}
	
	public Item createItem()
	{
		return new EquipableItem(elementGraphicDescription,itemElementTypeDescription);
	}
	public String getToolTip()
	{
		String ret="<html>"+super.getName()+"<br><br>";
		for(String s : this.itemElementTypeDescription.getKeys()) {
			if(this.itemElementTypeDescription.getStat(s)!=0) {
				ret += s+": "+this.itemElementTypeDescription.getStat(s)+"<br>";
			}
		}
		ret += "</html>";
		return ret;
	}
}
