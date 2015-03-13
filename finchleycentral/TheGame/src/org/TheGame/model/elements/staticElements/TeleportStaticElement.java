package org.TheGame.model.elements.staticElements;


import org.TheGame.events.actions.results.ActionResultAttack;
import org.TheGame.main.GameCore;
import org.TheGame.main.resourcemanagement.InventoryManager;
import org.TheGame.main.resourcemanagement.MapManager;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.player.Player;
import org.TheGame.model.items.Inventory;
import org.TheGame.model.items.Item;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.sound.SFXManager;
import org.TheGame.sound.SFXThread;
import org.TheGame.visualization.graphics.element.StaticElementGraphic;

public class TeleportStaticElement extends StaticElement {

	protected String tileMapString;
	protected Position position;
	protected String keyString;
	protected Item key;
	protected boolean needsKey;
	
	public TeleportStaticElement(StaticElementType elementType,
			StaticElementGraphic graphic, String map, Position position) {
		super(elementType, graphic);
		
		this.tileMapString = map;
		this.position = position;
	}
	
	public TeleportStaticElement(StaticElementType elementType, 
			StaticElementGraphic graphic, String map, Position position, String keyString) {
		this(elementType, graphic, map, position);
		this.keyString = keyString;
		this.needsKey = true;
	}
	
	public void setKey(String keyString) {
		this.keyString = keyString;
	}
	
	public void setKey(Item key) {
		this.key = key;
	}
	
	@Override
	public ActionResultAttack defendFromAttackBy(Element attacker,
			int attackStrength) {
		Inventory inventory = InventoryManager.getInstance().getPlayerInventory("quest");
		if(!this.needsKey || inventory.removeItem(keyString)) {
			Player player = GameCore.getInstance().getPlayer();
			if(attacker.equals(player)) {
				this.needsKey = false;
				TileMap tileMap = MapManager.getInstance().getValue(this.tileMapString);
				GameCore.getInstance().setMap(tileMap, position);
				
				SFXManager.playSound("teleport");
			}
		}
		else {
			GameCore.getInstance().getSidePanelGUI().addText("You need " + this.keyString + " to pass!\n");
		}
		return ActionResultAttack.cannotBeAttacked;		
	}

}
