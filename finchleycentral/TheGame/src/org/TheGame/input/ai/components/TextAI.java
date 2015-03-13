package org.TheGame.input.ai.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.TheGame.events.actions.Action;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameCore;
import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.model.items.Inventory;
import org.TheGame.model.map.maputils.Position;

public class TextAI extends AIComponent {

	String[] text;
	int current;

	public TextAI(String[] text) {
		this.text = text;
		this.current = 0;
	}

	@Override
	public List<Action> doThink() throws InvalidPositionException,
			OccupiedPositionException {
		Inventory inventory = InventoryManager.getInstance().getPlayerInventory("normal");
		if(this.getCreep().isAttacked()) {
			if(!inventory.removeItem("Crown of Justice")) {
				if (current < text.length && this.getCreep().isAttacked()) {
					GameCore.getInstance().getSidePanelGUI().addText(
							text[current] + "\n");
					++current;
				}
				this.getCreep().resetAttacked();
			}
			else {
				GameCore.getInstance().getSidePanelGUI().addText("You just won the game!\n");
				current = text.length;
				this.getCreep().resetAttacked();
			}
		}
		return new ArrayList<Action>();
	}
}
