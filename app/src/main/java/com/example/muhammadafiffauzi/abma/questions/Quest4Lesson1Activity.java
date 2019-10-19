package com.example.muhammadafiffauzi.abma.questions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.audiofx.AudioEffect;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muhammadafiffauzi.abma.Model.Question3Model;
import com.example.muhammadafiffauzi.abma.Model.Question4Model;
import com.example.muhammadafiffauzi.abma.R;
import com.example.muhammadafiffauzi.abma.ScoreActivity;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Quest4Lesson1Activity extends AppCompatActivity {

    private static final String TAG = "Afif App" ;
    private Button btnOk, btnClear;
    private PaintView paintView;

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
        setContentView(R.layout.activity_quest4_lesson1);

        paintView = (PaintView) findViewById(R.id.canvas4);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        btnClear = (Button) findViewById(R.id.btnClear4);
        btnOk = (Button) findViewById(R.id.btnOk4);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase = mDatabase = FirebaseDatabase.getInstance().getReference("Question4").child(currentUserId);

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
            }
        });
    }

    private void compareImg(){
        FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.BRISK);
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

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
        String alfaA = "A";
        String alfaB = "B";
        String alfaC = "C";
        String alfaD = "D";
        String alfaE = "E";

        String quest3Id = mDatabase.push().getKey();

        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String tanggal = dt.format(date);

        //mengambil gambar yang telah dibuat user untuk dijadikan bitmap lalu di ubah ke format mat
        paintView.setDrawingCacheEnabled(true);
        paintView.buildDrawingCache(true);
        Bitmap userImg = Bitmap.createBitmap(paintView.getDrawingCache());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImg.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int userImgHeight = userImg.getHeight();
        int userImgWidth = userImg.getWidth();
        userImg = Bitmap.createScaledBitmap(userImg, userImgWidth, userImgHeight, true);
        Mat img1 = new Mat(userImgWidth, userImgHeight, CvType.CV_8UC3);
        Utils.bitmapToMat(userImg, img1);

        //inisialisasi gambar template dari format bitmap diubah menjadi format MAt
        Bitmap templateImg = BitmapFactory.decodeResource(getResources(), R.drawable.lvl1quest4);
        int templateImgHeight = templateImg.getHeight();
        int templateImgWidth = templateImg.getWidth();
        Bitmap.createScaledBitmap(templateImg, templateImgWidth, templateImgHeight, true);
        Mat img2 = new Mat(templateImgWidth, templateImgHeight, CvType.CV_8UC3);
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

        if (total >= 490){

            saveScore(quest3Id, tingkat10, tanggal,alfaA);
            sendUserToScoreActivity(tingkat10, alfaA);

        } else if (total >= 486 && total <= 489) {

            saveScore(quest3Id, tingkat9, tanggal, alfaA);
            sendUserToScoreActivity(tingkat9, alfaA);

        } else if (total >= 482 && total <= 485) {

            saveScore(quest3Id, tingkat8, tanggal, alfaB);
            sendUserToScoreActivity(tingkat8, alfaB);

        } else if (total >= 478 && total <= 481) {

            saveScore(quest3Id, tingkat7, tanggal,alfaB);
            sendUserToScoreActivity(tingkat7, alfaB);

        } else if (total >= 474 && total <= 477) {

            saveScore(quest3Id, tingkat6, tanggal, alfaC);
            sendUserToScoreActivity(tingkat6, alfaC);

        } else if (total >= 470 && total <= 473){

            saveScore(quest3Id, tingkat5, tanggal, alfaC);
            sendUserToScoreActivity(tingkat5, alfaC);

        } else if (total >= 466 && total <= 469){

            saveScore(quest3Id, tingkat4, tanggal, alfaD);
            sendUserToScoreActivity(tingkat4, alfaD);

        } else if (total >= 462 && total <= 465){

            saveScore(quest3Id, tingkat3, tanggal, alfaD);
            sendUserToScoreActivity(tingkat3, alfaD);

        } else if (total >= 458 && total <= 461){

            saveScore(quest3Id, tingkat2, tanggal, alfaE);
            sendUserToScoreActivity(tingkat2, alfaE);

        }else if (total >= 300 && total <= 454){

            saveScore(quest3Id, tingkat1, tanggal, alfaE);
            sendUserToScoreActivity(tingkat1, alfaE);

        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Peringatan!!!");
            dialog.setMessage("Mohon kerjakan dengan benar");
            dialog.setNegativeButton("Ok", null);
            dialog.show();
        }

        paintView.destroyDrawingCache();
    }

    private void sendUserToScoreActivity(String score, String scoreAlfa) {
        Bundle bundle = new Bundle();
        bundle.putString("score", score);
        bundle.putString("scoreAlfa", scoreAlfa);
        Intent intent = new Intent(Quest4Lesson1Activity.this, ScoreActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void saveScore(String id, String score, String date, String scoreAlfa){
        Question4Model question4Model = new Question4Model(id, score, date, scoreAlfa);
        mDatabase.child(id).setValue(question4Model);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
