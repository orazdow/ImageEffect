package edu.bu.ollie.imageeffect.image;

import android.graphics.Bitmap;
import java.nio.IntBuffer;

public class ImageProcessor {

    Bitmap baseImg;
    IntBuffer buffer, cleanBuffer;
    int w, h, size;
    ToneProcessor toneproc;

    public ImageProcessor(){
        toneproc = new ToneProcessor();
    }

    public void setToneBrightness(int b){ toneproc.setBrightness(b);}
    public void setToneContrast(int c){toneproc.setContrast(c);}
    public void setToneGamma(int g){toneproc.setGamma(g);}


    public void loadImage(Bitmap img){
        baseImg = img;
        w = img.getWidth();
        h = img.getHeight();
        size = w*h;
        buffer = IntBuffer.allocate(size);
        baseImg.copyPixelsToBuffer(buffer);
        buffer.rewind();
        cleanBuffer = IntBuffer.allocate(size);
        baseImg.copyPixelsToBuffer(cleanBuffer);
        cleanBuffer.rewind();
    }

    public void resetParams(){
        toneproc.setBrightness(0);
        toneproc.setContrast(0);
        toneproc.setGamma(0);
    }

    public void process(){
        buffer.rewind();
        toneproc.process(cleanBuffer, buffer, w, h);
        cleanBuffer.rewind();
        buffer.rewind();
        baseImg.copyPixelsFromBuffer(buffer);
   }

}
