package edu.bu.ollie.imageeffect.image;

import java.nio.IntBuffer;

public class Convolver {

    public static float[][] blur = new float[][]{{1, 1, 1},{1, 1, 1},{1, 1, 1}};
    public static float[][] lblur = new float[][]{{1, 1, 1, 1, 1},{1, 1, 1, 1, 1},{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
    public static float[][] gblur = new float[][]{{1, 2, 1},{2, 4, 2},{1, 2, 1}};
    public static float[][] edge = new float[][]{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
    public static float[][] sharp = new float[][]{{0,-1,0},{-1,5,-1},{0,-1,0}};


    void process(IntBuffer inbuff, IntBuffer outbuff, int w, int h, float[][] kernel){
        float sum = sum(kernel);
        int msize = kernel[0].length;
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                outbuff.put(convolve(inbuff, y, x, h, w, kernel, msize, sum));
            }
        }
    }

    void process(IntBuffer buffer, int w, int h, float[][] kernel){
        float sum = sum(kernel);
        int msize = kernel[0].length;
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                buffer.put(convolve(buffer, y, x, h, w, kernel, msize, sum));
            }
        }
    }

    static int convolve(IntBuffer img, int x, int y, int w, int h, float[][] mat, int matsize, float sum){
        float r = 0, g = 0, b = 0;
        int a = 255;
        int offs = matsize/2;
        int c;
        for (int i = 0; i < matsize; i++){
            for (int j = 0; j < matsize; j++){
                c = img.get(toIndex(constrain(x+(i-offs),0,w-1), constrain(y+(j-offs),0,h-1), w));
                a = c >> 24;
                r += ((c >> 16 & 0xff) * mat[i][j]);
                g += ((c >> 8 & 0xff) * mat[i][j]);
                b += ((c  & 0xff) * mat[i][j]);
            }
        }
        r = constrain((int)(r/sum),0,255);
        g = constrain((int)(g/sum),0,255);
        b = constrain((int)(b/sum),0,255);

       return a << 24 | (((int)r) << 16) | (((int)g) << 8) | (int)b;
    }

    private static int constrain(int in, int min, int max){
        return in < min ? 0 : in > max ? max : in;
    }

    private static int toIndex(int x, int y, int w){
        return y*w+x;
    }

    private float sum(float[][] m){
        int l1 = m[0].length;
        int l2 = m[1].length;
        float rtn = 0;
        for(int i = 0; i < l1; i++){
            for(int j = 0; j < l2; j++){
                rtn += m[i][j];
            }
        }
        if(rtn < 1 && rtn > -1){ rtn = 1; }
        return rtn;
    }
}
