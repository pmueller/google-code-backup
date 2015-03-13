/* TiledZelda, a top-down 2d action-rpg game written in Java.
    Copyright (C) 2008  Facundo Manuel Quiroga <facundoq@gmail.com>
 	
 	This file is part of TiledZelda.

    TiledZelda is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    TiledZelda is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with TiledZelda.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.TheGame.model.elements.factories;

import java.io.File;
import java.util.HashMap;

import org.TheGame.exceptions.GameLoadingError;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.model.elements.factories.types.CreepDescription;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.MapTeleporter;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.util.RandomChooser;
import org.TheGame.model.items.Item;
import org.TheGame.model.items.EquipableItem;
import org.TheGame.model.items.ConsumableItem;
import org.TheGame.model.items.InstantItem;
import org.TheGame.model.items.QuestItem;
/**
 * @author Facundo Manuel Quiroga Oct 22, 2008
 */
public class ItemFactory{
	protected HashMap<String, Item> itemDescriptions;
	protected ItemDescriptionFactory itemDescriptionFactory = null;

	public ItemFactory() 
	{
		this.setItemDescriptions(new HashMap<String, Item>());
		
		ItemDescriptionFactory itemDescriptionFactory = this
				.getItemDescriptionLoader();
		// System.out.println(creepDescriptionFactory.getCreepDescriptions());
		this.getItemDescriptions().putAll(
				itemDescriptionFactory.getItemDescriptions());
	}

	protected ItemDescriptionFactory getItemDescriptionLoader() {
		if (this.itemDescriptionFactory == null) {
			// TODO sort out how to get the root directory
			String itemDescriptionsDirectoryName = Configuration.getInstance()
					.getValue("itemDescriptionsDirectory");
			String itemDescriptionsDirectory = Configuration.getInstance()
					.getRootDirectory()
					+ "" + itemDescriptionsDirectoryName;
			File itemDescriptionsDirectoryFile = new File(
					itemDescriptionsDirectory);
			this.itemDescriptionFactory = new ItemDescriptionFactory(
					itemDescriptionsDirectoryFile);
		}
		return this.itemDescriptionFactory;
	}

	protected void setItemDescriptionLoader(
			ItemDescriptionFactory itemDescriptionFactory) {
		this.itemDescriptionFactory = itemDescriptionFactory;
	}

	protected HashMap<String, Item> getItemDescriptions() {
		return this.itemDescriptions;
	}

	protected void setItemDescriptions(
			HashMap<String, Item> itemDescriptions) {
		this.itemDescriptions = itemDescriptions;
	}

	/**
	 * @return a random creep picked from all the available descriptions
	 */
	
	public Item CreateItem(String name)
	{
		return this.getItemDescriptions().get(name);
	}
	
	public Item createRandomItem(int creepLevel) 
	{
		int descriptionsQuantity = this.getItemDescriptions().size();
		Item[] descriptions = this.getItemDescriptions().values()
				.toArray(new Item[descriptionsQuantity]);
		
		
		int[] probabilities = this
				.getUniformProbabilities(descriptionsQuantity);
		
		RandomChooser<Item> randomChooser = new RandomChooser<Item>(
				descriptions, probabilities);
		Item ret = randomChooser.getObject().createItem();
		//System.out.println(creepLevel);
		//ret.setMonsterDropLevel(creepLevel);
		return ret;
	}


	/**
	 * @param descriptionsQuantity
	 * @return
	 */
	private int[] getUniformProbabilities(int descriptionsQuantity) {
		int[] probabilities = new int[descriptionsQuantity];
		if (descriptionsQuantity > 0) {
			int probability = 100 / descriptionsQuantity;
			int remaining = 100 % descriptionsQuantity;
			int positionsThatGetOneMore = remaining - 1;
			for (int i = 0; i < probabilities.length; i++) {
				probabilities[i] = probability;
				if (i <= positionsThatGetOneMore) {
					probabilities[i]++;
				}
			}
		}
		return probabilities;
	}

}
