package tools.ysapps.com.wallpapergallery.system;

import android.app.Application;


/**
 * Created by yshahak on 07/01/2015.
 */
public class MyApplication extends Application {
    final String TRIM = "TRIM_MEMORY";
    final String CRITTERCISEM_APP_ID = "557dc677d247870a1a30a521";
    //final String MINT_API_KEY = "9250e064";
    //final String FLARRY = "DZ54NXYY29XMW8XDMCC8";
    //private MainActivity mCurrentActivity = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
       // FlurryAgent.setLogEnabled(false);
        //FlurryAgent.init(this, FLARRY);
        if (ExceptionHandler.enableReporting) {
            //Fabric.with(this, new Crashlytics());
        }
    }



   /* public MainActivity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(MainActivity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }


*/
}