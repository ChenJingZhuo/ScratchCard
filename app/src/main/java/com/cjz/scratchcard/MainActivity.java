package com.cjz.scratchcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgv;
    private Bitmap alterbitmap;
    private double nX, nY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgv = findViewById(R.id.imgv);
        //从资源文件中解析一张bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fg);
        //创建一个要修改的图片的副本
        alterbitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        //计算图片相对于手机屏幕的缩放比例
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        nX=bitmap.getWidth()/dm.widthPixels;
        nY=bitmap.getHeight()/dm.heightPixels;
        //创建一个canva对象
        Canvas canvas = new Canvas(alterbitmap);
        //创建画笔对象
        Paint paint = new Paint();
        //为画笔设置颜色
        paint.setColor(Color.BLACK);
        //抗锯齿效果，让边界更柔和
        paint.setAntiAlias(true);
        //创建Matrix对象
        Matrix matrix = new Matrix();
        //在alterBitmap上画图
        canvas.drawBitmap(bitmap,matrix,paint);
        mImgv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                try {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    //定义一个半径为100的圆形区域
                    for (int i = -50; i < 50; i++) {
                        for (int j = -50; j < 50; j++) {
                            //将区域的像素点设为透明像素
                            if (Math.sqrt((i*i)+(j*j))<=50){
                                alterbitmap.setPixel((int)(x*nX)+i,(int)(y*nY+90)+j,Color.TRANSPARENT);
                            }
                        }
                    }
                    mImgv.setImageBitmap(alterbitmap);
                } catch (Exception e) {
                    //加try{}catch(){}放置用户触摸图片以外的地方而异常退出
                    e.printStackTrace();
                }
                //销毁该触摸事件
                return true;
            }
        });
    }
}
