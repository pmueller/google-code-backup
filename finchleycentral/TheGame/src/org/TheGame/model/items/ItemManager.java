package org.TheGame.model.items;

import java.net.ProtocolException;
import java.util.HashMap;

public class ItemManager {

	private static ItemManager itemManager;
	private HashMap<Long, Item> idCheck; //Reference to an item, given its id (may not be in play)
	private HashMap<String, Integer> itemCount; //items in play: on a monster, on a player, or on the ground
	
	private ItemManager()
	{
		itemCount = new HashMap<String, Integer>();
		idCheck = new HashMap<Long, Item>();
	}
	
	public static ItemManager getInstance()
	{
		if(itemManager == null)
		{
			itemManager = new ItemManager();
		}
		return itemManager;
	}
	
	public void removeItemFromPlay(Item item)
	{
		removeItemFromPlay(item.getName());
	}
	
	public void removeItemFromPlay(String name)
	{
		if(!itemCount.containsKey(name))
		{
			throw new RuntimeException();
		}
		itemCount.put(name, itemCount.get(name)-1);
	}
	
	public void addItemIntoPlay(Item item)
	{
		if(idCheck.containsKey(item.getId()))
		{
			throw new RuntimeException();
		}
		
		idCheck.put(item.getId(), item);
		if(itemCount.containsKey(item.getName()))
		{
			itemCount.put(item.getName(), itemCount.get(item.getName())+1);
		}
		else
		{
			itemCount.put(item.getName(), 1);
		}
	}
	
	public int howManyInPlay(String name)
	{
		if(this.itemCount.containsKey(name))
			return this.itemCount.get(name);
		return 0;
	}
	
	public int howManyInPlay(Item item)
	{
		return howManyInPlay(item.getName());
	}
	
	
}
