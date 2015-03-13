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

package org.TheGame.input.ai.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.TheGame.events.actions.Action;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.input.ai.AI;
import org.TheGame.input.ai.NoValidDirectionForMovingException;
import org.TheGame.main.GameObjects;
import org.TheGame.main.logic.RandomNumberGenerator;
import org.TheGame.model.elements.movableElements.creeps.Creep;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Area;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;

/**
 * @author Facundo Manuel Quiroga Feb 10, 2009
 */
public abstract class AIComponent {

	protected AI ai;

	public AIComponent() {
	}

	public abstract List<Action> doThink() throws InvalidPositionException, OccupiedPositionException;

	protected Collection<Position> emptyAdjacentPositions() {
		TileMap creepsMap = this.getCreep().getCurrentTileMap();

		Position creepsPosition = this.getCreep().getPosition();

		Area nearbyArea = Area.newAreaAround(creepsPosition, 1);
		Collection<Position> positions = new ArrayList<Position>();
		for (Position position : nearbyArea) {
			if (creepsMap.validPosition(position)) {
				if (creepsMap.occupable(position)) {
					positions.add(position);
				}
			}
		}
		assert (!positions.contains(creepsPosition));
		return positions;
	}

	public Creep getCreep() {
		return this.ai.getCreep();
	}

	protected Collection<Direction> validDirectionsForMoving() {
		Collection<Direction> directions = new ArrayList<Direction>();
		Position creepsPosition = this.getCreep().getPosition();
		for (Position position : this.emptyAdjacentPositions()) {
			directions.add(creepsPosition.directionToGoTo(position));
		}
		return directions;
	}

	protected Direction randomValidDirectionForMoving()
			throws NoValidDirectionForMovingException {
		Collection<Direction> directions = this.validDirectionsForMoving();
		if (directions.isEmpty()) {
			throw new NoValidDirectionForMovingException("");
		}
		RandomNumberGenerator randomNumberGenerator = GameObjects.getInstance()
				.getRandomNumberGenerator();
		int moves = randomNumberGenerator.randomNumber() % directions.size();
		Iterator<Direction> i = directions.iterator();
		Direction direction = null;
		while (moves >= 0) {
			direction = i.next();
			moves--;
		}
		return direction;
	}

	protected int getAreaOfDetection() {
		return this.ai.getAreaOfDetection();
	}

	public AI getAi() {
		return this.ai;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}

	protected List<Position> findPathTo(Position targetPosition) {
		ArrayList<Position> path = new ArrayList<Position>();
		Creep creep = this.getCreep();
		Position creepsPosition = creep.getPosition();
		TileMap creepsMap = creep.getCurrentTileMap();
		if(creepsMap.validPosition(targetPosition) && creepsMap.occupable(targetPosition)) {			
			//System.out.println("New attempt to find path from " + creepsPosition + " to: " + targetPosition);
			class WeightedPosition implements Comparable<WeightedPosition>{
				Position position;
				Position target;
				WeightedPosition parent;
				int f;
				int g;
				private int h;
				
				public WeightedPosition(Position current, Position target, 
						WeightedPosition parent, int g) {
					this.position = current;
					this.target = target;
					this.parent = parent;
					this.g = g;
					//Distance d = current.distanceTo(target);
					
					//for now, this uses the manhattan distance, if we use a better
					//heuristic, it could find a better path. This just seemed easiest
					//and fastest to calculate.
						
					this.h = 10*(Math.abs(current.getX() - target.getX()) + Math.abs(current.getY() - target.getY()));
					this.f = this.g + this.h;
				}
				
				public int compareTo(WeightedPosition wp) {
					return this.f - wp.f;
				}
				
				public boolean equals(WeightedPosition wp) {
					return this.position.equals(wp.position);
				}
				
				public String toString() {
					return position.toString() + " f: " + this.f + " g: " + this.g + " h: " + this.h; 
				}
			}
			
			List<WeightedPosition> closedList = new LinkedList<WeightedPosition>();
			PriorityQueue<WeightedPosition> pq = new PriorityQueue<WeightedPosition>();
				
			WeightedPosition start = new WeightedPosition(creepsPosition, targetPosition, null, 0); 
			WeightedPosition target = new WeightedPosition(targetPosition, targetPosition, null, 0);
			
			pq.add(start);
			label: while(!pq.isEmpty()) {
				WeightedPosition cur = pq.poll();
				//System.out.println("Choosing: " + cur);
				closedList.add(cur);
				
				//remove all nodes equal to cur from the queue
				while(pq.remove(cur));
				
				int[] dx = new int[]{-1, 0, 1};
				int[] dy = new int[]{-1, 0, 1};
				
				//look at each of the surrounding positions
				for(int i : dx) {
					for(int j : dy) {
						int newX = cur.position.getX() + i;
						int newY = cur.position.getY() + j;
						
						
						Position p = Position.newPosition(newX, newY);
						int g = cur.g + (i*j==0? 10 : 14);
						WeightedPosition next = new WeightedPosition(p, cur.target, cur, g);
						//System.out.println("Next: " + next.toString());
						if(!closedList.contains(next) && creepsMap.validPosition(p) 
								&& creepsMap.occupable(p)) {
							pq.add(next);
						}
						if(next.equals(target)) {
							target = next;
							break label;
						}
					}
				}
			}
			
			//now that we have the list, add a bunch of walk actions
			WeightedPosition current = target;
			while(current != null) {
				path.add(0, current.position);
				//System.out.println(current.position);
				current = current.parent;
			}
			//System.out.println(path.size());
		}
		else {
				Direction direction = creepsPosition.directionToGoTo(targetPosition);
				path.add(creepsPosition.positionAfterMovingIn(direction));
		}
		return path;
	}
}
