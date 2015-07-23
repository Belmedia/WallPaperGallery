package tools.ysapps.com.wallpapergallery.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tools.ysapps.com.wallpapergallery.R;
import tools.ysapps.com.wallpapergallery.activities.ActivityGallery;

/**
 * Created by B.E.L on 23/07/2015.
 */
public class EnlargeFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private static final String EXTRA_POSITION = "extraPosition";
    private static final String EXTRA_ARRAY = "extraArray";

    private String path;
    private int position;

    public static EnlargeFragment newInstance(int position, ArrayList<String> list) {
        EnlargeFragment fragment = new EnlargeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(EXTRA_ARRAY, list);
        args.putInt(EXTRA_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    ActivityGallery context;
    ImageView image;
    ViewGroup.LayoutParams params;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (ActivityGallery) getActivity();
        params =  new ViewGroup.LayoutParams((int)(0.7f * ActivityGallery.screenWidth), (int)(0.7f * ActivityGallery.screeHeight));
        Bundle bundle = getArguments();
        position = bundle.getInt(EXTRA_POSITION);
        path = bundle.getStringArrayList(EXTRA_ARRAY).get(position);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.gallery_cell_item, container, false);
        image = (ImageView) root.findViewById(R.id.wall_paper_item);
        ImageView itemLoveIcon = (ImageView) root.findViewById(R.id.love_item);
        itemLoveIcon.setVisibility(View.GONE);
       /* FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) itemLoveIcon.getLayoutParams();
        if (ActivityGallery.screenWidth > ActivityGallery.screeHeight) {
            params.width = (int) (ActivityGallery.screeHeight * 0.1f);
            params.height = (int) (ActivityGallery.screeHeight * 0.1f);
        } else {
            params.width = (int) (ActivityGallery.screenWidth * 0.1f);
            params.height = (int) (ActivityGallery.screenWidth * 0.1f);
        }
        itemLoveIcon.setLayoutParams(params);
        //itemLoveIcon.setOnClickListener(context);
        itemLoveIcon.setTag(context.pref.getBoolean(ActivityGallery.EXTRA_LIKE_SUFFIX + Integer.toString(position), false));*/
        //params = image.getLayoutParams();
        Picasso.with(context)
                .load(Uri.parse(ActivityGallery.ASSETS_PATH + path))
                .resize(this.params.width, this.params.height)
                .centerCrop()
                .into(image);
        return root;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
