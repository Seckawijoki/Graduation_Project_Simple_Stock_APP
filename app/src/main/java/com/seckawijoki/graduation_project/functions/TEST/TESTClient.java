package com.seckawijoki.graduation_project.functions.TEST;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/4.
 */

public class TESTClient extends WebSocketClient{
  private static final String TAG = "TESTClient";
  public TESTClient(URI serverUri) {
    super(serverUri);
  }

  @Override
  public void onOpen(ServerHandshake handShakeData) {
    Log.e(TAG, "onOpen: ");
  }

  @Override
  public void onMessage(String message) {
    Log.e(TAG, "onMessage: " + message);
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    Log.e(TAG, "onClose: " + reason);
  }

  @Override
  public void onError(Exception ex) {
    Log.e(TAG, "onError: ", ex);
  }
}
