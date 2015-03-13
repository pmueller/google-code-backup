package model;

/*
 Sneak
 An Occupation that will be responsible for handling an Entity's ranged combat and
 dexterity stats, such as Agility and Movement, whenever that Entity levels up.
 Author: Jason
 Testing: Zach
 */

public class Sneak extends Occupation {
	private static final int AGILITY_BONUS = 2;
	private static final int INTELLECT_BONUS = 1;
	private static final int HARDINESS_BONUS = -2;
	private static final int BASE_STAT = 10;
	private static final int MINOR_BONUS = 1;

	@SuppressWarnings("unused")
	private String name = "Sneak";

	/**
	 * will update the entity's stats based on their new level every third level
	 * they get a boost to their non primary stats
	 * 
	 * @param leveledEntity
	 *            a reference to the entity that just gained a new level
	 */
	public void handleLevelUp(Entity leveledEntity) {
		int currentAgility = leveledEntity.getAgility();
		int currentIntellect = leveledEntity.getIntellect();

		leveledEntity.setAgility(currentAgility + AGILITY_BONUS);
		leveledEntity.setIntellect(currentIntellect + INTELLECT_BONUS);

		if (leveledEntity.level() % 3 == 0) // on every third level
		{
			int currentStrength = leveledEntity.getStrength();
			int currentHardiness = leveledEntity.getHardiness();

			leveledEntity.setStrength(currentStrength + MINOR_BONUS);
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

		firstLevelEntity.setStrength(BASE_STAT);
		firstLevelEntity.setAgility(BASE_STAT + AGILITY_BONUS);
		firstLevelEntity.setIntellect(BASE_STAT + INTELLECT_BONUS);
		firstLevelEntity.setHardiness(BASE_STAT + HARDINESS_BONUS);
	}

	/**
	 * @return "Assets/Avatar"
	 */
	public String getRepresentation() {
		return "Assets/Avatar";
	}
}