package hu.ait.android.saaforumapp;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.saaforumapp.NewFlairDialog.FlairHandler;
import hu.ait.android.saaforumapp.adapter.PostsAdapter;
import hu.ait.android.saaforumapp.data.Post;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener, FlairHandler, DisplayFlairDialog.DisplayFlairHandler {


    public static final String KEY_POST = "KEY_POST";
    public static final String KEY_OF_POST = "KEY_OF_POST";
    public static final String TAG_NEW_ITEM = "TAG_NEW_ITEM";
    public static final String TAG_NEW_FLAIR = "TAG_NEW_FLAIR";
    public static final String TAG_NEW_FLAIR1 = "TAG_NEW_FLAIR";
    private PostsAdapter postsAdapter;
    private DrawerLayout drawer;
    private ChildEventListener eventListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreatePostActivity.class));
            }
        });


        ButterKnife.bind(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_nav_drawer,
                R.string.close_nav_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.add_post:
                        startActivity(new Intent(MainActivity.this, CreatePostActivity.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.flair:
                        setFlair();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.school_locations:
                        startActivity(new Intent(MainActivity.this, MapActivity.class));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.about:
                        drawer.closeDrawer(GravityCompat.START);
                        showSnackBarMessage(getString(R.string.this_app_was_made_by_ck));

                }

                return false;
            }
        });


        postsAdapter = new PostsAdapter(this,
                FirebaseAuth.getInstance().getCurrentUser().getUid());
        RecyclerView recyclerView = findViewById(R.id.recyclerPosts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initPosts();
    }

    private void setFlair() {
        new NewFlairDialog().show(getSupportFragmentManager(),
                TAG_NEW_FLAIR);
    }

    public void writePost(Post post, String postKey){

        Intent intent = new Intent();
        intent.setClass(this, PostsCommentSectionActivity.class);
        intent.putExtra(KEY_POST, post);
        intent.putExtra(KEY_OF_POST, postKey);
        startActivity(intent);
    }


    private void initPosts(){
        postsAdapter.deleteAllPost();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts");

        if (eventListener!=null){
            ref.removeEventListener(eventListener);
        }

        eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class); //data inserted was a post
                postsAdapter.addPost(post, dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                postsAdapter.removePostByKey(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addChildEventListener(eventListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FirebaseAuth.getInstance().signOut();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        else if (id == R.id.about){
            showSnackBarMessage(getString(R.string.this_app_was_made_by_ck));
        }
        else if (id == R.id.school_locations){
            startActivity(new Intent(MainActivity.this, MapActivity.class));
        }
        else if (id == R.id.flair){
            setFlair();
        }
        else{
            startActivity(new Intent(MainActivity.this, CreatePostActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showSnackBarMessage(String message) {
        Snackbar.make(drawer,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.hide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        }).show();
    }
}

