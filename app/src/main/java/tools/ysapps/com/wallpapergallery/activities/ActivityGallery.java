package tools.ysapps.com.wallpapergallery.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import tools.ysapps.com.wallpapergallery.R;
import tools.ysapps.com.wallpapergallery.fragments.GalleryFragment;
import tools.ysapps.com.wallpapergallery.fragments.LikesFragment;
import tools.ysapps.com.wallpapergallery.system.EnlargeImageInflater;
import tools.ysapps.com.wallpapergallery.system.MyViewPager;
import tools.ysapps.com.wallpapergallery.system.Utils;

/**
 * Created by yshahak on 22/07/2015.
 */
public class ActivityGallery extends FragmentActivity implements View.OnClickListener {

    final String ASSETS_FOLDER_NAME = "wallpaper";

    //public static final String EXTRA_LIKE = "extraLike";
    public final static String ASSETS_PATH = "file:///android_asset/wallpaper/";
    public static String EXTRA_LIKE_SUFFIX = "extraLikeSuffix";
    public static int screenWidth;
    public static int screeHeight;
    String[] titles = {"Gallery", "Likes"};
    public String[] galleryItems;

    public Fragment enlargeFragment;

    public MyViewPager viewPager;
    public SharedPreferences pref;
    public ArrayList<Integer> indexes;
    public EnlargeImageInflater enlargeInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        galleryItems = Utils.getGalleryItemsFromAssets(this, ASSETS_FOLDER_NAME);

        indexes = Utils.retrieveLikedItems(pref, galleryItems);
        viewPager = (MyViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new GalleryPagerAdapter(getSupportFragmentManager()));
        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screeHeight = display.getHeight();
        enlargeInflater = new EnlargeImageInflater();
    }

    @Override
    public void onBackPressed() {
        if (enlargeFragment != null){
            enlargeInflater.collapse(this, enlargeFragment);
        } else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();;
        boolean like = ((ImageView)v).getDrawable().getLevel() == 1;
        if (like) {
            indexes.remove((Integer)position);
            ((ImageView) v).setImageLevel(0);
        } else {
            indexes.add(position);
            ((ImageView) v).setImageLevel(1);
        }
        pref.edit().putBoolean(EXTRA_LIKE_SUFFIX + Integer.toString(position), !like).apply();
        LikesFragment likesFragment = (LikesFragment)getSupportFragmentManager().findFragmentByTag(LikesFragment.getFragmentTag(viewPager.getId(), 1));
        likesFragment.refreshData();
    }


    class GalleryPagerAdapter extends FragmentPagerAdapter{

        public GalleryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return GalleryFragment.newInstance();
                default:
                    return LikesFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
