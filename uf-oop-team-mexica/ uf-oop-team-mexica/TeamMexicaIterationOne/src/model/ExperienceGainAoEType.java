/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 * AoEType that gives an Entity more experience.
 * 
 * @author korytov
 */
public class ExperienceGainAoEType extends AoEType {
	private int expAmount;

	/**
	 * Creates an Experience Gaining AoEType with the given amount of experience
	 * to add each call.
	 * 
	 * @param expAmount
	 *            Amount of experience to add on each update.
	 */
	public ExperienceGainAoEType(int expAmount) {
		this.expAmount = expAmount;
	}

	/**
	 * Adds to Entity's experience by some amount.
	 */
	protected void doEffect(Entity e) {
		e.addExp(expAmount);
	}

	/**
	 * "exp"
	 */
	public String toString() {
		return "exp";
	}

	/**
	 * returns the exp amount
	 */
	public int getNum() {
		return this.expAmount;
	}
}
