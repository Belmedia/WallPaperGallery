package tools.ysapps.com.wallpapergallery.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import tools.ysapps.com.wallpapergallery.R;
import tools.ysapps.com.wallpapergallery.activities.ActivityGallery;
import tools.ysapps.com.wallpapergallery.system.Utils;

/**
 * Created by yshahak on 22/07/2015.
 */
public class GalleryFragment extends android.support.v4.app.Fragment {
    final String ASSETS_FOLDER_NAME = "wallpaper";
    String[] galleryItems;
    Context context;
    GridView.LayoutParams params;
    SharedPreferences pref;
    private GridView gridView;
    private GalleryGridAdapter adapter;


    public static GalleryFragment newInstance(){
        return new GalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        params = new GridView.LayoutParams(((ActivityGallery.screenWidth / 4)), ((ActivityGallery.screeHeight / 4)));
        pref = ((ActivityGallery)context).pref;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        gridView = (GridView) root.findViewById(R.id.grid);
        galleryItems = Utils.getGalleryItemsFromAssets(context, ASSETS_FOLDER_NAME);
        adapter = new GalleryGridAdapter();
        gridView.setAdapter(adapter);
        return root;
    }

    class GalleryGridAdapter extends BaseAdapter implements View.OnClickListener{
        ArrayList<View> cells = new ArrayList<>();

        @Override
        public int getCount() {
            return galleryItems.length;
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
                viewHolder = new ViewHolder(this, (ImageView)convertView.findViewById(R.id.wall_paper_item), (ImageView)convertView.findViewById(R.id.love_item));
                convertView.setTag(viewHolder);
                convertView.setLayoutParams(params);
                cells.add(convertView);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.path = ActivityGallery.ASSETS_PATH + galleryItems[position];
            viewHolder.position = position;
            viewHolder.itemLoveIcon.setTag(position);
            boolean like = pref.getBoolean(ActivityGallery.EXTRA_LIKE_SUFFIX + Integer.toString(position), false);
            if (like)
                viewHolder.itemLoveIcon.setImageLevel(1);
            else
                viewHolder.itemLoveIcon.setImageLevel(0);
            Picasso.with(context)
                    .load(Uri.parse(viewHolder.path))
                            .resize(params.width, params.height)
                            .centerCrop()
                            .into(viewHolder.itemWallPaper);
            return convertView;
        }

        @Override
        public void onClick(View v) {
            ViewHolder viewHolder  = (ViewHolder) ((View)v.getParent()).getTag();
            Utils.fadeOutGrid(gridView);
            enlargeImage(viewHolder);
        }
    }

    private void enlargeImage(ViewHolder viewHolder) {
        ((ActivityGallery)context).enlargeInflater.inflate((ActivityGallery) context, this, new ArrayList<>(Arrays.asList(galleryItems)), viewHolder.position);

    }

    class ViewHolder{
        ImageView itemWallPaper;
        ImageView itemLoveIcon;
        String path;
        int position;

        public ViewHolder(GalleryGridAdapter galleryGridAdapter, ImageView itemWallPaper, ImageView itemLoveIcon) {
            this.itemWallPaper = itemWallPaper;
            this.itemLoveIcon = itemLoveIcon;
            itemLoveIcon.setOnClickListener(((ActivityGallery)context));
            itemWallPaper.setOnClickListener(galleryGridAdapter);
        }
    }



    public  void refreshData(){
        adapter.notifyDataSetChanged();
    }
}
