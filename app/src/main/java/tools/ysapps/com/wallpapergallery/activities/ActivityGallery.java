package tools.ysapps.com.wallpapergallery.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import tools.ysapps.com.wallpapergallery.GalleryFragment;
import tools.ysapps.com.wallpapergallery.R;

/**
 * Created by yshahak on 22/07/2015.
 */
public class ActivityGallery extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new GalleryPagerAdapter(getSupportFragmentManager()));
    }

    class GalleryPagerAdapter extends FragmentStatePagerAdapter{

        public GalleryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Gallery";
        }

        @Override
        public Fragment getItem(int position) {
            return GalleryFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
