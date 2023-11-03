// Breakout Project - CIS 36A
// Created by Margarita Cordova

import objectdraw.WindowController;
import objectdraw.*;

public class BreakoutController extends WindowController {

    public int num = 1;

    BallMovement ball;

    public void begin() {
        ball = new BallMovement(canvas);
    }

    //SPECIAL FEATURE: The user can increase the x-speed by 1 every time the mouse is clicked
    public void onMousePress(Location point){
        ball.IncreaseXSpeed(num);
        num += 1;
    }

    public void onMouseMove(Location point){
        ball.MovePaddle(point);
    }

    public static void main(String[] args) {
        new BreakoutController().startController(BreakoutProgram.CANVAS_WIDTH, BreakoutProgram.CANVAS_HEIGHT);
    }

}
