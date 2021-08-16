package hu.ait.android.saaforumapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.saaforumapp.PostsCommentSectionActivity;
import hu.ait.android.saaforumapp.R;
import hu.ait.android.saaforumapp.data.Comment;
import hu.ait.android.saaforumapp.data.Post;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {


    private Context context;
    private List<Comment> commentList;
    private String uId;
    private int lastPosition = -1;

    public CommentsAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;

        commentList = new ArrayList<Comment>();
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.row_comment, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentsAdapter.ViewHolder holder, final int position) {
        final Comment comment = commentList.get(holder.getAdapterPosition());
        holder.commentBody.setText(comment.getBody());
        holder.commentAuthor.setText(comment.getAuthor());


        if (comment.getUid().equals(uId)) {
            holder.commentDelete.setVisibility(View.VISIBLE);
            int color = Color.parseColor(context.getString(R.string.color_for_comment));
            holder.commentDelete.setColorFilter(color);
            holder.commentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteComment(holder.getAdapterPosition());
                }
            });
        } else {
            holder.commentDelete.setVisibility(View.GONE);
        }


        setAnimation(holder.itemView, position);
    }

    public void deleteComment(int index) {
        commentList.remove(index);
        ((PostsCommentSectionActivity) context).deleteComment(index);
        notifyItemRemoved(index);
    }


    @Override
    public int getItemCount() {
        return commentList.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
        notifyDataSetChanged();
    }

    public void updateComments(Post post) {
        commentList.clear();
        for (Comment comment : post.getComments()) {
            commentList.add(comment);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder { //never contains logic

        public TextView commentAuthor;
        public TextView commentBody;
        public ImageButton commentDelete;


        public ViewHolder(View itemView) {
            super(itemView);
            commentAuthor = itemView.findViewById(R.id.commentAuthor);
            commentBody = itemView.findViewById(R.id.commentBody);
            commentDelete = itemView.findViewById(R.id.commentDelete);

        }
    }


}
