package app.imast.com.findingme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;

import app.imast.com.findingme.R;
import app.imast.com.findingme.model.User;
import app.imast.com.findingme.util.VolleySingleton;

import static app.imast.com.findingme.util.LogUtils.LOGD;
import static app.imast.com.findingme.util.LogUtils.makeLogTag;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = makeLogTag(SignUpActivity.class);

    EditText edtUser, edtPass, edtPassConfirm, edtEmail;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtPassConfirm = (EditText) findViewById(R.id.edtPassConfirm);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);

        setupToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.btnSignUp:
                signUp();
                break;
        }

    }

    private void signUp() {

        String user = edtUser.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String passConfirm = edtPassConfirm.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        // Mapeo de los pares clave-valor
        HashMap<String, String> parametros = new HashMap();
        parametros.put("username", user);
        parametros.put("password", pass);
        parametros.put("password_confirm", passConfirm);
        parametros.put("email", email);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        "http://192.168.1.100:1337/user",
                        new JSONObject(parametros),
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                LOGD(TAG, "Response: " + response.toString());

                                //Type listType = new TypeToken<ArrayList<User>>() {}.getType();

                                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

                                User user = gson.fromJson(response.toString(), User.class);

                                if (user != null)
                                {
                                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                LOGD(TAG, "Error Volley:"+ error.getMessage());
                            }
                        }

        );

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
