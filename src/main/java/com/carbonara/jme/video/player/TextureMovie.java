package com.carbonara.jme.video.player;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import com.jme3.app.Application;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ColorSpace;
import com.jme3.util.BufferUtils;
import com.sun.media.jfxmedia.control.VideoDataBuffer;
import com.sun.media.jfxmedia.control.VideoFormat;
import com.sun.media.jfxmedia.events.NewFrameEvent;
import com.sun.media.jfxmedia.events.VideoRendererListener;

/**
 *
 * Please be sure you do not lose reference to TextureMovie while you need the
 * result texture - if it ever gets garbage collected, movie can stop playing.
 */
public class TextureMovie {

    public enum LetterboxMode {
        /**
         * This mode uses entire texture including some garbage data on right
         * and bottom side; always utilize together with corner data to cut the
         * texture and aspectRatio to size it properly
         */
        RAW_SQUARE,
        /**
         * This mode uses entire texture, but fills it only with valid data.
         * Proper texture coordinates will be always 0-1, but you should use
         * aspectRatio for it to look proper
         */
        VALID_SQUARE,
        /**
         * This is cinema-like presentation, with borders on shorter sides
         * (mostly top and down). Displaying it on square object will make it
         * look proper aspectRatio-wise. You can utilize corner data to display
         * only interesting part of the movie.
         */
        VALID_LETTERBOX
    }

    private static Image emptyImage = new Image(Format.ABGR8, 1, 1, BufferUtils.createByteBuffer(4), ColorSpace.sRGB);

    private final com.sun.media.jfxmedia.MediaPlayer m_JfxPlayer;
    private final LetterboxMode mode;
    private final ColorRGBA letterboxColor = ColorRGBA.Red.clone();
    private final Vector2f upperLeftCorner = new Vector2f(0, 0);
    private final Vector2f bottomRightCorner = new Vector2f(1, 1);
    private final VideoRendererListener vrListener;
    private float aspectRatio = 1.0f;

    private Application app;
    private Texture2D texture;
    private Format format;
    private ImageExchanger imageExchanger;

    /**
     *
     * @param app
     * @param mediaPlayer
     */
    public TextureMovie(final Application app, javafx.scene.media.MediaPlayer mediaPlayer) {
        this(app, mediaPlayer, LetterboxMode.VALID_LETTERBOX);
    }

    /**
     *
     * @param app
     * @param mediaPlayer
     * @param mode
     */
    public TextureMovie(final Application app, javafx.scene.media.MediaPlayer mediaPlayer, LetterboxMode mode) {
        this.app = app;
        this.mode = mode;
        this.format = Format.ARGB8;

        try {
            Method m1 = mediaPlayer.getClass().getDeclaredMethod("retrieveJfxPlayer");
            m1.setAccessible(true);

            while (true) {
                com.sun.media.jfxmedia.MediaPlayer player = (com.sun.media.jfxmedia.MediaPlayer) m1.invoke(mediaPlayer);
                if (player != null) {
                    m_JfxPlayer = player;
                    break;
                }
                Thread.sleep(50);
            }

        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }

        vrListener = createVrListener();
    }

    private VideoRendererListener createVrListener() {
        return new VideoRendererListener() {

            @Override
            public void videoFrameUpdated(NewFrameEvent event) {
                try {
                    VideoDataBuffer frame = event.getFrameData();
                    if (frame == null) {
                        return;
                    }

                    final VideoDataBuffer argbFrame = frame.convertToFormat(VideoFormat.ARGB);
                    frame.releaseFrame();

                    if (argbFrame == null) {
                        return;
                    }

                    int expectedWidth, expectedHeight;
                    int xOffset;
                    int xSize;
                    int yOffset;
                    int ySize;

                    switch (mode) {
                        case RAW_SQUARE:
                            xOffset = 0;
                            yOffset = 0;
                            expectedWidth = argbFrame.getEncodedWidth();
                            expectedHeight = argbFrame.getEncodedHeight();
                            xSize = expectedWidth;
                            ySize = expectedHeight;
                            break;

                        case VALID_SQUARE:
                            xOffset = 0;
                            yOffset = 0;
                            expectedWidth = argbFrame.getWidth();
                            expectedHeight = argbFrame.getHeight();
                            xSize = expectedWidth;
                            ySize = expectedHeight;
                            break;

                        case VALID_LETTERBOX:
                        default:
                            expectedWidth = Math.max(argbFrame.getWidth(), argbFrame.getHeight());
                            expectedHeight = expectedWidth;

                            if (argbFrame.getWidth() >= argbFrame.getHeight()) {
                                xOffset = 0;
                                xSize = expectedWidth;
                                ySize = argbFrame.getHeight();
                                yOffset = (xSize - ySize) / 2;
                            } else {
                                yOffset = 0;
                                ySize = expectedHeight;
                                xSize = argbFrame.getWidth();
                                xOffset = (ySize - xSize) / 2;
                            }
                            break;
                    }

                    aspectRatio = argbFrame.getWidth() / argbFrame.getHeight();
                    upperLeftCorner.set(xOffset / (float) expectedWidth, yOffset / (float) expectedHeight);
                    bottomRightCorner.set((xOffset + argbFrame.getWidth()) / (float) expectedWidth, (yOffset + argbFrame.getHeight()) / (float) expectedHeight);

                    ByteBuffer src = argbFrame.getBufferForPlane(0); //(ByteBuffer) argbFrame.getBuffer();
                    Image image = texture.getImage();

                    if (image.getWidth() != expectedWidth || image.getHeight() != expectedHeight) {

                        imageExchanger = new ImageExchanger(expectedWidth, expectedHeight, format, app);
                        ByteBuffer bb = imageExchanger.getFxData();

                        for (int i = 0; i < bb.limit(); i += 4) {
                            bb.put(i, (byte) (letterboxColor.a * 255));
                            bb.put(i + 1, (byte) (letterboxColor.b * 255));
                            bb.put(i + 2, (byte) (letterboxColor.g * 255));
                            bb.put(i + 3, (byte) (letterboxColor.r * 255));
                        }

                        bb.position(0);
                        texture.setImage(imageExchanger.getImage());
                    }

                    imageExchanger.startUpdate();

                    try {
                        ByteBuffer bb = imageExchanger.getFxData();
                        bb.clear();
                        for (int y = 0; y < ySize; y++) {
                            int ty = expectedHeight - (y + yOffset) - 1;
                            bb.position(4 * (ty * xSize + xOffset));
                            src.position(4 * (y * argbFrame.getEncodedWidth())).limit(4 * (y * argbFrame.getEncodedWidth() + xSize));
                            bb.put(src);
                            src.limit(src.capacity());
                        }

                        bb.position(bb.limit());
                        bb.flip();

                        argbFrame.releaseFrame();

                    } finally {
                        imageExchanger.flushUpdate();
                    }

                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
            }

            @Override
            public void releaseVideoFrames() {
            }
        };
    }

    /**
     * Sets the color which should be used for letterbox fill. It is annoying
     * red by default to help with debugging.
     *
     * @param letterboxColor
     */
    public void setLetterboxColor(ColorRGBA letterboxColor) {
        this.letterboxColor.set(letterboxColor);
    }

    /**
     * Corner texture coordinates of valid movie area
     *
     * @return
     */
    public Vector2f getUpperLeftCorner() {
        return upperLeftCorner;
    }

    /**
     * Corner texture coordinates of valid movie area
     *
     * @return
     */
    public Vector2f getBottomRightCorner() {
        return bottomRightCorner;
    }

    /**
     *
     * @return aspect ratio of played movie (width/height) - for widescreen
     * movies it will be in range of 1.8-2.9
     */
    public float getAspectRatio() {
        return aspectRatio;
    }

    public Texture2D getTexture() {
        if (texture == null) {
            texture = new Texture2D(emptyImage);
            m_JfxPlayer.getVideoRenderControl().addVideoRendererListener(vrListener);
        }
        return texture;
    }
}
