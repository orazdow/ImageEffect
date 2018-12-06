package edu.bu.ollie.imageeffect.image;

import java.nio.IntBuffer;

public class ConvProcessor extends Processor {

    Convolver convolver = new Convolver();

    @Override
    public void process(IntBuffer buffer, int w, int h) {
        convolver.process(buffer, h, w, Convolver.lblur);
    }

    @Override
    public void process(IntBuffer inbuffer, IntBuffer outbuffer, int w, int h) {
             convolver.process(inbuffer, outbuffer, h, w, Convolver.lblur);
    }

    @Override
    public void setParam(String key, double value) {

    }

    @Override
    public void setParam(double value) {

    }
}
