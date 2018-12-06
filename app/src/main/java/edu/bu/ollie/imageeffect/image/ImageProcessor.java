package edu.bu.ollie.imageeffect.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import java.nio.IntBuffer;
import edu.bu.ollie.imageeffect.Global;
import edu.bu.ollie.imageeffect.ProcessActivity;

public class ImageProcessor {

    Bitmap baseImg, prevImg;
    IntBuffer bufferp, staticBufferp, baseBuffer;
    int w_p, h_p, size_p, w, h, size;
    Matrix matrix;
    ProcessActivity parent;

    ToneProcessor toneproc;
    ColorProcessor colorproc;

    public ImageProcessor(ProcessActivity parent){
        this.parent = parent;
        toneproc = new ToneProcessor();
        colorproc = new ColorProcessor();
        matrix = new Matrix();
    }

    public void setToneBrightness(int b){ toneproc.setBrightness(b);}
    public void setToneContrast(int c){toneproc.setContrast(c);}
    public void setToneGamma(int g){toneproc.setGamma(g);}

    public void setcolorHue(int h){colorproc.setHue(h);}
    public void setcolorSat(int s){colorproc.setSat(s);}


    public Bitmap loadImage(Bitmap img){
        baseImg = img;
        w = img.getWidth();
        h = img.getHeight();
        size = w*h;
        //prevImg = Bitmap.createScaledBitmap(img, Global.preview_width, Math.round(h*(Global.preview_width/(float)w)), false);
        float scale = Global.preview_width/(float)w;
        matrix.setScale(scale, scale);
        prevImg = Bitmap.createBitmap(img, 0, 0, w, h, matrix, false);
        matrix.reset();
        w_p = prevImg.getWidth();
        h_p = prevImg.getHeight();
        size_p = w_p*h_p;

        bufferp = IntBuffer.allocate(size_p);
        prevImg.copyPixelsToBuffer(bufferp);
        bufferp.rewind();

        staticBufferp = IntBuffer.allocate(size_p);
        prevImg.copyPixelsToBuffer(staticBufferp);
        staticBufferp.rewind();

        baseBuffer = IntBuffer.allocate(size);
        baseImg.copyPixelsToBuffer(baseBuffer);
        baseBuffer.rewind();

        return prevImg;
    }

    public void resetParams(){
        switch(parent.mode){
            case TONE:
                toneproc.setBrightness(0);
                toneproc.setContrast(0);
                toneproc.setGamma(0);
                break;
            case COLOR:
                colorproc.setHue(0);
                break;
        }
    }

    public void apply(){
        staticBufferp.rewind();
        bufferp.flip();
        staticBufferp.put(bufferp);
        bufferp.compact();
        staticBufferp.rewind();
        baseBuffer.rewind();
        switch(parent.mode){
            case TONE:
                toneproc.process(baseBuffer, w, h);
                break;
            case COLOR:
                colorproc.process(baseBuffer, w, h);
                break;
        }
        baseBuffer.rewind();
        baseImg.copyPixelsFromBuffer(baseBuffer);
    }

    public void process(){
        bufferp.rewind();
        switch(parent.mode){
            case TONE:
                toneproc.process(staticBufferp, bufferp, w_p, h_p);
                break;
            case COLOR:
                colorproc.process(staticBufferp, bufferp, w_p, h_p);
                break;
        }
        staticBufferp.rewind();
        bufferp.rewind();
        prevImg.copyPixelsFromBuffer(bufferp);
   }

}
