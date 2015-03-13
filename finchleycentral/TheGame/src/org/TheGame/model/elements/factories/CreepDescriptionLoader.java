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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.TheGame.exceptions.GameLoadingError;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.model.elements.factories.types.CreepAIDescription;
import org.TheGame.model.elements.factories.types.CreepDescription;
import org.TheGame.model.elements.factories.types.CreepElementGraphicDescription;
import org.TheGame.model.elements.factories.types.CreepElementTypeDescription;
import org.TheGame.model.map.maputils.Position;
import org.ini4j.Ini;
import org.ini4j.InvalidIniFormatException;
import org.ini4j.Ini.Section;

/**
 * @author Facundo Manuel Quiroga Mar 16, 2009
 */
public class CreepDescriptionLoader {

	public CreepDescription load(File file, String name) {
		try {
			Ini iniFile = new Ini(new FileInputStream(file));
			CreepAIDescription creepAIDescription = this
					.getCreepAIDescription(iniFile.get("AI"));
			CreepElementGraphicDescription creepElementGraphicDescription = this
					.getCreepElementGraphicDescription(iniFile
							.get("ElementGraphic"));
			CreepElementTypeDescription creepElementTypeDescription = this
					.getCreepElementTypeDescription(iniFile.get("ElementType"),
							name);
			return new CreepDescription(creepElementTypeDescription,
					creepElementGraphicDescription, creepAIDescription);

		} catch (NumberFormatException e) {
			throw new GameLoadingError("Error loading CreepDescriptionFile "
					+ file.getPath() + ". Message: " + e.getMessage());
		} catch (InvalidIniFormatException e) {
			throw new GameLoadingError(
					"InvalidIniFormatException when opening creepDescriptionFile file"
							+ file.getPath() + ". Message: " + e.getMessage());
		} catch (FileNotFoundException e) {
			throw new GameLoadingError(
					"FileNotFoundException when opening creepDescriptionFile file"
							+ file.getPath() + ". Message: " + e.getMessage());
		} catch (IOException e) {
			throw new GameLoadingError(
					"IOException when opening creepDescriptionFile file"
							+ file.getPath() + ". Message: " + e.getMessage());
		} catch (GameLoadingError e) {
			throw new GameLoadingError(
					"Error when opening creepDescriptionFile file"
							+ file.getPath() + ". Message: " + e.getMessage());
		}

	}

	/**
	 * @param section
	 * @param name2
	 * @return
	 */
	protected CreepElementTypeDescription getCreepElementTypeDescription(
			Section section, String name) {
		
		double diffMult = 1.0;
		if(Configuration.getInstance().getValue("difficulty").equals("hard"))
			diffMult = 1.5;
		else if(Configuration.getInstance().getValue("difficulty").equals("easy"))
			diffMult = .5;
		String team = section.get("team");
		int level = (int)(this.getValueAsInt(section, "level"));
		int damage = (int)(this.getValueAsInt(section, "damage")*diffMult);
		int damageReduction = (int)(this.getValueAsInt(section, "damageReduction")*diffMult);
		int staminaHitCost = (int)(this.getValueAsInt(section, "staminaHitCost")*diffMult);
		int baseSpeed = (int)(this.getValueAsInt(section, "baseSpeed")/diffMult);
		int chanceToHit = (int)(this.getValueAsInt(section, "chanceToHit")*diffMult);
		int chanceToDodge = (int)(this.getValueAsInt(section, "chanceToDodge")*diffMult);
		int maxHealth = (int)(this.getValueAsInt(section, "maxHealth")*diffMult);
		int maxStamina = (int)(this.getValueAsInt(section, "maxStamina")*diffMult);
		int healthRestorationRate = (int)(this.getValueAsInt(section,
				"healthRestorationRate")/diffMult);
		int staminaRestorationRate = (int)(this.getValueAsInt(section,
				"staminaRestorationRate")/diffMult);
		return new CreepElementTypeDescription(name, team, level,damage, damageReduction,
				staminaHitCost, baseSpeed, chanceToHit, chanceToDodge,
				maxHealth, maxStamina, healthRestorationRate,
				staminaRestorationRate);
	}

	protected int getValueAsInt(Section section, String name) {
		String value = section.get(name);
		try {
			return new Integer(value);
		} catch (NumberFormatException e) {
			throw new GameLoadingError("Key " + name + " in section "
					+ section.getName() + " is not an integer; value = "
					+ value);
		}
	}

	protected boolean getValueAsBoolean(Section section, String name) {
		String value = section.get(name);
		if (value.equals("true")) {
			return true;
		} else if (value.equals("false")) {
			return false;
		} else {
			throw new GameLoadingError("Key " + name + " in section "
					+ section.getName()
					+ " should have a boolean value (true|false), has " + value);
		}
	}

	/**
	 * @param section
	 * @return
	 */
	private CreepElementGraphicDescription getCreepElementGraphicDescription(
			Section section) {
		String graphic = section.get("graphic");
		boolean graphicIsSimple = this.getValueAsBoolean(section,
				"graphicIsSimple");
		return new CreepElementGraphicDescription(graphic, graphicIsSimple);
	}

	/**
	 * @param section
	 * @return
	 */
	private CreepAIDescription getCreepAIDescription(Section section) {
		String type = section.get("type");
		CreepAIDescription creepAIDescription = new CreepAIDescription(type);
		
		Collection<String> reactiveToTypeList = new ArrayList<String>();
		reactiveToTypeList.addAll(Arrays.asList(new String[] {"escaping", "following", "passiveFollowing", 
				"bodyguard", "suicide", "healing"})); 
				
		if(reactiveToTypeList.contains(type)) {
			String typesReactiveTo = section.get("typesReactiveTo");
			String[] types = typesReactiveTo.split(",\\s*");
			List<String> list = Arrays.asList(types);		
			ArrayList<String> typeNames = new ArrayList<String>(list);
			
			String teamsReactiveTo = section.get("teamsReactiveTo");
			String[] teams = teamsReactiveTo.split(",\\s*");
			List<String> teamList = Arrays.asList(teams);
			ArrayList<String> teamNames = new ArrayList<String>(teamList);		
			
			creepAIDescription = new CreepAIDescription(type, typeNames, teamNames);
			
			if(type.equals("bodyguard")) {
				String typesDefendingFrom = section.get("typesDefendingFrom");
				String[] defendTypes = typesDefendingFrom.split(",\\s*");
				List<String> defendList = Arrays.asList(defendTypes);
				ArrayList<String> defendTypeNames = new ArrayList<String>(defendList);
				
				String teamsDefendingFrom = section.get("teamsDefendingFrom");
				String[] defendTeams = teamsDefendingFrom.split(",\\s*");
				List<String> defendTeamList = Arrays.asList(defendTeams);
				ArrayList<String> defendTeamNames = new ArrayList<String>(defendTeamList);
				creepAIDescription =  new CreepAIDescription(type, typeNames, teamNames,
						defendTypeNames, defendTeamNames);
			}
		} else if(type.equals("pathWalking") || type.equals("boundedWalking")) {
			//System.out.println("CDL: " + type);
			String positionString = section.get("positions");
			//wayPointString.replaceAll("[\\(\\)]","");
			String[] positionCoords = positionString.split(";");
			ArrayList<Position> positions = new ArrayList<Position>();
			for(int i = 0; i < positionCoords.length; ++i) {
				
				int x = Integer.parseInt(positionCoords[i].split(",")[0]);
				int y = Integer.parseInt(positionCoords[i].split(",")[1]);
				positions.add(Position.newPosition(x, y));
			}
			creepAIDescription = new CreepAIDescription(type, positions);
		} else if(type.equals("explain")) {
			creepAIDescription = new CreepAIDescription(type, section.get("text").split(",\\s*"));
		}
		return creepAIDescription; 
	}
}
