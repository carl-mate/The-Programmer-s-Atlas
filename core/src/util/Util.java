package util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Util {

    public static void drawTextureRegion(SpriteBatch batch, Texture image, Vector2 position, Vector2 offset) {
        float x = position.x - offset.x;
        float y = position.y - offset.y;

        batch.draw(
                image,
                x,
                y,
                0,
                0,
                image.getWidth(),
                image.getHeight(),
                1,
                1,
                0,
                0,
                0,
                image.getWidth(),
                image.getHeight(),
                false,
                false);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, Vector2 position) {
        drawTextureRegion(batch, region, position.x, position.y);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, Vector2 position, Vector2 offset) {
        drawTextureRegion(batch, region, position.x - offset.x, position.y - offset.y);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, float x, float y) {
        batch.draw(
                region.getTexture(),
                x,
                y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public static long secondsSince(long timeNanos) {
        return (long) (MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeNanos));
    }


}
