package com.mobdeve.group19.clink;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderFeedback extends RecyclerView.ViewHolder {
    private TextView usernameTv, commentTv;
    //private ImageView userIv;

    public ViewHolderFeedback (@NonNull View itemView) {
        super(itemView);
        this.usernameTv = itemView.findViewById(R.id.namefbTv);
        this.commentTv = itemView.findViewById(R.id.commentfbTv);
        //this.userIv = itemView.findViewById(R.id.profpicIv);
    }

    public void setUsernameTv (String name) {this.usernameTv.setText(name);}

    public void setCommentTv (String time) {this.commentTv.setText(time);}

    //public void setUserIv (int cocktailIv) {this.userIv.setImageResource(cocktailIv);}
}
