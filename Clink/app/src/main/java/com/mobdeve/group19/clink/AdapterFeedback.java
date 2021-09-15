package com.mobdeve.group19.clink;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;
import com.mobdeve.group19.clink.model.Profile;
import com.mobdeve.group19.clink.model.ProfileCallback;
import com.mobdeve.group19.clink.model.Review;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterFeedback extends RecyclerView.Adapter<ViewHolderFeedback> {
    private ArrayList<Review> data;
    private ApiHelper helper;

    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";

    private String authToken;
    private String recipeId;

    public AdapterFeedback(ArrayList<Review> data, String recipeId, String authToken) {

        helper = new ApiHelper();
        this.data = data;
        this.authToken = authToken;
        this.recipeId = recipeId;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderFeedback onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout, parent, false);
        ViewHolderFeedback ViewHolderFeedback = new ViewHolderFeedback(v);

        return ViewHolderFeedback;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderFeedback holder, int position) {

        helper.getUsername(data.get(position).getUserId(), new ProfileCallback() {
            @Override
            public void success(Message message, Profile profile) {
                holder.setUsernameTv(profile.getUsername());
                holder.setCommentTv(data.get(position).getReview());
                holder.setReviewId(data.get(position).getRecipeId());
            }

            @Override
            public void error(Message message) {
                holder.setUsernameTv(data.get(position).getUserId());
                holder.setCommentTv(data.get(position).getReview());
            }

            @Override
            public void failure(Throwable t) {
                t.printStackTrace();
            }
        });

        holder.setDeleteButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;helper.deleteReview(recipeId, data.get(position).getId(), authToken, new CustomCallback() {
                    @Override
                    public void success(Message message) {

                    }

                    @Override
                    public void error(Message message) {

                    }

                    @Override
                    public void failure(Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}