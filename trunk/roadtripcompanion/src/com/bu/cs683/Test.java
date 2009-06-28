package com.bu.cs683;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Test extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test", "testing a change to SVN");
        setContentView(R.layout.main);
    }
}