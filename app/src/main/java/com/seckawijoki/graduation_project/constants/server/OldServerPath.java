package com.seckawijoki.graduation_project.constants.server;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/10/25.
 */

public interface OldServerPath {
  int PORT = 8080;
  String WITH_WIFI_CONNECTION = "ws://192.168.191.1:" + PORT + "/";
  String GET_SZ_ID_AND_NAME = WITH_WIFI_CONNECTION + "getSzIdAndName";
  String GET_SZ_STOCK_ID = WITH_WIFI_CONNECTION + "getSzStockId";
  String GET_ACCESSIBLE_SINA_SZ_ID_AND_NAME = WITH_WIFI_CONNECTION + "getAccessibleSinaSzIdAndName";

}
