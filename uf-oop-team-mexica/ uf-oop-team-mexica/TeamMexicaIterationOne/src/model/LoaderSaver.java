package model;

import java.io.*;
import java.util.*;

public class LoaderSaver {
	private MapGrid map;
	private MapStuffManager stuff;

	/**
	 * Constructor
	 * 
	 * @param mg
	 *            the map grid
	 * @param ms
	 *            the map stuff manager
	 */
	public LoaderSaver(MapGrid mg, MapStuffManager ms) {
		map = mg;
		stuff = ms;
	}

	/**
	 * loads the file from the location
	 * 
	 * @param location
	 *            the filename to load data from
	 */
	public void load(String location) {
		// Loads the file
		Scanner scan;
		try {
			FileReader filereader = new FileReader(location);
			BufferedReader reader = new BufferedReader(filereader);
			scan = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + location);
			e.printStackTrace();
			return;
		}

		// starts to interpt the file
		// System.out.println("Loading File");
		try {
			String data = "";
			while (scan.hasNext()) {
				data = scan.next();

				if (data.equals("Map{")) {
					loadMap(scan);
				} else if (data.equals("Entity{")) {
					loadEntity(scan);
				} else if (data.equals("TakeableItem{")) {
					loadTakeableItem(scan);
				} else if (data.equals("ObstacleItem{")) {
					loadObstacleItem(scan);
				} else if (data.equals("OneShotItem{")) {
					loadOneShotItem(scan);
				} else if (data.equals("InteractiveItem{")) {
					loadInteractiveItem(scan);
				} else if (data.equals("AoE{")) {
					loadAoE(scan);
				} else if (data.equals("Decal{")) {
					loadDecal(scan);
				} else {
					// throw custom
				}
			}
		} catch (Exception e) {
			System.out.println("!!!!!!!!!!!!!");
			System.out.println("!LOAD FAILED!");
			System.out.println("!!!!!!!!!!!!!");
			System.out.print(e.getMessage());
			e.printStackTrace();

		}
		// catch(custom){}
		scan.close();
	}

	/**
	 * loads the aoe from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 */
	private void loadAoE(Scanner scan) {
		int x = 0;
		int y = 0;
		String rep = "";
		String type = "";
		int num = 0;
		List<Vector> list = new LinkedList<Vector>();
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("location:")) {
				x = scan.nextInt();
				y = scan.nextInt();
				list.add(new Vector(x, y));
			} else if (data.equals("type:")) {
				type = scan.next();
			} else if (data.equals("num:")) {
				num = scan.nextInt();
			} else if (data.equals("rep:")) {
				rep = scan.next();
			} else if (data.equals("}")) {
				AoE aoe = null;
				if (type.equals("heal")) {
					aoe = new AoE(new HealDamageAoEType(num));
					aoe.setRepresentation(rep);
				} else if (type.equals("damage")) {
					aoe = new AoE(new TakeDamageAoEType(num));
					aoe.setRepresentation(rep);
				} else if (type.equals("death")) {
					aoe = new AoE(new InstantDeathAoEType());
					aoe.setRepresentation(rep);
				} else if (type.equals("exp")) {
					aoe = new AoE(new ExperienceGainAoEType(num));
					aoe.setRepresentation(rep);
				} else if (type.equals("level")) {
					aoe = new AoE(new LevelUpAoEType());
					aoe.setRepresentation(rep);
				}
				Iterator<Vector> iter = list.iterator();

				while (iter.hasNext()) {
					AoE temp = new AoE(aoe);
					Tile tile = map.getTileAt(iter.next());
					tile.add(temp);
					stuff.addAoE(temp);
					temp.setCurrentTile(tile);
				}
				break;
			} else {
				// throw
				break;
			}
		}
		// throw
	}

	/**
	 * loads the decal from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 */
	private void loadDecal(Scanner scan) {
		int x = 0;
		int y = 0;
		String rep = null;
		Decal decal = null;
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("location:")) {
				x = scan.nextInt();
				y = scan.nextInt();
			} else if (data.equals("rep:")) {
				rep = scan.next();
			} else if (data.equals("}")) {
				decal = new Decal();
				decal.setRepresentation(rep);
				map.getTileAt(x, y).add(decal);
				break;
			} else {
				// throw
				break;
			}
		}
		// throw custom
	}

	/**
	 * loads the interactive item from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 */
	private void loadInteractiveItem(Scanner scan) {
		int x = 0;
		int y = 0;
		String rep = null;
		String type = null;
		String info = null;
		InventoryItem item = null;
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("location:")) {
				x = scan.nextInt();
				y = scan.nextInt();
			} else if (data.equals("type:")) {
				type = scan.next();
			} else if (data.equals("info:")) {
				info = scan.next();
			} else if (data.equals("item:")) {
				if (scan.next().equals("InventoryItem{")) {
					item = loadInventoryItem(scan);
				}
			} else if (data.equals("rep:")) {
				rep = scan.next();
			} else if (data.equals("}")) {
				if (type.equals("treasureChest")) {
					TreasureChest tcTemp = new TreasureChest(info, item);
					tcTemp.setRepresentation(rep);
					map.getTileAt(x, y).add(tcTemp);
				}
				break;
			} else {
				// throw
				break;
			}
		}
		// throw custom
	}

	/**
	 * loads the inventory item from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 * @return the loaded inventory item
	 */
	private InventoryItem loadInventoryItem(Scanner scan) {
		String name = null;
		String type = null;
		String rep = null;
		int bonus = 0;
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("type:")) {
				type = scan.next();
			} else if (data.equals("rep:")) {
				rep = scan.next();
			} else if (data.equals("name:")) {
				name = scan.next();
			} else if (data.equals("bonus:")) {
				bonus = scan.nextInt();
			} else if (data.equals("}")) {
				if (type.equals("weapon")) {
					return new Weapon(name, rep, bonus);
				} else if (type.equals("armor")) {
					return new Armor(name, rep, bonus);
				} else if (type.equals("other")) {
					return new InventoryItem(name, rep);
				} else {
					// throw
				}
				break;
			} else {
				// throw
			}
		}
		return null;
	}

	/**
	 * loads the entity from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 */
	private void loadEntity(Scanner scan) {
		int x = 0;
		int y = 0;
		String name = null;
		Occupation occupation = null;
		InventoryItem wep = null;
		InventoryItem armor = null;
		List<InventoryItem> itemList = new LinkedList<InventoryItem>();
		int lives = 0;
		int exp = 0;
		int hp = -1;
		int mana = -1;
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("location:")) {
				x = scan.nextInt();
				y = scan.nextInt();
			} else if (data.equals("name:")) {
				name = scan.next();

			} else if (data.equals("equip1:")) {
				if (scan.next().equals("InventoryItem{")) {
					armor = loadInventoryItem(scan);
				}
			} else if (data.equals("equip2:")) {
				if (scan.next().equals("InventoryItem{")) {
					wep = loadInventoryItem(scan);
				}
			} else if (data.equals("item:")) {
				if (scan.next().equals("InventoryItem{")) {
					itemList.add(loadInventoryItem(scan));
				}
			} else if (data.equals("occupation:")) {
				String occ = scan.next();
				if (occ.equals("Smasher")) {
					occupation = new Smasher();
				} else if (occ.equals("Summoner")) {
					occupation = new Summoner();
				} else if (occ.equals("Sneak")) {
					occupation = new Sneak();
				} else {
					// throw
				}
			} else if (data.equals("exp:")) {
				exp = scan.nextInt();

			} else if (data.equals("lives:")) {
				lives = scan.nextInt();

			} else if (data.equals("mana:")) {
				mana = scan.nextInt();
			} else if (data.equals("hp:")) {
				hp = scan.nextInt();
			} else if (data.equals("}")) {
				Entity ent = new Entity(name, occupation);
				if (armor != null)
					armor.useOnEntity(ent);
				// System.out.println("Armor: " +armor.name());
				// System.out.println("Wepaon: "+wep.name());
				if (wep != null)
					wep.useOnEntity(ent);
				Iterator<InventoryItem> iter = itemList.iterator();
				while (iter.hasNext()) {
					InventoryItem item = iter.next();
					// System.out.println("InvenItem: "+item.name());
					ent.addToInventory(item);
				}
				ent.addExp(exp);
				ent.setLivesLeft(lives);
				// System.out.println(hp);
				// ystem.out.println(mana);
				if (hp != -1)
					ent.setCurrentLife(hp);
				if (mana != -1)
					ent.setCurrentMana(mana);
				// stuff.setAvatarSpawnLocation(new Vector(x,y));
				stuff.replaceAvatar(ent);
				stuff.getAvatar().setCurrentTile(map.getTileAt(x, y));
				stuff.getAvatar().setCoordinate(new Vector(x, y));
				Tile tile = map.getTileAt(x, y);
				tile.add(stuff.getAvatar());
				break;
			} else {
				// throw
				break;
			}

		}
		// throw custom
	}

	/**
	 * loads the map from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 */
	private void loadMap(Scanner scan) {
		int width = 0;
		int height = 0;
		Terrain grass = null;
		Terrain mountain = null;
		Terrain water = null;
		int startX = 0;
		int startY = 0;
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("size:")) {
				width = scan.nextInt();
				height = scan.nextInt();
				map.setDimensions(width, height);

			} else if (data.equals("grass:")) {
				data = scan.next();
				if (data.equals("Terrain{")) {
					grass = loadTerrain(scan);
				}
			} else if (data.equals("mountain:")) {
				data = scan.next();
				if (data.equals("Terrain{")) {
					mountain = loadTerrain(scan);
				}
			} else if (data.equals("water:")) {
				data = scan.next();
				if (data.equals("Terrain{")) {
					water = loadTerrain(scan);
				}
			} else if (data.equals("startLoc:")) {
				startX = scan.nextInt();
				startY = scan.nextInt();
				stuff.setAvatarSpawnLocation(new Vector(startX, startY));
			} else if (data.equals("Grid{")) {
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						String terr = scan.next();
						if (terr.equals(".")) {
							map.setTile(x, y, grass);
						} else if (terr.equals("~")) {
							map.setTile(x, y, water);
						} else {
							map.setTile(x, y, mountain);
						}
					}
				}

				stuff.setAvatarSpawnTile(map.getTileAt(startX, startY));

				scan.next();
			} else if (data.equals("Grid2{")) {
				int max_x = 0;
				int current_x = 0;
				int max_y = 0;
				List<List<Terrain>> yList = new LinkedList<List<Terrain>>();
				List<Terrain> xList = new LinkedList<Terrain>();
				while (scan.hasNext()) {
					data = scan.next();
					if (data.equals(";")) {
						max_y++;
						if (current_x > max_x)
							max_x = current_x;
						yList.add(xList);
						xList = new LinkedList<Terrain>();
					} else if (data.equals("m")) {
						xList.add(mountain);
						current_x++;
					} else if (data.equals("~")) {
						xList.add(water);
						current_x++;
					} else if (data.equals(".")) {
						xList.add(grass);
						current_x++;
					} else if (data.equals("}")) {
						map.setDimensions(max_x, max_y);
						Iterator<List<Terrain>> y_iter = yList.iterator();
						Iterator<Terrain> x_iter = null;
						int i = 0;
						int j = 0;
						while (y_iter.hasNext()) {
							x_iter = y_iter.next().iterator();
							while (x_iter.hasNext()) {
								map.setTile(i, j, x_iter.next());
								i++;
							}
							j++;
						}
					}

				}
			} else if (data.equals("}")) {

				stuff.setAvatarSpawnTile(map.getTileAt(startX, startY));
				stuff.setAvatarSpawnLocation(new Vector(startX, startY));

				break;
			} else {
				// throw
			}

		}
		// throw custom
	}

	/**
	 * loads the obstacle item from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 */
	private void loadObstacleItem(Scanner scan) {
		int x = 0;
		int y = 0;
		String rep = null;
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("location:")) {
				x = scan.nextInt();
				y = scan.nextInt();
			} else if (data.equals("rep:")) {
				rep = scan.next();
			} else if (data.equals("}")) {
				map.getTileAt(x, y).add(new ObstacleItem(rep));
				break;
			} else {
				// throw
				break;
			}

		}
		// throw custom
	}

	/**
	 * loads the one shot item from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 */
	private void loadOneShotItem(Scanner scan) {
		int x = 0;
		int y = 0;
		String rep = null;
		String type = null;
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("location:")) {
				x = scan.nextInt();
				y = scan.nextInt();
			} else if (data.equals("type:")) {
				type = scan.next();
			} else if (data.equals("rep:")) {
				rep = scan.next();
			} else if (data.equals("}")) {
				if (type.equals("potion")) {
					map.getTileAt(x, y).add(new Potion(rep));
				}
				break;
			} else {
				// throw
				break;
			}

		}
		// throw custom

	}

	/**
	 * loads the takeable item from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 */
	private void loadTakeableItem(Scanner scan) {
		int x = 0;
		int y = 0;
		String rep = null;
		InventoryItem item = null;
		String data;
		while (scan.hasNext()) {
			data = scan.next();
			if (data.equals("location:")) {
				x = scan.nextInt();
				y = scan.nextInt();
			} else if (data.equals("item:")) {
				if (scan.next().equals("InventoryItem{"))
					item = loadInventoryItem(scan);
			} else if (data.equals("rep:")) {
				rep = scan.next();
			} else if (data.equals("}")) {
				map.getTileAt(x, y).add(new TakeableItem(item, rep));
				break;
			} else {
				// throw
				break;
			}

		}
		// throw custom
	}

	/**
	 * loads the terrain from the scanner
	 * 
	 * @param scan
	 *            the scanner
	 * @return the loaded terrain
	 */
	private Terrain loadTerrain(Scanner scan) {
		String rep = null;
		boolean passable = false;

		while (scan.hasNext()) {
			String data = scan.next();
			if (data.equals("passable:")) {
				passable = scan.nextBoolean();
			} else if (data.equals("rep:")) {
				rep = scan.next();
			} else if (data.equals("}")) {
				return new Terrain(passable, rep);
			} else {
				// throw;
				break;
			}
		}
		return null;
	}

	/**
	 * saves the map and tiles
	 * 
	 * @param filepath
	 *            the filepath to save the map and tiles to
	 */
	public void save(String filepath) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(filepath));
		} catch (Exception e) {
			System.out.print(e);
			System.out.print(e.getMessage());
			System.out.print("Failure, not saved");

		}
		try {
			saveMap(out);
			saveTiles(out);
			out.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * saves the map
	 * 
	 * @param out
	 *            the buffered writer
	 * @throws IOException
	 */
	public void saveMap(BufferedWriter out) throws IOException {
		String str = map.saveString(stuff.getAvatarSpawnLocation());
		System.out.println(str);
		out.write(str);
	}

	/**
	 * saves the tiles
	 * 
	 * @param out
	 *            the buffered writer
	 * @throws IOException
	 */
	public void saveTiles(BufferedWriter out) throws IOException {
		String str = map.saveTiles();
		System.out.println(str);
		out.write(str);
	}
}
