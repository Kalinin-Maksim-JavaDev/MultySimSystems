/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.DataStructers;

/**
 *
 * @author kalinin
 */
public class LinkedContainers extends LinkedList {

    @Override
    public ILinkedElement add(ILinkedElement e) {
        return super.add(new LinkedElementContainer(e));
    }

    @Override
    public void del(ILinkedElement e) {
        super.del(e);
    }

    @Override
    public ISelection select() {
        return new ContainerSelection();
    }

    public class ContainerSelection extends LinkedList.Selection {

        @Override
        public ILinkedElement getCurrent() {
            return ((IContainer) super.getCurrent()).getItem();
        }
    }
}
