package hu.ait.android.saaforumapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import hu.ait.android.saaforumapp.data.User;

public class NewFlairDialog extends DialogFragment {

    public interface FlairHandler {

    }

    private FlairHandler flairHandler;
    private Spinner dropSchool;
    private Spinner dropPosition;
    private User userToChange;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FlairHandler) {
            flairHandler = (FlairHandler) context;
        } else {
            throw new RuntimeException("Can Not Implement");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.create_flair);
        initDialogContent(builder);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //keep empty
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();


    }

    private void initDialogContent(AlertDialog.Builder builder) {
        View dialogLayout = getActivity().getLayoutInflater().inflate(
                R.layout.flair_fragment, null, false);

        setupSpinners(dialogLayout);

        builder.setView(dialogLayout);

    }

    private void setupSpinners(View dialogLayout) {
        dropSchool = dialogLayout.findViewById(R.id.dropSchool);
        ArrayAdapter<CharSequence> schoolAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.schools, android.R.layout.simple_spinner_item);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropSchool.setAdapter(schoolAdapter);

        dropPosition = dialogLayout.findViewById(R.id.dropPosition);
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.positions, android.R.layout.simple_spinner_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropPosition.setAdapter(positionAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //if not selected?
                    handleFlairSet();
                    dialog.dismiss();
                }

            });
        }
    }

    private void handleFlairSet() {
        String key =
                FirebaseDatabase.getInstance().getReference(). //reference = tree
                        child("users").push().getKey();

        User currUser = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                dropSchool.getSelectedItemPosition(),
                dropPosition.getSelectedItemPosition());

        FirebaseDatabase.getInstance().getReference().
                child("users").child(key).setValue(currUser);

        Toast.makeText(getContext(), R.string.user_flair_set, Toast.LENGTH_SHORT);
    }
}