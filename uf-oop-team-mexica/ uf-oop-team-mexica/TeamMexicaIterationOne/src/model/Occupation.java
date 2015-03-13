package model;

/*
 The Entity is going to defer to the Occupation class for it's representation. It is
 also going to be responsible for setting an Entity's intital stats. In addition it will
 handle when an Entity levels up because the stats that are affected will be dependant
 on the Entity's Occupation.
 Author: Jason
 Tester: Zach
 */

public abstract class Occupation implements Representation {
	private String name;

	abstract public void handleLevelUp(Entity leveledEntity);

	abstract public void setInitialStats(Entity zeroLevelEntity);

	/**
	 * 
	 * @return the occupation name
	 */
	public String getName() {
		return name;
	}

}