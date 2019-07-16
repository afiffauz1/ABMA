package com.example.muhammadafiffauzi.abma.canvas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import android.Manifest;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect2d;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.example.muhammadafiffauzi.abma.R;
import com.example.muhammadafiffauzi.abma.SelectLesson.SelectLesson1Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Quest1Lesson1Activity extends AppCompatActivity {
    private static final String TAG = "Afif App" ;
    private static final org.opencv.features2d.Features2d Features2d = null;
    private PaintView paintView;
    private Button btnOk;
    private Button btnClear;
    public static final int SAVE_IMAGE_PERMISSION_REQUEST_CODE = 1;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
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

    @Override
    protected void onResume() {
        super.onResume();
        if(!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        }

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


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.clear();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                compareImg();
                paintView.clear();
                //sendUserToLesson1Activity();

            }
        });

    }

    private void compareImg() {

        paintView.setDrawingCacheEnabled(true);
        paintView.buildDrawingCache(true);
        Bitmap usrImg = Bitmap.createBitmap(paintView.getDrawingCache());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        usrImg.compress(Bitmap.CompressFormat.JPEG, 100, baos);


        int usrImgHeight = usrImg.getHeight();
        int usrImgWidth = usrImg.getWidth();
        usrImg = Bitmap.createScaledBitmap(usrImg, usrImgWidth, usrImgHeight, true);
        Mat img1 = new Mat(usrImgWidth, usrImgHeight, CvType.CV_8UC3);
        Utils.bitmapToMat(usrImg, img1);

        Bitmap imgTemplate = BitmapFactory.decodeResource(getResources(), R.drawable.lvl1quest1);
        int imgTemplateHeight = imgTemplate.getHeight();
        int imgTemplateWidth = imgTemplate.getWidth();
        imgTemplate = Bitmap.createScaledBitmap(imgTemplate, imgTemplateWidth, imgTemplateHeight, true);
        Mat img2 = new Mat(imgTemplate.getWidth(), imgTemplate.getHeight(), CvType.CV_8UC3);
        Utils.bitmapToMat(imgTemplate, img2);

        FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.BRISK);
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        Mat descriptors1 = new Mat();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        detector.detect(img1, keypoints1);
        extractor.compute(img1, keypoints1, descriptors1);

        //second image
        Mat descriptors2 = new Mat();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        detector.detect(img2, keypoints2);
        extractor.compute(img2, keypoints2, descriptors2);


        //matcher image descriptors
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1,descriptors2,matches);

        int total = (int) matches.size().height + (int) matches.size().width;

        int tingkat10 = 100;
        int tingkat9 = 90;
        int tingkat8 = 80;
        int tingkat7 = 70;
        int tingkat6 = 60;
        int tingkat5 = 50;
        int tingkat4 = 40;
        int tingkat3 = 30;
        int tingkat2 = 20;
        int tingkat1 = 10;

        if (total >= 490){
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat10 , Toast.LENGTH_SHORT).show();
        } else if (total >= 486 && total <= 489) {
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat9 , Toast.LENGTH_SHORT).show();
        } else if (total >= 482 && total <= 485) {
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat8 , Toast.LENGTH_SHORT).show();
        } else if (total >= 478 && total <= 481) {
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat7 , Toast.LENGTH_SHORT).show();
        } else if (total >= 474 && total <= 477) {
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat6 , Toast.LENGTH_SHORT).show();
        } else if (total >= 470 && total <= 473){
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat5 , Toast.LENGTH_SHORT).show();
        } else if (total >= 466 && total <= 469){
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat4 , Toast.LENGTH_SHORT).show();
        } else if (total >= 462 && total <= 465){
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat3 , Toast.LENGTH_SHORT).show();
        } else if (total >= 458 && total <= 461){
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat2 , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Quest1Lesson1Activity.this, "score : " + tingkat1 , Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(Quest1Lesson1Activity.this, "kecocokan : " +total , Toast.LENGTH_SHORT).show();
        paintView.destroyDrawingCache();
    }

    private void sendUserToLesson1Activity() {
        Intent intent = new Intent(Quest1Lesson1Activity.this, SelectLesson1Activity.class);
        startActivity(intent);
        finish();
    }

}