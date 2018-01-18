package com.seckawijoki.graduation_project.client;

import android.os.Handler;
import android.util.Log;

import com.seckawijoki.graduation_project.constants.server.OldServerPath;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/11 at 18:28.
 */

public class StockWebSocketClient extends WebSocketClient {
  private static final String TAG = "StockWebSocketClient";
  private static final int MAX_CONNECT_TIMES = 5;
  private OnWebSocketClientListener listener;
  private int connectionTimes = 0;
  public StockWebSocketClient() throws URISyntaxException {
    super(new URI(OldServerPath.WITH_WIFI_CONNECTION));
  }
  public StockWebSocketClient(String uriString) throws URISyntaxException {
    super(new URI(uriString));
  }
  public void setOnWebSocketClientListener(OnWebSocketClientListener listener){
    this.listener = listener;
  }
  @Override
  public void onOpen(ServerHandshake handShakeData) {
    listener.onOpen(handShakeData);
  }
  @Override
  public void onMessage(String message) {
    connectionTimes = 0;
    new Thread(() -> listener.onMessage(message)).start();
  }
  @Override
  public void onClose(int code, String reason, boolean remote) {
    listener.onClose(code, reason, remote);
  }
  @Override
  public void onError(Exception e) {
    if (e instanceof WebsocketNotConnectedException && connectionTimes <= MAX_CONNECT_TIMES){
      ++connectionTimes;
      Log.w(TAG, "onError(): Trying to reconnect...");
      new Handler().postDelayed(this::connect, 1000);
    } else {
      connectionTimes = 0;
      listener.onError(e);
    }
  }
}
