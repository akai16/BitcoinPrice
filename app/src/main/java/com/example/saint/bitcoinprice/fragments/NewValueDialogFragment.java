package com.example.saint.bitcoinprice.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saint.bitcoinprice.R;
import com.example.saint.bitcoinprice.constants.SecurityPreferencesConstants;
import com.example.saint.bitcoinprice.utils.SecurityPreferences;

public class NewValueDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String DIALOG_TAG = "new_value_dialog_tag";
    public static final String OPTION_EXTRA = "option";
    public static final String TOTAL_INVESTIDO_EXTRA = "total_investido_extra";
    public static final String TOTAL_BITCOIN_EXTRA = "total_bitcoin_extra";

    String optionToBeUpdated;
    ViewHolder mViewHolder = new ViewHolder();
    SecurityPreferences preferences;

    public static NewValueDialogFragment newInstance(String option) {
        Bundle args = new Bundle();

        args.putString(NewValueDialogFragment.OPTION_EXTRA,option);

        NewValueDialogFragment fragment = new NewValueDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.preferences = new SecurityPreferences(getContext());
        this.optionToBeUpdated = this.getArguments().getString(NewValueDialogFragment.OPTION_EXTRA,"");

        if(this.optionToBeUpdated.equals(NewValueDialogFragment.TOTAL_INVESTIDO_EXTRA)) {
            this.optionToBeUpdated = SecurityPreferencesConstants.TOTAL_INVESTIDO;
        }

        if(this.optionToBeUpdated.equals(NewValueDialogFragment.TOTAL_BITCOIN_EXTRA)) {
            this.optionToBeUpdated = SecurityPreferencesConstants.TOTAL_BITCOIN;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dialog_update_values,container,false);

        this.mViewHolder.mTextValues = (TextView) layout.findViewById(R.id.text_dialog_value);
        this.mViewHolder.mEditValues = (EditText) layout.findViewById(R.id.edit_dialog_new_value);
        this.mViewHolder.mButtonCancel = (Button) layout.findViewById(R.id.dialog_button_cancel);
        this.mViewHolder.mButtonOk = (Button) layout.findViewById(R.id.dialog_button_ok);

        this.mViewHolder.mButtonCancel.setOnClickListener(this);
        this.mViewHolder.mButtonOk.setOnClickListener(this);

        return layout;
    }

    public void abrir(FragmentManager fm) {
        if(fm.findFragmentByTag(NewValueDialogFragment.DIALOG_TAG) == null) {
            show(fm,NewValueDialogFragment.DIALOG_TAG);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.dialog_button_cancel:
                this.dismiss();
                break;
            case R.id.dialog_button_ok:
                if (!this.mViewHolder.mEditValues.getText().toString().equals("")) {
                    Float newValue = Float.valueOf(this.mViewHolder.mEditValues.getText().toString());
                    this.preferences.saveFloatValue(this.optionToBeUpdated,newValue);
                }

                this.dismiss();
                break;
        }
    }

    private class ViewHolder {
        TextView mTextValues;
        EditText mEditValues;
        Button mButtonCancel;
        Button mButtonOk;
    }
}
