package com.example.muhammadafiffauzi.abma.questions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muhammadafiffauzi.abma.Model.Question1Model;
import com.example.muhammadafiffauzi.abma.Model.Question2Model;
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


public class Quest2Lesson1Activity extends AppCompatActivity {

    private static final String TAG = "Afif App" ;
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
        setContentView(R.layout.activity_quest2_lesson1);

        paintView = (PaintView) findViewById(R.id.canvas2);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        btnOk = (Button) findViewById(R.id.btnOk2);
        btnClear = (Button) findViewById(R.id.btnClear2);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase = mDatabase = FirebaseDatabase.getInstance().getReference("Question2").child(currentUserId);

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

    private void compareImg() {
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
        String tingkat0 = "0";
        String alfaA = "A";
        String alfaB = "B";
        String alfaC = "C";
        String alfaD = "D";
        String alfaE = "E";

        String quest2Id = mDatabase.push().getKey();

        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String tanggal = dt.format(date);

        //inisialisasi gambar dari user dan merubahnya dari Bitmap ke format Mat
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

        //inisialisasi gambar dari user dan merubahnya dari Bitmap ke format Mat
        Bitmap imgTemplate = BitmapFactory.decodeResource(getResources(), R.drawable.lvl1quest2);
        int imgTemplateHeight = imgTemplate.getHeight();
        int imgTemplateWidth = imgTemplate.getWidth();
        imgTemplate = Bitmap.createScaledBitmap(imgTemplate, imgTemplateWidth, imgTemplateHeight, true);
        Mat img2 = new Mat(imgTemplate.getWidth(), imgTemplate.getHeight(), CvType.CV_8UC3);
        Utils.bitmapToMat(imgTemplate, img2);

        //gambar pertama
        Mat descriptors1 = new Mat();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        detector.detect(img1, keypoints1);
        extractor.compute(img1, keypoints1, descriptors1);

        //gambar kedua
        Mat descriptors2 = new Mat();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        detector.detect(img2, keypoints2);
        extractor.compute(img2, keypoints2, descriptors2);

        //matcher image descriptors
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1,descriptors2,matches);

        int total = (int) matches.size().height + (int) matches.size().width;


        if (total >= 490){

            saveScore(quest2Id, tingkat10, tanggal,alfaA);
            sendUserToScoreActivity(tingkat10, alfaA);

        } else if (total >= 486 && total <= 489) {

            saveScore(quest2Id, tingkat9, tanggal, alfaA);
            sendUserToScoreActivity(tingkat9, alfaA);

        } else if (total >= 482 && total <= 485) {

            saveScore(quest2Id, tingkat8, tanggal, alfaB);
            sendUserToScoreActivity(tingkat8, alfaB);

        } else if (total >= 478 && total <= 481) {

            saveScore(quest2Id, tingkat7, tanggal,alfaB);
            sendUserToScoreActivity(tingkat7, alfaB);

        } else if (total >= 474 && total <= 477) {

            saveScore(quest2Id, tingkat6, tanggal, alfaC);
            sendUserToScoreActivity(tingkat6, alfaC);

        } else if (total >= 470 && total <= 473){

            saveScore(quest2Id, tingkat5, tanggal, alfaC);
            sendUserToScoreActivity(tingkat5, alfaC);

        } else if (total >= 466 && total <= 469){

            saveScore(quest2Id, tingkat4, tanggal, alfaD);
            sendUserToScoreActivity(tingkat4, alfaD);

        } else if (total >= 462 && total <= 465){

            saveScore(quest2Id, tingkat3, tanggal, alfaD);
            sendUserToScoreActivity(tingkat3, alfaD);

        } else if (total >= 458 && total <= 461){

            saveScore(quest2Id, tingkat2, tanggal, alfaE);
            sendUserToScoreActivity(tingkat2, alfaE);

        }else if (total >= 300 && total <= 454){

            saveScore(quest2Id, tingkat1, tanggal, alfaE);
            sendUserToScoreActivity(tingkat1, alfaE);

        } else {
            saveScore(quest2Id, tingkat0, tanggal, alfaE);
            sendUserToScoreActivity(tingkat0, alfaE);
        }

        paintView.destroyDrawingCache();
    }

    private void sendUserToScoreActivity(String score, String scoreAlfa) {
        Bundle bundle = new Bundle();
        bundle.putString("score", score);
        bundle.putString("scoreAlfa", scoreAlfa);
        Intent intent = new Intent(Quest2Lesson1Activity.this, ScoreActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void saveScore(String id, String score, String date, String scoreAlfa){
        Question2Model question2Model = new Question2Model(id, score, date, scoreAlfa);
        mDatabase.child(id).setValue(question2Model);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
