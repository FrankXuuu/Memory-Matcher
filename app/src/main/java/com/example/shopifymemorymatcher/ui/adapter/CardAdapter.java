package com.example.shopifymemorymatcher.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.shopifymemorymatcher.R;
import com.example.shopifymemorymatcher.service.model.ProductImage;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<ProductImage> productImages;
    @Nullable
    private final CardOnClickListener cardOnClickListener;

    public boolean buffer = false;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView hidden, image;
        CardState cardState;

        ViewHolder(View view) {
            super(view);
            hidden = view.findViewById(R.id.hidden);
            hidden.setOnClickListener(this);
            image = view.findViewById(R.id.product_image);
            image.setOnClickListener(this);
            cardState = CardState.HIDDEN;
        }

        private void setCardState(CardState cardState) {
            switch (cardState) {
                case HIDDEN:
                    hidden.setVisibility(View.VISIBLE);
                    image.setVisibility(View.GONE);
                    this.cardState = CardState.HIDDEN;
                    break;
                case VISIBLE:
                    hidden.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    this.cardState = CardState.VISIBLE;
                    break;
                case REVEALED:
                    hidden.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    this.cardState = CardState.REVEALED;
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (buffer || cardState == CardState.REVEALED)
                return;

            switch (v.getId()) {
                case R.id.hidden:
                //case R.id.product_image:
                    setCardState(CardState.VISIBLE);
                    if (cardOnClickListener != null) {
                        cardOnClickListener.onClick(getAdapterPosition(), cardState);
                    }
                    break;
            }
        }
    }

    public CardAdapter(@Nullable CardOnClickListener cardOnClickListener) {
        this.cardOnClickListener = cardOnClickListener;
    }

    public void setProductImages(final List<ProductImage> productImages) {
        if (this.productImages == null) {
            this.productImages = productImages;
            notifyItemRangeInserted(0, productImages.size());
        } else {
            this.productImages = productImages;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_card, viewGroup, false);
        return new CardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder viewHolder, int i) {
        Log.d("BINDING", "..." + i);
        ProductImage productImage = productImages.get(i);
        String url = productImage.getSrc();
        Glide.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.image);
        viewHolder.setCardState(productImage.getCardState());
    }

    @Override
    public int getItemCount() {
        if (productImages != null)
            return productImages.size();
        else
            return 0;
    }
}
