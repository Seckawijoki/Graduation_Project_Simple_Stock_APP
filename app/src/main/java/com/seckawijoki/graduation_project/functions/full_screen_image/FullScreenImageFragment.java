package com.seckawijoki.graduation_project.functions.full_screen_image;
/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/16 at 13:21.
 */

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentKey;

public class FullScreenImageFragment extends Fragment {
  public static FullScreenImageFragment newInstance() {
    Bundle args = new Bundle();
    FullScreenImageFragment fragment = new FullScreenImageFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_full_screen_image, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    View view = getView();
    ImageView img = view.findViewById(R.id.img_full_screen);
    String imageUri = getActivity().getIntent().getStringExtra(IntentKey.FULL_SCREEN_IMAGE_URI);
    img.setImageBitmap(
            BitmapFactory.decodeFile(imageUri)
    );
    img.setOnClickListener(v -> FullScreenImageFragment.this.getActivity().finish());
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

}