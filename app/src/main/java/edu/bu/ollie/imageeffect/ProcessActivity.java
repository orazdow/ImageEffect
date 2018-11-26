package edu.bu.ollie.imageeffect;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.bu.ollie.imageeffect.image.ImageProcessor;

public class ProcessActivity extends AppCompatActivity implements ChangeDialog.ChangeDialogListener, BackDialog.BackDialogListener{

    protected FragmentManager fragManager;
    protected Global.EffectMode mode;
    Bitmap baseImage;
    File img;
    public ProcImageFragment procViewFragment;
    ImageZoomFragment zoomFragment;
    public ImageProcessor processor;
    RequestOptions glideOptions;
    boolean imgModified;
    BackDialog backDialog;

    public ProcessActivity(){
        mode = Global.EffectMode.values()[0];
        procViewFragment = new ProcImageFragment();
        processor = new ImageProcessor();
        glideOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        imgModified = false;
    }

    protected void apply(){
        processor.apply();
        imgModified = true;
    }

    // start processor view
    protected void toProcView(){
        img = new File(Global.imagePaths.get(Global.currentIndex));
        Bitmap globImage = BitmapFactory.decodeFile(img.getAbsolutePath());
        if(!imgModified) {
            baseImage = globImage.copy(Bitmap.Config.ARGB_8888, true);
            processor.loadImage(baseImage);
        }
        FragmentTransaction transaction = fragManager.beginTransaction();
        transaction.replace(R.id.procMainView, procViewFragment);
        transaction.addToBackStack( procViewFragment.toString());
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        fragManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragManager.beginTransaction();
        zoomFragment = new ImageZoomFragment();
        backDialog = new BackDialog();
        transaction.replace(R.id.procMainView, zoomFragment, "zoom_fragment");
        transaction.commit();
    }

    @Override // back dialog if imgmodified and zoomfragment active
    public void onBackPressed(){
        //https://stackoverflow.com/questions/9294603/get-currently-displayed-fragment?answertab=votes#tab-top
        ImageZoomFragment frag = (ImageZoomFragment)fragManager.findFragmentByTag("zoom_fragment");
        if(frag != null && frag.isVisible()){
            if(!imgModified){
                super.onBackPressed();
            }else {
                backDialog.show(fragManager, "back_dialog");
            }
        }else {
            super.onBackPressed();
        }

    }

    String newName(String in){
        String str = in.substring(0, in.lastIndexOf('.'));
        int index = str.indexOf("_imageEffect_");
        if(index < 0){
            str = str.concat("_imageEffect_0.jpg");

        }else {
            Integer i = Integer.parseInt(str.substring(index+13))+1;
            str = str.substring(0, index);
            str = str.concat("_imageEffect_"+i.toString()+".jpg");
        }
        return str;
    }

    // https://stackoverflow.com/questions/21258221/how-to-create-an-app-image-folder-to-show-in-android-gallery
    public String save(){
        if(!imgModified){return "nochange";}
        String filename = newName(img.getName());
        File saveDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ImageEffect");
        if(!saveDir.exists()){saveDir.mkdirs();}
        File saveFile = new File((saveDir.getPath() + File.separator +filename));
        try {
            if (saveFile == null) {
                throw new FileNotFoundException("error saving to path");
            }
            FileOutputStream fos = new FileOutputStream(saveFile);
            baseImage.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();

            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, saveFile.getName());
            values.put(MediaStore.Images.Media.DATE_ADDED, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
            values.put(MediaStore.Images.Media.DESCRIPTION, "saved from ImageEffect");
            values.put(MediaStore.Images.Media.BUCKET_ID, saveFile.toString().hashCode());
            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, saveFile.getName());
            values.put("_data", saveFile.getAbsolutePath());
            cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            //MediaStore.Images.Media.insertImage(cr,saveFile.getAbsolutePath(),saveFile.getName(), "saved from ImageEffect");

        }catch (Exception e){
            Log.i("SAVE_FILE", "ERROR SAVING TO DIR: "+e.getMessage());
            return "null";
        }
        imgModified = false;
        return filename;
    }

    // swipe, back unsaved img dialogs:
    @Override
    public void onChangeDialogPositiveClick(DialogFragment dialog) {
        zoomFragment.changeImg();
    }

    @Override
    public void onChangeDialogNegativeClick(DialogFragment dialog) {
        // donothing
    }

    @Override
    public void onBackDialogPositiveClick(DialogFragment dialog) {
      imgModified = false;
    }

    @Override
    public void onBackDialogNegativeClick(DialogFragment dialog) {
        super.onBackPressed();
    }
}
