package com.example.lisa.customadapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends ArrayAdapter {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference pathRef = storageRef.child("images");
    File localFile;

    private Context mContext;
    private List<Feed> feedList = new ArrayList<>();

    public FeedAdapter(@NonNull Context context, ArrayList<Feed> list) {
        super(context, 0, list);
        mContext = context;
        feedList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View conentView, @NonNull ViewGroup parent) {
        View listItem = conentView;

        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Feed currentFeed = feedList.get(position);

        TextView name = (TextView)listItem.findViewById(R.id.name_input);
        name.setText((currentFeed.getName()));

        TextView age = (TextView)listItem.findViewById(R.id.age_input);
        age.setText(Integer.toString(currentFeed.getAge()));

        TextView job = (TextView)listItem.findViewById(R.id.job_input);
        job.setText((currentFeed.getJob()));

        TextView search = (TextView)listItem.findViewById(R.id.search_input);
        search.setText((currentFeed.getSearch()));

        TextView location = (TextView)listItem.findViewById(R.id.location_input);
        location.setText((currentFeed.getLocation()));

        TextView qm = (TextView)listItem.findViewById(R.id.qm_input);
        qm.setText(Integer.toString(currentFeed.getQm()));

        TextView pricing = (TextView)listItem.findViewById(R.id.pricing_input);
        pricing.setText(Integer.toString(currentFeed.getPricing()));

        TextView others = (TextView)listItem.findViewById(R.id.others_input);
        others.setText((currentFeed.getOthers()));

        ImageView image1 = (ImageView)listItem.findViewById(R.id.profilePicture);
        Glide.with(mContext).using(new FirebaseImageLoader()).load(pathRef).into(image1);
        //Glide.with(image1).load("https://scontent.fmuc4-1.fna.fbcdn.net/v/t1.0-9/552879_425553407506378_1054855192_n.jpg?_nc_cat=0&oh=e4a59a4ff2746522d61d92494ac73681&oe=5BCC5AC9").into(image1);
        //image1.setImageResource(Integer.parseInt(currentFeed.getPicture1()));

        return listItem;
    }

}
