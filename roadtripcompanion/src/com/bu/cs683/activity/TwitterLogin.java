package com.bu.cs683.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Screen to get the twitter userid / password from the user
 */


public class TwitterLogin extends Activity
{
	/**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.login);

        Button button = (Button) findViewById(R.id.cancelbtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        button = (Button) findViewById(R.id.loginbtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send the userid/password back to the initial screen.
                EditText user = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);
                //setResult(RESULT_OK, user.getText().toString() + ":" + password.getText().toString());
                finish();
            }
        });
    }

}
