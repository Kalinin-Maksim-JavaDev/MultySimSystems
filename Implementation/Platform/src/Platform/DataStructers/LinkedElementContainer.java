/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.DataStructers;

/**
 *
 * @author kalinin
 */
public final class LinkedElementContainer implements ILinkedElement,IContainer {

    private final ILinkedElement item;
    private ILinkedElement next;

    public LinkedElementContainer(ILinkedElement item) {
        this.item = item;
    }

    public ILinkedElement getItem() {
        return item;
    }

    public void setNext(ILinkedElement e) {
        next = e;
    }

    public ILinkedElement getNext() {
        return next;
    }
}
