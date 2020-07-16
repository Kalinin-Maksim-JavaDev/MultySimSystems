/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Presenter.Projections;

import Platform.Core.IMotion;
import Platform.Core.IMotionsProducer;
import Model.Game.Presenter.Space.Image;
import Global.Tools;
import Logic.Reflections.Projections.IImaginator;
import Logic.Reflections.Space.IImage;
import Platform.Core.ISystemMContainer;
import Render.Graphics.Geometry.ICoordinates;
import View.Presenter.Projections.IViewUnit.states;

/**
 *
 * @author kalinin
 */
public abstract class Imaginator implements IImaginator, IImaginator.charged, IMotionsProducer {

    protected ICoordinates position;
    public IImage[] imagesCollection;
    public IImage currentImage;
    public ICoordinates OriginalPosition;
    public ProjectMotionFactory motionProjectfactory;
    public ProjectionD prD;
    private String forLOGunitName;
    private int forLog_index;

    public Imaginator(int n_, ICoordinates OriginalPosition, ProjectionD prD_) {
        super();
        this.OriginalPosition = OriginalPosition;
        forLog_index = n_;
        prD = prD_;
        imagesCollection = new Image[10];
        position = CreatePosition();
        motionProjectfactory = new ProjectMotionFactory(1, this);
    }

    public ISystemMContainer getReflectionPerformer() {
        return prD;
    }

    public ICoordinates getPosition() {
        return position;
    }

    public int getIndex() {
        return forLog_index;
    }

    public IImaginator.charged setName(String name) {
        this.forLOGunitName = name;
        return this;
    }

    public String getName() {
        return forLOGunitName;
    }

    public void setPosition(ICoordinates position) {
        this.position = position;
    }

    public IMotion CreateMotionImageGenerator(states state) {
        //  prD.conveerInterafe.RegBeg(Tools.currentTimeMillis(), Thread.currentThread().getName(), prD.sysName, "Create " + String.valueOf(this.hashCode()) + "\t" + index+"\t"+motionProjectfactory.mot.motionName);

//        if ((Thread.currentThread().getName().equalsIgnoreCase("Calc0"))&&(index==1)){
//            System.out.println("err");//Tools.UnsupportedOperationException();
//        }
//        if ((Thread.currentThread().getName().equalsIgnoreCase("Calc1"))&&(index==0)){
//            System.out.println("err");//Tools.UnsupportedOperationException();
//        }
        IMotion mot_ = motionProjectfactory.GetMotion(1);
        ((MotionProject) mot_).SetArg(state.getId());
        mot_.setMotionName("Project " + forLOGunitName);
        //  prD.conveerInterafe.RegEnd(Tools.currentTimeMillis(), Thread.currentThread().getName(), prD.sysName, "Create " + String.valueOf(this.hashCode()) + "\t" + index+"\t"+motionProjectfactory.mot.motionName);
        return mot_;

    }

    public IImaginator.charged SetImage(IImage im_, int n) {
        currentImage = im_;
        imagesCollection[n] = currentImage;
        return this;

    }

    public IImage GetImage() {
        return currentImage;

    }

    protected abstract ICoordinates CreatePosition();

    public void PutImage(int n) {
        currentImage = imagesCollection[n];
        if (currentImage == null) {
            Tools.UnsupportedOperationException();
        }
        currentImage.PutImage(getPosition());
    }

    public ICoordinates GetPosition() {

        Tools.UnsupportedOperationException();
        return null;
    }

    public void PreChangeUnitPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void PostChangeUnitPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
