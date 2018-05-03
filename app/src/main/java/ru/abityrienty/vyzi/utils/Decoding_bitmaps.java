package ru.abityrienty.vyzi.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

/**
 * Created by 800704 on 30.04.2018.
 */

public class Decoding_bitmaps extends AsyncTask<String, Void, Drawable>{

    @Override
    protected Drawable doInBackground(String... strings) {
        /*try {
            for (String str : strings) {
                Uri uri = Uri.parse(str);
                InputStream inputStream = getContentResolver().openInputStream(uri);
                return new Drawable.createFromStream(inputStream, uri.toString());
            }
        }   catch (Exception e){
            return Drawable.createFromPath("android.resource://ru.abityrienty.vyzi/drawable/img_default.jpeg");
        }*/
return null;

    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        super.onPostExecute(drawable);
    }
}
