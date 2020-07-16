/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.DataStructers;

import Global.Tools;

/**
 *
 * @author kalinin
 */
public class LinkedList implements ILinkedList {

    private ILinkedElement head;
    private ILinkedElement tale;
    private int size;

    public LinkedList() {
    }

    public ILinkedElement add(ILinkedElement e) {

        if (e.getNext() != null) {
            Tools.UnsupportedOperationException("Use LinkedElementContainer");
        }

        if (head == null) {
            head = e;
        } else {

            tale.setNext(e);
        }

        tale = e;

        size++;

        return e;
    }

    public void del(ILinkedElement e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ISelection select() {
        return new Selection();
    }

    public int size() {

        return size;
    }

    public IContainer[] math(IFilter filter) {

        ISelection selection = select();

        LinkedList unitsContainer = new LinkedList();

        ILinkedElement unit;
        while (selection.getNext()) {
            unit = selection.getCurrent();
            if (filter.suit(unit)) {
                unitsContainer.add(new LinkedElementContainer(unit));
            }
        }

        IContainer[] result = new IContainer[unitsContainer.size()];
        int i = 0;
        ISelection ContainersSelection = unitsContainer.select();
        while (ContainersSelection.getNext()) {
            result[i++] = (IContainer) ((LinkedElementContainer) ContainersSelection.getCurrent()).getItem();
        }
        return result;
    }

    class Selection implements ISelection {

        private boolean firstSelect;
        ILinkedElement cursor;

        public Selection() {

            reset();
        }

        public void reset() {

            cursor = head;
            firstSelect = true;
        }

        public ILinkedElement getCurrent() {

            return cursor;
        }

        public boolean getNext() {

            if (!firstSelect) {
                cursor = cursor.getNext();
            }
            firstSelect = false;

            boolean result = cursor != null;

            return result;
        }
    }
}
