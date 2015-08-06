package app.imast.com.findingme.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import app.imast.com.findingme.Config;
import app.imast.com.findingme.R;
import app.imast.com.findingme.model.PetType;
import app.imast.com.findingme.model.Race;
import app.imast.com.findingme.ui.MapsActivity;

public class LostPetInfoFragment extends Fragment implements View.OnClickListener {

    private TextView txvPetName, txvPetSex, txvPetType, txvPetRace, txvPetAge, txvPetVaccinated, txvPetLostInfo, txvPetInfo;
    private ImageButton btnLastLocation;

    public LostPetInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolBar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolBar.setTitle(Config.lostPet.getPet().getName());

        btnLastLocation.setOnClickListener(this);

        String petType = "";
        String petRace = "";

        for(PetType type : Config.lstPetType) {
            if (type.getId() == Config.lostPet.getPet().getPet_type_id()) {
                petType = type.getName();
                break;
            }
        }

        for(Race race : Config.lstRace) {
            if (race.getId() == Config.lostPet.getPet().getRace_id()) {
                petRace = race.getName();
                break;
            }
        }

        txvPetName.setText(Config.lostPet.getPet().getName());
        txvPetSex.setText(Config.lostPet.getPet().getSex());
        txvPetAge.setText(String.valueOf(Config.lostPet.getPet().getAge()));
        txvPetType.setText(petType);
        txvPetRace.setText(petRace);
        txvPetVaccinated.setText(Config.lostPet.getPet().getVaccinated());
        txvPetInfo.setText(Config.lostPet.getPet().getInformation());
        txvPetLostInfo.setText(Config.lostPet.getInfo());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lost_pet_info, container, false);

        txvPetName = (TextView) view.findViewById(R.id.txvPetName);
        txvPetSex = (TextView) view.findViewById(R.id.txvPetSex);
        txvPetType = (TextView) view.findViewById(R.id.txvPetType);
        txvPetRace = (TextView) view.findViewById(R.id.txvPetRace);
        txvPetAge = (TextView) view.findViewById(R.id.txvPetAge);
        txvPetVaccinated = (TextView) view.findViewById(R.id.txvPetVaccinated);
        txvPetLostInfo = (TextView) view.findViewById(R.id.txvPetLostInfo);
        txvPetInfo = (TextView) view.findViewById(R.id.txvPetInfo);
        btnLastLocation = (ImageButton) view.findViewById(R.id.btnlastLocation);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case  R.id.btnlastLocation:
                goToMapLastLocation();
                break;
        }

    }

    private void goToMapLastLocation() {

        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);

    }

}
