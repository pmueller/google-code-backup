package org.TheGame.events.actions;

import org.TheGame.events.actions.results.ActionResultAttack;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.model.elements.Element;

public class RangedAttackAction extends Action {
	private Element attacker;
	private Element target;
	
	public RangedAttackAction(Element attacker, Element target) {
		this.setAttacker(attacker);
		this.setTarget(target);
	}
	
	public ActionResultAttack execute() throws InvalidPositionException, OccupiedPositionException {
		return this.getAttacker().attackAtRange(this.getTarget());
	}
	
	public Element getAttacker() {
		return this.attacker;
	}
	
	public void setAttacker(Element attacker) {
		this.attacker = attacker;
	}

	public Element getTarget() {
		return this.target;
	}

	public void setTarget(Element target) {
		this.target = target;
	}

	
}
