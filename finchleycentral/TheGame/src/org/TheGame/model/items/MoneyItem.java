package org.TheGame.model.items;

import java.util.Random;

import org.TheGame.main.GameCore;
import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.model.elements.factories.types.ElementGraphicDescription;
import org.TheGame.model.elements.factories.types.ItemElementTypeDescription;

public class MoneyItem extends Item{


	public MoneyItem(ElementGraphicDescription _elementGraphicDescription,
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
		Long moneyMultipler = 0L + this.itemElementTypeDescription.getMoneyMultipler();
	    Random randomGenerator = new Random();
	    int randomInt = randomGenerator.nextInt(4)+5; // from 5 to 8
	    int monsterLevel = this.getMonsterDropLevel();
	    
	    Long moneyToBeAdded = moneyMultipler*randomInt*monsterLevel;
	   // System.out.println(GameCore.getInstance().getPlayer().getMoney()+"+ ("+moneyMultipler+"*"+randomInt+"*"+monsterLevel+")");
		GameCore.getInstance().getPlayer().addMoney(moneyToBeAdded);
		//System.out.println("Current money: " + GameCore.getInstance().getPlayer().getMoney());
		return true;
	}
	
	public Item createItem()
	{
		return new MoneyItem(elementGraphicDescription,itemElementTypeDescription);
	}
	
	public String getToolTip() {
		return "How did you see this? this is instant money!";
	}
}

