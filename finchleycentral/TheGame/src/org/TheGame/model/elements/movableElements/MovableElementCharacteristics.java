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

package org.TheGame.model.elements.movableElements;

import java.util.ArrayList;

import org.TheGame.model.elements.movableElements.statistics.Statistic;
import org.TheGame.model.elements.movableElements.statistics.characteristics.Characteristics;
import org.TheGame.model.elements.movableElements.statistics.characteristics.attributes.Attribute;
import org.TheGame.model.elements.movableElements.statistics.characteristics.attributes.CommonAttributes;
import org.TheGame.model.elements.movableElements.statistics.characteristics.skills.CommonSkills;
import org.TheGame.model.elements.movableElements.statistics.characteristics.skills.Skill;
import org.TheGame.model.elements.movableElements.statistics.derived.ChanceToDodgeStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.ChanceToHitStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.DamageReductionStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.DamageStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.HealthRecoveryRateStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.MaxHealthStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.MaxStaminaStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.MovementSpeedStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.StaminaCostPerHitStatistic;
import org.TheGame.model.elements.movableElements.statistics.derived.StaminaRecoveryRateStatistic;
import org.TheGame.model.items.Item;

/**
 * @author Facundo Manuel Quiroga Jul 22, 2009
 */
public class MovableElementCharacteristics extends Characteristics {
	protected ArrayList<Attribute> attributes;
	protected ArrayList<Skill> skills;
	protected ArrayList<Statistic> statistics;
	protected ArrayList<Item> equipment;

	public MovableElementCharacteristics(float baseDamage,
			float baseDamageReduction, float baseChanceToHit,
			float baseChanceToDodge, float staminaHitCost, float baseSpeed,
			float baseHealth, float baseStamina,
			float baseHealthRestorationRate, float baseStaminaRestorationRate) {

		this.setAttributes(new ArrayList<Attribute>(CommonAttributes.getAll()));
		Attribute constitution = this.getAttributeNamed("Constitution");
		Attribute speed = this.getAttributeNamed("Speed");
		Attribute dexterity = this.getAttributeNamed("Dexterity");
		Attribute concentration = this.getAttributeNamed("Concentration");
		Attribute endurance = this.getAttributeNamed("Endurance");
		Attribute strength = this.getAttributeNamed("Strength");
		Attribute intelligence = this.getAttributeNamed("Intelligence");

		ArrayList<Skill> skills = new ArrayList<Skill>();
		Skill combat = CommonSkills.newCombat();
		Skill defense = CommonSkills.newDefense(combat);
		Skill weapons = CommonSkills.newWeapons(combat, strength, dexterity);
		Skill dodge = CommonSkills.newDodge(defense, speed, dexterity,
				intelligence);
		skills.add(combat);
		skills.add(defense);
		skills.add(weapons);
		skills.add(dodge);
		this.setSkills(skills);

		ArrayList<Statistic> statistics = new ArrayList<Statistic>();
		statistics
				.add(DamageStatistic.newDamage(baseDamage, strength, weapons));
		statistics.add(DamageReductionStatistic.newDamageReduction(
				baseDamageReduction, constitution));
		statistics.add(ChanceToHitStatistic.newChanceToHit(baseChanceToHit,
				speed, dexterity, weapons));
		statistics.add(ChanceToDodgeStatistic.newChanceToDodge(
				baseChanceToDodge, speed, dexterity, concentration));
		statistics.add(MovementSpeedStatistic
				.newMovementSpeed(baseSpeed, speed));
		statistics.add(StaminaCostPerHitStatistic.newStaminaCostPerHit(
				staminaHitCost, endurance, dexterity, weapons));

		statistics.add(MaxHealthStatistic.newMaxHealth(baseHealth,
				constitution, strength));
		statistics.add(MaxStaminaStatistic.newMaxStamina(baseStamina,
				constitution, endurance));
		statistics.add(HealthRecoveryRateStatistic.newHealthRecoveryRate(
				baseHealthRestorationRate, constitution));
		statistics.add(StaminaRecoveryRateStatistic.newStaminaRecoveryRate(
				baseStaminaRestorationRate, endurance));

		this.setStatistics(statistics);
		
		equipment = new ArrayList<Item>();
	}

	public ArrayList<Attribute> getAttributes() {
		return this.attributes;
	}

	protected void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}

	public ArrayList<Skill> getSkills() {
		return this.skills;
	}

	protected void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}

	public ArrayList<Statistic> getStatistics() {
		return this.statistics;
	}

	public ArrayList<Item> getEquipment()
	{
		return this.equipment;
	}
	
	public boolean wearItem(Item item)
	{
		//This returns false if an item of that slot is already worn.
		String slotName = item.itemElementTypeDescription.slot;
		for(int i=0;i<equipment.size();++i)
		{
			if(equipment.get(i).getId() == item.getId())
			{
				return false;
			}
			if(equipment.get(i).itemElementTypeDescription.slot.equalsIgnoreCase(slotName))
			{
				return false;
			}
		}
		equipment.add(item);
		return true;
	}
	
	public boolean takeItemOff(String slotName)
	{
		//items are taken off by slotname only.
		for(int i=0;i<equipment.size();++i)
		{
			if(equipment.get(i).itemElementTypeDescription.slot.equalsIgnoreCase(slotName))
			{
				equipment.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeNonexistingItem(long id)
	{
		/*
		 * This method is really only called by inventory when an item is
		 *  deleted from the inventory.
		 */
		if(id < 0) return false;
		boolean ret = false;
		for(int i=0;i<equipment.size();++i)
		{
			if(equipment.get(i).getId() == id)
			{
				equipment.remove(i);
				ret = true;
				break;
			}
		}
		return ret;
	}
	
	protected void setStatistics(ArrayList<Statistic> statistics) {
		this.statistics = statistics;
	}

}
