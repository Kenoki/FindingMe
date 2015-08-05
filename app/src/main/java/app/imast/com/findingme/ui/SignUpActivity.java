package app.imast.com.findingme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.imast.com.findingme.Config;
import app.imast.com.findingme.R;
import app.imast.com.findingme.model.User;
import app.imast.com.findingme.util.ValidationUtils;
import app.imast.com.findingme.util.VolleySingleton;

import static app.imast.com.findingme.util.LogUtils.LOGD;
import static app.imast.com.findingme.util.LogUtils.makeLogTag;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = makeLogTag(SignUpActivity.class);

    TextInputLayout tilUser, tilPass, tilPassConfirm, tilEmail;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tilUser = (TextInputLayout) findViewById(R.id.tilUser);
        tilPass = (TextInputLayout) findViewById(R.id.tilPass);
        tilPassConfirm = (TextInputLayout) findViewById(R.id.tilPassConfirm);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);

        setupToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.btnSignUp:
                signUp();
                break;
        }

    }

    private void signUp() {

        if(!ValidationUtils.isEmpty(tilUser, tilPass, tilPassConfirm, tilEmail)) {

            String user = tilUser.getEditText().getText().toString().trim();
            String pass = tilPass.getEditText().getText().toString().trim();
            String passConfirm = tilPassConfirm.getEditText().getText().toString().trim();
            String email = tilEmail.getEditText().getText().toString().trim();

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonUser = new JSONObject();

            JsonObjectRequest jsObjRequest = null;

            try {

                jsonObject.put("username", user);
                jsonObject.put("password", pass);
                jsonObject.put("password_confirmation", passConfirm);
                jsonObject.put("email", email);

                jsonUser.put("user", jsonObject);


                jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        "http://findmewebapp-eberttoribioupc.c9.io/users",
                        jsonUser,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                LOGD(TAG, "Response: " + response.toString());

                                //Type listType = new TypeToken<ArrayList<User>>() {}.getType();

                                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

                                User user = gson.fromJson(response.toString(), User.class);

                                if (user != null)
                                {

                                    Config.user = user;

                                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                LOGD(TAG, "Error Volley:"+ error.getMessage());
                            }
                        }

                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Accept", "application/json");

                        return params;
                    }
                };
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
