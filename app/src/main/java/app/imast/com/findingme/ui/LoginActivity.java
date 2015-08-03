package app.imast.com.findingme.ui;

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

import org.json.JSONObject;

import java.util.HashMap;

import app.imast.com.findingme.R;
import app.imast.com.findingme.model.User;
import app.imast.com.findingme.util.GsonRequest;
import app.imast.com.findingme.util.VolleySingleton;

import static app.imast.com.findingme.util.LogUtils.LOGD;
import static app.imast.com.findingme.util.LogUtils.makeLogTag;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = makeLogTag(LoginActivity.class);

    EditText edtUser, edtPass;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);

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
        parametros.put("user", user);
        parametros.put("pass", pass);

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
                new GsonRequest<User>(
                        Request.Method.POST,
                        "",
                        User.class,
                        null,
                        new JSONObject(parametros),
                        new Response.Listener<User>(){
                            @Override
                            public void onResponse(User response) {
                                LOGD(TAG, response.toString());
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                LOGD(TAG, "Error Volley:"+ error.getMessage());
                            }
                        }
                )
        );

    }

    //endregion

}
