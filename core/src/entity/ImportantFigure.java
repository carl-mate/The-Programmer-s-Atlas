package entity;

import com.badlogic.gdx.graphics.Texture;

public class ImportantFigure {
    private Texture image;
    private String name;

    public ImportantFigure(Texture image, String name){
        this.image = image;
        this.name = name;
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
