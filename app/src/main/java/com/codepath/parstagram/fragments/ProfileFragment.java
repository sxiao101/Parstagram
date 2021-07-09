package com.codepath.parstagram.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parstagram.LoginActivity;
import com.codepath.parstagram.Post;
import com.codepath.parstagram.PostsAdapter;
import com.codepath.parstagram.ProfileAdapter;
import com.codepath.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    public static final String TAG = "PostsFragment";

    private RecyclerView rvPics;
    private ProfileAdapter adapter;
    private List<Post> allPosts;
    private ImageView ivProfile;
    private TextView tvUser;

    private Button btnLogout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setVisibility(View.GONE);

        ivProfile = view.findViewById(R.id.ivProfile);
        tvUser = view.findViewById(R.id.tvUser);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                goLoginActivity();
            }
        });

        rvPics = view.findViewById(R.id.rvPics);
        allPosts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), allPosts);

        rvPics.setAdapter(adapter);
        rvPics.setLayoutManager(new GridLayoutManager(getContext(), 3));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvPics.addItemDecoration(itemDecoration);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String userId = bundle.getString("user");
            Log.i("ProfileFragment", userId);

            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", userId);
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    load(objects.get(0));
                }
            });
        } else {
            load(ParseUser.getCurrentUser());
        }

        getView().setVisibility(View.VISIBLE);
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void load(ParseUser user) {
        tvUser.setText(user.getUsername());


        ParseFile pf = user.getParseFile("pfp");
        Glide.with(getContext())
                .load(pf.getUrl())
                .circleCrop()
                .into(ivProfile);

        queryPosts(user);
    }

    private void queryPosts(ParseUser user) {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Specify the object id
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, user);
        // order posts by creation date (newest first)
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }
}