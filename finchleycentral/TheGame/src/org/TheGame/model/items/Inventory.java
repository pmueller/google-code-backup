package org.TheGame.model.items;

import java.util.ArrayList;
import java.util.HashMap;

import org.TheGame.main.GameCore;

public class Inventory 
{
	//protected static Inventory instance;
	private ArrayList<Item> inventoryList;
	private HashMap<String, Integer> itemCount;
	
	/*public static Inventory getInstance() 
	{
		if (Inventory.instance == null) 
		{
			Inventory.instance = new Inventory();
		}
		return Inventory.instance;
	}*/
	
	public Inventory()
	{
		super();
		this.inventoryList = new ArrayList<Item>();
		this.itemCount = new HashMap<String,Integer>();
	}
	
	public boolean isFull() {
		if(inventoryList.size()==15) return true;
		return false;
	}
	
	public void AddItem(Item newItem)
	{
		if(itemCount.size() >= 15)
			return;
		if(itemCount.containsKey(newItem.getName()))
		{
			itemCount.put(newItem.getName(), itemCount.get(newItem.getName())+1);
		}
		else
		{
			itemCount.put(newItem.getName(),1);
		}
		inventoryList.add(newItem);
	}
	
	public ArrayList<Item> getInventoryList()
	{
		return inventoryList;
	}
	
	public boolean removeItem(String name)
	{
		boolean ret = false;
		long itemId = -1;
		for(int i=0;i<inventoryList.size();++i)
		{
			if(inventoryList.get(i).itemElementTypeDescription.name.equals(name))
			{
				itemId = inventoryList.get(i).getId();
				inventoryList.remove(i);
				ret = true;
			}
		}
		if(ret)
		{
			GameCore.getInstance().getPlayer().getElementType().getCharacteristics().removeNonexistingItem(itemId);
			itemCount.put(name, itemCount.get(name)-1);
			if(itemCount.get(name)==0)
			{
				itemCount.remove(name);
			}
			
			ItemManager.getInstance().removeItemFromPlay(name);
		}
		
		return ret;
	}
	
	public boolean removeItem(Item item)
	{
		boolean ret = false;
		long itemId = -1;
		for(int i=0;i<inventoryList.size();++i)
		{
			if(inventoryList.get(i).itemElementTypeDescription.name.equals(item.itemElementTypeDescription.name))
			{
				itemId = inventoryList.get(i).getId();
				inventoryList.remove(i);
				ret = true;
				break;
			}
		}
		if(ret)
		{
			GameCore.getInstance().getPlayer().getElementType().getCharacteristics().removeNonexistingItem(itemId);
			itemCount.put(item.getName(), itemCount.get(item.getName())-1);
			if(itemCount.get(item.getName())==0)
			{
				itemCount.remove(item.getName());
			}
			
			ItemManager.getInstance().removeItemFromPlay(item);
		}
		return ret;
	}

	public int getItemCount(String name)
	{
		return itemCount.get(name);
	}
	
	public boolean hasItem(String name)
	{
		return itemCount.containsKey(name);
	}
	
	public boolean hasObject(Item item) {
		return inventoryList.contains(item);
	}
	
	public boolean hasItem(Item item)
	{
		return itemCount.containsKey(item.itemElementTypeDescription.name);
	}
	
	public int getItemCount(Item item)
	{
		if(!itemCount.containsKey(item.getName()))
			return 0;
		return itemCount.get(item.getName());
	}
}
