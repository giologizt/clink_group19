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
    //private ImageView userIv;

    private String reviewId;
    private ApiHelper helper;

    public ViewHolderFeedback (@NonNull View itemView) {
        super(itemView);
        this.usernameTv = itemView.findViewById(R.id.namefbTv);
        this.commentTv = itemView.findViewById(R.id.commentfbTv);

        this.editTv = itemView.findViewById(R.id.editreviewTv);
        this.deleteTv = itemView.findViewById(R.id.deletereviewTv);
        this.llModify = itemView.findViewById(R.id.ll_modify);

        helper = new ApiHelper();

        //this.userIv = itemView.findViewById(R.id.profpicIv);
    }

    public void setUsernameTv (String name) {this.usernameTv.setText(name);}

    public void setCommentTv (String time) {this.commentTv.setText(time);}

    public void setReviewId (String id) {this.reviewId = id;}

    public void setEditButtonOnClickListenen(View.OnClickListener onClickListener) {
        this.editTv.setOnClickListener(onClickListener);
    }

    public void setDeleteButtonOnClickListener(View.OnClickListener onClickListener) {
        this.deleteTv.setOnClickListener(onClickListener);
    }

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
