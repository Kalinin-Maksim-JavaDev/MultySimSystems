/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.DataStructers;

import Platform.Core.IArgument;

/**
 *
 * @author kalinin
 */
public interface ILinkedList  extends IArgument{

    ISelection select();

    ILinkedElement add(ILinkedElement e);

    void del(ILinkedElement e);

    int size();

    public IContainer[] math(IFilter filter);
}
