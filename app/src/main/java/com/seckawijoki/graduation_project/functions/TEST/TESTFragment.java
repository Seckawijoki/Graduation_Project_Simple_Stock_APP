package com.seckawijoki.graduation_project.functions.TEST;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.seckawijoki.graduation_project.R;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/4.
 */

public class TESTFragment extends Fragment implements TESTContract.View, View.OnClickListener {
  private ActionCallback callback;
  private EditText etContent;
  private TextView etReception;
  private Button btnConnect;
  private Button btnSend;
  private Button btnGetSz;
  private CharSequence content = "";
  public static TESTFragment newInstance() {
    Bundle args = new Bundle();
    TESTFragment fragment = new TESTFragment();
    fragment.setArguments(args);
    return fragment;
  }
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_test, container, false);
    etContent = view.findViewById(R.id.et_test_content);
    etReception = view.findViewById(R.id.et_test_reception);
    btnConnect = view.findViewById( R.id.btn_connect_to_server );
    btnSend = view.findViewById(R.id.btn_test_client_send);
    btnGetSz = view.findViewById(R.id.btn_test_get_sz_id_and_name);
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    etContent.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        content = s;
      }
      @Override
      public void afterTextChanged(Editable s) {}
    });
    btnConnect.setOnClickListener( this );
    btnSend.setOnClickListener(this);
    btnGetSz.setOnClickListener(this);
  }

  @Override
  public void initiate() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displaySendEditText( CharSequence s) {
    etContent.setText(s);
  }

  @Override
  public void displayReceptionEditText( CharSequence s) {
    etReception.setText(s);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.btn_connect_to_server:
        callback.onRequestConnectToServer();
        break;
      case R.id.btn_test_client_send:
        callback.onRequestNormalDownload(content);
        break;
      case R.id.btn_test_get_sz_id_and_name:
        callback.onRequestGetSzIdAndName();
        break;
    }
  }
}
