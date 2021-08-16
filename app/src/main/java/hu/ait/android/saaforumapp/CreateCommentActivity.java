package hu.ait.android.saaforumapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.saaforumapp.data.Comment;
import hu.ait.android.saaforumapp.data.Post;

public class CreateCommentActivity extends AppCompatActivity {

    @BindView(R.id.etBody)
    EditText etBody;

    private Post postToComment = null;
    private String postToCommentKey = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        if (getIntent().hasExtra(MainActivity.KEY_POST)) {
            postToComment = (Post) getIntent().getSerializableExtra(MainActivity.KEY_POST);
            postToCommentKey = getIntent().getStringExtra(MainActivity.KEY_OF_POST);
        }

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSend)
    void sendClick() {
        uploadComment();
    }

    private void uploadComment() {

        Comment comment =
                new Comment(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        etBody.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        postToComment.addComment(comment);

        FirebaseDatabase.getInstance().getReference().
                child("posts").child(postToCommentKey).setValue(postToComment);

        Toast.makeText(this, R.string.comment_uploaded, Toast.LENGTH_SHORT).show();
        Intent intentResult = new Intent();
        intentResult.putExtra(MainActivity.KEY_POST, postToComment);
        setResult(RESULT_OK, intentResult);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
        }
    }
}
