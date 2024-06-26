package com.carbonara.game.tools.video.player;

/**
 * 
 * @author capdevon
 */
public class MovieSettings {

    private final String path;
    private final int width;
    private final int height;
    private final float zoomingFactor;
    private final boolean skippable;

    public MovieSettings(String path, int width, int height, float zoomingFactor, boolean skippable) {
        this.path = path;
        this.width = width;
        this.height = height;
        this.zoomingFactor = zoomingFactor;
        this.skippable = skippable;
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getZoomingFactor() {
        return zoomingFactor;
    }

    public boolean isSkippable() {
        return skippable;
    }

}