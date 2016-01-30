package com.example.marat.myapplication.Single;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import com.example.marat.myapplication.R;

public class  MainActivity extends AppCompatActivity{


    SurfaceView surface;
    Map map;
    Update update;
    DrawThread drawThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        surface = (SurfaceView) findViewById(R.id.surfaceView);

        map = new Map();
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                update = new Update(surface);
                drawThread = new DrawThread(surface, getResources());
                drawThread.setResources(getResources());
                drawThread.setRunning(true);
                drawThread.start();
                drawThread.update.backButton((Button) findViewById(R.id.button));
                drawThread.update.enterButton((Button) findViewById(R.id.button2));
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                drawThread.setRunning(false);
                while (retry) {
                    try {
                        drawThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        drawThread.update.onTouchEvent(event);
        //drawThread.moveBall();
        return true;
    }
}
