package com.example.marat.myapplication.Single;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by marat on 18.11.15.
 */
public class Map
{
    //width, height - lengths of surfaceView
    private void drawField(Canvas canvas, int width, int height)
    {
        width /= 8;
        height /= 8;

        boolean color = false;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);


        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j++) {
                if (!color) {
                    paint.setColor(Color.rgb(65, 132, 84));
                    color = true;
                } else {
                    paint.setColor(Color.rgb(6, 110, 60));
                    color = false;
                }

                Rect rect = new Rect(j * width, i * height, j * width + width, i * height + height);
                canvas.drawRect(rect, paint);
            }
            if (!color) { color = true; } else { color = false; }
        }

    }

    //width, height - lengths of surfaceView
    private void drawGates(Canvas canvas, int width, int height)
    {
        width /= 8;
        height /= 8;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);

        Path path = new Path();

        //up gates
        path.moveTo(width * 3, 0);
        path.lineTo(width * 3, height);

        path.moveTo(width * 5, 0);
        path.lineTo(width * 5, height);
        //

        //down gates
        path.moveTo(width * 3, height * 8);
        path.lineTo(width * 3, height * 7);

        path.moveTo(width * 5, height * 8);
        path.lineTo(width * 5, height * 7);
        //

        canvas.drawPath(path, paint);

    }

    //width, height - lengths of surfaceView
    private void drawLinesOfField(Canvas canvas, int width, int height)
    {
        width /= 8;
        height /= 8;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.WHITE);

        Path path = new Path();

        path.moveTo(0, 0);
        path.lineTo(width * 8, 0);

        path.moveTo(0, 0);
        path.lineTo(0, height * 8);

        path.moveTo(width * 8, 0);
        path.lineTo(width * 8, height * 8);

        path.moveTo(0, height * 8);
        path.lineTo(width * 8, height * 8);

        canvas.drawPath(path, paint);


        paint.setAlpha(80);
        path.moveTo(0, height * 4);
        path.lineTo(width * 8, height * 4);

        canvas.drawPath(path, paint);


        canvas.drawCircle(width * 4, height * 4, 100, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width * 4, height * 4, 10, paint);
    }

    public void drawMap(Canvas canvas, int width, int height)
    {
        drawField(canvas, width, height);
        drawGates(canvas, width, height);
        drawLinesOfField(canvas, width, height);
    }
}
