package app.imast.com.findingme.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = makeLogTag(LoginActivity.class);

    TextView txvRecoverLink;
    TextInputLayout tilUser, tilPass;
    Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txvRecoverLink = (TextView) findViewById(R.id.txvRecoverLink);

        tilUser = (TextInputLayout) findViewById(R.id.tilUser);
        tilPass = (TextInputLayout) findViewById(R.id.tilPass);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        txvRecoverLink.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
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
            case R.id.btnSignIn:
                login();
                break;
            case R.id.btnSignUp:
                goToSignUp();
                break;
            case R.id.txvRecoverLink:
                goToRecoverPassword();
                break;
        }

    }

    //region MÉTODOS CONFIGURACIÓN

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //endregion

    //region MÉTODOS DE OPERACIÓN

    private void login() {

        if (!ValidationUtils.isEmpty(tilUser, tilPass)) {

            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Finding Me");
            progress.setMessage("Espere mientras se verifica credenciales...");
            progress.show();


            String user = tilUser.getEditText().getText().toString().trim();
            String pass = tilPass.getEditText().getText().toString().trim();

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonUser = new JSONObject();
            try {

                jsonObject.put("username", user);
                jsonObject.put("password", pass);

                jsonUser.put("user", jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, "http://findmewebapp-eberttoribioupc.c9.io/login", jsonUser, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            LOGD(TAG, "Response: " + response.toString());

                            //Type listType = new TypeToken<ArrayList<User>>() {}.getType();

                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

                            User user = gson.fromJson(response.toString(), User.class);

                            if (user != null)
                            {
                                if (!TextUtils.isEmpty(user.getStatus())){
                                    progress.dismiss();
                                    Toast.makeText(getApplicationContext(), user.getStatus(), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                Config.user = user;
                                progress.dismiss();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.dismiss();
                            LOGD(TAG, "Error Volley:" + error.getMessage());
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("Accept", "application/json");

                    return params;
                }
            };



            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
        }
    }

    private void goToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void goToRecoverPassword() {
        Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);
        startActivity(intent);
    }

    //endregion

}
