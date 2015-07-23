package tools.ysapps.com.wallpapergallery.activities;

/**
 * Created by B.E.L on 07/12/2014.
 */

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import tools.ysapps.com.wallpapergallery.system.ExceptionHandler;
import tools.ysapps.com.wallpapergallery.R;

/**
 * Wallpaper picker for the Home application. User can choose from
 * a gallery of stock photos.
 */
public class ActivityWallPaper extends Activity implements View.OnClickListener {

    Button setWallpaper;
    ImageView wallPaper;
    private boolean mIsWallpaperSet;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wallpaper);
        setWallpaper = (Button)findViewById(R.id.set_wallPaper);
        wallPaper = (ImageView)findViewById(R.id.bel_wallPaper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsWallpaperSet = false;
    }


    /*
     * When using touch if you tap an image it triggers both the onItemClick and
     * the onTouchEvent causing the wallpaper to be set twice. Synchronize this
     * method and ensure we only set the wallpaper once.
     */
    public  void setBellWallPaper(View v) {
        if (mIsWallpaperSet) {
            return;
        }
        mIsWallpaperSet = true;
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(this);
        try {
            int height = myWallpaperManager.getDesiredMinimumHeight();
            int width = myWallpaperManager.getDesiredMinimumWidth();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.wallpaper_bitmap);
            if (bitmapDrawable != null)
                myWallpaperManager.setBitmap(Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(), width, height, true));
            setResult(RESULT_OK);
            finish();
        } catch (IOException |  RuntimeException e) {
            ExceptionHandler.handleException(e);
        }

    }

    @Override
    public void onClick(View view) {
        setBellWallPaper(view);
    }
}