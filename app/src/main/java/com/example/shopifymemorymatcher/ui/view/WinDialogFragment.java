package com.example.shopifymemorymatcher.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopifymemorymatcher.R;

import java.util.Locale;

public class WinDialogFragment extends DialogFragment implements View.OnClickListener {
    private int score = 24;

    public static WinDialogFragment newInstance(int score) {

        Bundle args = new Bundle();
        args.putInt("score", score);

        WinDialogFragment fragment = new WinDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
            score = bundle.getInt("score", 24);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_win, container, false);

        view.findViewById(R.id.close_button).setOnClickListener(this);
        view.findViewById(R.id.reset_button).setOnClickListener(this);

        TextView scoreMessage = view.findViewById(R.id.score_message);
        scoreMessage.setText(String.format(Locale.CANADA, "Score: %d", score));

        return view;
    }

    @Override
    public void onClick(View v) {
        MemoryMatcherActivity activity = (MemoryMatcherActivity) getActivity();
        switch (v.getId()) {
            case R.id.close_button:
                if (activity != null)
                    activity.onBackPressed();
                dismiss();
                break;
            case R.id.reset_button:
                if (activity != null)
                    activity.viewModel.resetProductImages();
                dismiss();
                break;
        }
    }
}
