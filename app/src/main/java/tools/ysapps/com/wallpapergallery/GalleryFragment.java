package tools.ysapps.com.wallpapergallery;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

/**
 * Created by yshahak on 22/07/2015.
 */
public class GalleryFragment extends android.support.v4.app.Fragment {
    final String ASSETS_FOLDER_NAME = "wallpaper";
    private final String ASSETS_PATH = "file:///android_asset/wallpaper/";
    String[] galleryItems;
    Context context;
    GridView.LayoutParams params;


    public static GalleryFragment newInstance(){
        return new GalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screeHeight = display.getHeight();
        params = new GridView.LayoutParams(((screenWidth / 4)), ((screeHeight / 4)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        GridView gridView = (GridView) root.findViewById(R.id.grid);
        galleryItems = Utils.getGalleryItemsFromAssets(context, ASSETS_FOLDER_NAME);
        gridView.setAdapter(new GalleryGridAdapter());
        return root;
    }

    class GalleryGridAdapter extends BaseAdapter implements View.OnClickListener{


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
                viewHolder = new ViewHolder(this, (ImageButton)convertView.findViewById(R.id.wall_paper_item), (ImageButton)convertView.findViewById(R.id.love_item));
                convertView.setTag(viewHolder);
                convertView.setLayoutParams(params);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            Picasso.with(context)
                    .load(Uri.parse(ASSETS_PATH + galleryItems[position]))
                    .resize(params.width, params.height)
                    .centerCrop()
                    .into(viewHolder.itemWallPaper);
            return convertView;
        }

        @Override
        public void onClick(View v) {

        }
    }

    class ViewHolder{
        ImageButton itemWallPaper;
        ImageButton itemLoveIcon;

        public ViewHolder(GalleryGridAdapter galleryGridAdapter, ImageButton itemWallPaper, ImageButton itemLoveIcon) {
            this.itemWallPaper = itemWallPaper;
            this.itemLoveIcon = itemLoveIcon;
            itemLoveIcon.setOnClickListener(galleryGridAdapter);
            itemWallPaper.setOnClickListener(galleryGridAdapter);
        }
    }
}
