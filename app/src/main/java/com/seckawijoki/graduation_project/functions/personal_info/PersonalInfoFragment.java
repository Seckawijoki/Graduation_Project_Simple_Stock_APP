package com.seckawijoki.graduation_project.functions.personal_info;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/20 at 21:05.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.seckawijoki.graduation_project.R;
import com.seckawijoki.graduation_project.constants.common.IntentAction;
import com.seckawijoki.graduation_project.constants.common.ActivityRequestCode;
import com.seckawijoki.graduation_project.constants.common.IntentKey;
import com.seckawijoki.graduation_project.db.client.User;
import com.seckawijoki.graduation_project.functions.settings.SettingsDecoration;
import com.seckawijoki.graduation_project.tools.FileTools;
import com.seckawijoki.graduation_project.others.GlideImageLoader;
import com.seckawijoki.graduation_project.utils.ToastUtils;
import com.seckawijoki.graduation_project.utils.ViewUtils;

import java.io.File;
import java.util.ArrayList;

public class PersonalInfoFragment extends Fragment
        implements PersonalInfoContract.View, OnPersonalInfoListener, View.OnClickListener {
  private AppCompatActivity activity;
  private View view;
  private static final String TAG = "PersonalInfoFragment";
  private ActionCallback callback;
  private boolean hasChanged;
  private PersonalInfoAdapter adapter;
  private AlertDialog portraitChangeDialog;
  private AlertDialog nicknameChangeDialog;
  private EditText etNewNickname;

  public static PersonalInfoFragment newInstance() {
    Bundle args = new Bundle();
    PersonalInfoFragment fragment = new PersonalInfoFragment();
    fragment.setArguments(args);
    return fragment;
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    return inflater.inflate(R.layout.fragment_personal_info, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    activity = ( (AppCompatActivity) getActivity() );
    view = getView();
    Toolbar tb = view.findViewById(R.id.tb_personal_info);
    activity.setSupportActionBar(tb);
    RecyclerView rv = view.findViewById(R.id.rv_personal_info);
    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    rv.addItemDecoration(new SettingsDecoration(activity));
    rv.setAdapter(adapter = new PersonalInfoAdapter(getActivity())
            .setOnPersonalInfoListener(this));

//    String filePath = "//GraduationProject//sh000001_min.gif";
//    callback.onRequestUploadPortrait(filePath);
  }

  private void onImagePickerResult(Intent data){
    Log.d(TAG, "onImagePickerResult()\n: data = " + data);
    ArrayList<ImageItem> imageItemList =
            (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
    if ( imageItemList == null || imageItemList.size() <= 0 ) return;
    ImageItem imageItem = imageItemList.get(0);
    Log.d(TAG, "onImagePickerResult()\n: imageItem.path = " + imageItem.path);
    callback.onRequestUploadPortrait(imageItem.path);
  }

  private void onSystemCameraResult(Intent data){
    Log.d(TAG, "onSystemCameraResult()\n: ActivityRequestCode.SYSTEM_CAMERA: data = " + data);
    File file = FileTools.getTempImage();
    Log.d(TAG, "onSystemCameraResult()\n: file = " + file);
    callback.onRequestUploadPortrait(file.getPath());
    if ( data != null ) { //可能尚未指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
      //返回有缩略图
      if ( data.hasExtra("data") ) {
        Bitmap thumbnail = data.getParcelableExtra("data");
        //得到bitmap后的操作
      }
    } else {
      //由于指定了目标uri，存储在目标uri，intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
      // 通过目标uri，找到图片
      // 对图片的缩放处理
      // 操作
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if ( resultCode == ImagePicker.RESULT_CODE_ITEMS &&
            requestCode == ActivityRequestCode.IMAGE_PICKER ) {
      onImagePickerResult(data);
    }
    if ( requestCode == ActivityRequestCode.SYSTEM_CAMERA ) {
      onSystemCameraResult(data);
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_personal_info, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch ( item.getItemId() ) {
      case android.R.id.home:
        Intent intent = activity.getIntent();
        if ( hasChanged ) {
          intent.putExtra(IntentKey.HAS_CHANGED_PERSONAL_INFO, true);
          activity.setResult(Activity.RESULT_OK, intent);
        } else {
          activity.setResult(Activity.RESULT_CANCELED);
        }
        activity.finish();
        break;
      case R.id.menu_message:
        startActivity(new Intent(IntentAction.MESSAGE));
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  private void changeUserPortrait() {
    View view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_on_user_portrait_click, null);
    ViewUtils.bindOnClick(this,
            view.findViewById(R.id.tv_photograph),
            view.findViewById(R.id.tv_select_from_album),
            view.findViewById(R.id.tv_check_portrait)
    );
    portraitChangeDialog = new AlertDialog.Builder(getContext())
            .setTitle(R.string.label_select_portrait)
            .setView(view)
            .create();
    portraitChangeDialog.show();
  }

  private void changeNickname(String nickname) {
    View view = getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_on_nickname_change, null);
    ViewUtils.bindOnClick(this,
            view.findViewById(R.id.btn_cancel),
            view.findViewById(R.id.btn_save)
    );
    etNewNickname = view.findViewById(R.id.et_new_nickname);
    nicknameChangeDialog = new AlertDialog.Builder(getContext())
            .setTitle(R.string.label_change_nickname)
            .setView(view)
            .create();
    nicknameChangeDialog.show();
    etNewNickname.setText(nickname);
    etNewNickname.selectAll();
    etNewNickname.requestFocus();
  }

  @Override
  public void onPersonalInfoClick(int position, User user) {
    switch ( position ) {
      case PersonalInfoAdapter.PersonalInfoOptions.PORTRAIT:
        changeUserPortrait();
        break;
      case PersonalInfoAdapter.PersonalInfoOptions.NICKNAME:
        changeNickname(user.getNickname());
        break;
      case PersonalInfoAdapter.PersonalInfoOptions.EMAIL:

        break;
      default:

        break;
    }
  }


  private void openImagePickerCamera(){
    if ( !FileTools.isSDCardExistent() ) {
      ToastUtils.show(activity, R.string.error_sdcard_not_exist);
      return;
    }
    Intent intent = new Intent(activity, ImageGridActivity.class);
    ImagePicker.getInstance().setImageLoader(new GlideImageLoader(activity));
    intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,
            new ArrayList<ImageItem>());
    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
    startActivityForResult(intent, ActivityRequestCode.IMAGE_PICKER);
  }

  private void openImagePickerAlbums() {
    Resources resources = activity.getResources();
    int width = resources.getInteger(R.integer.int_image_picker_crop_width);
    int height = resources.getInteger(R.integer.int_image_picker_crop_height);
    width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            width, getResources().getDisplayMetrics());
    height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            height, getResources().getDisplayMetrics());
    int outputX = resources.getInteger(R.integer.int_image_picker_output_x);
    int outputY = resources.getInteger(R.integer.int_image_picker_output_y);
    ImagePicker imagePicker = ImagePicker.getInstance();
    imagePicker.setImageLoader(new GlideImageLoader(activity));
    imagePicker.setCrop(true);
    imagePicker.setMultiMode(false);
    imagePicker.setShowCamera(true);
    imagePicker.setStyle(CropImageView.Style.RECTANGLE);
    imagePicker.setFocusWidth(width);
    imagePicker.setFocusHeight(height);
    imagePicker.setOutPutX(outputX);
    imagePicker.setOutPutY(outputY);
    Intent intent = new Intent(getActivity(), ImageGridActivity.class);
    intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,
            new ArrayList<ImageItem>());
    startActivityForResult(intent, ActivityRequestCode.IMAGE_PICKER);
  }

  @Override
  public void onClick(View v) {
    switch ( v.getId() ) {
      case R.id.tv_photograph:
        portraitChangeDialog.dismiss();
        openImagePickerCamera();
        break;
      case R.id.tv_select_from_album:
        portraitChangeDialog.dismiss();
        openImagePickerAlbums();
        break;
      case R.id.tv_check_portrait:
        portraitChangeDialog.dismiss();
        break;
      case R.id.btn_cancel:
        nicknameChangeDialog.dismiss();
        break;
      case R.id.btn_save:
        String nickname = etNewNickname.getText().toString();
        nicknameChangeDialog.dismiss();
        callback.onRequestChangeNickname(nickname);
        break;
    }
  }

  @Override
  public void onPresenterInitiate() {

  }

  @Override
  public void destroy() {

  }

  @Override
  public void setActionCallback(ActionCallback callback) {
    this.callback = callback;
  }

  @Override
  public void displayUpdatePersonalInfo() {
    hasChanged = true;
    adapter.setUser().notifyDataSetChanged();
  }

}