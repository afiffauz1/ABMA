package com.example.muhammadafiffauzi.abma.canvas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.Manifest;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;

import com.example.muhammadafiffauzi.abma.R;
import com.example.muhammadafiffauzi.abma.SelectLesson.SelectLesson1Activity;

import java.io.File;
import java.io.InputStream;


public class Quest1Lesson1Activity extends AppCompatActivity {
    private PaintView paintView;
    private Button btnOk;
    private Button btnClear;
    public static final int SAVE_IMAGE_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest1_lesson1);

        paintView = (PaintView) findViewById(R.id.canvas);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        btnOk = (Button) findViewById(R.id.btn_ok);
        btnClear = (Button) findViewById(R.id.btn_clear);

        //permission of write in to local storage
        if (paintView.getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                AlertDialog.Builder dialog = new AlertDialog.Builder(Quest1Lesson1Activity.this);
                dialog.setMessage(R.string.permission_explanation);
                dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SAVE_IMAGE_PERMISSION_REQUEST_CODE);
                    }
                });
                dialog.create().show();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SAVE_IMAGE_PERMISSION_REQUEST_CODE);
            }
        }

        BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS:
                    {
                        Log.i("OpenCV", "OpenCV loaded successfully");
                    } break;
                    default:
                    {
                        super.onManagerConnected(status);
                    } break;
                }
            }
        };

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clear();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveImgQ1L1();
                paintView.clear();
                comparisonImg();
                //sendUserToLesson1Activity();

            }
        });

    }

    private void comparisonImg() {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        String imgPath1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "test.jpg";
        Mat path1 = Imgcodecs.imread(imgPath1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
    }

    private String getPath(Uri imgPath1) {
        if (imgPath1 == null){
            return null;
        } else {
            String[] projections = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imgPath1, projections, null, null, null);

            if (cursor != null){
                int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                return cursor.getString(col_index);
            }
        }

        return imgPath1.getPath();
    }

    private void sendUserToLesson1Activity() {
        Intent intent = new Intent(Quest1Lesson1Activity.this, SelectLesson1Activity.class);
        startActivity(intent);
        finish();
    }

    private void saveImgQ1L1() {

            paintView.setDrawingCacheEnabled(true);
            String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), paintView.getDrawingCache(), "quest1.jpg", "quest1result");

            if (imgSaved != null){
                Toast.makeText(Quest1Lesson1Activity.this, "Yay its saved", Toast.LENGTH_SHORT).show();
        } else {
                Toast.makeText(Quest1Lesson1Activity.this, "oh damn it!!", Toast.LENGTH_SHORT).show();
            }
            paintView.destroyDrawingCache();
    }

}
