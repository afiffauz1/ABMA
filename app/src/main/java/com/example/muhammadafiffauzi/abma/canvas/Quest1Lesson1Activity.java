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
        // Mat img2 = Imgcodecs.imread(path2);
        Mat descriptors2 = new Mat();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        detector.detect(img2, keypoints2);
        extractor.compute(img2, keypoints2, descriptors2);


        //matcher image descriptors
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1,descriptors2,matches);

        // Filter matches by distance
        MatOfDMatch filtered = filterMatchesByDistance(matches);

        int total = (int) matches.size().height;
        int Match= (int) filtered.size().height;
        Log.d("LOG", "total:" + total + " Match:" + Match);

        Toast.makeText(Quest1Lesson1Activity.this, "kecocokan : " +total+" || "+Match , Toast.LENGTH_LONG).show();
        paintView.destroyDrawingCache();
    }

    static MatOfDMatch filterMatchesByDistance(MatOfDMatch matches) {
        List<DMatch> matches_original = matches.toList();
        List<DMatch> matches_filtered = new ArrayList<DMatch>();

        int DIST_LIMIT = 30;
        // Check all the matches distance and if it passes add to list of filtered matches
        Log.d("DISTFILTER", "ORG SIZE:" + matches_original.size() + "");
        for (int i = 0; i < matches_original.size(); i++) {
            DMatch d = matches_original.get(i);
            if (Math.abs(d.distance) <= DIST_LIMIT) {
                matches_filtered.add(d);
            }
        }
        Log.d("DISTFILTER", "FIL SIZE:" + matches_filtered.size() + "");

        MatOfDMatch mat = new MatOfDMatch();
        mat.fromList(matches_filtered);
        return mat;
    }


    private void sendUserToLesson1Activity() {
        Intent intent = new Intent(Quest1Lesson1Activity.this, SelectLesson1Activity.class);
        startActivity(intent);
        finish();
    }

//    private void saveImgQ1L1() {
//
//        paintView.setDrawingCacheEnabled(true);
//        paintView.buildDrawingCache();
//        Bitmap mBitmap = paintView.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//
////        String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), paintView.getDrawingCache(), "quest1.jpg", "quest1result");
//
//
//        final StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("lesson1/Q1L1");
//
//        UploadTask uploadTask = mStorage.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Quest1Lesson1Activity.this,"astagfirullah" , Toast.LENGTH_LONG).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                mStorage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        Toast.makeText(Quest1Lesson1Activity.this,"alhamdulillah" , Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });
//
//        paintView.destroyDrawingCache();
//
//    }

}