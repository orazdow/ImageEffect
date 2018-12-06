package edu.bu.ollie.imageeffect.image;

import java.nio.IntBuffer;

public class ColorProcessor extends Processor {
// http://graficaobscura.com/matrix/index.html
// https://stackoverflow.com/questions/13806483/increase-or-decrease-color-saturation
// https://stackoverflow.com/questions/8507885/shift-hue-of-an-rgb-color

    double U = 0;
    double W = 0;
    double toRad = Math.PI/180.0;

    double sat = 1;

    void setHue(int in){
        int deg = (int)(1.4*in);
        U = Math.cos(deg*toRad);
        W = Math.sin(deg*toRad);
    }

    void setSat(int in){
        sat = in/128.0;
    }

    double rotateR(float r, float g, float b){
        return (0.299+0.701*U+.0168*W)*r + (.587-.587*U+.330*W)*g + (.114-.114*U-.497*W)*b;
    }
    double rotateG(float r, float g, float b){
        return (0.299-0.299*U-0.328*W)*r + (0.587+0.413*U+0.035*W)*g + (0.114-0.114*U+0.292*W)*b;
    }
    double rotateB(float r, float g, float b){
        return (0.299-0.3*U+10.25*W)*r + (0.587-0.588*U-10.05*W)*g + (0.114+0.886*U-0.203*W)*b;
    }

    int proc(int c){
        int a = c >> 24;
        int r = c >> 16 & 0xff;
        int g = c >> 8 & 0xff;
        int b = c & 0xff;

        float rf = r/255f; float gf = g/255f; float bf = b/255f;
        double gray = 0.2989*rf + 0.5870*gf + 0.1140*bf;

        float rr = (float)clamp((-gray*sat + rf*(1+sat)));
        float gg = (float)clamp((-gray*sat + gf*(1+sat)));
        float bb = (float)clamp((-gray*sat + bf*(1+sat)));

        // remove hue clamp for interesting effect
        int _r = clamp((int)(rotateR(rr, gg, bb)*255));
        int _g = clamp((int)(rotateG(rr, gg, bb)*255));
        int _b = clamp((int)(rotateB(rr, gg, bb)*255));

        return a << 24 | _r << 16 | _g << 8 | _b;
    }

    @Override
    public void process(IntBuffer buffer, int w, int h) {
        for(int i = 0; i < w*h; i++){
            int c = buffer.get(i);
            buffer.put(proc(c));
        }
    }

    @Override
    public void process(IntBuffer inbuffer, IntBuffer outbuffer, int w, int h) {
        for(int i = 0; i < w*h; i++){
            int c = inbuffer.get(i);
            outbuffer.put(proc(c));
        }
    }

    @Override
    public void setParam(String key, double value) {

    }

    @Override
    public void setParam(double value) {

    }
}
