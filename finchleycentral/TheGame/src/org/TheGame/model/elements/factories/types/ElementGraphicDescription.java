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

package org.TheGame.model.elements.factories.types;

import org.TheGame.main.GameFactories;
import org.TheGame.main.resourcemanagement.graphics.MovableElementGraphicFactory;
import org.TheGame.visualization.graphics.element.movable.MovableElementGraphic;

/**
 * @author Facundo Manuel Quiroga Mar 15, 2009
 */
public class ElementGraphicDescription {

	protected String graphic;
	protected boolean graphicIsSimple;

	public ElementGraphicDescription(String graphic,
			boolean graphicIsSimple) {
		super();
		this.graphic = graphic;
		this.graphicIsSimple = graphicIsSimple;
	}


	public String getGraphic() {
		return this.graphic;
	}

	protected void setGraphic(String graphic) {
		this.graphic = graphic;
	}

	public boolean isGraphicIsSimple() {
		return this.graphicIsSimple;
	}

	protected void setGraphicIsSimple(boolean graphicIsSimple) {
		this.graphicIsSimple = graphicIsSimple;
	}
}
