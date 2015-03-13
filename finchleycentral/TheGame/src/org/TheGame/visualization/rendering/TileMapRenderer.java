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

package org.TheGame.visualization.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Random;

import org.TheGame.main.logic.RandomNumberGenerator;
import org.TheGame.main.resourcemanagement.Configuration;
import org.TheGame.main.resourcemanagement.ImageManager;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.MovableElement;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Area;
import org.TheGame.model.map.maputils.Distance;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.visualization.screen.ScreenManager;

/**
 * The TileMapRenderer class draws a TileMap on the screen. It draws all
 * FloorCells and Elements.
 * 
 * @author Facundo Manuel Quiroga Last Update: 05/10/2008
 */
public class TileMapRenderer {

	private int maxImageSize = 5; // the maximum size an image can have

	private int blockSize = Configuration.getInstance().getValueAsInt(
			"blockSize");

	private int numberOfBlocks = Configuration.getInstance().getValueAsInt(
			"numberOfBlocks");

	public TileMapRenderer() {
		super();
	}

	/**
	 * @param screen
	 *            where to draw
	 * @param element
	 *            where the camera is centered (could be player or not, another
	 *            npc, a rock, etc), whose current map is going to be drawn.
	 * @param player
	 *            from which the on-screen info is to be retrieved
	 */
	public void draw(ScreenManager screen, Element element,
			MovableElement player) {
		// draw the map, with center position being playerBlockX,playerBlockY
		TileMap tileMap = element.getCurrentTileMap();

		// obtain player's position to know where is the center of the
		// sub-matrix
		// of the map that needs to be drawn, and calculate the area that needs
		// to be drawn and the Position of the top-left block.
		// numberOfBlocks is always odd.
		int screenHalf = (this.numberOfBlocks - 1) / 2;
		Distance playerToTopLeftBlock = new Distance(-1 * screenHalf, -1
				* screenHalf);
		Position topLeftBlock = player.getPosition().positionAt(
				playerToTopLeftBlock);
		// Drawing area is one block larger for all borders so when player moves
		// the tile row or column that is going to be hidden gets drawn too as
		// it goes away from sight.
		int extraDrawingArea = this.maxImageSize - 1;
		Position drawingAreaStartingPosition = Position.newPosition(
				topLeftBlock.getX() - extraDrawingArea, topLeftBlock.getY()
						- extraDrawingArea);
		Position drawingAreaEndingPosition = Position.newPosition(topLeftBlock
				.getX()
				+ 15 + extraDrawingArea, topLeftBlock.getY()
				+ 15 + extraDrawingArea);
		Area drawingArea = new Area(drawingAreaStartingPosition,
				drawingAreaEndingPosition);

		// Correct it to put the player always in the center
		int correctionX = element.getGraphic().getXCorrection();
		int correctionY = element.getGraphic().getYCorrection();
		Graphics2D g = screen.getGraphics();
		// draw game!

		// long lastTime = System.currentTimeMillis();
		// long currentTime = lastTime;

		this.drawFloor(g, tileMap, drawingArea, topLeftBlock, correctionX,
				correctionY);
		// currentTime = System.currentTimeMillis();
		// lastTime = currentTime - lastTime;
		// System.out.println("Drawing floor took "+ lastTime);
		// lastTime = currentTime;

		this.drawElements(g, tileMap, drawingArea, topLeftBlock, correctionX,
				correctionY);
		// currentTime = System.currentTimeMillis();
		// lastTime = currentTime - lastTime;
		// System.out.println("Drawing elements took"+ lastTime);

		g.dispose();
		screen.update();
	}

	private void drawFloor(Graphics2D g, TileMap tileMap, Area drawingArea,
			Position topLeftBlock, int correctionX, int correctionY) {
		g.setColor(Color.black);
		Iterator<Position> areaIterator = drawingArea.iteratorLeftRightUpDown();
		while (areaIterator.hasNext()) {
			Position position = areaIterator.next();
			Distance topLeftBlockToCurrentPosition = topLeftBlock
					.distanceTo(position);
			int screenX = topLeftBlockToCurrentPosition.getHorizontalDistance()
					* this.blockSize;
			int screenY = topLeftBlockToCurrentPosition.getVerticalDistance()
					* this.blockSize;
			screenX -= correctionX;
			screenY -= correctionY;
			if (tileMap.validPosition(position)) { // draw the floor tile
				tileMap.getFloor(position).draw(g, screenX, screenY);
			} else { // draw black background
				g.fillRect(screenX, screenY, this.blockSize, this.blockSize);
				//int i = (int)(2*Math.random());
				//if(i==0)
					g.drawImage(ImageManager.getInstance().getValue(tileMap.getBorder()), screenX, screenY, null);
				//else
				//	g.drawImage(ImageManager.getInstance().getValue("shrub2"), screenX, screenY, null);						
			}
		}

	}

	private void drawElements(Graphics2D g, TileMap tileMap, Area drawingArea,
			Position topLeftBlock, int correctionX, int correctionY) {
		Iterator<Position> areaIterator = drawingArea.iteratorLeftRightUpDown();
		while (areaIterator.hasNext()) {
			Position position = areaIterator.next();
			Distance topLeftBlockToCurrentPosition = topLeftBlock
					.distanceTo(position);
			int screenX = topLeftBlockToCurrentPosition.getHorizontalDistance()
					* this.blockSize;
			int screenY = topLeftBlockToCurrentPosition.getVerticalDistance()
					* this.blockSize;
			screenX -= correctionX;
			screenY -= correctionY;
			if (tileMap.validPosition(position)) {
				Element element = tileMap.elementAt(position);
				element.getGraphic().draw(g, screenX, screenY);
			}
		}
	}

	protected int drawingSurfaceHeight() {
		return this.numberOfBlocks * this.blockSize;
	}

	protected int drawingSurfaceWidth() {
		return this.numberOfBlocks * this.blockSize;
	}

}
