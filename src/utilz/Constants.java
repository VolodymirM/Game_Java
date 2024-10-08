package utilz;

import main.Game;

public class Constants {

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class UrmButton {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
            
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;

        public static int GetSpriteAmount(int player_action) {

            switch (player_action) {
                case RUNNING:
                    return 5;
                case IDLE:
                    return 14;
                case JUMP:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }

}
