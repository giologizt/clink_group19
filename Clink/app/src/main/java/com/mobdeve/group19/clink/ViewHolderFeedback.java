package com.mobdeve.group19.clink;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.group19.clink.model.ApiHelper;

public class ViewHolderFeedback extends RecyclerView.ViewHolder {
    private TextView usernameTv, commentTv;
    private TextView editTv, deleteTv;
    private LinearLayout llModify;

    private String reviewId;


    // Initializes the View Holder
    public ViewHolderFeedback (@NonNull View itemView) {
        super(itemView);
        this.usernameTv = itemView.findViewById(R.id.namefbTv);
        this.commentTv = itemView.findViewById(R.id.commentfbTv);

        this.editTv = itemView.findViewById(R.id.editreviewTv);
        this.deleteTv = itemView.findViewById(R.id.deletereviewTv);
        this.llModify = itemView.findViewById(R.id.ll_modify);

    }

    // Sets the Username TextView
    public void setUsernameTv (String name) {this.usernameTv.setText(name);}

    // Sets the Review TextView
    public void setCommentTv (String time) {this.commentTv.setText(time);}

    // Sets the Review ID
    public void setReviewId (String id) {this.reviewId = id;}

    // Sets the Edit Button Listener
    public void setEditButtonOnClickListenen(View.OnClickListener onClickListener) {
        this.editTv.setOnClickListener(onClickListener);
    }

    // Sets the Delete Button Listener
    public void setDeleteButtonOnClickListener(View.OnClickListener onClickListener) {
        this.deleteTv.setOnClickListener(onClickListener);
    }

    // Sets the visibility of the edit and delete review buttons depending if the user is the one who posted
    // the review.
    public void setEditDeleteTv(Boolean b) {
        if(b == false) {
            this.deleteTv.setVisibility(View.GONE);
            this.editTv.setVisibility(View.GONE);
            this.llModify.setVisibility(View.GONE);
        } else {
            this.deleteTv.setVisibility(View.VISIBLE);
            this.editTv.setVisibility(View.VISIBLE);
            this.llModify.setVisibility(View.VISIBLE);
        }
    }
}
