package org.TheGame.model.elements.factories;

import java.io.File;
import java.util.HashMap;

import org.TheGame.exceptions.GameLoadingError;
import org.TheGame.main.GameFactories;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.model.items.Item;
import org.TheGame.model.items.EquipableItem;
import org.TheGame.model.items.ConsumableItem;
import org.TheGame.model.items.InstantItem;

public class ItemDescriptionFactory {
	protected HashMap<String, Item> itemDescriptions;
	
	public ItemDescriptionFactory(File itemDescriptionFolder) {
		this.itemDescriptions = this.loadItemDescriptions(
				itemDescriptionFolder, "");
	}
	
	protected HashMap<String, Item> loadItemDescriptions(
			File itemDescriptionDirectory, String baseName) {
		HashMap<String, Item> itemDescriptions = new HashMap<String, Item>();
		for (File file : itemDescriptionDirectory.listFiles()) {
			String newBaseName = baseName;
			// add the point (.) and the directory name as part of the base name
			// of all the descriptions inside the file
			if (!baseName.equals("")) {
				newBaseName = newBaseName + ".";
			}
			newBaseName = newBaseName + file.getName();
			// if file is a directory, recurse and load its descriptions
			if (file.isDirectory()) {
				itemDescriptions.putAll(this.loadItemDescriptions(file,
						newBaseName));
			} else if (file.isFile()) {
				// if its a file and it ends with the proper suffix, load its
				// description
				if (file.getName().endsWith(
						"." + this.getItemDescriptionFileSuffix())) {
					// remove the suffix from the name of the description
					int endIndex = newBaseName.length() - 1
							- this.getItemDescriptionFileSuffix().length();
					newBaseName = newBaseName.substring(0, endIndex);
					itemDescriptions.put(newBaseName, this
							.loadItemDescription(file, newBaseName));
				}
			} else {
				GameFactories.getInstance().getFactoriesLogger().logWarning(
						"Warning: While loading item descriptions: file "
								+ file.getPath()
								+ " is not a file or a directory.");
			}
		}
		return itemDescriptions;
	}
	
	protected Item loadItemDescription(File file, String name) {
		return new ItemDescriptionLoader().load(file, name);
	}
	
	public Item getItemDescription(String name) {
		Item item = this.getItemDescriptions().get(
				name);
		if (item == null) {
			throw new GameLoadingError("Creep description named '" + name
					+ "' not found.");
		}
		return item;
	}
	
	public HashMap<String, Item> getItemDescriptions() {
		return this.itemDescriptions;
	}
	
	protected String getItemDescriptionFileSuffix() {
		return Configuration.getInstance().getValue(
				"itemDescriptionFileExtension");
	}
	
	protected void setItemDescriptions(
			HashMap<String, Item> itemDescriptions) {
		this.itemDescriptions = itemDescriptions;
	}
}
