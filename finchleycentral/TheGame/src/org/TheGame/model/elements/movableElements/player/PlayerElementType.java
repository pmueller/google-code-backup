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

package org.TheGame.model.elements.movableElements.player;

import java.util.ArrayList;

import org.TheGame.model.elements.movableElements.MovableElementCharacteristics;
import org.TheGame.model.elements.movableElements.MovableElementType;
import org.TheGame.model.items.Item;

/**
 * @author Facundo Manuel Quiroga Mar 10, 2009
 */
public class PlayerElementType extends MovableElementType {

	public PlayerElementType(String name, String team,
			MovableElementCharacteristics movableElementCharacteristics) {
		super(name, team, movableElementCharacteristics);
	}
	
	public int getDamage() 
	{
		int equipmentBonus=0;
		ArrayList<Item> playerEquipment = this.getCharacteristics().getEquipment();
		for(int i=0;i<playerEquipment.size();++i)
		{
			equipmentBonus += playerEquipment.get(i).itemElementTypeDescription.getDamage();
		}
		return (int) this.getCharacteristics().getStatisticNamed("Damage")
				.getValue() + equipmentBonus;
	}

	public int getStaminaHitCost() {
		return (int) this.getCharacteristics().getStatisticNamed(
				"StaminaCostPerHit").getValue();
	}

	public int getBaseSpeed() {
		int equipmentBonus=0;
		ArrayList<Item> playerEquipment = this.getCharacteristics().getEquipment();
		for(int i=0;i<playerEquipment.size();++i)
		{
			equipmentBonus += playerEquipment.get(i).itemElementTypeDescription.getBaseSpeed();
		}
		return (int) this.getCharacteristics().getStatisticNamed(
				"MovementSpeed").getValue()+equipmentBonus;
	}

	public int getDamageReduction() {
		int equipmentBonus=0;
		ArrayList<Item> playerEquipment = this.getCharacteristics().getEquipment();
		for(int i=0;i<playerEquipment.size();++i)
		{
			equipmentBonus += playerEquipment.get(i).itemElementTypeDescription.getDamageReduction();
		}
		return (int) this.getCharacteristics().getStatisticNamed(
				"DamageReduction").getValue()+equipmentBonus;
	}

	public int getChanceToHit() {
		int equipmentBonus=0;
		ArrayList<Item> playerEquipment = this.getCharacteristics().getEquipment();
		for(int i=0;i<playerEquipment.size();++i)
		{
			equipmentBonus += playerEquipment.get(i).itemElementTypeDescription.getChanceToHit();
		}
		return (int) this.getCharacteristics().getStatisticNamed("ChanceToHit")
				.getValue()+equipmentBonus;
	}

	public int getChanceToDodge() {
		int equipmentBonus=0;
		ArrayList<Item> playerEquipment = this.getCharacteristics().getEquipment();
		for(int i=0;i<playerEquipment.size();++i)
		{
			equipmentBonus += playerEquipment.get(i).itemElementTypeDescription.getChanceToDodge();
		}
		return (int) this.getCharacteristics().getStatisticNamed(
				"ChanceToDodge").getValue()+equipmentBonus;
	}
	
	
}
