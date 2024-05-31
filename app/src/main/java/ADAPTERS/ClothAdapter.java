package ADAPTERS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import il.co.shivhit.androidproject.R;
import il.co.shivhit.helper.BitMapHelper;
import il.co.shivhit.model.Cloth;
import il.co.shivhit.model.Cloths;

public class ClothAdapter extends RecyclerView.Adapter<ClothAdapter.ClothHolder> {
    private Context context;
    private Cloths cloths;
    private int single_cloth_layout;
    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    public interface OnItemClickListener {
        void onItemClicked(Cloth cloth);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(Cloth cloth);
    }

    public ClothAdapter(Context context, Cloths cloths, int single_cloth_layout, OnItemClickListener listener, OnItemLongClickListener longListener) {
        this.context = context;
        this.cloths = cloths;
        this.single_cloth_layout = single_cloth_layout;
        this.listener = listener;
        this.longListener = longListener;
    }

    @NonNull
    @Override
    public ClothHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClothHolder(LayoutInflater.from(context).inflate(single_cloth_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClothHolder holder, int position) {
        Cloth cloth = cloths.get(position);
        if (cloth != null) {
            holder.bind(cloth, listener, longListener);
        }
    }

    @Override
    public int getItemCount() {
        return (cloths != null) ? cloths.size() : 0;
    }

    // Set list of cloths
    public void setCloths(Cloths cloths) {
        this.cloths = cloths;
        notifyDataSetChanged();        // Refresh RecyclerView
    }

    public static class ClothHolder extends RecyclerView.ViewHolder {
        public ImageView cloth_imageView;

        public ClothHolder(@NonNull View itemView) {
            super(itemView);
            cloth_imageView = itemView.findViewById(R.id.cloth_imageView);
        }

        public void bind(Cloth cloth, OnItemClickListener listener, OnItemLongClickListener longListener) {
            //------------- check -------------
            // Convert the String representation of the image to Bitmap
            Bitmap bitmap = BitMapHelper.decodeBase64(cloth.getImage());

            //------------- check -------------
            // Set the Bitmap to the ImageView
            cloth_imageView.setImageBitmap(bitmap);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClicked(cloth);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longListener != null)
                        return longListener.onItemLongClicked(cloth);
                    return false;
                }
            });
        }
    }
}
