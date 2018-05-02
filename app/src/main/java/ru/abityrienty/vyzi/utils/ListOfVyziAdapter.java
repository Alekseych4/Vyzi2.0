package ru.abityrienty.vyzi.utils;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ListOfVyziAdapter extends SimpleCursorAdapter {

    /**
     * It's important to place column where you store images on the first place
     * of the @from array
     */


    Context ctx;
    // String [] columns = {0="img_src",1="for main_text", 2="for sub_text"}
    String [] columns;
    Cursor c;


    public ListOfVyziAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        ctx = context;
        columns = from;
        this.c = c;
    }


    @Override
    public void setViewImage(ImageView v, String value) {
        Picasso.with(ctx).load(c.getString(c.getColumnIndex(columns[0]))).fit().into(v);
        //v.setImageDrawable(imageForList(getCursor()));
    }

    /*private Drawable imageForList(Cursor cur){



        byte [] blob = cur.getBlob(cur.getColumnIndex(columns[0]));
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        return drawable;
    }*/
}
