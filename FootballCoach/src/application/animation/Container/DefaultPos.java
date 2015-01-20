/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.animation.Container;

import static application.animation.Container.DefaultPos.L0;
import static application.animation.Container.DefaultPos.L1;
import static application.animation.Container.DefaultPos.L10;
import static application.animation.Container.DefaultPos.L2;
import static application.animation.Container.DefaultPos.L3;
import static application.animation.Container.DefaultPos.L4;
import static application.animation.Container.DefaultPos.L5;
import static application.animation.Container.DefaultPos.L6;
import static application.animation.Container.DefaultPos.L7;
import static application.animation.Container.DefaultPos.L8;
import static application.animation.Container.DefaultPos.L9;
import static application.animation.Container.DefaultPos.R0;
import static application.animation.Container.DefaultPos.R1;
import static application.animation.Container.DefaultPos.R10;
import static application.animation.Container.DefaultPos.R2;
import static application.animation.Container.DefaultPos.R3;
import static application.animation.Container.DefaultPos.R4;
import static application.animation.Container.DefaultPos.R5;
import static application.animation.Container.DefaultPos.R6;
import static application.animation.Container.DefaultPos.R7;
import static application.animation.Container.DefaultPos.R8;
import static application.animation.Container.DefaultPos.R9;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * L means left, R means right side, 0 = keeper, 1-4 = defender, 5-7 = midfield,
 * 8-10 = attacker
 *
 * @author Faris
 */
public enum DefaultPos {

    L0(new Circle(60, 381, 8, Color.BLUE), new Position(60, 381)),
    L1(new Circle(330, 160, 8, Color.BLUE), new Position(330, 160)),
    L2(new Circle(288, 290, 8, Color.BLUE), new Position(288, 290)),
    L3(new Circle(288, 467, 8, Color.BLUE), new Position(288, 467)),
    L4(new Circle(330, 605, 8, Color.BLUE), new Position(330, 605)),
    L5(new Circle(550, 250, 8, Color.BLUE), new Position(550, 250)),
    L6(new Circle(450, 385, 8, Color.BLUE), new Position(450, 385)),
    L7(new Circle(550, 513, 8, Color.BLUE), new Position(550, 513)),
    L8(new Circle(713, 259, 8, Color.BLUE), new Position(713, 259)),
    L9(new Circle(785, 385, 8, Color.BLUE), new Position(785, 385)),
    L10(new Circle(719, 494, 8, Color.BLUE), new Position(719, 494)),
    R0(new Circle(963, 381, 8, Color.RED), new Position(963, 381)),
    R1(new Circle(700, 160, 8, Color.RED), new Position(700, 160)),
    R2(new Circle(730, 290, 8, Color.RED), new Position(730, 290)),
    R3(new Circle(730, 467, 8, Color.RED), new Position(730, 467)),
    R4(new Circle(700, 605, 8, Color.RED), new Position(700, 605)),
    R5(new Circle(461, 250, 8, Color.RED), new Position(461, 250)),
    R6(new Circle(562, 385, 8, Color.RED), new Position(562, 385)),
    R7(new Circle(461, 513, 8, Color.RED), new Position(461, 513)),
    R8(new Circle(306, 259, 8, Color.RED), new Position(306, 259)),
    R9(new Circle(233, 385, 8, Color.RED), new Position(233, 385)),
    R10(new Circle(306, 494, 8, Color.RED), new Position(306, 494));

    private DefaultPos(Circle circle, Position pos) {
        this.circle = circle;
        this.pos = pos;
    }

    public final Circle circle;
    public final Position pos;

    /**
     * Usefull class for looping over the different values of the enum
     */
    public static class Loop {

        /**
         * Get a Left player by index
         * @param i index of the player
         * @return the position of the player
         */
        public static DefaultPos getL(int i) {
            switch (i) {
                case 0:
                    return L0;
                case 1:
                    return L1;
                case 2:
                    return L2;
                case 3:
                    return L3;
                case 4:
                    return L4;
                case 5:
                    return L5;
                case 6:
                    return L6;
                case 7:
                    return L7;
                case 8:
                    return L8;
                case 9:
                    return L9;
                case 10:
                    return L10;

                default:
                    throw new AssertionError();
            }
        }

        /**
         * Get a Right player by index
         * @param i index of the player
         * @return the position of the player
         */
        public static DefaultPos getR(int i) {
            switch (i) {
                case 0:
                    return R0;
                case 1:
                    return R1;
                case 2:
                    return R2;
                case 3:
                    return R3;
                case 4:
                    return R4;
                case 5:
                    return R5;
                case 6:
                    return R6;
                case 7:
                    return R7;
                case 8:
                    return R8;
                case 9:
                    return R9;
                case 10:
                    return R10;

                default:
                    throw new AssertionError();
            }
        }
    }

}
