/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Reflections.Projections;

import Logic.Reflections.Projections.IImaginator.ILogged;
import Platform.Core.IMotion;
import Logic.Reflections.Space.IImage;
import Platform.Core.IArgument;
import Platform.Core.ISystemMContainer;
import View.Presenter.Projections.IViewUnit.states;

/**
 *
 * @author kalinin
 */
public interface IImaginator {

    charged SetImage(IImage im_, int n);

    interface ILogged {

        int getIndex();

        ILogged setName(String name);

        String getName();
    }

    interface charged extends ILogged {

        charged SetImage(IImage im_, int n);

        charged setName(String name);

        IImage GetImage();

        ISystemMContainer getReflectionPerformer();

        void Translate();

        void Project();

        public void Project(int n);

        public IMotion CreateMotionImageGenerator(states state);
    }
}
