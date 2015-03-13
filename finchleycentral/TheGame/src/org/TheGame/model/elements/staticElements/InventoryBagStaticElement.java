package org.TheGame.model.elements.staticElements;


import org.TheGame.events.actions.results.ActionResultAttack;
import org.TheGame.events.actions.results.ActionResultDelete;
import org.TheGame.events.actions.results.ActionResultPickUp;
import org.TheGame.exceptions.InvalidPositionException;
import org.TheGame.exceptions.OccupiedPositionException;
import org.TheGame.main.GameCore;
import org.TheGame.main.logic.DiceRoller;
import org.TheGame.main.resourcemanagement.MapManager;
import org.TheGame.model.elements.Element;
import org.TheGame.model.elements.movableElements.MovableElementType;
import org.TheGame.model.elements.movableElements.player.Player;
import org.TheGame.model.items.Inventory;
import org.TheGame.model.items.Item;
import org.TheGame.model.map.AbstractTileMap;
import org.TheGame.model.map.TileMap;
import org.TheGame.model.map.maputils.Direction;
import org.TheGame.model.map.maputils.Position;
import org.TheGame.sound.SFXManager;
import org.TheGame.sound.SFXThread;
import org.TheGame.visualization.graphics.element.StaticElementGraphic;

public class InventoryBagStaticElement extends StaticElement implements Comparable {

	protected TileMap tileMap;
	protected Position position;
	protected Element owner;
	protected Item item;
	protected long creationTime;
	
	public InventoryBagStaticElement(StaticElementType elementType,
			StaticElementGraphic graphic, TileMap map, Position position, Element e, Item item) {
		super(elementType, graphic);
		owner = e;
		this.tileMap = map;
		this.position = position;
		this.item = item;
		creationTime = System.currentTimeMillis();
		GameCore.getInstance().addBag(this);
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public ActionResultPickUp defendFromPickUpBy(Element attacker)
		throws InvalidPositionException, OccupiedPositionException {
		if(attacker!=null&&attacker.getPosition().isAdjacentTo(position)&&attacker.equals(GameCore.getInstance().getPlayer())) {
			Player player = GameCore.getInstance().getPlayer();
			if(attacker.equals(player)) {
				GameCore.getInstance().getSidePanelGUI().pickUpItem(position, item, tileMap, this);
			}
			GameCore.getInstance().removeBag(this);
		}
		return ActionResultPickUp.cannotBePickedUp;
	}

	public ActionResultDelete defendFromDeleteBy(Element attacker)
		throws InvalidPositionException, OccupiedPositionException {
		if(attacker!=null&&attacker.getPosition().isAdjacentTo(position)&&attacker.equals(GameCore.getInstance().getPlayer())) {
			Player player = GameCore.getInstance().getPlayer();
			if(attacker.equals(player)) {
				GameCore.getInstance().getSidePanelGUI().deleteItem(position, item, tileMap, this);
			}
			GameCore.getInstance().removeBag(this);
		}
		return ActionResultDelete.cannotBeDeleted;
	}

	public int compareTo(Object o) {
		InventoryBagStaticElement e = (InventoryBagStaticElement)o;
		return (int)(creationTime-e.creationTime);
	}

	public long creationTime() {
		return this.creationTime;
	}
	
	public TileMap getMap() {
		return this.tileMap;
	}
}
