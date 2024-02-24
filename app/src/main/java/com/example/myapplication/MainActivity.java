package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView screen, coordinates;

    private float x;
    private float y;
    private int[] coordinateKeys;
    private int interval;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.screen);
        coordinates = findViewById(R.id.coordinates);

        //   размеры экрана
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        //   интервал в 50dp и конвертирует его в пиксели
        interval = dpToPixels(50);

        //   случайные координаты для ключей
        generateRandomCoordinates();

        screen.setOnTouchListener(listener);
    }

    private View.OnTouchListener listener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            x = motionEvent.getX();
            y = motionEvent.getY();

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    coordinates.setText("нажатие" + x + "," + y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    coordinates.setText("движение" + x + "," + y);
                    // Проверяем, попадает ли текущее касание в одну из ключевых точек
                    for (int i = 0; i < coordinateKeys.length; i += 2) {
                        if (x >= (coordinateKeys[i] - interval) && x <= (coordinateKeys[i] + interval) &&
                                y >= (coordinateKeys[i + 1] - interval) && y <= (coordinateKeys[i + 1] + interval)) {
                            int keyNumber = i / 2 + 1;
                            Toast.makeText(MainActivity.this, "Найден ключ №" + keyNumber, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    coordinates.setText("отпускание" + x + ", " + y);
                    break;
            }
            return true;
        }
    };

    //   преобразования dp в пиксели
    private int dpToPixels(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    //  генерацияслучайных координат для ключей
    private void generateRandomCoordinates() {
        Random random = new Random();
        coordinateKeys = new int[10]; // 5 ключей, по 2 координаты на каждый
        for (int i = 0; i < coordinateKeys.length; i++) {
            if (i % 2 == 0) {
                //   случайная x-координату
                coordinateKeys[i] = random.nextInt(screenWidth);
            } else {
                // случайная y-координату
                coordinateKeys[i] = random.nextInt(screenHeight);
            }
        }
    }
}