package com.example.shopifymemorymatcher.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shopifymemorymatcher.R;
import com.example.shopifymemorymatcher.ui.adapter.config.GridSizeAdapter;
import com.example.shopifymemorymatcher.ui.adapter.config.GridSizeOnClickListener;
import com.example.shopifymemorymatcher.ui.adapter.config.MatchNumAdapter;
import com.example.shopifymemorymatcher.ui.adapter.config.MatchNumOnClickListener;
import com.example.shopifymemorymatcher.ui.shared.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigureSizeDialogFragment extends DialogFragment {
    private SessionManager sessionManager;
    private int matches, columns, rows;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getContext());
        matches = sessionManager.getMatches();
        columns = sessionManager.getColumns();
        rows = sessionManager.getUniques()*matches/columns;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configure_size, container, false);

        setUpView(view);

        return view;
    }

    private void setUpView(View view) {
        RecyclerView matchNumRecycler = view.findViewById(R.id.match_num_recycler);
        matchNumRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayout.HORIZONTAL, false));
        MatchNumAdapter adapter = new MatchNumAdapter(3, matchNumOnClickListener);
        adapter.selected = matches-2;
        matchNumRecycler.setAdapter(adapter);

        RecyclerView gridSizeRecycler = view.findViewById(R.id.grid_size_recycler);
        gridSizeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        GridSizeAdapter adapter1 = new GridSizeAdapter(getGridSizes(), gridSizeOnClickListener);

        Log.d("HE????", Arrays.asList(rows, columns).toString());

        adapter1.selected = getGridSizes().indexOf(Arrays.asList(rows, columns));
        gridSizeRecycler.setAdapter(adapter1);
    }

    private List<List<Integer>> getGridSizes() {
        List<List<Integer>> gridSizes = new ArrayList<>();

        List<Integer> gridSize = Arrays.asList(6, 4);
        gridSizes.add(gridSize);

        gridSize = Arrays.asList(6, 6);
        gridSizes.add(gridSize);

        return gridSizes;
    }

    public MatchNumOnClickListener matchNumOnClickListener = new MatchNumOnClickListener() {
        @Override
        public void onClick(int matches) {
            sessionManager.setMatches(matches);
        }
    };

    public GridSizeOnClickListener gridSizeOnClickListener = new GridSizeOnClickListener() {
        @Override
        public void onClick(List<Integer> gridSize) {
            int row = gridSize.get(0);
            int column = gridSize.get(1);

            sessionManager.setRows(row);
            sessionManager.setColumns(column);
        }
    };
}
