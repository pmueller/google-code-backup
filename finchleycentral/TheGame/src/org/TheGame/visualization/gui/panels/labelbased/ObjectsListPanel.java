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

package org.TheGame.visualization.gui.panels.labelbased;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Facundo Manuel Quiroga Jul 25, 2009
 */
public abstract class ObjectsListPanel extends JPanel {

	protected int usedHeight;

	public static int defaultWidth = 200;
	protected static int defaultHeight = 20;
	protected String title;

	protected ArrayList<JLabel> labels;

	public ObjectsListPanel(String title) {
		this.setTitle(title);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.GRAY);
		this.addLabel(title, 0, 0);

		this.setLabels(new ArrayList<JLabel>());
		// this.setupLabels();
	}

	public void setupLabels() {
		this.removeLabels();
		int currentY = defaultHeight * 2;
		for (int i = 0; i < this.getObjects().size(); i++) {
			JLabel label = this.addLabel("asdasd", 0, currentY);
			this.getLabels().add(label);
			currentY += defaultHeight;
		}
		this.setUsedHeight(currentY);
		this.updateLabels();
	}

	public void removeLabels() {
		for (JLabel label : this.getLabels()) {
			this.remove(label);
		}
		this.setLabels(new ArrayList<JLabel>());
	}

	public JLabel addLabel(String name, int x, int y, int width, int height) {
		JLabel label = new JLabel(name);
		// label.setSize(width, height);
		label.setBounds(x, y, width, height);
		this.add(label);
		return label;
	}

	public JLabel addLabel(String name, int x, int y) {
		return this.addLabel(name, x, y, defaultWidth, defaultHeight);
	}

	public int getUsedHeight() {
		return this.usedHeight;
	}

	protected void setUsedHeight(int usedHeight) {
		this.usedHeight = usedHeight;
	}

	public void update() {
		ArrayList<JLabel> labels = this.getLabels();
		List<Object> objects = this.getObjects();
		if (labels.size() != objects.size()) {
			this.setupLabels();
		} else {
			updateLabels();
		}
	}

	public void updateLabels() {
		ArrayList<JLabel> labels = this.getLabels();
		List<Object> objects = this.getObjects();
		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setText(this.printObjectLabel(objects.get(i)));
		}
	}

	public String printObjectLabel(Object object) {
		return object.toString();
	}

	public abstract ArrayList<Object> getObjects();

	public ArrayList<JLabel> getLabels() {
		return this.labels;
	}

	public void setLabels(ArrayList<JLabel> labels) {
		this.labels = labels;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
