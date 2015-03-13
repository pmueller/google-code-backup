package model;

/*
 Summoner
 An Occupation that will be responsible for improving an Entity's magical stats, such
 as Mana and Intellect, whenever that Entity levels up.
 Author: Jason
 Testing: Zach
 */

public class Summoner extends Occupation {
	private static final int STRENGTH_BONUS = -3;
	private static final int INTELLECT_BONUS = 3;
	private static final int BASE_STAT = 10;
	private static final int MINOR_BONUS = 1;

	@SuppressWarnings("unused")
	private String name = "Summoner";

	/**
	 * will update the entity's stats based on their new level every third level
	 * they get a boost to their non primary stats
	 * 
	 * @param leveledEntity
	 *            a reference to the entity that just gained a new level
	 */
	public void handleLevelUp(Entity leveledEntity) {
		int currentIntellect = leveledEntity.getIntellect();

		leveledEntity.setIntellect(currentIntellect + INTELLECT_BONUS);

		if (leveledEntity.level() % 3 == 0) // on every third level
		{
			int currentStrength = leveledEntity.getStrength();
			int currentAgility = leveledEntity.getAgility();
			int currentHardiness = leveledEntity.getHardiness();

			leveledEntity.setStrength(currentStrength + MINOR_BONUS);
			leveledEntity.setAgility(currentAgility + MINOR_BONUS);
			leveledEntity.setHardiness(currentHardiness + MINOR_BONUS);
		}
	}

	/**
	 * when an entity is first instantiated, this method sets their statistics
	 * to their initial value, based on class
	 * 
	 * @param firstLevelEntity
	 *            a reference to the newly instantiated entity
	 */
	public void setInitialStats(Entity firstLevelEntity) {
		if (firstLevelEntity.getExperience() > 0)
			return;

		firstLevelEntity.setStrength(BASE_STAT + STRENGTH_BONUS);
		firstLevelEntity.setAgility(BASE_STAT);
		firstLevelEntity.setIntellect(BASE_STAT + INTELLECT_BONUS);
		firstLevelEntity.setHardiness(BASE_STAT);
	}

	/**
	 * @return "Assets/Avatar"
	 */
	public String getRepresentation() {
		return "Assets/Avatar";
	}
}