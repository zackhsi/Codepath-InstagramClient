package com.zackhsi.instagramclient;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by zackhsi on 2/21/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
}
