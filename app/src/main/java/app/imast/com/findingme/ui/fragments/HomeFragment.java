package app.imast.com.findingme.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.imast.com.findingme.Config;
import app.imast.com.findingme.R;
import app.imast.com.findingme.adapters.LostPetAdapter;
import app.imast.com.findingme.model.District;
import app.imast.com.findingme.model.LostPet;
import app.imast.com.findingme.model.Pet;
import app.imast.com.findingme.model.PetType;
import app.imast.com.findingme.util.VolleySingleton;

import static app.imast.com.findingme.util.LogUtils.LOGD;
import static app.imast.com.findingme.util.LogUtils.makeLogTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = makeLogTag(HomeFragment.class);

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private LostPetAdapter lostPetAdapter;
    private RecyclerView.LayoutManager lManager;

    private LinearLayout llSearchLostPets;
    private Spinner spnDistrict;
    private Spinner spnPetType;
    private Button btnSearchLostPets;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<District> distritoAdapter = new ArrayAdapter<District>(getActivity(), android.R.layout.simple_spinner_item, Config.lstDistrict);
        distritoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDistrict.setAdapter(distritoAdapter);


        ArrayAdapter<PetType> petTypeAdapter = new ArrayAdapter<PetType>(getActivity(), android.R.layout.simple_spinner_item, Config.lstPetType);
        petTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPetType.setAdapter(petTypeAdapter);

        btnSearchLostPets.setOnClickListener(this);

        List items = new ArrayList();
        items.add(new Pet("Fido", "http://www.cdc.gov/importation/images/dog2.jpg", "My Info sssssssssssssss"));
        items.add(new Pet("Ham Ham", "http://www.depi.vic.gov.au/__data/assets/image/0020/182261/Image_6a.jpg", "My Info sssssssssssssss"));
        items.add(new Pet("Lucas", "http://www.depi.vic.gov.au/__data/assets/image/0020/182261/Image_6a.jpg", "My Info sssssssssssssss"));

        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);

        lostPetAdapter = new LostPetAdapter(items);

        // Crear un nuevo adaptador
        adapter = lostPetAdapter;
        recycler.setAdapter(adapter);

        recycler.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        llSearchLostPets = (LinearLayout) view.findViewById(R.id.llSearchLostPets);
        spnDistrict = (Spinner) view.findViewById(R.id.district_spinner);
        spnPetType = (Spinner) view.findViewById(R.id.petType_spinner);
        btnSearchLostPets = (Button) view.findViewById(R.id.btnSearchLostPets);
        recycler = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_search:
                showHideSearch();
                break;
        }

        return true;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.btnSearchLostPets:
                searchLostPets();
                break;
            case R.id.my_recycler_view:

                break;
        }

    }

    private void searchLostPets() {

        showHideSearch();

        District district = (District) spnDistrict.getSelectedItem();
        PetType petType = (PetType) spnPetType.getSelectedItem();

        String parameters = String.format("?district_id=%d&race_id=%d", district.getId(), petType.getId());

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, "http://findmewebapp-eberttoribioupc.c9.io/lost_pets" + parameters, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        LOGD(TAG, "Response: " + response.toString());

                        Type listType = new TypeToken<ArrayList<LostPet>>() {}.getType();

                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

                        List<LostPet> lstLostPet = gson.fromJson(response.toString(), listType);

                        if (lstLostPet.size() > 0)
                        {

                            List items = new ArrayList();
                            items.add(new Pet("1 Fido", "http://www.cdc.gov/importation/images/dog2.jpg", "My Info sssssssssssssss"));
                            items.add(new Pet("2 Ham Ham", "http://www.depi.vic.gov.au/__data/assets/image/0020/182261/Image_6a.jpg", "My Info sssssssssssssss"));
                            items.add(new Pet("3 Lucas", "http://www.depi.vic.gov.au/__data/assets/image/0020/182261/Image_6a.jpg", "My Info sssssssssssssss"));

                            lostPetAdapter.clearData();
                            lostPetAdapter.setItems(items);
                            lostPetAdapter.notifyDataSetChanged();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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



        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsArrayRequest);

    }

    private void showHideSearch() {
        llSearchLostPets.setVisibility(llSearchLostPets.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
}
