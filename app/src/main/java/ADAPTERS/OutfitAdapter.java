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
    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;
    public interface OnItemClickListener {
        void onItemClicked(Outfit outfit);
    }
    public interface OnItemLongClickListener {
        boolean onItemLongClicked(Outfit outfit);
    }

    public OutfitAdapter(Context context, String outfit_name, Outfits outfits, int single_outfit_name, OnItemClickListener listener, OnItemLongClickListener longListener) {
        this.context = context;
        this.outfit_name = outfit_name;
        this.outfits = outfits;
        this.single_outfit_name = single_outfit_name;
        this.listener = listener;
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
            holder.bind(outfit, listener, longListener);
        }
    }

    @Override
    public int getItemCount() { return (outfits != null) ? outfits.size() : 0; }

    public static class OutfitHolder extends RecyclerView.ViewHolder {
        public TextView outfitName_tv;

        public OutfitHolder(@NonNull View itemView) {
            super(itemView);
            outfitName_tv = itemView.findViewById(R.id.outfitName_tv);
        }

        public void bind(Outfit outfit, OnItemClickListener listener, OnItemLongClickListener longListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClicked(outfit);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longListener != null)
                        return longListener.onItemLongClicked(outfit);
                    return false;
                }
            });
        }
    }
}
