package com.carbonara.game.managers;

import com.jme3.audio.AudioNode;

import java.util.HashMap;
import java.util.Optional;

public class SoundManager {
    private static final HashMap<String, AudioNode> audioNodeHashMap = new HashMap<>();

    public static void add(String name, AudioNode audioNode){
        audioNodeHashMap.put(name, audioNode);
    }

    public static Optional<AudioNode> get(String name){
        Optional<AudioNode> audioNodeOptional;
        AudioNode audioNode = audioNodeHashMap.get(name);

        if (audioNode != null) audioNodeOptional = Optional.of(audioNode);
        else audioNodeOptional = Optional.empty();

        return audioNodeOptional;
    }

    public static void click_button(){
        get("button_clicked").ifPresent(AudioNode::playInstance);
    }
}
