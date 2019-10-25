package com.example.profile2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreen extends AppCompatActivity {
private GifImageView img;
private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
            img=findViewById(R.id.gifimg);
            progress=findViewById(R.id.progress);
            progress.setVisibility(View.VISIBLE);
            try {
                InputStream inputStream=getAssets().open("splash.gif");
                byte[]  bytes= IOUtils.toByteArray(inputStream);
                img.setBytes(bytes);
                img.startAnimation();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    SplashScreen.this.finish();
                }
            },4000);

    }
}
