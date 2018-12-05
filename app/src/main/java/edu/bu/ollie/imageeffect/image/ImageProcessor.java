package edu.bu.ollie.imageeffect.image;

import android.graphics.Bitmap;
import java.nio.IntBuffer;
import edu.bu.ollie.imageeffect.Global;

public class ImageProcessor {

    Bitmap baseImg, prevImg;
    IntBuffer bufferp, staticBufferp, baseBuffer;
    int w_p, h_p, size_p, w, h, size;
    ToneProcessor toneproc;

    public ImageProcessor(){
        toneproc = new ToneProcessor();
    }

    public void setToneBrightness(int b){ toneproc.setBrightness(b);}
    public void setToneContrast(int c){toneproc.setContrast(c);}
    public void setToneGamma(int g){toneproc.setGamma(g);}


    public Bitmap loadImage(Bitmap img){
        baseImg = img;
        w = img.getWidth();
        h = img.getHeight();
        size = w*h;

        prevImg = Bitmap.createScaledBitmap(img, Global.preview_width, Math.round(h*(Global.preview_width/(float)w)), false);
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
        toneproc.setBrightness(0);
        toneproc.setContrast(0);
        toneproc.setGamma(0);
    }

    public void apply(){
        staticBufferp.rewind();
        bufferp.flip();
        staticBufferp.put(bufferp);
        bufferp.compact();
        staticBufferp.rewind();
        baseBuffer.rewind();
        toneproc.process(baseBuffer, w, h);
        baseBuffer.rewind();
        baseImg.copyPixelsFromBuffer(baseBuffer);
    }

    public void process(){
        bufferp.rewind();
        toneproc.process(staticBufferp, bufferp, w_p, h_p);
        staticBufferp.rewind();
        bufferp.rewind();
        prevImg.copyPixelsFromBuffer(bufferp);
   }

}
