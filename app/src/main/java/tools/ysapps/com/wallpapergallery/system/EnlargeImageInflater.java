package tools.ysapps.com.wallpapergallery.system;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.ArrayList;

import tools.ysapps.com.wallpapergallery.R;
import tools.ysapps.com.wallpapergallery.activities.ActivityGallery;
import tools.ysapps.com.wallpapergallery.fragments.EnlargeFragment;

/**
 * Created by B.E.L on 23/07/2015.
 */
public class EnlargeImageInflater implements View.OnClickListener{

    int currrentPosition;
    ArrayList<String> paths;

    public  void inflate(final ActivityGallery context,@NonNull Fragment fragment,final ArrayList<String> paths,int currentPosition){
       /* int[] folderCellPosition = new int[2];
        clickedView.getLocationOnScreen(folderCellPosition);
        final float width = 0.7f * widthScreen;
        final float height = 0.7f * heightScreen;
        folderCellPosition[0] = widthScreen /2  - (folderCellPosition[0] + clickedView.getWidth() / 2);
        folderCellPosition[1] = heightScreen / 2 - (folderCellPosition[1] + clickedView.getHeight() / 2);
        position = currentPosition;
        AnimatorSet scaleAndTranslate = Utils.createTranslationTargetAndScaleTargetAnimations((View) clickedView.getParent(),  0, folderCellPosition[0], folderCellPosition[1], 3f);
        scaleAndTranslate.start();
        Picasso.with(context)
                .load(Uri.parse(ActivityGallery.ASSETS_PATH + paths[currentPosition]))
                .resize((int)width, (int)height)
                .centerCrop()
                .into((ImageView) clickedView);
*/
        final float width = 0.8f * ActivityGallery.screenWidth;
        final float height = 0.8f * ActivityGallery.screeHeight;
        /*MyViewPager viewPager = new MyViewPager(context);
        viewPager.setId(R.id.pager_enlarge);*/
        //FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) width, (int)height);
        //params.gravity = Gravity.CENTER;
        FrameLayout frameLayout = (FrameLayout) fragment.getView().findViewById(R.id.frame);
        View root = LayoutInflater.from(context).inflate(R.layout.enlarge_pager, frameLayout, false);
        MyViewPager viewPager = (MyViewPager) root.findViewById(R.id.pager_enlarge);
        frameLayout.addView(root);
        this.paths = paths;
        this.currrentPosition = currentPosition;
        EnlargePagerAdapter adapter = new EnlargePagerAdapter(fragment.getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition);
        viewPager.setOnPageChangeListener(adapter);
        context.viewPager.setPagingEnabled(false);

        context.enlargeFragment = fragment;
        Button setWallPaper = (Button)root.findViewById(R.id.set_wallPaper);
        setWallPaper.setOnClickListener(this);

    }

    public void collapse(ActivityGallery activity, Fragment fragment){
        FrameLayout frameLayout = ((FrameLayout) fragment.getView().findViewById(R.id.frame));
        GridView gridView = (GridView) frameLayout.findViewById(R.id.grid);
        Utils.fadeInGrid(gridView);
        MyViewPager viewPager = (MyViewPager) frameLayout.findViewById(R.id.pager_enlarge);
        viewPager.setAdapter(null);
        viewPager.removeAllViews();
        frameLayout.removeView(frameLayout.findViewById(R.id.enlarge_root));
        activity.viewPager.setPagingEnabled(true);
        activity.enlargeFragment = null;

    }

    @Override
    public void onClick(View v) {
        Utils.setBellWallPaper((ActivityGallery) v.getContext(), paths.get(currrentPosition));
    }


    class EnlargePagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {



        public EnlargePagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            return EnlargeFragment.newInstance(position, paths);
        }

        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currrentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
