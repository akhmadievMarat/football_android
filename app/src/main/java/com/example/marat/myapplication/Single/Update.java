package com.example.marat.myapplication.Single;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

/**
 * Created by marat on 18.11.15.
 */
public class Update
{

    private ArrayList<String>[][] arrayAvailableDestinations; //массив доступных движений
    private ArrayList<String> listAllAvailableDestinations; //список всех заняятых точек

    public ArrayList<float[]> listDestinationsForDrawOfPlayer; //список движений последнего хода
    public ArrayList<float[]> listAllDestinationsForDraw; //список всех линий для рисования

    public float startPositionX;
    public float startPositionY;

    int x1, x2, y1, y2;

    private SurfaceView surfaceView;
    int width; //surfaceView width
    int height; //surfaceView height

    public Paint paint;
    public Paint paint2;

    public boolean playerTwo; //queue of player 2
    private boolean flag;

    public Update(SurfaceView newSurfaceView)
    {
        surfaceView = newSurfaceView;
        width = surfaceView.getWidth();
        height = surfaceView.getHeight();

        startPositionX = width/2;
        startPositionY = height/2;
        playerTwo = false;
        flag = true;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(6);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(Color.BLACK);
        paint2.setStrokeWidth(6);

        listAllAvailableDestinations = new ArrayList<String>();
        arrayAvailableDestinations = new ArrayList[9][9];
        fillListForAvailableDestination(arrayAvailableDestinations);

        listAllDestinationsForDraw = new ArrayList<float[]>();
        listDestinationsForDrawOfPlayer = new ArrayList<float[]>();
    }

    private String direction(int x1, int y1, int x2, int y2)
    {
        int distanceToMove = 50;
        int distanceBetweenTheLines = 50;

        if ((y2 - y1 >= distanceToMove) && (x2 - x1 <= distanceBetweenTheLines &&
                x2 - x1 >= -distanceBetweenTheLines))
            return "DOWN";
        if ((y1 - y2 >= distanceToMove) && (x2 - x1 <= distanceBetweenTheLines &&
                x2 - x1 >= -distanceBetweenTheLines))
            return "UP";
        if ((x2 - x1 >= distanceToMove) && (y2 - y1 <= distanceBetweenTheLines &&
                y2 - y1 >= -distanceBetweenTheLines))
            return "RIGHT";
        if ((x1 - x2 >= distanceToMove) && (y2 - y1 <= distanceBetweenTheLines &&
                y2 - y1 >= -distanceBetweenTheLines))
            return "LEFT";
        if ((y2 - y1 >= distanceToMove) && (x2 - x1 <= distanceBetweenTheLines))
            return "DOWNLEFT";
        if ((y2 - y1 >= distanceToMove) && (x2 - x1 >= distanceBetweenTheLines))
            return "DOWNRIGHT";
        if ((y1 - y2 >= distanceToMove) && (x2 - x1 <= distanceBetweenTheLines))
            return "UPLEFT";
        if ((y1 - y2 >= distanceToMove) && (x2 - x1 >= distanceBetweenTheLines))
            return "UPRIGHT";

        return "";
    }

    private void drawingLineOfMotion(String direction)
    {
        float lengthLeftRight = width/8;
        float lengthUpDown = height/8;

        float oldStartPositionX = startPositionX;
        float oldStartPositionY = startPositionY;

        float newStartPositionX = startPositionX;
        float newStartPositionY = startPositionY;

        int destinationFromX = ((int) startPositionX)/(width/8);
        int destinationFromY = ((int) startPositionY )/(height/8);
        int destinationInX;
        int destinationInY;

        if(!listAllAvailableDestinations.contains(Integer.toString(destinationFromX) +
                Integer.toString(destinationFromY)))
            listAllAvailableDestinations.add(Integer.toString(destinationFromX) +
                    Integer.toString(destinationFromY));

        switch (direction) {
            case "RIGHT":
                if (checkingAvailableDestinations(arrayAvailableDestinations,
                        destinationFromX, destinationFromY, Integer.toString(destinationFromX + 1) +
                                Integer.toString(destinationFromY))) {
                    newStartPositionX += lengthLeftRight;
                }
                break;
            case "LEFT":
                if (checkingAvailableDestinations(arrayAvailableDestinations,
                        destinationFromX, destinationFromY, Integer.toString(destinationFromX - 1) +
                                Integer.toString(destinationFromY))) {
                    newStartPositionX -= lengthLeftRight;
                }

                break;
            case "DOWN":
                if (checkingAvailableDestinations(arrayAvailableDestinations,
                        destinationFromX, destinationFromY, Integer.toString(destinationFromX) +
                                Integer.toString(destinationFromY + 1))) {
                    newStartPositionY += lengthUpDown;
                }
                break;
            case "UP":
                if (checkingAvailableDestinations(arrayAvailableDestinations,
                        destinationFromX, destinationFromY, Integer.toString(destinationFromX) +
                                Integer.toString(destinationFromY - 1))) {
                    newStartPositionY -= lengthUpDown;
                }
                break;
            case "UPRIGHT":
                if (checkingAvailableDestinations(arrayAvailableDestinations,
                        destinationFromX, destinationFromY, Integer.toString(destinationFromX + 1) +
                                Integer.toString(destinationFromY - 1))) {
                    newStartPositionX += lengthLeftRight;
                    newStartPositionY -= lengthUpDown;
                }
                break;
            case "UPLEFT":
                if (checkingAvailableDestinations(arrayAvailableDestinations,
                        destinationFromX, destinationFromY, Integer.toString(destinationFromX - 1) +
                                Integer.toString(destinationFromY - 1))) {
                    newStartPositionX -= lengthLeftRight;
                    newStartPositionY -= lengthUpDown;
                }
                break;
            case "DOWNRIGHT":
                if (checkingAvailableDestinations(arrayAvailableDestinations,
                        destinationFromX, destinationFromY, Integer.toString(destinationFromX + 1) +
                                Integer.toString(destinationFromY + 1))) {
                    newStartPositionX += lengthLeftRight;
                    newStartPositionY += lengthUpDown;
                }
                break;
            case "DOWNLEFT":
                if (checkingAvailableDestinations(arrayAvailableDestinations,
                        destinationFromX, destinationFromY, Integer.toString(destinationFromX - 1) +
                                Integer.toString(destinationFromY + 1))) {
                    newStartPositionX -= lengthLeftRight;
                    newStartPositionY += lengthUpDown;
                }
                break;
        }
        if (flag) {

            startPositionX = newStartPositionX;
            startPositionY = newStartPositionY;

            float[] arr = {oldStartPositionX, oldStartPositionY, startPositionX, startPositionY};
            listDestinationsForDrawOfPlayer.add(arr);

            destinationInX = ((int) startPositionX) / (width / 8);
            destinationInY = ((int) startPositionY) / (height / 8);

            arrayAvailableDestinations[destinationFromX][destinationFromY].add
                    (Integer.toString(destinationInX) + Integer.toString(destinationInY));
            arrayAvailableDestinations[destinationInX][destinationInY].add
                    (Integer.toString(destinationFromX) + Integer.toString(destinationFromY));

            flag = false;

            if (listAllAvailableDestinations.contains(Integer.toString(destinationInX) + Integer.toString(destinationInY))){
                flag = true;
            }
        }

    }

    private boolean checkingAvailableDestinations(ArrayList<String>[][] allAvailableDestinations,
                                                  int currentPositionX, int currentPositionY,
                                                  String newPosition)
    {
        if (allAvailableDestinations[currentPositionX][currentPositionY].contains(newPosition))
            return false;
           // if (!playerTwo) { playerTwo = true; } else { playerTwo = false; }

        return true;
    }

    private void fillListForAvailableDestination(ArrayList<String>[][] arrayAvailable)
    {
        for (int i = 0; i < arrayAvailable.length; i++)
            for (int j = 0; j < arrayAvailable[0].length; j++)
                arrayAvailable[i][j] = new ArrayList<String>();

        for (int k = 0; k < 8; k ++) {
            arrayAvailable[k][0].add(Integer.toString(k + 1) + "0");
            arrayAvailable[k + 1][0].add(Integer.toString(k) + "0");

            arrayAvailable[0][k].add("0" + Integer.toString(k + 1));
            arrayAvailable[0][k + 1].add("0" + Integer.toString(k));

            arrayAvailable[k][8].add(Integer.toString(k + 1) + "8");
            arrayAvailable[k + 1][8].add(Integer.toString(k) + "8");

            arrayAvailable[8][k].add("8" + Integer.toString(k + 1));
            arrayAvailable[8][k + 1].add("8" + Integer.toString(k));
        }

        for (int i = 0; i < 8; i++) {
            listAllAvailableDestinations.add(Integer.toString(i) + "0");
            listAllAvailableDestinations.add("0" + Integer.toString(i));
            listAllAvailableDestinations.add(Integer.toString(i) + "8");
            listAllAvailableDestinations.add("8" + Integer.toString(i));
        }

        arrayAvailable[3][0].add("31");
        arrayAvailable[3][1].add("30");
        arrayAvailable[5][0].add("51");
        arrayAvailable[5][1].add("50");

        arrayAvailable[3][8].add("37");
        arrayAvailable[3][7].add("38");
        arrayAvailable[5][8].add("57");
        arrayAvailable[5][7].add("58");

        listAllAvailableDestinations.add("31");
        listAllAvailableDestinations.add("51");
        listAllAvailableDestinations.add("37");
        listAllAvailableDestinations.add("57");

    }

    public void onTouchEvent(MotionEvent event)
    {
        String text = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                x1 = (int) event.getX();
                y1 = (int) event.getY();
                break;

            case MotionEvent.ACTION_UP:

                x2 = (int) event.getX();
                y2 = (int) event.getY();

                text = direction(x1, y1, x2, y2);

                drawingLineOfMotion(text);

                break;
        }
    }

    public void backButton(Button btn)
    {
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listDestinationsForDrawOfPlayer.size() > 0) {

                    float positionForDeleteX = listDestinationsForDrawOfPlayer.get(listDestinationsForDrawOfPlayer.size() - 1)[0]; //pixels
                    float positionForDeleteY = listDestinationsForDrawOfPlayer.get(listDestinationsForDrawOfPlayer.size() - 1)[1]; //pixels

                    int positionXbefore = (int) (positionForDeleteX / (width / 8));
                    int positionYbefore = (int) (positionForDeleteY / (height / 8));

                    int positionXafter = (int) (startPositionX / (width / 8));
                    int positionYafter = (int) (startPositionY / (height / 8));

                    arrayAvailableDestinations[positionXbefore][positionYbefore].remove(Integer.toString(positionXafter) +
                            Integer.toString(positionYafter));
                    arrayAvailableDestinations[positionXafter][positionYafter].remove(Integer.toString(positionXbefore) +
                            Integer.toString(positionYbefore));

                    //listAllAvailableDestinations.remove(Integer.toString(positionXafter) + Integer.toString(positionYafter));

                    startPositionX = positionForDeleteX;
                    startPositionY = positionForDeleteY;

                    listDestinationsForDrawOfPlayer.remove(listDestinationsForDrawOfPlayer.size() - 1);
                    flag = true;
                }

            }
        };

        btn.setOnClickListener(btnClick);
    }

    public void enterButton(Button btn)
    {
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listDestinationsForDrawOfPlayer.size() > 0) {
                   // paint.setColor(Color.BLACK);

                    for (int i = 0; i < listDestinationsForDrawOfPlayer.size(); i++) {
                        listAllDestinationsForDraw.add(listDestinationsForDrawOfPlayer.get(i));
                    }

                    listDestinationsForDrawOfPlayer.removeAll(listDestinationsForDrawOfPlayer);

                    flag = true;

                    if (!playerTwo) { playerTwo = true; } else { playerTwo = false; }
                }
            }
        };

        btn.setOnClickListener(btnClick);
    }
}
