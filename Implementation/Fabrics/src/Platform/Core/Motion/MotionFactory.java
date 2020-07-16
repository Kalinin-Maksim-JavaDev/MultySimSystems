/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core.Motion;

import Platform.Core.IMotion;
import Platform.Core.IMotionBody;
import Platform.Core.IMotionTemplate;

/**
 *
 * @author kalinin
 */
public class MotionFactory {

    public static IMotion newMotion(int iterates, String name, final IMotionBody body) {
        return new Motion(iterates, name) {

            @Override
            public void MotionMethod() {

                body.MotionMethod(this);
            }
        };
    }

    public static IMotion newMotion(final IMotionTemplate template) {
        return newMotion(template.getIterates(), template.getNаme(), template);
    }

    public static IMotionRepetable newRepetableMotion(int iterates, String name, final IMotionRepetableBody body) {
        return new RepetableMotion(iterates, name) {

            public void BeginOperation() {
                body.BeginOperation();
            }

            public boolean EndCondition() {
                return body.EndCondition();
            }

            public void EndOperation() {
                body.EndCondition();
            }

            public int getIterates() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String getNаme() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public static IMotionRepetable newRepetableMotion(IMotionRepetableTemplete template) {
        return newRepetableMotion(template.getIterates(), template.getNаme(), template);
    }
}
