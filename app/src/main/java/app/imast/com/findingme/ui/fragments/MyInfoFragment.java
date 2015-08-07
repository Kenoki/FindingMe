package app.imast.com.findingme.ui.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.imast.com.findingme.Config;
import app.imast.com.findingme.R;
import app.imast.com.findingme.model.District;
import app.imast.com.findingme.model.Profile;
import app.imast.com.findingme.util.ValidationUtils;
import app.imast.com.findingme.util.VolleySingleton;

import static app.imast.com.findingme.util.LogUtils.LOGD;
import static app.imast.com.findingme.util.LogUtils.makeLogTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = makeLogTag(MyInfoFragment.class);

    private NetworkImageView imgOwnerPhoto;
    private TextInputLayout tilEmail, tilFullname, tilSurname, tilAddress;
    private RadioGroup rbgSex;
    private Spinner spnDistrict;
    private FloatingActionButton fabSaveMyInfo;

    public MyInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imgOwnerPhoto.setDefaultImageResId(R.drawable.ic_action_contact);
        imgOwnerPhoto.setImageUrl(null, VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader());

        fabSaveMyInfo.setOnClickListener(this);

        ArrayAdapter<District> districtAdapter = new ArrayAdapter<District>(getActivity(), android.R.layout.simple_spinner_item, Config.lstDistrict);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDistrict.setAdapter(districtAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        spnDistrict = (Spinner) view.findViewById(R.id.spnDistrict);
        imgOwnerPhoto = (NetworkImageView) view.findViewById(R.id.ownerPhoto);
        tilEmail = (TextInputLayout) view.findViewById(R.id.tilEmail);
        tilFullname = (TextInputLayout) view.findViewById(R.id.tilFullname);
        tilSurname = (TextInputLayout) view.findViewById(R.id.tilSurname);
        tilAddress = (TextInputLayout) view.findViewById(R.id.tilAddress);
        rbgSex = (RadioGroup) view.findViewById(R.id.rbgSexo);
        fabSaveMyInfo = (FloatingActionButton) view.findViewById(R.id.fabSaveMyInfo);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fabSaveMyInfo:
                updateProfile();
                break;
        }
    }

    private void updateProfile() {

        if (!ValidationUtils.isEmpty(tilEmail, tilFullname, tilSurname, tilAddress)) {

            final ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setTitle("Finding Me");
            progress.setMessage("Actualizar Mi Información...");
            progress.show();

            int sexSelectedId = rbgSex.getCheckedRadioButtonId();
            RadioButton rbtnSexo = (RadioButton) getActivity().findViewById(sexSelectedId);

            District district = (District) spnDistrict.getSelectedItem();

            String name = tilFullname.getEditText().getText().toString().trim();
            String lastname = tilSurname.getEditText().getText().toString().trim();
            String address = tilAddress.getEditText().getText().toString().trim();
            String sex = rbtnSexo.getText().toString().trim();
            int districId = district.getId();


            JSONObject jsonObject = new JSONObject();
            JSONObject jsonMyInfo = new JSONObject();

            try {

                jsonObject.put("name", name);
                jsonObject.put("lastname", lastname);
                jsonObject.put("address", address);
                jsonObject.put("sex", sex);
                jsonObject.put("user_id", Config.user.getId());
                jsonObject.put("district_id", districId);

                jsonMyInfo.put("profile", jsonObject);

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.PUT, "http://findmewebapp-eberttoribioupc.c9.io/users/" + Config.user.getId() + "/profiles", jsonMyInfo, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                LOGD(TAG, "Response: " + response.toString());

                                try {
                                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

                                    Profile profile = gson.fromJson(response.toString(), Profile.class);

                                    if (profile != null)
                                    {
                                        progress.dismiss();
                                        Toast.makeText(getActivity().getApplicationContext(), "Se Actualizó Mi Información", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                    progress.dismiss();
                                    ex.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progress.dismiss();
                                Toast.makeText(getActivity().getApplicationContext(), "Error de Conexión con el Servidor", Toast.LENGTH_SHORT).show();
                                LOGD(TAG, "Error Volley:"+ error.getMessage());
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

                VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsObjRequest);

            } catch (JSONException e) {
                progress.dismiss();
                e.printStackTrace();
            } catch (Exception e) {
                progress.dismiss();
                e.printStackTrace();
            }

        }

    }
}
