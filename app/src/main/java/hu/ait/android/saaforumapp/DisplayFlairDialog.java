package hu.ait.android.saaforumapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hu.ait.android.saaforumapp.adapter.PostsAdapter;
import hu.ait.android.saaforumapp.data.User;

public class DisplayFlairDialog extends android.support.v4.app.DialogFragment {

    public interface DisplayFlairHandler {

    }

    private DisplayFlairDialog.DisplayFlairHandler flairHandler;
    private CircleImageView teamPic;
    private TextView userName;
    private TextView userPosition;
    private User userToShow;
    private String userToShowID;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DisplayFlairHandler) {
            flairHandler = (DisplayFlairHandler) context;
        } else {
            throw new RuntimeException("Can Not Implement");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.user_flair));
        initDialogContent(builder);


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }

    private void initDialogContent(AlertDialog.Builder builder) {
        View dialogLayout = getActivity().getLayoutInflater().inflate(
                R.layout.display_user_flair_fragment, null, false);

        /*userName = dialogLayout.findViewById(R.id.userName);
        userPosition = dialogLayout.findViewById(R.id.userPosition);
        teamPic = dialogLayout.findViewById(R.id.teamPic);
        initUsers();

        userToShowID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


        switch (userToShow.getPosition()) {
            case 0:
                userPosition.setText("Catcher");
            case 1:
                userPosition.setText("Pitcher");
            case 2:
                userPosition.setText("Third Base");
            case 3:
                userPosition.setText("Shortstop");
            case 4:
                userPosition.setText("Second Base");
            case 5:
                userPosition.setText("First Base");
            case 6:
                userPosition.setText("Outfield");
            case 7:
                userPosition.setText("Closer");
            case 8:
                userPosition.setText("Left Bench");
        }

        teamPic.setImageResource(userToShow.getUserTeamAsEnum().getIconId());*/

        builder.setView(dialogLayout);
    }

    /*private void initUsers() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String theUserTeam = dataSnapshot.child(userToShowID).child("team").toString();
                userToShow.setTeam(Integer.parseInt(theUserTeam));
                String theUserPosition = dataSnapshot.child(userToShowID).child("position").toString();
                userToShow.setPosition(Integer.parseInt(theUserPosition));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
        }
    }
}
