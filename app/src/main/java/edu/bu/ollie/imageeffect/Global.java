package edu.bu.ollie.imageeffect;

import java.util.ArrayList;

public class Global {
    public static ArrayList<String> imagePaths = new ArrayList<>();
    public static Integer currentIndex = null;
    public static final int preview_width = 400;
    public static final boolean use_orientation = true;

    public enum EffectMode{
        TONE ("tone"),
        COLOR ("color"),
        BLUR("blur"),
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
