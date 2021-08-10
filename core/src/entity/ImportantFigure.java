package entity;

import com.badlogic.gdx.graphics.Texture;

public class ImportantFigure {
    private Texture image;
    private String name;
    private String biography;

    public ImportantFigure(Texture image, String name, String biography){
        this.image = image;
        this.name = name;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }


}
