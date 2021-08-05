package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;
import util.Util;


public class PuzzlePiece extends InputAdapter {

    private Vector2 position;
    private Vector2 textureCenter;
    private TextureRegion textureRegion;
    private int row;
    private int col;

    private float grabOffsetX;
    private float grabOffsetY;

    private boolean touched;

    private Rectangle puzzlePieceBoundingBox;

    private PuzzleArea puzzleArea;

    public PuzzlePiece(Vector2 position, TextureRegion textureRegion){
        this.position = position;
        this.textureRegion = textureRegion;
    }

    public void render(SpriteBatch batch){
        Util.drawTextureRegion(
                batch,
                textureRegion,
                this.position,
                new Vector2(getWidth() / 2f, getHeight()/ 2f));
    }

    public void setPuzzlePieceBoundingBox(Rectangle puzzlePieceBoundingBox){
        this.puzzlePieceBoundingBox = puzzlePieceBoundingBox;
    }

    public Rectangle getPuzzlePieceBoundingBox(){
        return this.puzzlePieceBoundingBox;
    }

    public void setPosition(Vector2 position){
        this.position = position;
    }

    public int getWidth(){
        return this.textureRegion.getRegionWidth();
    }

    public int getHeight(){
        return this.textureRegion.getRegionHeight();
    }

    public Vector2 getPosition(){
        return this.position;
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

    public void setPuzzleArea(PuzzleArea pa) {
        puzzleArea = pa;
    }

    public PuzzleArea getPuzzleArea() {
        return puzzleArea;
    }

    public void clearPuzzleArea() {
        puzzleArea = null;
    }

    public boolean hasPuzzleArea() {
        return puzzleArea != null;
    }

    public boolean isCorrectlyPlaced() {
        return hasPuzzleArea()
                && this.row == puzzleArea.getRow()
                && this.col == puzzleArea.getCol();
    }

    public void setTouched(boolean touched){
        this.touched = touched;
    }

    public boolean getTouched(){
        return this.touched;
    }

}
