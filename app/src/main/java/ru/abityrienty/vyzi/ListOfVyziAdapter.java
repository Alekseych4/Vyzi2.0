package ru.abityrienty.vyzi;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 800704 on 07.08.2017.
 */

public class ListOfVyziAdapter extends SimpleCursorAdapter {

    LayoutInflater lInflater;
    Context ctx;
    // String [] columns = {0="img_src",1="for main_text", 2="for sub_text"}
    String [] columns;
    int l;

    public ListOfVyziAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        columns = from;
        l = layout;
    }

    @Override
    public int getCount() {
        return getCursor().getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(l, parent, false);
        }
        getCursor().moveToFirst();


            ((ImageView) view.findViewById(R.id.imageView)).setImageDrawable(imageForList(getCursor()));
            ((TextView) view.findViewById(R.id.main_text)).setText(getCursor().getString(getCursor().getColumnIndex(columns[1])));
            ((TextView) view.findViewById(R.id.sub_text)).setText(getCursor().getString(getCursor().getColumnIndex(columns[2])));


        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return getCursor().getString(position);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    /*@Override
    public void setViewImage(ImageView v, String value) {
        super.setViewImage(v, value);

    }*/
    public Drawable imageForList(Cursor cur){

        byte [] blob = cur.getBlob(cur.getColumnIndex(columns[0]));
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        return drawable;
    }
}
