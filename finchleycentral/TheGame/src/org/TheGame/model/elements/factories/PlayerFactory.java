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

import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameCore;
import org.TheGame.main.GameFactories;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.model.elements.movableElements.MovableElementCharacteristics;
import org.TheGame.model.elements.movableElements.player.AttributeState;
import org.TheGame.model.elements.movableElements.player.HealthState;
import org.TheGame.model.elements.movableElements.player.Player;
import org.TheGame.model.elements.movableElements.player.PlayerElementType;
import org.TheGame.model.elements.movableElements.statistics.Statistic;
import org.TheGame.model.elements.movableElements.statistics.characteristics.attributes.Attribute;
import org.TheGame.model.map.MapTeleporter;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.visualization.graphics.element.movable.MovableElementGraphic;

/**
 * @author Facundo Manuel Quiroga Oct 22, 2008
 */
public class PlayerFactory extends MovableElementFactory {

	public PlayerFactory() {

	}

	public Player createPlayer (TileMap tileMap, Position position)
			throws InvalidPositionException, OccupiedPositionException {

		MovableElementGraphic graphic = GameFactories.getInstance()
				.getMovableElementGraphicFactory().getMovableElementGraphic(
						"player");
		MovableElementCharacteristics characteristics;
		if(Configuration.getInstance().getValueAsBoolean("cheatMode")) {
			characteristics = new MovableElementCharacteristics(
					40, 2, 70, 10, 5, 120, 9001, 2000, 20, 20);
		}
		else {
			characteristics = new MovableElementCharacteristics(
					40, 2, 70, 10, 5, 120, 50, 20, 200, 200);
		}
		PlayerElementType playerElementType = new PlayerElementType("player", "player",
				characteristics);
		Statistic maxHealth = characteristics.getStatisticNamed("MaxHealth");
		Statistic maxStamina = characteristics.getStatisticNamed("MaxStamina");
		Statistic healthRecoveryRate;
		Statistic staminaRecoveryRate;
		AttributeState health;
		AttributeState stamina;
		if(Configuration.getInstance().getValueAsBoolean("cheatMode")) {
			healthRecoveryRate = characteristics
					.getStatisticNamed("HealthRecoveryRate");
			staminaRecoveryRate = characteristics
					.getStatisticNamed("StaminaRecoveryRate");
			health = new AttributeState("health", 9001, maxHealth,
					healthRecoveryRate);
			stamina = new AttributeState("stamina", 2000, maxStamina,
					staminaRecoveryRate);
			
		}
		else{
			healthRecoveryRate = characteristics
					.getStatisticNamed("HealthRecoveryRate");
			staminaRecoveryRate = characteristics
					.getStatisticNamed("StaminaRecoveryRate");
			health = new AttributeState("health", 100, maxHealth,
					healthRecoveryRate);
			stamina = new AttributeState("stamina", 40, maxStamina,
					staminaRecoveryRate);
		}
		HealthState healthState = new HealthState(health, stamina);
		Player player = new Player(playerElementType, graphic, healthState);
		if(Configuration.getInstance().getValueAsBoolean("cheatMode")) {
			player.getElementType().getCharacteristics().getAttributeNamed("Speed").resetCurrentLevelTo(25, 625);
		}
		MapTeleporter.getInstance().teleportTo(player, tileMap, position);
		return player;
	}

}
