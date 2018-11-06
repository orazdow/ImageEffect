package edu.bu.ollie.imageeffect;

import android.graphics.Bitmap;

import java.nio.IntBuffer;
import java.util.Stack;

public class ImageProcessor {

    Bitmap baseImg;
    Stack<IntBuffer> stack;
    int w, h;
    boolean frameModified = false;

    void loadImage(Bitmap img){
        baseImg = img;
        w = img.getWidth();
        h = img.getHeight();
        stack = new Stack<>();
        // bottom of stack is base buffer
        IntBuffer buff = IntBuffer.allocate(w*h);
        baseImg.copyPixelsToBuffer(buff);
        // top of stack is next copy
        stack.push(buff);
    }
    /*
     todo:
     -process udpate modes: onapply or onsliderchange
     -handle ui imgae update
     -implement undochanges warning on swipe or back if modified in zoom fragment
     */

    void process(){
        // ...
        // process using callback object or default
        // frameModified = true;
    }


    void apply(){
        // push new frame
        if(frameModified) {
            IntBuffer buff = cloneIntBuffer(stack.peek());
            stack.push(buff);
            frameModified = false;
        }
    }


    // https://stackoverflow.com/a/21388198/5455902
    public IntBuffer cloneIntBuffer(final IntBuffer original) {
        final IntBuffer clone = IntBuffer.allocate(original.capacity());
        final IntBuffer readOnlyCopy = original.asReadOnlyBuffer();
        readOnlyCopy.flip();
        clone.put(readOnlyCopy);
        return clone;
    }

    //void process(){}

    int toIndex(int x, int y){
        return y*w+x;
    }


}
