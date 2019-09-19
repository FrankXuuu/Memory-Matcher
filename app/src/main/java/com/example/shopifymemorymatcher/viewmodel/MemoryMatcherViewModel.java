package com.example.shopifymemorymatcher.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.shopifymemorymatcher.R;
import com.example.shopifymemorymatcher.service.model.ProductImage;
import com.example.shopifymemorymatcher.service.shopify.ShopifyRepository;
import com.example.shopifymemorymatcher.ui.adapter.card.CardState;
import com.example.shopifymemorymatcher.ui.shared.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryMatcherViewModel extends AndroidViewModel {
    private final LiveData<List<ProductImage>> productImagesObservable;
    private final LiveData<Integer> scoreObservable;
    public final List<Integer> selected = new ArrayList<>();

    private int matches;

    public MemoryMatcherViewModel(Application application) {
        super(application);

        Context context = application.getApplicationContext();
        SessionManager sessionManager = new SessionManager(context);

        String accessToken = context.getString(R.string.access_token);
        int uniques = sessionManager.getUniques();
        matches = sessionManager.getMatches();
        productImagesObservable = ShopifyRepository.getInstance().getProductImages(accessToken, uniques, matches);

        scoreObservable = new MutableLiveData<>();
        ((MutableLiveData<Integer>) scoreObservable).setValue(0);
    }

    public LiveData<List<ProductImage>> getProductImagesObservable() {
        return productImagesObservable;
    }

    public LiveData<Integer> getScoreObservable() {
        return scoreObservable;
    }

    public void shuffleProductImages() {
        List<ProductImage> productImages = productImagesObservable.getValue();
        if (productImages == null)
            productImages = new ArrayList<>();
        Collections.shuffle(productImages);
        ((MutableLiveData<List<ProductImage>>) productImagesObservable).setValue(productImages);
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
        if (selected.size() == matches) {
            List<ProductImage> compares = new ArrayList<>();

            List<ProductImage> productImages = productImagesObservable.getValue();
            if (productImages != null) {

                for (Integer i : selected) {
                    compares.add(productImages.get(i));
                }

                // compare if all visible cards are equal
                boolean equals = true;
                for (int i = 0; i < compares.size(); i++) {
                    for (int j = i+1; j < compares.size(); j++) {
                        if (!compares.get(i).getId().equals(compares.get(j).getId())) {
                            equals = false;
                            break;
                        }
                    }

                    if (!equals)
                        break;
                }

                for (int i = 0; i < compares.size(); i++) {
                    int selectedIndex = selected.get(i);
                    ProductImage productImage = compares.get(i);

                    // if same, set states to revealed
                    if (equals)
                        productImage.setCardState(CardState.REVEALED);
                    // else hide it again
                    else
                        productImage.setCardState(CardState.HIDDEN);

                    productImages.set(selectedIndex, productImage);
                }

                if (equals) {
                    Integer score = scoreObservable.getValue();
                    if (score == null)
                        score = 0;
                    score += matches;
                    ((MutableLiveData<Integer>)scoreObservable).setValue(score);
                }

                ((MutableLiveData<List<ProductImage>>)productImagesObservable)
                        .setValue(productImages);
            }

            selected.clear();
        }
    }
}
