package com.codepath.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parstagram.R;

public class DetailsFragment extends Fragment {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTime;
    private ImageView ivProfile;

    private ImageButton ibLike;
    private ImageButton ibComment;
    private ImageButton ibDirect;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUsername = view.findViewById(R.id.tvUsername);
        ivImage = view.findViewById(R.id.ivImage);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvTime = view.findViewById(R.id.tvTime);
        ivProfile = view.findViewById(R.id.ivProfile);
        ibLike = view.findViewById(R.id.ibLike);
        ibComment = view.findViewById(R.id.ibComment);
        ibDirect = view.findViewById(R.id.ibDirect);

        Bundle bundle = this.getArguments();
        String user = bundle.getString("user");
        String description = bundle.getString("description");
        tvUsername.setText(user);
        String sourceString = "<b>" + user + "</b> " + description;
        tvDescription.setText(Html.fromHtml(sourceString));

        Glide.with(getContext())
                .load(bundle.getString("pfp"))
                .circleCrop()
                .into(ivProfile);
        Glide.with(getContext())
                    .load(bundle.getString("image"))
                    .into(ivImage);
        tvTime.setText(bundle.getString("time"));
    }
}