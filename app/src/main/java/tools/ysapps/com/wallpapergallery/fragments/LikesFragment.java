package tools.ysapps.com.wallpapergallery.fragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tools.ysapps.com.wallpapergallery.R;
import tools.ysapps.com.wallpapergallery.activities.ActivityGallery;
import tools.ysapps.com.wallpapergallery.system.Utils;

/**
 * Created by yshahak on 22/07/2015.
 */
public class LikesFragment extends android.support.v4.app.Fragment {
    String[] galleryItems;
    ActivityGallery context;
    GridView.LayoutParams params;
    ArrayList<Integer> indexes = new ArrayList<>();
    SharedPreferences pref;
    private GalleryGridAdapter adapter;
    private GridView gridView;


    public static LikesFragment newInstance(){
        return new LikesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (ActivityGallery)getActivity();
        pref = context.pref;
        Display display = context.getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screeHeight = display.getHeight();
        params = new GridView.LayoutParams(((screenWidth / 4)), ((screeHeight / 4)));
        galleryItems = context.galleryItems;
        indexes = context.indexes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        gridView = (GridView) root.findViewById(R.id.grid);
        adapter = new GalleryGridAdapter();
        gridView.setAdapter(adapter);
        return root;
    }

    class GalleryGridAdapter extends BaseAdapter implements View.OnClickListener{

        @Override
        public void notifyDataSetInvalidated() {
            super.notifyDataSetInvalidated();
        }


        @Override
        public int getCount() {
            return indexes.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_cell_item, parent, false);
                viewHolder = new ViewHolder((ImageView)convertView.findViewById(R.id.wall_paper_item), (ImageView)convertView.findViewById(R.id.love_item));
                convertView.setTag(viewHolder);
                convertView.setLayoutParams(params);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            String path = galleryItems[indexes.get(position)];
            viewHolder.itemLoveIcon.setTag(indexes.get(position));
            viewHolder.position = position;
            Picasso.with(context)
                    .load(Uri.parse(ActivityGallery.ASSETS_PATH + path))
                    .resize(params.width, params.height)
                    .centerCrop()
                    .into(viewHolder.itemWallPaper);
            return convertView;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.wall_paper_item){
                ViewHolder viewHolder  = (ViewHolder) ((View)v.getParent()).getTag();
                Utils.fadeOutGrid(gridView);
                enlargeImage(viewHolder.position);
            } else {
                int position = (int) v.getTag();
                indexes.remove((Integer) position);

                pref.edit().putBoolean(ActivityGallery.EXTRA_LIKE_SUFFIX + Integer.toString(position), false).apply();
                notifyDataSetChanged();
                GalleryFragment fragment = (GalleryFragment) context.getSupportFragmentManager().findFragmentByTag(LikesFragment.getFragmentTag(context.viewPager.getId(), 0));
                fragment.refreshData();
            }
        }
    }

    public  void refreshData(){
        adapter.notifyDataSetChanged();
    }

    public static String getFragmentTag(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    class ViewHolder{
        ImageView itemWallPaper;
        ImageView itemLoveIcon;
        int position;

        public ViewHolder(ImageView itemWallPaper, ImageView itemLoveIcon) {
            this.itemWallPaper = itemWallPaper;
            this.itemLoveIcon = itemLoveIcon;
            itemLoveIcon.setImageLevel(1);
            itemLoveIcon.setOnClickListener(adapter);
            itemWallPaper.setOnClickListener(adapter);
        }
    }

    private void enlargeImage(int position) {
        ArrayList<String> converted = Utils.createArrayFromIndexes(galleryItems, indexes);
        context.enlargeInflater.inflate(context, this, converted, position);

    }
}
