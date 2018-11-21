package edu.bu.ollie.imageeffect;

import android.graphics.Bitmap;

import java.nio.IntBuffer;

import edu.bu.ollie.imageeffect.image.BrightnessProcessor;
import edu.bu.ollie.imageeffect.image.ContrastProcessor;

public class TestProcessor {

    Bitmap baseImg;
    IntBuffer buffer;
    int w, h, size;
    ContrastProcessor proc;

    TestProcessor(){
        proc = new ContrastProcessor();
        proc.setParam(30);
    }

    void loadImage(Bitmap img){
        baseImg = img;
        w = img.getWidth();
        h = img.getHeight();
        size = w*h;
        buffer = IntBuffer.allocate(size);
        baseImg.copyPixelsToBuffer(buffer);
        buffer.rewind();
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

    void process(){
       // Log.i("START_BUFFER_LIMIT", ""+buffer.limit()+" size: "+size+" pos: "+buffer.position()+" remaining: "+buffer.remaining());
        buffer.rewind();
        proc.process(buffer, w, h);
        buffer.rewind();
        baseImg.copyPixelsFromBuffer(buffer);
   }

}
