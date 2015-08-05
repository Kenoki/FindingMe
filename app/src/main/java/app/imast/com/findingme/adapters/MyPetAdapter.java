package app.imast.com.findingme.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import app.imast.com.findingme.R;
import app.imast.com.findingme.model.Pet;
import app.imast.com.findingme.util.VolleySingleton;

import static app.imast.com.findingme.util.LogUtils.makeLogTag;

/**
 * Created by aoki on 05/08/2015.
 */
public class MyPetAdapter extends RecyclerView.Adapter<MyPetAdapter.MyPetViewHolder>{

    private static final String TAG = makeLogTag(MyPetAdapter.class);

    private Context context;

    private List<Pet> items;

    public MyPetAdapter(Context context, List<Pet> items) {
        this.context = context;
        this.items = items;
    }

    public List<Pet> getItems() {
        return items;
    }

    public void setItems(List<Pet> items) {
        this.items = items;
    }

    public void clearData() {
        this.items.clear();
    }

    public static class MyPetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public NetworkImageView urlPetPhoto;
        public TextView txvPetName;
        public TextView txvPetInfo;

        private ClickListener clickListener;

        public MyPetViewHolder(View itemView) {
            super(itemView);
            urlPetPhoto = (NetworkImageView) itemView.findViewById(R.id.petPhoto);
            txvPetName = (TextView) itemView.findViewById(R.id.petName);
            txvPetInfo = (TextView) itemView.findViewById(R.id.petInfo);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public interface ClickListener {

            /**
             * Called when the view is clicked.
             *
             * @param v view that is clicked
             * @param position of the clicked item
             * @param isLongClick true if long click, false otherwise
             */
            public void onClick(View v, int position, boolean isLongClick);

        }

        /* Setter for listener. */
        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {

            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {

            // If long clicked, passed last variable as true.
            clickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }

    }

    public MyPetAdapter.MyPetViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_lost_pet_item, viewGroup, false);
        return new MyPetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyPetAdapter.MyPetViewHolder myPetViewHolder, int i) {

        String base_url_photo = "http://findmewebapp-eberttoribioupc.c9.io/system/pets/photos/000/000/";
        String folder = String.format("%03d", items.get(i).getId()) + "/";

        String final_url_photo = base_url_photo + folder + items.get(i).getPhoto_file_name();

        myPetViewHolder.urlPetPhoto.setImageUrl(final_url_photo, VolleySingleton.getInstance(context).getImageLoader());
        myPetViewHolder.urlPetPhoto.setDefaultImageResId(R.drawable.ic_my_pets);
        myPetViewHolder.urlPetPhoto.setErrorImageResId(R.drawable.ic_sign_out);
        myPetViewHolder.txvPetName.setText(items.get(i).getName());
        myPetViewHolder.txvPetInfo.setText(items.get(i).getInformation());

        myPetViewHolder.setClickListener(new MyPetViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                if (!isLongClick) {
                    //Config.lostPet = items.get(position);
                    //Intent intent = new Intent(context, );
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
