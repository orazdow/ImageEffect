package edu.bu.ollie.imageeffect;

import android.graphics.Bitmap;

import java.nio.IntBuffer;

import edu.bu.ollie.imageeffect.image.BrightnessProcessor;
import edu.bu.ollie.imageeffect.image.ContrastProcessor;
import edu.bu.ollie.imageeffect.image.ToneProcessor;

public class TestProcessor {

    Bitmap baseImg;
    IntBuffer buffer, cleanBuffer;
    int w, h, size;
    ToneProcessor toneproc;

    TestProcessor(){
        toneproc = new ToneProcessor();
       // toneproc.setParams(-40,30,30);
    }

    public void setToneBrightness(int b){ toneproc.setBrightness(b);}
    public void setToneContrast(int c){toneproc.setContrast(c);}
    public void setToneGamma(int g){toneproc.setGamma(g);}


    void loadImage(Bitmap img){
        baseImg = img;
        w = img.getWidth();
        h = img.getHeight();
        size = w*h;
        buffer = IntBuffer.allocate(size);
        baseImg.copyPixelsToBuffer(buffer);
        buffer.rewind();
//        savebuffer = IntBuffer.allocate(size);
//        baseImg.copyPixelsToBuffer(savebuffer);
//        savebuffer.rewind();
        cleanBuffer = IntBuffer.allocate(size);
        baseImg.copyPixelsToBuffer(cleanBuffer);
        cleanBuffer.rewind();
    }

    int darken(int c, int amt){
       int a = c >> 24;
       int r = c >> 16 & 0xff;
       int g = c >> 8 & 0xff;
       int b = c & 0xff;

       r = r >= amt? r-amt : 0;
       g = g >= amt? g-amt : 0;
       b = b >= amt? b-amt : 0;

       return a << 24 | r << 16 | g << 8 | b;
    }

    public void preview(){
        buffer.rewind();
        toneproc.process(cleanBuffer, buffer, w, h);
        cleanBuffer.rewind();
        buffer.rewind();
        baseImg.copyPixelsFromBuffer(buffer);
    }

    public void process(){
       // Log.i("START_BUFFER_LIMIT", ""+buffer.limit()+" size: "+size+" pos: "+buffer.position()+" remaining: "+buffer.remaining());
        buffer.rewind();
        toneproc.process(cleanBuffer, buffer, w, h);
        cleanBuffer.rewind();
        buffer.rewind();
        baseImg.copyPixelsFromBuffer(buffer);
   }

}
