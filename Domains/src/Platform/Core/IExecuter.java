/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Platform.Core;

/**
 *
 * @author kalinin
 */
public interface IExecuter {

    boolean canOccupy();

    void release();

    void setProducer(IMotionsProducer producer);

    IMotion CreateMotion(IArgument[] arg);

    IMotion StopMotion(IArgument[] arg);

    void setNext(IExecuter executer);

    IExecuter getNext();
}
