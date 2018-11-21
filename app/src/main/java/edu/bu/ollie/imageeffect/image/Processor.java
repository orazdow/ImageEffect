package edu.bu.ollie.imageeffect.image;

import java.nio.IntBuffer;

public abstract class Processor {

    public abstract void process(IntBuffer buffer, int w, int h);

    public abstract void setParam(String key, double value);

    public abstract void setParam(double value);

    public int clamp(int c){
        if(c < 0){
            return 0;
        }else if(c > 255){
            return 255;
        }else return c;
    }

    public int toIndex(int x, int y, int w){
        return y*w+x;
    }

}
