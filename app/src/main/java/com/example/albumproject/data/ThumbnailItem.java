package com.example.albumproject.data;

import android.graphics.Bitmap;

public class ThumbnailItem {
    public Bitmap image;
    public Filter filter;
    public String filterName;

    public ThumbnailItem() {
        image = null;
        filter = new Filter();
        filterName = "";
    }
}
