package com.memorist.memorist_android.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class UriPathHelper {

    public static String getPathFromURI_Image(Context context, Uri contentUri) {
        String[] dataPath = { MediaStore.Images.Media.DATA };
        try (Cursor mediaCursor = context.getContentResolver().query(contentUri, dataPath, null, null, null)) {
            if (mediaCursor != null) {
                int column_index = mediaCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                mediaCursor.moveToFirst();

                return mediaCursor.getString(column_index);
            }
        }

        return null;
    }

    public static String getPathFromURI_Video(Context context, Uri contentUri) {
        String[] dataPath = { MediaStore.Video.Media.DATA};
        try (Cursor mediaCursor = context.getContentResolver().query(contentUri, dataPath, null, null, null)) {
            if(mediaCursor != null) {
                int column_index = mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                mediaCursor.moveToFirst();

                return mediaCursor.getString(column_index);
            }
        }

        return null;
    }

    public static String getPathFromURI_Audio(Context context, Uri contentUri) {
        String[] dataPath = { MediaStore.Audio.Media.DATA};
        try (Cursor mediaCursor = context.getContentResolver().query(contentUri, dataPath, null, null, null)) {
            if(mediaCursor != null) {
                int column_index = mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                mediaCursor.moveToFirst();

                return mediaCursor.getString(column_index);
            }
        }

        return null;
    }

}
