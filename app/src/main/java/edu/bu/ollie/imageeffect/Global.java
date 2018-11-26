package edu.bu.ollie.imageeffect;

import java.util.ArrayList;

public class Global {
    public static ArrayList<String> imagePaths = new ArrayList<>();
    public static Integer currentIndex = null;

    public enum EffectMode{
        TONE ("tone"),
        BLUR_SHARP ("blur/sharpen"),
        EDGE ("edge");
        private final String name;
        EffectMode(String s) {
            name = s;
        }
        public String toString() {
            return this.name;
        }
    }

}
