package org.TheGame.model.items;

import org.TheGame.model.elements.factories.types.ElementGraphicDescription;
import org.TheGame.model.elements.factories.types.ItemElementTypeDescription;

public class InstantItem extends Item
{
	public InstantItem(ElementGraphicDescription _elementGraphicDescription,
			ItemElementTypeDescription _itemElementTypeDescription)
{
	super(_elementGraphicDescription, _itemElementTypeDescription);
}

	public boolean pickUp()
	{
		useItem();
		return true;
	}
	
	public boolean useItem()
	{
		// Here we would apply the attributes in the ItemElementTypeDescription right away.
		return true;
	}
	
	public Item createItem()
	{
		return new InstantItem(elementGraphicDescription,itemElementTypeDescription);
	}
	
	public String getToolTip()
	{
		return "Opps how did you read this?";
	}
}
