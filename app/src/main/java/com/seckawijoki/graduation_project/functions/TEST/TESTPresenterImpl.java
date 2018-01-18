package com.seckawijoki.graduation_project.functions.TEST;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/25.
 */

class TESTPresenterImpl implements TESTContract.Presenter,
        TESTContract.View.ActionCallback,
        TESTContract.Model.DataCallback {
  private TESTContract.View view;
  private TESTContract.Model model;

  @Override
  public TESTContract.Presenter setView( TESTContract.View view ) {
    this.view = view;
    return this;
  }

  @Override
  public TESTContract.Presenter setModel( TESTContract.Model model ) {
    this.model = model;
    return this;
  }

  @Override
  public void initiate() {
    view.setActionCallback( this );
    model.setDataCallback( this );
    view.initiate();
    model.initiate();
  }

  @Override
  public void destroy() {
    view.destroy();
    model.destroy();
  }

  @Override
  public void onDisplaySendEditText( CharSequence s ) {
    view.displaySendEditText( s );
  }

  @Override
  public void onDisplayReceptionEditText( CharSequence s ) {
    view.displayReceptionEditText( s );
  }

  @Override
  public void onRequestConnectToServer() {
    model.requestConnectToServer();
  }

  @Override
  public void onRequestNormalDownload( CharSequence s ) {
    model.requestNormalDownload( s );
  }

  @Override
  public void onRequestGetSzIdAndName() {
    model.requestGetSzIdAndName();
  }
}