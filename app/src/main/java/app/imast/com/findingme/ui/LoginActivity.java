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
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = makeLogTag(LoginActivity.class);

    TextView txvRecoverLink;
    EditText edtUser, edtPass;
    Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txvRecoverLink = (TextView) findViewById(R.id.txvRecoverLink);

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        txvRecoverLink.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        setupToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        String user = edtUser.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();

        // Mapeo de los pares clave-valor
        HashMap<String, String> parametros = new HashMap();
        parametros.put("username", user);
        parametros.put("password", pass);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, "http://192.168.1.100:1337/user", new JSONObject(parametros), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        LOGD(TAG, "Response: " + response.toString());

                        //Type listType = new TypeToken<ArrayList<User>>() {}.getType();

                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

                        User user = gson.fromJson(response.toString(), User.class);

                        if (user != null)
                        {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LOGD(TAG, "Error Volley:"+ error.getMessage());
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);

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
