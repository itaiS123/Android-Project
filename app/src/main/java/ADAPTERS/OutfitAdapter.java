package ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.Outfit;
import il.co.shivhit.model.Outfits;

public class OutfitAdapter extends RecyclerView.Adapter<OutfitAdapter.OutfitHolder>{
    private Context context;
    private String outfit_name;
    private Outfits outfits;
    private int single_outfit_name;
    private OnItemLongClickListener longListener;
    public interface OnItemLongClickListener {
        boolean onItemLongClicked(Outfit outfit);
    }

    public OutfitAdapter(Context context, Outfits outfits, int single_outfit_name, OnItemLongClickListener longListener) {
        this.context = context;
        this.outfit_name = outfit_name;
        this.outfits = outfits;
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
        Outfit outfit = outfits.get(position);
        if (outfit != null) {
            holder.bind(outfit,longListener);
        }
    }

    public void setOutfits(Outfits outfits){
        this.outfits = outfits;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return (outfits != null) ? outfits.size() : 0; }

    public static class OutfitHolder extends RecyclerView.ViewHolder {
        public TextView outfitName_tv;

        public OutfitHolder(@NonNull View itemView) {
            super(itemView);
            outfitName_tv = itemView.findViewById(R.id.outfitName_tv);
        }

        public void bind(Outfit outfit, OnItemLongClickListener longListener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longListener != null)
                        return longListener.onItemLongClicked(outfit);
                    return false;
                }
            });
            outfitName_tv.setText(outfit.getNameOfOutfit().toString());
        }
    }
}
