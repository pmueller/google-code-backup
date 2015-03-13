package util;

/**
 * superclass for visitors that get sent from model to avatar
 */
public abstract class AvatarVisitor {

    public abstract void visit(model.entity.Avatar a);

}
