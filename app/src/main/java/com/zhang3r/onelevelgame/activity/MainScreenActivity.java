package com.zhang3r.onelevelgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhang3r.onelevelgame.R;

/**
 * Created by Zhang3r on 8/2/2015.
 */
public class MainScreenActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_screen);
        Button start = (Button) findViewById(R.id.new_game);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.zhang3r.onelevelgame.MAINACTIVITY");
                startActivity(i);
            }
        });


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
