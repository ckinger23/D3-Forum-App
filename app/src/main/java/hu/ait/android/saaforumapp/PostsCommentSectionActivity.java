package hu.ait.android.saaforumapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import hu.ait.android.saaforumapp.adapter.CommentsAdapter;
import hu.ait.android.saaforumapp.adapter.PostsAdapter;
import hu.ait.android.saaforumapp.data.Comment;
import hu.ait.android.saaforumapp.data.Post;

public class PostsCommentSectionActivity extends YouTubeBaseActivity {

    private CommentsAdapter commentsAdapter;
    private RelativeLayout commentSectionLayout;
    private Post postToComment = null;
    private String postToCommentKey = null;
    private TextView tvPostTitle;
    private TextView tvPostAuthor;
    private TextView tvPostBody;
    private ImageView ivPostPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_comment_section);

        if (getIntent().hasExtra(MainActivity.KEY_POST)) {
            postToComment = (Post) getIntent().getSerializableExtra(MainActivity.KEY_POST);
            postToCommentKey = getIntent().getStringExtra(MainActivity.KEY_OF_POST);
        }

        tvPostTitle = findViewById(R.id.tvPostTitle);
        tvPostTitle.setText(postToComment.getTitle());
        tvPostAuthor = findViewById(R.id.tvPostAuthor);
        tvPostAuthor.setText(postToComment.getAuthor());
        tvPostBody = findViewById(R.id.tvPostBody);
        tvPostBody.setText(postToComment.getBody());


        ivPostPhoto = findViewById(R.id.ivPostPhoto);
        if (postToComment.getImgUrl() != null && !TextUtils.isEmpty(postToComment.getImgUrl())) {
            ivPostPhoto.setVisibility(View.VISIBLE);
            Glide.with(PostsCommentSectionActivity.this).load(postToComment.getImgUrl()).into(ivPostPhoto);
        } else {
            ivPostPhoto.setVisibility(View.GONE);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabComments);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostsCommentSectionActivity.this, CreateCommentActivity.class);
                intent.putExtra(MainActivity.KEY_POST, postToComment);
                intent.putExtra(MainActivity.KEY_OF_POST, postToCommentKey);
                startActivityForResult(intent, 1001);
            }
        });

        ButterKnife.bind(this);

        commentsAdapter = new CommentsAdapter(this,
                FirebaseAuth.getInstance().getCurrentUser().getUid());
        RecyclerView recyclerView = findViewById(R.id.recyclerComments);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commentsAdapter);

        //initPosts();

        for (Comment comment : postToComment.getComments()) {
            commentsAdapter.addComment(comment);
        }
    }

    public void deleteComment(int index) {
        postToComment.getComments().remove(index);

        FirebaseDatabase.getInstance().getReference("posts").child(postToCommentKey).setValue(postToComment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            postToComment = (Post) data.getSerializableExtra(MainActivity.KEY_POST);
            commentsAdapter.updateComments(postToComment);
        }
    }


    private void showSnackBarMessage(String message) {
        Snackbar.make(commentSectionLayout,
                message,
                Snackbar.LENGTH_LONG).show();
    }

    ;


}



