package com.example.shopifymemorymatcher.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopifymemorymatcher.R;

public class WinDialogFragment extends DialogFragment implements View.OnClickListener {

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
        View view = inflater.inflate(R.layout.fragment_win, container, false);

        view.findViewById(R.id.close_button).setOnClickListener(this);
        view.findViewById(R.id.reset_button).setOnClickListener(this);

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
