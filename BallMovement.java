// Breakout Project - CIS 36A
// Created by Margarita Cordova

import objectdraw.ActiveObject;
import java.util.Random;
import objectdraw.*;
import java.awt.*;

public class BallMovement extends ActiveObject {

    private static final double BALL_DIAMETER = BreakoutProgram.BALL_RADIUS*2;
    private static final int CANVAS_WIDTH = BreakoutProgram.CANVAS_WIDTH; //420
    private static final int CANVAS_HEIGHT = BreakoutProgram.CANVAS_HEIGHT; //600
    private static final int HALF_WIDTH = CANVAS_WIDTH/2; // 210

    public double widthPaddle = BreakoutProgram.PADDLE_WIDTH; //60
    public double velocity_x;
    public double velocity_y;
    public int turns = BreakoutProgram.NTURNS;
    public int score = 0;

    FilledOval ball;
    ColoredBricks bricks;
    Location canvasCenter, paddleStartPosition;
    Text displayScore, playerWins, playerLoses, displayIncreaseMessage;
    FilledRect topRect, leftRect, rightRect, bottomRect, paddle;

    DrawingCanvas canvas;
    Random num = new Random();

    public boolean gameStillGoing = true; //used in the run function
    public boolean mousePressed = false; //used in both the increaseSpeed and run functions

    public BallMovement(DrawingCanvas canvas) {

        this.canvas = canvas; //displays canvas

        paddleStartPosition = new Location(HALF_WIDTH-(widthPaddle/2), CANVAS_HEIGHT-BreakoutProgram.PADDLE_Y_OFFSET);
        canvasCenter = new Location(CANVAS_WIDTH/2,CANVAS_HEIGHT/2);

        //set random velocity for ball
        velocity_x = num.nextInt((int)BreakoutProgram.VELOCITY_X_MAX-(int)BreakoutProgram.VELOCITY_X_MIN)+BreakoutProgram.VELOCITY_X_MIN;
        velocity_y = BreakoutProgram.VELOCITY_Y;

        while(velocity_x == 0){
            velocity_x = num.nextInt((int)BreakoutProgram.VELOCITY_X_MAX-(int)BreakoutProgram.VELOCITY_X_MIN)+BreakoutProgram.VELOCITY_X_MIN;
        }

        //display ball, paddle, and bricks
        ball = new FilledOval(canvasCenter.getX()-(BALL_DIAMETER/2), canvasCenter.getY()-(BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER,canvas);
        paddle = new FilledRect(paddleStartPosition, widthPaddle, BreakoutProgram.PADDLE_HEIGHT, canvas);
        bricks = new ColoredBricks(canvas);

        start();
    }

    public void MovePaddle(Location point){

        double topRightCorner = (point.getX()+widthPaddle/2);
        double topLeftCorner = (point.getX()-widthPaddle/2);

        if(topRightCorner < CANVAS_WIDTH && topLeftCorner > 0){
            paddle.moveTo(point.getX()-(BreakoutProgram.PADDLE_WIDTH/2), paddleStartPosition.getY());
        }

    }

    public void IncreaseXSpeed(int num) {

        displayIncreaseMessage.hide();
        mousePressed = true;

        if(velocity_x < 0) {
            velocity_x = velocity_x-num;
        }
        if(velocity_x >= 1) {
            velocity_x = velocity_x+num;
        }

    }

    public void messagesDisplayed() {

        displayScore = new Text(("Score: " + score + ", Turns: " + turns), 10, 20, Color.black, canvas);
        displayScore.setFontSize(13);
        displayScore.setFont(BreakoutProgram.SCREEN_FONT);

        playerWins = new Text("YOU WIN!", canvasCenter, Color.black, canvas);
        playerWins.setFont(BreakoutProgram.SCREEN_FONT);
        playerWins.setFontSize(20);
        playerWins.moveTo(playerWins.getX()-(playerWins.getWidth()/2), playerWins.getY());
        playerWins.hide();

        playerLoses = new Text("YOU LOSE", canvasCenter, Color.black, canvas);
        playerLoses.setFont(BreakoutProgram.SCREEN_FONT);
        playerLoses.setFontSize(20);
        playerLoses.moveTo(playerLoses.getX()-(playerLoses.getWidth()/2), playerLoses.getY());
        playerLoses.hide();

        displayIncreaseMessage = new Text("Press on Mouse to Increase Speed",CANVAS_WIDTH, 20, Color.black, canvas);
        displayIncreaseMessage.moveTo(displayIncreaseMessage.getX()-displayIncreaseMessage.getWidth()-57, displayIncreaseMessage.getY());
        displayIncreaseMessage.setFontSize(13);
        displayIncreaseMessage.setFont(BreakoutProgram.SCREEN_FONT);

    }

    public void brickAndBallCollision() { //detects if the ball has hit a brick

        for (int i = 0; i < BreakoutProgram.NBRICK_ROWS; i++)
        {
            for (int j = 0; j < BreakoutProgram.NBRICK_COLUMNS; j++)
            {
                if(bricks.GetBricks()[i][j].overlaps(ball))
                {
                    score += 1;
                    displayScore.setText("Score: " + score + ", Turns: " + turns);
                    bricks.GetBricks()[i][j].moveTo(-100, -100);
                    velocity_y *= -1;

                    if(score == 5){
                        displayIncreaseMessage.hide();
                    }

                    if(score == 100){
                        ball.removeFromCanvas();
                        paddle.removeFromCanvas();
                        playerWins.show();
                        gameStillGoing = false;
                    }
                }
            }
        }

    }

    public void run()
    {
        messagesDisplayed();

        topRect = new FilledRect(0, -20, CANVAS_WIDTH, 20, canvas);
        leftRect = new FilledRect(-20, 0, 20, CANVAS_HEIGHT, canvas);
        rightRect = new FilledRect(CANVAS_WIDTH, 0, 20, CANVAS_HEIGHT, canvas);
        bottomRect = new FilledRect(0, canvas.getHeight(), canvas.getWidth(), 20, canvas);

        while (gameStillGoing)
        {
            ball.move(velocity_x, velocity_y);

            if (ball.overlaps(paddle)) //if ball touches paddle
                velocity_y *= -1; //allows ball to move in a reflected angle on the canvas

            if (ball.overlaps(topRect)) //if ball touches top wall then
                velocity_y *= -1;

            if (ball.overlaps(rightRect)) //if ball touches right wall
                velocity_x *= -1;

            if (ball.overlaps(leftRect)) //if ball touches left wall
                velocity_x *= -1;

            if (ball.overlaps(bottomRect)) //if ball touches bottom wall then you turns decrease by 1 and ball moves to center.
            {
                if (mousePressed = false) {
                    velocity_x = num.nextInt((int)BreakoutProgram.VELOCITY_X_MAX-(int)BreakoutProgram.VELOCITY_X_MIN)+BreakoutProgram.VELOCITY_X_MIN;
                    while(velocity_x == 0) {
                        velocity_x = num.nextInt((int)BreakoutProgram.VELOCITY_X_MAX-(int)BreakoutProgram.VELOCITY_X_MIN)+BreakoutProgram.VELOCITY_X_MIN;
                    }
                }

                turns -=1;
                ball.moveTo(canvasCenter);
                displayScore.setText("Score: " + score + ", Turns: " + turns);

                if(turns == 0) { //game over!!
                    displayIncreaseMessage.hide();
                    playerLoses.show();
                    paddle.hide();
                    ball.removeFromCanvas();
                    gameStillGoing = false;

                }
            }

            brickAndBallCollision();
            pause(BreakoutProgram.DELAY);
        }
    }
}

