package com.example.sugarlibrary.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sugarlibrary.R;
import com.example.sugarlibrary.app.AppController;
import com.example.sugarlibrary.model.User;
import com.example.sugarlibrary.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "InsertActivity";

    private EditText mIdEditText, mUserNameEditText, mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        mIdEditText = findViewById(R.id.edit_text_id);
        mUserNameEditText = findViewById(R.id.edit_text_user_name);
        mPasswordEditText = findViewById(R.id.edit_text_password);
        findViewById(R.id.button_send).setOnClickListener(this);
        findViewById(R.id.button_check_server).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send:
                AlertDialog.Builder builder = new AlertDialog.Builder(InsertActivity.this);
                builder.setMessage("Do you want to insert data to server directly?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                insertDataToServer();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                insertDataToSqLite();
                            }
                        }).create().show();
                break;
            case R.id.button_check_server:
                checkServerResponse();
                break;
        }
    }

    private void insertDataToServer() {
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Constants.INSERT_URL, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.e(TAG, "onResponse: " + response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("user_name", mUserNameEditText.getText().toString());
//                params.put("password", mPasswordEditText.getText().toString());
//
//                return params;
//            }
//        };

        StringRequest request = new StringRequest(Request.Method.POST, Constants.INSERT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("username", "root");
//                params.put("password", "");
                params.put("user_name", mUserNameEditText.getText().toString());
                params.put("password", mPasswordEditText.getText().toString());

                return params;
            }
        };

//        StringRequest request = new StringRequest(Request.Method.GET, Constants.CONFIGURATION_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e(TAG, "onResponse: " + response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "onErrorResponse: " + error);
//            }
//        });

        AppController.getInstance(this).addToRequestQueue(request);
    }

    private void insertDataToSqLite() {
        User user = new User(Integer.parseInt(mIdEditText.getText().toString()),
                mUserNameEditText.getText().toString(), mPasswordEditText.getText().toString());
        user.save();
    }

    private void checkServerResponse() {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.CHECK_RESPONSE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "onResponse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error);
            }
        });

        AppController.getInstance(this).addToRequestQueue(request);
    }
}
