package Model.Models.Jumper;

import Application.Build.Panels.Model.IEnvironment;
import Model.Game.GParametr;
import Model.Game.Game;
import Model.Game.World.Unit.GameUnitNotActor;
import Model.Game.World.Unit.GameUnitNotActorState;
import Model.Models.Resolvers.IResolverCollisionWithBall;
import Model.Models.Resolvers.IResolverCollisionWithFundament;
import Model.Models.Resolvers.IResolverCollisionWithJumper;
import Model.Game.World.Unit.Command.MotionAddSpeed;
import Model.Game.World.Unit.GameUnit;
import Platform.Core.Motion.Motion;
import Platform.Util.Random;
import Presenter.Platformer.JParametr;
import Global.Tools;
import Model.Geometry.D2.Coordinates2d;
import Logic.Model.Game.IGame;
import Logic.Model.Game.World.Collisions.IBodie;
import Logic.Model.Game.World.Interaction.Resolvers.ICollisionResolver;
import Logic.Model.IDataSource;
import MVC.Control.MovementsProducers.UnitCommands.IProducer.IBasicMoves;
import Model.Game.Ratio;
import Model.Models.Unit.D2.Vector2d;
import Platform.Calculation.ITasksReciver;
import Platform.ILogCanvas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalinin
 */
abstract class SimpleJumper extends Game {

    public static Random random = new Random();
    SJFundGroup blockGroupFundament;
    SJGroupJ jumper;
    // Group.MotionMoveWithSpeedAndGravity jspeed;
    SJBall SJBalls = null;
    public static final int f = 1;
    public static final int j = 2;
    public static final int b = 3;
    public static final int g = 4;
    public static final int h = 5;
    public static final int[] l1 = {h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h};
    public static final int[] l2 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l3 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, h};
    public static final int[] l4 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, h};
    public static final int[] l5 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, f, 0, f, f, 0, 0, 0, h};
    public static final int[] l6 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l7 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, h};
    public static final int[] l8 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, b, f, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l9 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, f, f, f, 0, h};
    public static final int[] l10 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l11 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, f, 0, 0, 0, f, h};
    public static final int[] l12 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, f, f, 0, h};
    public static final int[] l13 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, h};
    public static final int[] l14 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, f, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, h};
    public static final int[] l15 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, h};
    public static final int[] l16 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l17 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, h};
    public static final int[] l18 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, h};
    public static final int[] l19 = {h, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, f, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l20 = {h, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, h};
    public static final int[] l21 = {h, 0, 0, 0, f, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l22 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, h};
    public static final int[] l23 = {h, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, h};
    public static final int[] l24 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l25 = {h, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, h};
    public static final int[] l26 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l27 = {h, 0, 0, 0, 0, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, f, f, 0, h};
    public static final int[] l28 = {h, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, f, 0, 0, 0, 0, 0, f, 0, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l29 = {h, 0, 0, 0, f, f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, f, 0, f, f, 0, 0, 0, 0, 0, 0, 0, f, f, f, f, 0, 0, 0, 0, 0, h};
    public static final int[] l30 = {h, 0, j, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l31 = {h, f, f, f, 0, 0, 0, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, b, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, h};
    public static final int[] l32 = {h, 0, 0, h, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, h};
    public static final int[][] coor = {l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22, l23, l24, l25, l26, l27, l28, l29, l30, l31, l32};
    public Coordinates2d[] jumperViewVector = {new Coordinates2d(), new Coordinates2d()};

    SimpleJumper(IEnvironment.control environment, ILogCanvas logCanvas_) {
        super(environment, logCanvas_, JParametr.w, JParametr.h, 2, new Ratio(500, GParametr.quant, ((double) GParametr.quant) / 4, JParametr.grad));


        //   userTurn =false;// new State(false);
        //  cpuTurn = new Lacthe(false);
    }

//    protected void Reglaments() {
//        mainAI.repeat = true;
//        mainAI.Resume();
//        super.Reglaments();
//    }
    public IGameUnitGroup CreateGroupsSet(IDataSource dataSource) {

        jumper = new SJGroupJ(modcalc.getCurrentReciver(), 0, 255, 0, this);
        blockGroupFundament = new SJFundGroup(modcalc.getCurrentReciver(), 255, 0, 0, this);


        unitsList.add(jumper);


        int k = 0;
        for (int j = 0; j < JParametr.h; j++) {
            for (int i = 0; i < JParametr.w; i++) {
                int c = SimpleJumper.coor[j][i];
                if (c != 0) {
                }
            }
        }

        return new GameUnitGroup() {

            public IGameUnitGroup SetSpeedEts() {
                getMotionsReciver().AddMotions(newMotionMoveWithSpeedAndGravity((int) (10000 * getRatio().getMoveperiod()), jumper));
                jumper.GetWalker().getBalisticsStrategy().StopFreeFall();
                SJBall b_ = SJBalls;
                do {
                    getMotionsReciver().AddMotions(newMotionMoveWithSpeedAndGravity((int) (10000 * getRatio().getMoveperiod()), b_));
                    b_.GetWalker().getBalisticsStrategy().StopFreeFall();
                    b_ = b_.nextSJBall;
                } while (b_ != SJBalls);


                StartSJBall(SJBalls);
                return this;
                // getViewSource().PutImage(JParametr.AmbientIndex, Arguments.getZero());
            }

            public unit getUnit() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

    }


    public void StartSJBall(SJBall b_) {
        double x = 1 + random.nextDouble();
        double y = 1 + random.nextDouble();
        if (random.nextDouble() < 0.5) {
            x = -x;
            //y = -y;
        }




//        if ((x == 0) && (y == 0)) {
//            x = 1;
//            y = 1;
//        }
        b_.data.Set(b_.jumpSpeed, new Vector2d(1 * getRatio().getGrad() * x / getRatio().getMoveperiod(), 5 * getRatio().getGrad() * y / getRatio().getMoveperiod()));
        //  if (IMap.OnSurface(b_.lastUnit)) {

        //b_.speed.Add(v);
        //   b_.ms.Refresh();
        // }
        //   b_.jump=b_.GetJumpMotionProducer(1,1);
        b_.GetWalker().getBalisticsStrategy().getJump().Go(b_.GetWalker());
    }

    class SJBall extends GameUnitNotActor {

        public final int[] ux = {1, 1, 1, 0, -1, -1, -1, 0, 0};
        public final int[] uy = {1, 0, -1, -1, -1, 0, 1, 1, 0};
        public SJBall nextSJBall = null;

        public SJBall(ITasksReciver sys_, IGame game_) {
            super("SJBall", sys_, 0, 0, 0, 0, game_, game_.getRatio());

            //onGrav=true;
        }

        public void AddUnit(int j, int xc, int yc) {
            super.PreMoveUnit(xc + ux[j], yc + uy[j]);
            //   unit_.Set_();

        }

        public void ResolverCollisionWithfundament(double x, double y) {
            GetWalker().Stand();
            //  ms.Refresh();
            GetWalker().getSpeed().SetNull();
            if (((Coordinates2d) asPoint().getPosition()).y > ((Coordinates2d) asPoint().getPosition()).yd) {
                //  onGrav = true;
                GetWalker().getBalisticsStrategy().StopFreeFall();

                ((SimpleJumper) game).StartSJBall(this.nextSJBall);
            }
        }

        public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {
            GetWalker().Stand();
            //   ms.Refresh();

            if (!inGorscope) {
                ((Vector2d) GetWalker().getSpeed()).multX(-1);
            }

            if (!inVertscope) {
                ((Vector2d) GetWalker().getSpeed()).multY(-1);
            }

        }

        public void ResolveCollisionWith(IBodie unit_, double x, double y) {
            ((IResolverCollisionWithBall) unit_.getCollisionResolver()).ResolveCollisionWithBall(getCollisionResolver() , x, y);
        }

        public states getViewState() {
            return states.zero;
        }

        public Motion CreateMotionMoveUp() {
            return new MotionJump(1, "Jump", this.GetWalker());
        }

        public boolean FallAble() {
            return true;
        }

        @Override
        protected ICollisionResolver createCollisionResolver() {
            return new CollisionResolver();
        }

        private class CollisionResolver implements IResolverCollisionWithFundament, IResolverCollisionWithBall, IResolverCollisionWithJumper {

            public void ResolveCollisionWithfundament(double x, double y) {
                ResolverCollisionWithfundament(x, y);
            }

            public void ResolveCollisionWithjumper(double x, double y) {
                game.getEventsHandler().Over();
            }

            public void ResolveCollisionWithBall(ICollisionResolver unit_, double x, double y) {
                // if (gravity instanceof MotionProducerNon){
                ((IResolverCollisionWithFundament) unit_).ResolveCollisionWithfundament(x, y);
                //}else{
                // unit_.StandGroup();
                // }

            }
        }
    }

    class SJFundGroup extends GameUnitNotActorState {

        public SJFundGroup(ITasksReciver sys_, int r, int g, int b, IGame game_) {
            super(sys_, 0, r, g, b, game_, game_.getRatio());
        }

        public void ResolveCollisionWith(IBodie unit_, double x, double y) {
            ((IResolverCollisionWithFundament) unit_.getCollisionResolver()).ResolveCollisionWithfundament(x, y);
        }

        public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {

            Tools.UnsupportedOperationException();
        }

        public states getViewState() {
            return states.zero;
        }

        @Override
        protected ICollisionResolver createCollisionResolver() {
            return new CollisionResolver();
        }

        private class CollisionResolver implements ICollisionResolver {
        }
    }

    class SJGroupJ extends GameUnit implements IBasicMoves {

        //Object[] viewState = zero;
        public SJGroupJ(ITasksReciver motionReciver, int r, int g, int b, IGame game_) {
            super("Jumper", motionReciver, 0, r, g, b, game_, game_.getRatio());
        }

//        boolean leftAllowed = true;
//        boolean rightallowed = true;
//        boolean upAllowed = true;
//        boolean downAllowed = true;
        public void ResolveCollisionOverScope(boolean inGorscope, boolean inVertscope) {
            GetWalker().Stand();
            GetWalker().getSpeed().SetNull();
            //  jspeed.Refresh();
        }

        public Motion CreateMotionMoveLeft() {
            return new MotionAddSpeed(new Vector2d(-2 * game.getRatio().getGrad() / game.getRatio().getMoveperiod(), 0), GetWalker().getSpeed(), calcMotionsReciver);
        }

        public Motion StopMotionMoveLeft() {
            return new MotionAddSpeed(new Vector2d(2 * game.getRatio().getGrad() / game.getRatio().getMoveperiod(), 0), GetWalker().getSpeed(), calcMotionsReciver);
        }

        public Motion CreateMotionMoveRight() {
            return new MotionAddSpeed(new Vector2d(2 * game.getRatio().getGrad() / game.getRatio().getMoveperiod(), 0), GetWalker().getSpeed(), calcMotionsReciver);
        }

        public Motion StopMotionMoveRight() {
            return new MotionAddSpeed(new Vector2d(-2 * game.getRatio().getGrad() / game.getRatio().getMoveperiod(), 0), GetWalker().getSpeed(), calcMotionsReciver);
        }

        public Motion CreateMotionMoveUp() {
            return new MotionJump(1, "Jump", this.GetWalker());
        }

        public Motion StopMotionMoveUp() {
            return null;
        }

        public Motion CreateMotionMoveDown() {
            return new Motion(1, "End") {

                public void MotionMethod() {
                    game.getEventsHandler().Over();
                }
            };
        }

        public Motion StopMotionMoveDown() {
            return null;
        }

        public void ResolveCollisionWith(IBodie unit_, double x, double y) {
            if (unit_ == this.getItem()) {
                return;
            }
            ((IResolverCollisionWithJumper) unit_.getCollisionResolver()).ResolveCollisionWithjumper(x, y);

        }

        public states getViewState() {
            if (((Vector2d) GetWalker().getSpeed()).getY() != 0) {
                if (((Vector2d) GetWalker().getSpeed()).getY() > 0) {
                    return states.Jumper_Jump;
                } else {
                    return states.Jumper_Fall;
                }
            } else if (((Vector2d) GetWalker().getSpeed()).getX() != 0) {
                return states.Jumper_Run;
            } else {
                return states.Jumper_State;
            }
        }

        public boolean FallAble() {
            return true;
        }

        @Override
        protected ICollisionResolver createCollisionResolver() {
            return new CollisionResolver();
        }

        private class CollisionResolver implements IResolverCollisionWithFundament, IResolverCollisionWithBall {

            public void ResolveCollisionWithfundament(double x, double y) {
                GetWalker().Stand();
                //  jspeed.Refresh();

                if (((Coordinates2d) asPoint().getPosition()).y > ((Coordinates2d) asPoint().getPosition()).yd) {
                    ((Vector2d) GetWalker().getSpeed()).setY(0);
                    //  onGrav = true;
                    GetWalker().getBalisticsStrategy().StopFreeFall();
                }

                //upAllowed = true;
//            viewState = zero;

            }

            public void ResolveCollisionWithBall(ICollisionResolver unit_, double x, double y) {
                game.getEventsHandler().Over();
            }
        }
    }
//class MotionProducerFair extends MotionProducer {
//
//    public MotionProducerFair(Group gr_, int x_, int y_) {
//        super(gr_, x_, y_);
//    }
//
//    public void startProduction() {
//        gr.fair = gr.GetNonMotionProducer();
//
//    }
//}
}
