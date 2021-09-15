package com.mobdeve.group19.clink;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.group19.clink.model.ApiHelper;
import com.mobdeve.group19.clink.model.CustomCallback;
import com.mobdeve.group19.clink.model.Message;
import com.mobdeve.group19.clink.model.Profile;
import com.mobdeve.group19.clink.model.ProfileCallback;
import com.mobdeve.group19.clink.model.Review;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdapterFeedback extends RecyclerView.Adapter<ViewHolderFeedback> {
    private ArrayList<Review> data;
    private ApiHelper helper;

    private static final String JSON_TOKEN_KEY = "JSON_TOKEN_KEY";

    private String authToken;
    private String recipeId;

    ExecutorService executorService;

    public AdapterFeedback(ArrayList<Review> data, String recipeId, String authToken) {

        helper = new ApiHelper();
        this.data = data;
        this.authToken = authToken;
        this.recipeId = recipeId;

        this.executorService = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderFeedback onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout, parent, false);
        ViewHolderFeedback ViewHolderFeedback = new ViewHolderFeedback(v);

        ViewHolderFeedback.setDeleteButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        helper.deleteReview(recipeId, data.get(ViewHolderFeedback.getAdapterPosition()).getId(), authToken, new CustomCallback() {
                            @Override
                            public void success(Message message) {
                                data.remove(ViewHolderFeedback.getAdapterPosition());
                                Toast.makeText((ExpandActivity) v.getContext(), "Review Deleted", Toast.LENGTH_SHORT).show();
                                Log.d("Message", message.getMessage());
                                ((ExpandActivity) v.getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        notifyDataSetChanged();
                                    }
                                });
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
        });

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



    }

    public void refresh () {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}