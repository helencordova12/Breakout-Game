// Breakout Project - CIS 36A
// Created by Margarita Cordova

import objectdraw.ActiveObject;
import objectdraw.*;
import java.awt.*;

public class ColoredBricks extends ActiveObject {

    private static final int CANVAS_WIDTH = BreakoutProgram.CANVAS_WIDTH; //420

    public int brickColumns = BreakoutProgram.NBRICK_COLUMNS; //10
    public int brickRows = BreakoutProgram.NBRICK_ROWS; // 10
    public double spaceBetweenIndividualBricks = BreakoutProgram.BRICK_SEP; //4
    public double topWallSpace = BreakoutProgram.BRICK_Y_OFFSET; //70
    public double widthBrick = BreakoutProgram.BRICK_WIDTH; //37
    public double heightBrick = BreakoutProgram.BRICK_HEIGHT; //8

    public double horizontalOuterSpace = (CANVAS_WIDTH-((10*widthBrick)+(spaceBetweenIndividualBricks*9)))/2; //7

    FilledRect bricks[][] = new FilledRect[brickRows][brickColumns];

    public ColoredBricks(DrawingCanvas canvas) {

        double xCoordinate = horizontalOuterSpace;
        double yCoordinate = topWallSpace; //70

        for (int i = 0; i < brickRows; i++) //rows
        {
            for (int j = 0; j < brickColumns; j++) //columns
            {

                if(j == 0) {
                    xCoordinate = horizontalOuterSpace;
                }
                bricks[i][j] = new FilledRect(xCoordinate, yCoordinate, widthBrick, heightBrick, canvas);

                if (i == 0 || i == 1) {
                    bricks[i][j].setColor(Color.red);
                }else if(i == 2 || i == 3) {
                }else if(i == 2 || i == 3) {
                    bricks[i][j].setColor(Color.orange);
                }else if(i == 4 || i == 5) {
                    bricks[i][j].setColor(Color.yellow);
                }else if(i == 6 || i == 7) {
                    bricks[i][j].setColor(Color.green);
                }else{
                    bricks[i][j].setColor(Color.cyan);
                }
                xCoordinate += widthBrick+spaceBetweenIndividualBricks;
            }
            yCoordinate += heightBrick+5;
        }

    }

    public FilledRect[][] GetBricks() {
        return bricks;
    }

}


