/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Motion;

import Platform.Core.IExecuter;
import Platform.Core.IMotionsProducer;

/**
 *
 * @author Adm
 */
public abstract class Executer implements IExecuter {

    public IMotionsProducer producer;
    private boolean occupyed;
    private IExecuter next;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Executer other = (Executer) obj;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public Executer() {
    }

    public void setNext(IExecuter next) {
        this.next = next;
    }

    public IExecuter getNext() {
        return next;
    }

    public Executer(IMotionsProducer producer) {
        setProducer(producer);
    }

    public final void setProducer(IMotionsProducer producer) {
        this.producer = producer;
    }

    public boolean canOccupy() {
        if (occupyed) {
            return false;
        } else {
            occupyed = true;
            return true;
        }
    }

    public void release() {
        occupyed = false;
    }
}
