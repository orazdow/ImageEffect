package edu.bu.ollie.imageeffect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected FragmentManager fragManager;
    protected ArrayList<String> imagePaths;
    protected static final int PERMISSION_RTN = 1;
    protected boolean mediaLoaded = false;

    public MainActivity(){
        imagePaths = Global.imagePaths;
    }

    protected void zoomImage(int index){
        Global.currentIndex = index;
        Intent intent = new Intent(MainActivity.this, ProcessActivity.class);
        startActivity(intent);
    }

    protected void loadMedia(){
        Log.d("MAIN_CREATE:","LOADING MEDIA");
        imagePaths.clear();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] selector = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, selector,
                null, null, MediaStore.Images.Media.DATE_TAKEN+" DESC");
        Cursor cursor = loader.loadInBackground();
        int len = cursor.getCount();
        for(int i = 0; i < len; i++){
            cursor.moveToPosition(i);
            int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            imagePaths.add(cursor.getString(index));
        }
        mediaLoaded = true;
    }


    protected void startGallery(){
        loadMedia();
        FragmentTransaction transaction = fragManager.beginTransaction();
        transaction.replace(R.id.mainView, new GalleryFragment());
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        startGallery();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragManager = getSupportFragmentManager();
        boolean zoom = getIntent().getBooleanExtra("zoom", false);
        if(zoom){
            if(!mediaLoaded){
                loadMedia();
            }
            zoomImage(Global.currentIndex);

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_RTN);
        }else {
            if(!mediaLoaded)
                startGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode) {
            case PERMISSION_RTN: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSION_GRANTED", "LOADING_MEDIA");
                    if(!mediaLoaded){
                        startGallery();
                    }
                } else {
                    Log.i("PERMISSION_CALLBACK", "DENIED");
                }
                return;
            }

        }
    }
}
