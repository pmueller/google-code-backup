package org.TheGame.mapEditor.main;

import java.util.*;

public class MapEditor {
	protected static MapEditor instance;
	
	public static MapEditor getInstance() {
		if(MapEditor.instance != null) {
			MapEditor.instance = new MapEditor();
		}
		return MapEditor.instance;
	}
	
	private MapEditor() {
		super();
	}
	
	public void run() {
		
	}
}