package ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.Outfit;
import il.co.shivhit.model.Outfits;

public class OutfitAdapter extends RecyclerView.Adapter<OutfitAdapter.OutfitHolder>{
    private Context context;
    private String outfit_name;
    private ArrayList<String> outfits_names;
    private int single_outfit_name;
    private OnItemLongClickListener longListener;
    public interface OnItemLongClickListener {
        boolean onItemLongClicked(String outfit_name);
    }

    public OutfitAdapter(Context context, ArrayList<String> outfits_names, int single_outfit_name, OnItemLongClickListener longListener) {
        this.context = context;
        this.outfit_name = outfit_name;
        this.outfits_names = outfits_names;
        this.single_outfit_name = single_outfit_name;
        this.longListener = longListener;
    }

    @NonNull
    @Override
    public OutfitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OutfitAdapter.OutfitHolder(LayoutInflater.from(context).inflate(single_outfit_name, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitHolder holder, int position) {
        String outfit_name = outfits_names.get(position);
        if (outfit_name != null) {
            holder.bind(outfit_name, longListener);
        }
    }

    public void setOutfits_names(ArrayList<String> outfits_names){
        this.outfits_names = outfits_names;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return (outfits_names != null) ? outfits_names.size() : 0; }

    public static class OutfitHolder extends RecyclerView.ViewHolder {
        public TextView outfitName_tv;

        public OutfitHolder(@NonNull View itemView) {
            super(itemView);
            outfitName_tv = itemView.findViewById(R.id.outfitName_tv);
        }

        public void bind(String outfit_name, OnItemLongClickListener longListener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longListener != null)
                        return longListener.onItemLongClicked(outfit_name);
                    return false;
                }
            });
            outfitName_tv.setText(outfit_name);
        }
    }
}
