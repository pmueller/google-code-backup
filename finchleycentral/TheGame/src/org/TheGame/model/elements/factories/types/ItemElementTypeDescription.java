package org.TheGame.model.elements.factories.types;

import java.util.HashMap;
import java.util.Set;

public class ItemElementTypeDescription 
{	
	/* each of these represents what bonus the item gives you */
	public String type;
	public String name;
	public String graphicName;
	public String slot;
	
	private HashMap<String,Integer> stats;
	
	// consumable & instant
	private int healing;
	private int duration;
	
	//quest
	private boolean usable; // if its consumed once it comes into play
	private int maxAmount; //max of item you can have
	private int useAmount; //amount lost when 'used'
	private String questDescrip;
	
	//money
	private int moneyMultipler;
	
	public ItemElementTypeDescription(String type,String name,String graphicName,String slot,int damage, int damageReduction,int chanceToHit, 
			int chanceToDodge,int baseSpeed,int health, int stamina,int healthRestorationRate
			,int staminaRestorationRate)
	{
		stats = new HashMap<String,Integer>();
		this.type = type;
		this.name = name;
		this.graphicName = graphicName;
		this.slot = slot;
		
		stats.put("damage", damage);
		stats.put("damageReduction", damageReduction);
		stats.put("chanceToHit", chanceToHit);
		stats.put("chanceToDodge", chanceToDodge);
		stats.put("baseSpeed", baseSpeed);
		stats.put("health", health);
		stats.put("stamina", stamina);
		stats.put("healthRestorationRate", healthRestorationRate);
		stats.put("staminaRestorationRate", staminaRestorationRate);
	}
	
	//TODO: implement all other possible stat improvements
	public ItemElementTypeDescription(String type,String name,String graphicName, int healing,int duration){
		this.type = type;
		this.name = name;
		this.graphicName = graphicName;
		this.setDuration(duration);
		this.setHealing(healing);
	}
	public ItemElementTypeDescription(String type,String name,String graphicName, int moneyMultipler){
		this.type = type;
		this.name = name;
		this.graphicName = graphicName;
		this.setMoneyMultipler(moneyMultipler);
	}
	public ItemElementTypeDescription(String type,String name,String graphicName, boolean usable, int maxAmt,int useAmt,String description){
		this.type = type;
		this.name = name;
		this.graphicName = graphicName;
		this.setUsable(usable);
		this.setMaxAmount(maxAmt);
		this.setUseAmount(useAmt);
		this.setQuestDescrip(description);
	}

	public void setHealing(int healing) {
		this.healing = healing;
	}

	public int getHealing() {
		return healing;
	}

	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	public boolean isUsable() {
		return usable;
	}

	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	public int getMaxAmount() {
		if(maxAmount == 0) maxAmount = Integer.MAX_VALUE;
		return maxAmount;
	}

	public void setUseAmount(int useAmount) {
		this.useAmount = useAmount;
	}

	public int getUseAmount() {
		return useAmount;
	}

	public void setQuestDescrip(String questDescrip) {
		this.questDescrip = questDescrip;
	}

	public String getQuestDescrip() {
		return questDescrip;
	}

	public int getDamage() 
	{
		return stats.get("damage");
	}

	public int getBaseSpeed() {
		return stats.get("baseSpeed");
	}

	public int getDamageReduction() {
		return stats.get("damageReduction");
	}

	public int getChanceToHit() {
		return stats.get("chanceToHit");
	}

	public int getChanceToDodge() {
		return stats.get("chanceToDodge");
	}
	
	public int getStat(String s)
	{
		return stats.get(s);
	}
	
	public Set<String> getKeys()
	{
		return stats.keySet();
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void setMoneyMultipler(int moneyMultipler) {
		this.moneyMultipler = moneyMultipler;
	}

	public int getMoneyMultipler() {
		return moneyMultipler;
	}
}
