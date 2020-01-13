package Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.aglowmudra.R;

public class FragmentPolicy extends DialogFragment {

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
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.d("TAG","Value is creted in  system"+isChecked);
                    }
                });

            }
        });
        return alertDialog;
    }

    private void policy_dialog() {
        mpolicyview = getActivity().getLayoutInflater().inflate(R.layout.policy_fragment, null);

    }
    public void initi(){
        checkBox=mpolicyview.findViewById(R.id.checkpolicy);
        button=mpolicyview.findViewById(R.id.policyclick);
    }


}
