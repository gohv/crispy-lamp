package xyz.georgihristov.myadds;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * Created by gohv on 02.03.17.
 */

public class PhotosAdapter extends BaseAdapter{
    private static final int REQUEST_IMAGE_LOAD = 2;
    ImageView[] imageViews;
    private Context context;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Ad ad;



    public PhotosAdapter(ImageView[] imageViews, Context context) {
        this.imageViews = imageViews;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageViews.length;
    }

    @Override
    public Object getItem(int position) {
        return imageViews[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.photo_list_item,null);
            holder = new ViewHolder();

            holder.photoImageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.photoImageView2 = (ImageView) convertView.findViewById(R.id.imageView2);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TOAST 1"+ " " + position, Toast.LENGTH_SHORT).show();
                createDiaglog();
            }
        });
        holder.photoImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TOAST "+" "+ position, Toast.LENGTH_SHORT).show();
                createDiaglog();
            }
        });


        return convertView;
    }



    public static class ViewHolder{
        //public by default
        public static ImageView photoImageView;
        ImageView photoImageView2;

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            ((Activity) context).startActivityForResult(takePictureIntent,
                    REQUEST_IMAGE_CAPTURE);
        }
    }
    private void dispatchGalleryIntent(){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(intent, REQUEST_IMAGE_LOAD);
    }

    private void createDiaglog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle("Choose:");
        Button galleryButton = (Button) dialog.findViewById(R.id.galleryButton);
        Button cameraButton = (Button) dialog.findViewById(R.id.cameraButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryIntent();
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        dialog.show();
    }

}
