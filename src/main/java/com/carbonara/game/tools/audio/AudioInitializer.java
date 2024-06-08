package com.carbonara.game.tools.audio;

import com.carbonara.game.managers.SoundManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;

public class AudioInitializer {
    private AudioNode sound;
    private final String path = "Media/";
    private final String type = ".wav";
    public void initialize(AssetManager assetManager){

        sound = new AudioNode(assetManager, path + "Sound/button_clicked" + type, AudioData.DataType.Buffer);
        sound.setPositional(false);
        SoundManager.add("button_clicked", sound);

        sound = new AudioNode(assetManager, path + "Music/main" + type, AudioData.DataType.Stream);
        sound.setPositional(false);
        sound.setLooping(true);
        SoundManager.add("main_music", sound);

        sound = new AudioNode(assetManager, path + "Music/environment" + type, AudioData.DataType.Stream);
        sound.setPositional(false);
        sound.setLooping(true);
        SoundManager.add("main_environment", sound);
    }
}
