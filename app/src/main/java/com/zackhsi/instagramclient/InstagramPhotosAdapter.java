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

    private static class ViewHolder {
        ImageView avatar;
        TextView username;
        TextView caption;
        ImageView photo;
        TextView likes;
        TextView createdAgo;
    }

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
            viewHolder.username = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.caption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.likes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.createdAgo = (TextView) convertView.findViewById(R.id.tvCreatedAgo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.avatar.setImageResource(0);
        Picasso.with(getContext()).load(photo.userImageUrl).resize(60, 60).into(viewHolder.avatar);
        viewHolder.username.setText(photo.username);
        viewHolder.caption.setText(Html.fromHtml("<font color=#3D719D>" + photo.username + "</font> " + photo.caption));
        viewHolder.photo.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.photo);
        viewHolder.likes.setText(photo.likesCount + " likes");
        viewHolder.createdAgo.setText(getCreatedAgo(photo.createdAt));

        return convertView;
    }

    /**
     * @param createdAt seconds since epoch
     * @return formatted string, e.g. '1h' or '39m'
     */
    private String getCreatedAgo(int createdAt) {
        String verboseCreatedAgo = (String) DateUtils.getRelativeTimeSpanString(
                createdAt * DateUtils.SECOND_IN_MILLIS,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS
        );

        String[] quantityUnitsAgo = verboseCreatedAgo.split(" ");
        String quantity = quantityUnitsAgo[0];
        String units = quantityUnitsAgo[1];

        return quantity + units.charAt(0);
    }
}
