package edu.bu.ollie.imageeffect.image;

import java.nio.IntBuffer;

public class ToneProcessor extends Processor {

    int brightness = 0; // -128, 128
    int contrast = 0; // -128, 128
    int gamma = 0; // -128, 128

    @Override
    public void process(IntBuffer buffer, int w, int h) {
        for(int i = 0; i < w*h; i++){
            int c = buffer.get(i);
            buffer.put(proc(c, brightness, contrast, gamma));
        }
    }

    @Override
    public void process(IntBuffer inbuffer, IntBuffer outbuffer, int w, int h) {
        for(int i = 0; i < w*h; i++){
            int c = inbuffer.get(i);
            outbuffer.put(proc(c, brightness, contrast, gamma));
        }
    }

    int proc(int c, int bval, int cval, int gval){

        int a = c >> 24;
        int r = c >> 16 & 0xff;
        int g = c >> 8 & 0xff;
        int b = c & 0xff;

        r = clamp(proc_gamma(clamp(proc_contrast(clamp(r+bval), cval)), gval));
        g = clamp(proc_gamma(clamp(proc_contrast(clamp(g+bval), cval)), gval));
        b = clamp(proc_gamma(clamp(proc_contrast(clamp(b+bval), cval)), gval));

        return a << 24 | r << 16 | g << 8 | b;

    }

    int proc_contrast(int c, int val){
        float v = (250+val)/(float)(255-val);
        return (int)(v*((c-128)+128));
    }

    int proc_gamma(int c, int val){
        return (int)(Math.pow(c/255.0, (1.0+(val/128.0)))*256);
    }

    public void setParams(int b, int c, int g){
        brightness = b;
        contrast = c;
        gamma = g;
    }

    public void setBrightness(int b){
        brightness = b;
    }

    public void setContrast(int c){
        contrast = c;
    }

    public void setGamma(int g){
        gamma = g;
    }

    @Override
    public void setParam(String key, double value) {
        //
    }

    @Override
    public void setParam(double value) {
        /*val = (int)value;*/
    }
}
