package tools.ysapps.com.wallpapergallery;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;

/**
 * Created by yshahak on 22/07/2015.
 */
public class Utils {

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
}
