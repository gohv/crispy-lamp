package xyz.georgihristov.myadds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by gohv on 03.03.17.
 */

public class ListAdapter extends RecyclerView.Adapter{


    private Context context;


    public ListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        //TODO: SET THE LEN OF THE ADS
        return 1;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView itemImageView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView locationTextView;
        private TextView descriptionTextView;




        public ListViewHolder(View itemView) {
            super(itemView);

            itemImageView = (ImageView)itemView.findViewById(R.id.itemImage);
            nameTextView = (TextView) itemView.findViewById(R.id.nameText);
            priceTextView = (TextView)itemView.findViewById(R.id.priceText);
            //locationTextView = (TextView)itemView.findViewById(R.id.locationText);
            descriptionTextView = (TextView)itemView.findViewById(R.id.descriptionTExt);

            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
            //TODO:UPDATE TEXT VIEWS HERE !!!!!!!!!!!!!!!!

        }

        @Override
        public void onClick(View v) {

        }

    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.d("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
