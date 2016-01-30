package com.example.marat.myapplication.Single;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.view.SurfaceView;

import com.example.marat.myapplication.R;

class DrawThread extends Thread{
    private boolean runFlag = false;
    private SurfaceView surfaceView;
    private Bitmap picture;
    public Matrix matrix;
    private long prevTime;
    Resources resources;


    private Path path;
    private Path pathForAllDestinations;


    public void setResources(Resources newRes) {
        resources = newRes;
    }

    Map map;
    public Update update;
    public DrawThread(SurfaceView surfaceView, Resources resources){
        this.surfaceView = surfaceView;
        map = new Map();
        update = new Update(surfaceView);

        // загружаем картинку, которую будем отрисовывать
        picture = BitmapFactory.decodeResource(resources, R.drawable.ball);

        // формируем матрицу преобразований для картинки
        matrix = new Matrix();
        matrix.postScale(1.0f, 1.0f);
        matrix.postTranslate(update.startPositionX - picture.getWidth() / 2,
                update.startPositionY - picture.getHeight() / 2);

        // сохраняем текущее время
        prevTime = System.currentTimeMillis();
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (runFlag) {
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > 30){
                prevTime = now;
                matrix.preRotate(2.0f, picture.getWidth() / 2, picture.getHeight() / 2);

            }
            canvas = null;
            try {
                // получаем объект Canvas и выполняем отрисовку
                //canvas = surfaceHolder.lockCanvas(null);
                canvas = surfaceView.getHolder().lockCanvas(null);
                synchronized (surfaceView.getHolder()) {
                    map.drawMap(canvas, surfaceView.getWidth(), surfaceView.getHeight());

                    path = new Path();
                    pathForAllDestinations = new Path();

                    for (int i = 0; i < update.listDestinationsForDrawOfPlayer.size(); i++) {
                        path.moveTo(update.listDestinationsForDrawOfPlayer.get(i)[0], update.listDestinationsForDrawOfPlayer.get(i)[1]);
                        path.lineTo(update.listDestinationsForDrawOfPlayer.get(i)[2], update.listDestinationsForDrawOfPlayer.get(i)[3]);
                    }
                    for (int i = 0; i < update.listAllDestinationsForDraw.size(); i++) {
                        pathForAllDestinations.moveTo(update.listAllDestinationsForDraw.get(i)[0], update.listAllDestinationsForDraw.get(i)[1]);
                        pathForAllDestinations.lineTo(update.listAllDestinationsForDraw.get(i)[2], update.listAllDestinationsForDraw.get(i)[3]);
                    }

                    moveBall(resources);
                    canvas.drawPath(path, update.paint);
                    canvas.drawPath(pathForAllDestinations, update.paint2);
                    canvas.drawBitmap(picture, matrix, null);
                }
            }
            finally {
                if (canvas != null) {
                    // отрисовка выполнена. выводим результат на экран
                    //surfaceHolder.unlockCanvasAndPost(canvas);
                    surfaceView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void moveBall(Resources res)
    {

        matrix.setTranslate(update.startPositionX - picture.getWidth() / 2,
                update.startPositionY - picture.getHeight() / 2);
        if (update.playerTwo)
            picture = BitmapFactory.decodeResource(res, R.drawable.balls2);
        else
            picture = BitmapFactory.decodeResource(res, R.drawable.ball);
        //matrix.setRotate(2.0f, picture.getWidth() / 2, picture.getHeight() / 2);
    }

}