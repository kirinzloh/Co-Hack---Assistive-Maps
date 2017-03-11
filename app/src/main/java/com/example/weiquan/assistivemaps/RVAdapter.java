package com.example.weiquan.assistivemaps;

/**
 * Created by weiqu on 3/11/2017.
 */


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private List<HomeScreen.Destination> dest_list;

    private DetailsInterface detailsListener;

    RVAdapter(List<HomeScreen.Destination> dest_list,DetailsInterface detailsListener){
        this.dest_list = dest_list;
        this.detailsListener = detailsListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView destName;
        ImageView destPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            destName = (TextView)itemView.findViewById(R.id.dest_name);
            destPhoto = (ImageView)itemView.findViewById(R.id.dest_photo);

            destPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailsListener.showDestDetails(view,destName.getText().toString(),(Integer) destPhoto.getTag());
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.destination_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder ViewHolder, int i) {
        ViewHolder.destName.setText(dest_list.get(i).name);
        ViewHolder.destPhoto.setImageResource(dest_list.get(i).photoId);
        ViewHolder.destPhoto.setTag(dest_list.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return dest_list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}