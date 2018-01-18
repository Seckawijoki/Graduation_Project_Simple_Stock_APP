package com.seckawijoki.graduation_project.client;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/11 at 21:40.
 */

public interface OnWebSocketClientListener{
  void onOpen(ServerHandshake serverHandshake);
  void onMessage(String message);
  void onClose(int code, String reason, boolean remote);
  void onError(Exception e);
}
