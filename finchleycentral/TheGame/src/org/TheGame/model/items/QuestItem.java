package org.TheGame.model.items;

import org.TheGame.main.GameFactories;
import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.model.elements.factories.types.ElementGraphicDescription;
import org.TheGame.model.elements.factories.types.ItemElementTypeDescription;

public class QuestItem extends Item
{
	public QuestItem(ElementGraphicDescription _elementGraphicDescription,
			ItemElementTypeDescription _itemElementTypeDescription)
{
	super(_elementGraphicDescription, _itemElementTypeDescription);
}
	public boolean pickUp()
	{
		Inventory i = InventoryManager.getInstance().getPlayerInventory("quest");
		if(this.itemElementTypeDescription.getMaxAmount() > i.getItemCount(this)&&!i.hasObject(this))
		{
			i.AddItem(this);
			return true;
		}
		return false;
	}
	
	public boolean useItem()
	{
		//THIS METHOD should only be called by events, not by inventory bag.
		Inventory inven = InventoryManager.getInstance().getPlayerInventory("quest");
		if(this.itemElementTypeDescription.isUsable())
		{
			for(int i=0;i<this.itemElementTypeDescription.getUseAmount();++i)
				inven.removeItem(this);
		}
		return true;
	}
	
	public Item createItem()
	{
		return new QuestItem(elementGraphicDescription,itemElementTypeDescription);
	}
	
	public String getToolTip()
	{
		return "<html>" + super.getName() + "<br><br>" + this.itemElementTypeDescription.getQuestDescrip() +"</html>";
	}
}
