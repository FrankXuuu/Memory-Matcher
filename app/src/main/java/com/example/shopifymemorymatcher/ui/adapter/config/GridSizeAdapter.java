package com.example.shopifymemorymatcher.ui.adapter.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopifymemorymatcher.R;

import java.util.List;
import java.util.Locale;

public class GridSizeAdapter extends RecyclerView.Adapter<GridSizeAdapter.ViewHolder> {
    public int selected;

    private List<List<Integer>> sizes;
    private GridSizeOnClickListener gridSizeOnClickListener;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gridSizeText;

        ViewHolder(View view) {
            super(view);
            gridSizeText = view.findViewById(R.id.text);
            gridSizeText.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            selected = getAdapterPosition();
            notifyDataSetChanged();
            gridSizeOnClickListener.onClick(sizes.get(selected));
        }
    }

    public GridSizeAdapter(List<List<Integer>> sizes, GridSizeOnClickListener gridSizeOnClickListener) {
        this.sizes = sizes;
        this.gridSizeOnClickListener = gridSizeOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_text, viewGroup, false);
        return new GridSizeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        List<Integer> size = sizes.get(i);
        String matchNum = String.format(Locale.CANADA, "%d x %d", size.get(0), size.get(1));
        viewHolder.gridSizeText.setText(matchNum);

        Context context = viewHolder.itemView.getContext();
        if (selected == i) {
            final TypedValue color = new TypedValue ();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, color, true);
            viewHolder.gridSizeText.setTextColor(color.data);
            viewHolder.gridSizeText.setBackground(context.getDrawable(R.drawable.accent_rounded));
        } else {
            final TypedValue color = new TypedValue ();
            context.getTheme().resolveAttribute(R.attr.colorAccent, color, true);
            viewHolder.gridSizeText.setTextColor(color.data);
            viewHolder.gridSizeText.setBackground(context.getDrawable(R.drawable.primary_rounded));
        }
    }

    @Override
    public int getItemCount() {
        return sizes.size();
    }
}
