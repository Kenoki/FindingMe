package app.imast.com.findingme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.imast.com.findingme.R;
import app.imast.com.findingme.model.Pet;

/**
 * Created by aoki on 04/08/2015.
 */
public class LostPetAdapter extends RecyclerView.Adapter<LostPetAdapter.LostPetViewHolder> {

    private List<Pet> items;

    public List<Pet> getItems() {
        return items;
    }

    public void setItems(List<Pet> items) {
        this.items = items;
    }

    public void clearData() {
        this.items.clear();
    }

    public static class LostPetViewHolder extends RecyclerView.ViewHolder {

        public ImageView urlPetPhoto;
        public TextView txvPetName;
        public TextView txvPetInfo;

        public LostPetViewHolder(View itemView) {
            super(itemView);
            urlPetPhoto = (ImageView) itemView.findViewById(R.id.petPhoto);
            txvPetName = (TextView) itemView.findViewById(R.id.petName);
            txvPetInfo = (TextView) itemView.findViewById(R.id.petInfo);
        }
    }

    public LostPetAdapter(List<Pet> items) {
        this.items = items;
    }

    public LostPetAdapter.LostPetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_lost_pet_item, viewGroup, false);
        return new LostPetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LostPetAdapter.LostPetViewHolder lostPetViewHolder, int i) {
        lostPetViewHolder.urlPetPhoto.setImageResource(R.mipmap.ic_launcher);
        lostPetViewHolder.txvPetName.setText(items.get(i).getName());
        lostPetViewHolder.txvPetInfo.setText(items.get(i).getInfo());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
