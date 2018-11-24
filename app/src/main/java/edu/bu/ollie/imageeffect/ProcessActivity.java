package edu.bu.ollie.imageeffect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ProcessActivity extends AppCompatActivity implements ChangeDialog.ChangeDialogListener, BackDialog.BackDialogListener{

    protected FragmentManager fragManager;
    protected GlobalState.EffectMode mode;
    Bitmap baseImage;
    public ProcImageFragment procViewFragment;
    ImageZoomFragment zoomFragment;
    public TestProcessor testprocessor;
    RequestOptions glideOptions;
    boolean imgModified;
    BackDialog backDialog;

    public ProcessActivity(){
        mode = GlobalState.EffectMode.values()[0];
        procViewFragment = new ProcImageFragment();
        testprocessor = new TestProcessor();
        glideOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        imgModified = false;
    }

    // process image
    protected void procImg(){
        testprocessor.process();
        imgModified = true;
    }

    // start processor view
    protected void toProcView(){
        Bitmap globImage = BitmapFactory.decodeFile(GlobalState.imagePaths.get(GlobalState.currentIndex));
        baseImage = globImage.copy( Bitmap.Config.ARGB_8888 , true);

        testprocessor.loadImage(baseImage);
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

    @Override // back dialog if imgmodified and zommfragment active
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

    // placeholder function
    public void save(){
        // ...
        imgModified = false;
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
      save();
    }

    @Override
    public void onBackDialogNegativeClick(DialogFragment dialog) {
        super.onBackPressed();
    }
}
