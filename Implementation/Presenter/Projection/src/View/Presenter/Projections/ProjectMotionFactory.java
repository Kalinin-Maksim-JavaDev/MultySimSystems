/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package View.Presenter.Projections;

import Platform.Core.IMotion;
import Platform.Core.Motion.MotionFabric;

/**
 *
 * @author kalinin
 */
public class ProjectMotionFactory extends MotionFabric {

        Imaginator imaginator;

        public ProjectMotionFactory(int iterates_, Imaginator imaginator) {
            super(iterates_);
            this.imaginator = imaginator;
        }

        public IMotion GetMotion(int iterates_) {
            MotionProject cm_ = (MotionProject) super.GetMotion(iterates_);
            return (IMotion) cm_;
        }

        public IMotion NewMotion(int iterates_) {
            return new MotionProject(imaginator);
        }
    }
