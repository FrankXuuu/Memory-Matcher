package com.example.shopifymemorymatcher.ui.adapter.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shopifymemorymatcher.R;

public class MatchNumAdapter extends RecyclerView.Adapter<MatchNumAdapter.ViewHolder> {
    public int selected;

    private int max;
    private MatchNumOnClickListener matchNumOnClickListener;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView matchNumText;

        ViewHolder(View view) {
            super(view);
            matchNumText = view.findViewById(R.id.text);
            matchNumText.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            selected = getAdapterPosition();
            notifyDataSetChanged();
            matchNumOnClickListener.onClick(selected+2);
        }
    }

    public MatchNumAdapter(int max, MatchNumOnClickListener matchNumOnClickListener) {
        this.max = max;
        this.matchNumOnClickListener = matchNumOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_text, viewGroup, false);

        int width = (viewGroup.getMeasuredWidth() -
                viewGroup.getPaddingStart() - viewGroup.getPaddingEnd()) / 3;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(params);

        return new MatchNumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String matchNum = String.valueOf(i+2);
        viewHolder.matchNumText.setText(matchNum);

        Context context = viewHolder.itemView.getContext();
        if (selected == i) {
            final TypedValue color = new TypedValue ();
            context.getTheme().resolveAttribute(R.attr.colorPrimary, color, true);
            viewHolder.matchNumText.setTextColor(color.data);
            viewHolder.matchNumText.setBackground(context.getDrawable(R.drawable.accent_rounded));
        } else {
            final TypedValue color = new TypedValue ();
            context.getTheme().resolveAttribute(R.attr.colorAccent, color, true);
            viewHolder.matchNumText.setTextColor(color.data);
            viewHolder.matchNumText.setBackground(context.getDrawable(R.drawable.primary_rounded));
        }
    }

    @Override
    public int getItemCount() {
        return max;
    }
}
