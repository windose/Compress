package com.rohdeschwarz.compress.app;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    private byte[] loadPic(String path) {
        try {
            InputStream in = getContext().getAssets().open(path);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            return buffer;
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static byte[] compress(byte[] picture, int quality) {
        Bitmap inputPic = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        inputPic.compress(Bitmap.CompressFormat.JPEG, quality, out);
        return out.toByteArray();
    }

    private long superTest(String filename, int quality) {
        byte[] in = loadPic(filename);
        Log.d("Test", String.format("Picture loaded (Size: %d kb )", in.length / 1024));
        long start = System.nanoTime();
        byte[] out = compress(in, quality);
        Log.d("Test", String.format("Picture saved (Size: %d kb)", out.length / 1024));
        long end = System.nanoTime();
        long delta = end - start;
        //Log.d("Test", String.format("Time to Compress %d msec", delta / 1000000));
        return delta;
    }

    public void test1() {
        Log.d("Test", "Test1 started (30% Quality)");
        long sum = 0;
        for (int i = 0; i < 20; i++) {
            sum += superTest("f2.jpg", 10) / 1E6;
        }
        Log.d("Test", String.format("Time : %d msec", sum / 20));;
    }
}