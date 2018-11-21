package edu.bu.ollie.imageeffect.image;

import java.nio.IntBuffer;

public class BrightnessProcessor extends Processor {

    int val = 0; // -128, 128

    @Override
    public void process(IntBuffer buffer, int w, int h) {
        for(int i = 0; i < w*h; i++){
            int c = buffer.get(i);
            buffer.put(proc(c, val));
        }
    }

    int proc(int c, int val){

        int a = c >> 24;
        int r = c >> 16 & 0xff;
        int g = c >> 8 & 0xff;
        int b = c & 0xff;

        r = clamp(r+val);
        g = clamp(g+val);
        b = clamp(b+val);

        return a << 24 | r << 16 | g << 8 | b;
    }

    @Override
    public void setParam(String key, double value) {
        //
    }

    @Override
    public void setParam(double value) {
        val = (int)value;
    }
}
