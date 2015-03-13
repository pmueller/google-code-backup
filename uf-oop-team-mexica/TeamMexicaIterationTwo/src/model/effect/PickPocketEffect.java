package model.effect;

import java.io.IOException;
import java.util.List;
import model.Model;
import model.entity.Avatar;
import model.entity.Entity;
import model.item.InventoryItem;
import util.SaverLoader;

public class PickPocketEffect extends Effect {
    private boolean first;

    public PickPocketEffect(){
     first = true;
    }

    @Override
    public void applyToEntity(Entity target) {

        if ( first ) {
            first = false;

            Avatar pickpocket = Model.getInstance().getAvatar();

            List<InventoryItem> items = target.getInventory().getItems();
            if ( items.size() > 0 ) {
                InventoryItem i = target.getInventory().removeItemAtIndex(
                    (int)(items.size()*Math.random()) );

                Model.getInstance().getLog().logCombat(pickpocket.getName(), target.getName(), "pickpocket");

                pickpocket.getInventory().addItem( i );
            }
        }
    }

    @Override
    public String getRepresentation() {
        return "Star"; // TODO: change to an empty image
    }

    @Override
    public void load(SaverLoader s, boolean notSuperClass)throws IOException {
        super.load(s, false);

        if (notSuperClass) {
            s.assertEnd();
        }
    }

    @Override
    public void save(SaverLoader s, boolean notSuperClass)throws IOException {
        if (notSuperClass) {
            s.start("PickPocketEffect");
        }

        super.save(s, false);

        if (notSuperClass) {
            s.close();
        }
    }

}
