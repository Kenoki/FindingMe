package app.imast.com.findingme.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.imast.com.findingme.R;
import app.imast.com.findingme.adapters.LostPetAdapter;
import app.imast.com.findingme.model.Pet;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        List items = new ArrayList();
        items.add(new Pet("Fido", "http://www.cdc.gov/importation/images/dog2.jpg", "My Info sssssssssssssss"));
        items.add(new Pet("Ham Ham", "http://www.depi.vic.gov.au/__data/assets/image/0020/182261/Image_6a.jpg", "My Info sssssssssssssss"));
        items.add(new Pet("Lucas", "http://www.depi.vic.gov.au/__data/assets/image/0020/182261/Image_6a.jpg", "My Info sssssssssssssss"));

        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new LostPetAdapter(items);
        recycler.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recycler = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        return view;
    }


}
