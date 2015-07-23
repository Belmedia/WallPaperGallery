package tools.ysapps.com.wallpapergallery.system;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;

import tools.ysapps.com.wallpapergallery.activities.ActivityGallery;

/**
 * Created by yshahak on 22/07/2015.
 */
public class Utils {

    private static final long ANIMATION_DURATION = 200;

    public static String[]  getGalleryItemsFromAssets(Context context,String dirFrom) {
        Resources res = context.getResources(); //if you are in an activity
        AssetManager am = res.getAssets();
        try {
            return am.list(dirFrom);
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static AnimatorSet resizeAnimation(View view,float fromScale, float toScale, long duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", fromScale, toScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", fromScale, toScale);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(scaleX, scaleY);
        animSetXY.setDuration(duration);
        animSetXY.setInterpolator(new AccelerateDecelerateInterpolator());
        return animSetXY;
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static  AnimatorSet createTranslationTargetAndScaleTargetAnimations(View view, long delay, float endX, float endY, float toScale) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", endX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", endY);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX",toScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", toScale);

        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY, scaleX, scaleY);
        animSetXY.setDuration(ANIMATION_DURATION);
        animSetXY.setStartDelay(delay);
        return animSetXY;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static  ObjectAnimator createAlphaAnimations(View view,long delay, float fromScale, float toScale) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", fromScale, toScale);
        alpha.setDuration(ANIMATION_DURATION);
        alpha.setStartDelay(delay);
        return alpha;
    }

    public static void fadeOut(ArrayList<View> cells, int position){
        for (int i = 0 ; i < cells.size(); i++){
            if (i != position)
                createAlphaAnimations(cells.get(i), 0, 1f, 0f).start();
        }
    }

    public static void fadeOutGrid(GridView gridView){
        createAlphaAnimations(gridView, 0, 1f, 0f).start();
    }

    public static void fadeInGrid(GridView gridView){
        createAlphaAnimations(gridView, 0, 0f, 1f).start();
    }

    public static ArrayList<Integer> retrieveLikedItems(SharedPreferences pref, String[] galleryItems) {
        ArrayList<Integer> liked = new ArrayList<>();
        for (int i = 0 ; i < galleryItems.length; i++){
            Boolean like = pref.getBoolean(ActivityGallery.EXTRA_LIKE_SUFFIX + Integer.toString(i), false);
            if (like)
                liked.add(i);
        }
        return liked;
    }

    public static ArrayList<String> createArrayFromIndexes(String[] galleryItems, ArrayList<Integer> indexes) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Integer i : indexes)
            arrayList.add(galleryItems[i]);
        return arrayList;
    }

    /*
     * When using touch if you tap an image it triggers both the onItemClick and
     * the onTouchEvent causing the wallpaper to be set twice. Synchronize this
     * method and ensure we only set the wallpaper once.
     */
    public  static void setBellWallPaper(ActivityGallery activity, String path) {
        final WallpaperManager myWallpaperManager = WallpaperManager.getInstance(activity);
        int height = myWallpaperManager.getDesiredMinimumHeight();
        int width = myWallpaperManager.getDesiredMinimumWidth();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(Uri.parse(ActivityGallery.ASSETS_PATH + path)));

            myWallpaperManager.setBitmap(Bitmap.createScaledBitmap(bitmap, width, height, true));
            activity.setResult(Activity.RESULT_OK);
            activity.enlargeInflater.collapse(activity, activity.enlargeFragment);
        } catch (IOException |  RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

}
