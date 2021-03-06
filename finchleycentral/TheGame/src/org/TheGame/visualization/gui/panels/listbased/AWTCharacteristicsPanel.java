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

package org.TheGame.visualization.gui.panels.listbased;

import org.TheGame.model.elements.movableElements.MovableElement;

/**
 * @author Facundo Manuel Quiroga Jul 25, 2009
 */
public abstract class AWTCharacteristicsPanel extends AWTObjectsListPanel {

	protected MovableElement movableElement;

	/**
	 * @param title
	 */
	public AWTCharacteristicsPanel(String title, MovableElement movableElement) {
		super(title);
		this.setMovableElement(movableElement);
	}

	/**
	 * @return the movableElement
	 */
	public MovableElement getMovableElement() {
		return this.movableElement;
	}

	/**
	 * @param movableElement
	 *            the movableElement to set
	 */
	public void setMovableElement(MovableElement movableElement) {
		this.movableElement = movableElement;
	}

}
