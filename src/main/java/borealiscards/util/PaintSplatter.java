package borealiscards.util;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class PaintSplatter {
    public float x;
    public float y;
    public float rotation;
    public ArrayList<SplatterBlob> blobs;

    public PaintSplatter(float x, float y) {
        this.x = x;
        this.y = y;
        this.rotation = MathUtils.random(360f);
        this.blobs = new ArrayList<>();

        // Generate multiple overlapping blobs for organic shape
        int numBlobs = MathUtils.random(3, 6);
        for (int i = 0; i < numBlobs; i++) {
            float offsetX = MathUtils.random(-8f, 8f) * Settings.scale;
            float offsetY = MathUtils.random(-8f, 8f) * Settings.scale;
            float size = MathUtils.random(0.4f, 1.2f);
            blobs.add(new SplatterBlob(offsetX, offsetY, size));
        }
    }

    public static class SplatterBlob {
        public float offsetX;
        public float offsetY;
        public float size;

        public SplatterBlob(float offsetX, float offsetY, float size) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.size = size;
        }
    }
}