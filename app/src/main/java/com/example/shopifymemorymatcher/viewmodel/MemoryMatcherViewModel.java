package com.example.shopifymemorymatcher.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.shopifymemorymatcher.R;
import com.example.shopifymemorymatcher.service.model.ProductImage;
import com.example.shopifymemorymatcher.service.shopify.ShopifyRepository;
import com.example.shopifymemorymatcher.ui.adapter.CardState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryMatcherViewModel extends AndroidViewModel {
    private final LiveData<List<ProductImage>> productImagesObservable;
    private final LiveData<Integer> scoreObservable;
    public final List<Integer> selected = new ArrayList<>();

    public MemoryMatcherViewModel(Application application) {
        super(application);

        Context context = application.getApplicationContext();
        String accessToken = context.getString(R.string.access_token);
        productImagesObservable = ShopifyRepository.getInstance().getProductImages(accessToken);

        scoreObservable = new MutableLiveData<>();
        ((MutableLiveData<Integer>) scoreObservable).setValue(0);
    }

    public LiveData<List<ProductImage>> getProductImagesObservable() {
        return productImagesObservable;
    }

    public LiveData<Integer> getScoreObservable() {
        return scoreObservable;
    }

    public void resetProductImages() {
        List<ProductImage> productImages = productImagesObservable.getValue();
        if (productImages == null)
            productImages = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            productImage.setCardState(CardState.HIDDEN);
        }
        Collections.shuffle(productImages);
        ((MutableLiveData<List<ProductImage>>) productImagesObservable).setValue(productImages);

        Integer score = 0;
        ((MutableLiveData<Integer>) scoreObservable).setValue(score);
    }

    public void addSelected(int index) {
        selected.add(index);
        if (selected.size() == 2) {
            int i2 = selected.remove(1);
            int i1 = selected.remove(0);

            List<ProductImage> productImages = productImagesObservable.getValue();
            if (productImages != null) {
                ProductImage p1 = productImages.get(i1);
                ProductImage p2 = productImages.get(i2);

                // if same, set states to revealed
                if (p1.getId().equals(p2.getId())) {
                    p1.setCardState(CardState.REVEALED);
                    productImages.set(i1, p1);
                    p2.setCardState(CardState.REVEALED);
                    productImages.set(i2, p2);

                    Integer score = scoreObservable.getValue();
                    if (score == null)
                        score = 0;
                    score += 2;
                    ((MutableLiveData<Integer>)scoreObservable).setValue(score);
                }
                // otherwise set states back to hidden
                else {
                    p1.setCardState(CardState.HIDDEN);
                    productImages.set(i1, p1);
                    p2.setCardState(CardState.HIDDEN);
                    productImages.set(i2, p2);
                }

                ((MutableLiveData<List<ProductImage>>)productImagesObservable)
                        .setValue(productImages);
            }
        }
    }
}
