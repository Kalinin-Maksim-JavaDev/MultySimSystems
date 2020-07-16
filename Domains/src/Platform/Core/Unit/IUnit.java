package Platform.Core.Unit;

import Platform.Core.Unit.IUnit.drawable.projection;
import Platform.DataStructers.ILinkedElement;
import Render.Graphics.Geometry.IPointed;
import View.Presenter.Projections.IViewUnit;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
public interface IUnit extends INumered, IPointed {

    void setName(String string);

    String getName();

    IUnit setTypeID(int id);

    ILinkedElement asLinkedElement();

    interface list {

        IUnit add(IUnit e);

        selection select();

        interface selection {

            boolean end();

            IUnit get();
        }
    }

    drawable asDrawble();

    interface drawable {

        ILinkedElement asLinkedElement();

        boolean isVisible();

        interface projection extends drawable, IViewUnit, IViewUnit.imaginated {

            IViewUnit.imaginated asImaginated();
            
            interface list {

                IViewUnit.list asViewUnitslist();

                selection select();

                public int size();

                interface selection {

                    boolean end();

                    projection get();
                }
            }
        }
    }
}
