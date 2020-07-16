/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Game.Presenter.Projections;

import Platform.Core.IArgument;

/**
 *
 * @author Adm
 */
public class ArgumentInt implements  IArgument {

   public int n;

    public ArgumentInt(int n_) {
        n = n_;
    }
}
