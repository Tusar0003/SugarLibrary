package com.example.sugarlibrary.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sugarlibrary.R;
import com.example.sugarlibrary.app.AppController;
import com.example.sugarlibrary.model.User;
import com.example.sugarlibrary.utils.Constants;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private TextView mDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_show).setOnClickListener(this);
        findViewById(R.id.button_insert).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_show:
                selectOption();
                break;
            case R.id.button_insert:
                startActivity(new Intent(this, InsertActivity.class));
                break;
        }
    }

    private void selectOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to load data from server?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadFromServer();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadFromSqLite();
                    }
                }).create().show();
    }

    private void loadFromServer() {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Constants.GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mDataTextView = findViewById(R.id.text_view_data);
                mDataTextView.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance(this).addToRequestQueue(objectRequest);
    }

    private void loadFromSqLite() {
        List<User> users = User.listAll(User.class);

        User user = User.findById(User.class, 4L);
//        mDataTextView.setText(users.toString());
        Log.e(TAG, "loadFromSqLite: " + user.toString());
    }
}
