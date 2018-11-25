package edu.bu.ollie.imageeffect.image;

import android.graphics.Bitmap;
import java.nio.IntBuffer;

public class ImageProcessor {

    Bitmap baseImg;
    IntBuffer buffer, staticBuffer;
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
        staticBuffer = IntBuffer.allocate(size);
        baseImg.copyPixelsToBuffer(staticBuffer);
        staticBuffer.rewind();
    }

    public void resetParams(){
        toneproc.setBrightness(0);
        toneproc.setContrast(0);
        toneproc.setGamma(0);
    }

    public void apply(){
        buffer.flip();
        staticBuffer.put(buffer);
        buffer.compact();
        staticBuffer.rewind();
        baseImg.copyPixelsFromBuffer(staticBuffer);
    }

    public void process(){
        buffer.rewind();
        toneproc.process(staticBuffer, buffer, w, h);
        staticBuffer.rewind();
        buffer.rewind();
        baseImg.copyPixelsFromBuffer(buffer);
   }

}
