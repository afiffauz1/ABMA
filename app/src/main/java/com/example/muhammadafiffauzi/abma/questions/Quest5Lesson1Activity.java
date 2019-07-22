package com.example.muhammadafiffauzi.abma.questions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muhammadafiffauzi.abma.R;
import com.example.muhammadafiffauzi.abma.SelectLesson.SelectLesson1Activity;
import com.example.muhammadafiffauzi.abma.canvas.PaintView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;

import java.io.ByteArrayOutputStream;

public class Quest5Lesson1Activity extends AppCompatActivity {

    private static final String TAG = "afif app";
    private PaintView paintView;
    private Button btnOk, btnClear;

    private String currentUserId;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

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
        setContentView(R.layout.activity_quest5_lesson1);

        paintView = (PaintView) findViewById(R.id.canvas5);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        btnClear = (Button) findViewById(R.id.btnClear5);
        btnOk = (Button) findViewById(R.id.btnOk5);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                sendUserToLesson1Activity();
            }
        });
    }

    private void compareImg(){
        FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.BRISK);
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        //mengambil gambar yang dibuat user mengubahnya ke dalam bentuk bitmap lalu diubah kedalam format MAt
        paintView.setDrawingCacheEnabled(true);
        paintView.buildDrawingCache(true);
        Bitmap userImg = Bitmap.createBitmap(paintView.getDrawingCache());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImg.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int userImgHeigth = userImg.getHeight();
        int userImgWidth = userImg.getWidth();
        userImg.createScaledBitmap(userImg, userImgWidth, userImgHeigth, true);
        Mat img1 = new Mat(userImgWidth, userImgHeigth, CvType.CV_8UC3);
        Utils.bitmapToMat(userImg, img1);

        //mengambil template gambar dari drawable lalu diubah ke format mat
        Bitmap templateImg = BitmapFactory.decodeResource(getResources(), R.drawable.lvl1quest5);
        int templateImgHeigth = templateImg.getHeight();
        int templateImgWidth = templateImg.getWidth();
        templateImg.createScaledBitmap(templateImg, templateImgWidth, templateImgHeigth, true);
        Mat img2 = new Mat(userImgWidth, userImgHeigth, CvType.CV_8UC3);
        Utils.bitmapToMat(templateImg, img2);

        Mat descriptor1 = new Mat();
        MatOfKeyPoint keyPoint1 = new MatOfKeyPoint();
        detector.detect(img1, keyPoint1);
        extractor.compute(img1, keyPoint1, descriptor1);

        Mat descriptor2 = new Mat();
        MatOfKeyPoint keyPoint2 = new MatOfKeyPoint();
        detector.detect(img2, keyPoint2);
        extractor.compute(img2, keyPoint2, descriptor2);

        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptor1, descriptor2, matches);

        int total = (int) matches.size().height + (int) matches.size().width;

        String tingkat10 = "100";
        String tingkat9 = "90";
        String tingkat8 = "80";
        String tingkat7 = "70";
        String tingkat6 = "60";
        String tingkat5 = "50";
        String tingkat4 = "40";
        String tingkat3 = "30";
        String tingkat2 = "20";
        String tingkat1 = "10";

        if (total >= 490){

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat10).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (total >= 486 && total <= 489) {

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat9).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (total >= 482 && total <= 485) {

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat8).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (total >= 478 && total <= 481) {

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat7).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (total >= 474 && total <= 477) {

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat6).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (total >= 470 && total <= 473){

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat5).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (total >= 466 && total <= 469){

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat4).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (total >= 462 && total <= 465){

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat3).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if (total >= 458 && total <= 461){

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat2).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{

            mDatabase.child("Lesson1").child("Quest5").child(currentUserId).push().setValue(tingkat1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Quest5Lesson1Activity.this, "Yeyyyy" , Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = task.getException().toString();
                        Toast.makeText(Quest5Lesson1Activity.this, "Failed : "+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        paintView.destroyDrawingCache();
    }

    private void sendUserToLesson1Activity(){
        Intent intent = new Intent(Quest5Lesson1Activity.this, SelectLesson1Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
