package entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class PuzzleArea {

    private Vector2 position;
    private int row;
    private int col;

    private Rectangle puzzleAreaBoundingBox;

    private boolean targetable;

    public PuzzleArea(Vector2 position, Rectangle puzzleAreaBoundingBox){
        this.position = position;
        this.puzzleAreaBoundingBox = puzzleAreaBoundingBox;
        targetable = true;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public void setTargetable(boolean targetable){
        this.targetable = targetable;
    }

    public boolean isTargetable(){
        return targetable;
    }

    public Rectangle getPuzzleAreaBoundingBox(){
        return this.puzzleAreaBoundingBox;
    }

    public void setRow(int r) {
        row = r;
    }

    public void setCol(int c) {
        col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
