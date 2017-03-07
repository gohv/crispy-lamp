package controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fragment.MyAdsFragment;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import model.Ad;
import xyz.georgihristov.myadds.R;

/**
 * Created by gohv on 04.03.17.
 */

public class RealmRecyclerAdapter extends
        RealmRecyclerViewAdapter<Ad, RealmRecyclerAdapter.MyViewHolder> {
    private MyAdsFragment fragment;
    private Context context;


    public RealmRecyclerAdapter(MyAdsFragment activity, OrderedRealmCollection<Ad> data, Context context) {
        super(data, true);
        this.fragment = activity;
        this.context = context;
    }
    public RealmRecyclerAdapter(MyAdsFragment activity, OrderedRealmCollection<Ad> data) {
        super(data, true);
        this.fragment = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ad obj = getData().get(position);
        String test = obj.getProductPhoto();
        /*!!! FORCING SIZE FOR PERFORMANCE !!!*/
        Picasso.with(context).load(test).resize(500,500).into(holder.image);
        holder.data = obj;
        holder.title.setText(obj.getProductName());
        holder.description.setText(obj.getProductDescription());
        holder.price.setText(obj.getProductPrice());
        holder.location.setText(obj.getProductLocation());

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public Ad data;
        public TextView title;
        public TextView price;
        public TextView description;
        public TextView location;
        public ImageView image;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.nameText);
            description = (TextView) view.findViewById(R.id.descriptionTExt);
            price = (TextView) view.findViewById(R.id.priceText);
            image = (ImageView) view.findViewById(R.id.itemImage);
            location = (TextView) view.findViewById(R.id.locationText);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            /*CLEAR THE LIST WITH A LONG CLICK!*/
            fragment.clearList();
            return true;
        }
    }
}
