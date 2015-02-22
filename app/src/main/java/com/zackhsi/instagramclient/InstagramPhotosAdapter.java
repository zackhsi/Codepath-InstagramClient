package com.zackhsi.instagramclient;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zackhsi on 2/21/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        ImageView ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvCreatedAgo = (TextView) convertView.findViewById(R.id.tvCreatedAgo);

        ivAvatar.setImageResource(0);
        Picasso.with(getContext()).load(photo.userImageUrl).resize(60, 60).into(ivAvatar);
        tvUsername.setText(photo.username);
        tvCaption.setText(Html.fromHtml("<font color=#3D719D>" + photo.username + "</font> " + photo.caption));
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        tvLikes.setText(photo.likesCount + " likes");
        tvCreatedAgo.setText(DateUtils.getRelativeTimeSpanString(photo.createdAt * DateUtils.SECOND_IN_MILLIS, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        return convertView;
    }
}
