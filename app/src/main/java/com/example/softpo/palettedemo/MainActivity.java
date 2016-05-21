package com.example.softpo.palettedemo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper flipper;

//    声明手势监听
    private GestureDetector mGestureDetector;
    private Palette mPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initFlipper();

        initGesture();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setPictureColor();
    }

    private void initGesture() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


//                获取滑动事件前后的坐标值
                float x = e1.getX();//触摸屏幕时那一刻的点

                float x1 = e2.getX();//触摸屏幕结束时那一刻的点

                if((x-x1)>20){//向左滑动一定的距离，再次给个20像素
                    flipper.showNext();
                    ImageView imageView = (ImageView) flipper.getCurrentView();

                    Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                    mPalette = Palette.from(image).generate();

                    int color = getResources().getColor(R.color.colorPrimary);

                    int lightMutedColor = mPalette.getLightMutedColor(color);

//                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mPalette.getLightMutedSwatch().getRgb()));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(lightMutedColor));

                }else if((x1-x)>20){//向右滑动一定距离
                    flipper.showPrevious();

                    ImageView imageView = (ImageView) flipper.getCurrentView();

                    Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                    mPalette = Palette.from(image).generate();

                    int color = getResources().getColor(R.color.colorPrimary);

                    int lightMutedColor = mPalette.getLightMutedColor(color);

//                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mPalette.getLightMutedSwatch().getRgb()));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(lightMutedColor));
                }
//                返回true代表着滑动事件被消费
                return true;
            }
        });

    }

//    修改onTouchEvent的放回值，注册GestureDetector


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void initFlipper() {
        flipper.addView(getView(R.mipmap.p1));
        flipper.addView(getView(R.mipmap.p2));
        flipper.addView(getView(R.mipmap.p3));
        flipper.addView(getView(R.mipmap.p4));
    }

//    设置滑动

    private View getView(int id) {
        ImageView imageView = new ImageView(this);

        imageView.setImageResource(id);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }



    private void initView() {
        flipper = ((ViewFlipper) findViewById(R.id.flipper));
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setPictureColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_category_1);

        mPalette = Palette.from(bitmap).generate();

        Palette.Swatch swatch = mPalette.getLightVibrantSwatch();

        int color = getResources().getColor(R.color.colorPrimary);

        int lightMutedColor = mPalette.getLightMutedColor(color);

//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mPalette.getLightMutedSwatch().getRgb()));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(lightMutedColor));

        int bodyTextColor = swatch.getBodyTextColor();

        int rgb = swatch.getRgb();

        int titleTextColor = swatch.getTitleTextColor();

        if (getSupportActionBar() != null) {

            Log.d("flag","----------------->rgb: "+rgb);
            Log.d("flag","----------------->titleTextColor: "+titleTextColor);
            Log.d("flag","----------------->bodyTextColor: "+bodyTextColor);
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(rgb));
            ImageView imageView = (ImageView) flipper.getCurrentView();

            Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

            mPalette = Palette.from(image).generate();

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(mPalette.getLightMutedSwatch().getRgb()));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
