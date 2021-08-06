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

    private Rectangle puzzleAreaRectangle;

    public PuzzleArea(Vector2 position, Rectangle puzzleAreaRectangle){
        this.position = position;
        this.puzzleAreaRectangle = puzzleAreaRectangle;
    }

    public Rectangle getPuzzleAreaRectangle(){
        return this.puzzleAreaRectangle;
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
