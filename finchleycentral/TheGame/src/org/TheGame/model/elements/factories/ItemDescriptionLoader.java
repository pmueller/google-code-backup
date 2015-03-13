package org.TheGame.model.elements.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.TheGame.exceptions.GameLoadingError;
import org.TheGame.model.elements.factories.types.CreepDescription;
import org.TheGame.model.elements.factories.types.CreepElementGraphicDescription;
import org.TheGame.model.elements.factories.types.CreepElementTypeDescription;
import org.TheGame.model.elements.factories.types.ElementGraphicDescription;
import org.TheGame.model.elements.factories.types.ItemElementTypeDescription;
import org.TheGame.model.items.Item;
import org.TheGame.model.items.EquipableItem;
import org.TheGame.model.items.ConsumableItem;
import org.TheGame.model.items.InstantItem;
import org.ini4j.Ini;
import org.ini4j.InvalidIniFormatException;
import org.ini4j.Ini.Section;

public class ItemDescriptionLoader 
{
	//note: 'Item' Class is the item analog of creep's CreepDescription
	//TODO: rename Item and all others so it conforms with creep design
	public Item load(File file, String name) 
	{
		Item createdItem = null;
		try
		{
			Ini iniFile = new Ini(new FileInputStream(file));
			ElementGraphicDescription elementGraphicDescription = this
			.getElementGraphicDescription(iniFile
					.get("ElementGraphic"));
			ItemElementTypeDescription itemElementTypeDescription = this
					.getItemElementTypeDescription(iniFile,
							name);
			String type = iniFile.get("ElementType").get("itemType");
			
			Class [] classParm = {ElementGraphicDescription.class, ItemElementTypeDescription.class};
			
			Class itemTypeClass = Class.forName("org.TheGame.model.items."+type+"Item");
			Object [] argList = {elementGraphicDescription, itemElementTypeDescription};
			java.lang.reflect.Constructor co = itemTypeClass.getConstructor(classParm);
			createdItem = (Item)co.newInstance(argList);
					
		} catch (NumberFormatException e) {
			throw new GameLoadingError("Error loading itemDescriptionFile "
					+ file.getPath() + ". Message: " + e.getMessage());
		} catch (InvalidIniFormatException e) {
			throw new GameLoadingError(
					"InvalidIniFormatException when opening itemDescriptionFile file"
							+ file.getPath() + ". Message: " + e.getMessage());
		} catch (FileNotFoundException e) {
			throw new GameLoadingError(
					"FileNotFoundException when opening itemDescriptionFile file"
							+ file.getPath() + ". Message: " + e.getMessage());
		} catch (IOException e) {
			throw new GameLoadingError(
					"IOException when opening itemDescriptionFile file"
							+ file.getPath() + ". Message: " + e.getMessage());
		} catch (GameLoadingError e) {
			throw new GameLoadingError(
					"Error when opening itemDescriptionFile file"
							+ file.getPath() + ". Message: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return createdItem;
	}
	
	//TODO: This utility method is repeated in creepdescriptionloader class
	protected boolean getValueAsBoolean(Section section, String name) {
		String value = section.get(name);
		if (value.equals("true")) {
			return true;
		} else if (value.equals("false")) {
			return false;
		} else {
			throw new GameLoadingError("Key " + name + " in section "
					+ section.getName()
					+ " should have a boolean value (true|false), has " + value);
		}
	}
	protected int getValueAsInt(Section section, String name) {
		String value = section.get(name);
		try {
			return new Integer(value);
		} catch (NumberFormatException e) {
			throw new GameLoadingError("Key " + name + " in section "
					+ section.getName() + " is not an integer; value = "
					+ value);
		}
	}
	
	protected ItemElementTypeDescription getItemElementTypeDescription(
			Ini file, String graphicName) {
		Section section = file.get("ElementType");
		String name = section.get("name");
		String type = section.get("itemType");
		ItemElementTypeDescription ret = null;
		if(type.equalsIgnoreCase("Equipable"))
			ret = getEquipableItemTypeDescription(file,graphicName,name);
		else if(type.equalsIgnoreCase("Consumable"))
			ret = getConsumableItemTypeDescription(file,graphicName,name);
		else if(type.equalsIgnoreCase("Instant"))
			ret = getInstantItemTypeDescription(file,graphicName,name);
		else if(type.equalsIgnoreCase("Quest"))
			ret = getQuestItemTypeDescription(file,graphicName,name);
		else if(type.equalsIgnoreCase("Money"))
			ret = getMoneyItemTypeDescription(file,graphicName,name);
		return ret;
	}
	
	private ItemElementTypeDescription getMoneyItemTypeDescription(Ini file,
			String graphicName, String name) {
		Section section = file.get("Money");
		return new ItemElementTypeDescription("Money",name,graphicName,this.getValueAsInt(section, "moneyMultipler"));
	}

	private ItemElementTypeDescription getEquipableItemTypeDescription(Ini file, String graphicName,String name){
		Section section = file.get("Equipable");
		//System.out.println(section == null ? "Yes" : "No");
		String slot = section.get("slot");
		int damage = this.getValueAsInt(section, "damage");
		int damageReduction = this.getValueAsInt(section, "damageReduction");
		//int staminaHitCost = this.getValueAsInt(section, "staminaHitCost");
		int baseSpeed = this.getValueAsInt(section, "baseSpeed");
		int chanceToHit = this.getValueAsInt(section, "chanceToHit");
		int chanceToDodge = this.getValueAsInt(section, "chanceToDodge");
		int Health = this.getValueAsInt(section, "Health");
		int Stamina = this.getValueAsInt(section, "Stamina");
		int healthRestorationRate = this.getValueAsInt(section,
				"healthRestorationRate");
		int staminaRestorationRate = this.getValueAsInt(section,
				"staminaRestorationRate");

		return new ItemElementTypeDescription("Equipable",name,graphicName, slot,damage, damageReduction,
				chanceToHit,chanceToDodge,baseSpeed,
				Health, Stamina, healthRestorationRate,
				staminaRestorationRate);

	}
	private ItemElementTypeDescription getConsumableItemTypeDescription(Ini file, String graphicName, String name){
		Section section = file.get("Consumable");
		int healing = this.getValueAsInt(section, "healing");
		int duration = this.getValueAsInt(section,"duration");
		return new ItemElementTypeDescription("Consumbale",name,graphicName,healing,duration);
	}
	private ItemElementTypeDescription getQuestItemTypeDescription(Ini file, String graphicName, String name){
		Section section = file.get("Quest");
		int maxAmt = this.getValueAsInt(section, "maxAmount");
		int useAmt = this.getValueAsInt(section, "useAmount");
		String questDescription = section.get("description");
		//System.out.println(questDescription);
		return new ItemElementTypeDescription("Quest",name,graphicName,this.getValueAsInt(section, "useAmount")==0?false:true,maxAmt,useAmt,questDescription);
	}
	private ItemElementTypeDescription getInstantItemTypeDescription(Ini file, String graphicName, String name){
		Section section = file.get("Instant");
		return new ItemElementTypeDescription("Instant",name,graphicName,this.getValueAsInt(section, "healing"));
	}
	private ElementGraphicDescription getElementGraphicDescription(
			Section section) {
		String graphic = section.get("graphic");
		boolean graphicIsSimple = this.getValueAsBoolean(section,
				"graphicIsSimple");
		return new CreepElementGraphicDescription(graphic, graphicIsSimple);
	}
}
