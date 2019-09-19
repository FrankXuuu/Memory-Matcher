package com.example.shopifymemorymatcher.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shopifymemorymatcher.R;
import com.example.shopifymemorymatcher.service.model.ProductImage;
import com.example.shopifymemorymatcher.ui.adapter.card.CardAdapter;
import com.example.shopifymemorymatcher.ui.adapter.card.CardOnClickListener;
import com.example.shopifymemorymatcher.ui.adapter.card.CardState;
import com.example.shopifymemorymatcher.ui.adapter.card.CustomGridLayoutManager;
import com.example.shopifymemorymatcher.ui.shared.SessionManager;
import com.example.shopifymemorymatcher.viewmodel.MemoryMatcherViewModel;

import java.util.List;
import java.util.Locale;

public class MemoryMatcherActivity extends FragmentActivity implements View.OnClickListener{
    private TextView scoreText;
    private int uniques, columns, matches;
    private CardAdapter cardAdapter;
    private SessionManager sessionManager;

    public MemoryMatcherViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sessionManager = new SessionManager(this);

        if (sessionManager.isDark())
            setTheme(R.style.AppThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MemoryMatcherViewModel.class);

        uniques = sessionManager.getUniques();
        columns = sessionManager.getColumns();
        matches = sessionManager.getMatches();

        setContentView(R.layout.activity_memory_matcher);
        setUpView();

        observeViewModel(viewModel);
    }

    private void setUpView() {
        View closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(this);

        View shuffleButton = findViewById(R.id.shuffle_button);
        shuffleButton.setOnClickListener(this);

        scoreText = findViewById(R.id.score);

        setUpRecycler();
    }

    private void setUpRecycler() {
        RecyclerView recyclerView = findViewById(R.id.card_recycler);
        CustomGridLayoutManager customGridLayoutManager = new CustomGridLayoutManager(this, columns) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(customGridLayoutManager);

        cardAdapter = new CardAdapter(cardOnClickListener);
        recyclerView.setAdapter(cardAdapter);
    }

    private void observeViewModel(MemoryMatcherViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getProductImagesObservable().observe(this,
                (@Nullable List<ProductImage> productImages) -> {
            if (productImages != null) {
                // binding.setIsLoading(false);
                cardAdapter.setProductImages(productImages);
            }
        });

        viewModel.getScoreObservable().observe(this,
                (@Nullable Integer score) -> {
            if (score != null) {
                scoreText.setText(String.format(Locale.CANADA, "%d", score));
                sessionManager.setMaxScore(score);
                if (score == uniques*matches) {
                    DialogFragment dialogFragment = WinDialogFragment.newInstance(uniques*matches);
                    dialogFragment.show(getSupportFragmentManager(), "win");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_button:
                onBackPressed();
                break;
            case R.id.shuffle_button:
                viewModel.shuffleProductImages();
                break;
        }
    }

    private final CardOnClickListener cardOnClickListener = new CardOnClickListener() {
        @Override
        public void onClick(int index, CardState cardState) {
            if (cardState == CardState.VISIBLE) {
                if (viewModel.selected.size() == matches-1) {
                    cardAdapter.buffer = true;
                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        cardAdapter.buffer = false;
                        viewModel.addSelected(index);
                    }, 500);
                } else
                    viewModel.addSelected(index);
            }
        }
    };
}
