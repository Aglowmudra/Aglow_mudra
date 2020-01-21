package com.example.aglowmudra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.aglowmudra.LoanApplyActvity;
import com.example.aglowmudra.R;

public class WrongPassword extends DialogFragment {
    View mpolicyview;
    CheckBox checkBox;
    Button button;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        policy_dialog();
        initi();
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(mpolicyview)
                .create();
//        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {






            }
        });
        return alertDialog;
    }

    private void policy_dialog() {
        mpolicyview = getActivity().getLayoutInflater().inflate(R.layout.wrong_password, null);

    }
    public void initi(){
        checkBox=mpolicyview.findViewById(R.id.checkpolicy);
        button=mpolicyview.findViewById(R.id.policyclick);
    }

}
