package entity;

import com.badlogic.gdx.graphics.Texture;

public class ImportantFigure {
    private Texture image;
    private Texture biography;
    private String name;

    public ImportantFigure(Texture image, Texture biography, String name){
        this.image = image;
        this.biography = biography;
        this.name = name;
    }

    public Texture getBiography() {
        return biography;
    }

    public void setBiography(Texture biography) {
        this.biography = biography;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
