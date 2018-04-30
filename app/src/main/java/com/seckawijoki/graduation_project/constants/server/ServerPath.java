package com.seckawijoki.graduation_project.constants.server;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/23 at 18:09.
 */

public interface ServerPath {
  String WITH_LAPTOP_WIFI_CONNECTION = "http://192.168.191.1:80/";
  String IN_THE_DORMITORY = "http://172.29.25.78:80/";
  String IN_THE_LIBRARY = "http://172.27.10.123:80/";
  String AT_HOME = "http://192.168.1.105:80/";
  String WITH_WHOLESALE_DEPARTMENT_WIFI_CONNECTION = "http://192.168.0.107:80/";
  String BASE_PATH = WITH_LAPTOP_WIFI_CONNECTION;
//  String BASE_PATH = IN_THE_DORMITORY;
//  String BASE_PATH = AT_HOME;
//  String BASE_PATH = WITH_WHOLESALE_DEPARTMENT_WIFI_CONNECTION;
  /**
   * @Param
   * phone {@link String}
   * password {@link String}
   * mac {@link String} if needed
   * @return an {@link org.json.JSONObject} containing
   * {@link Integer} in  by key "LOGIN_STATUS"
   * {@link Long} in  by key "userId"
   */
  String LOGIN = BASE_PATH + "user/login";
  /**
   * @Param
   * account {@link String}
   * @return
   * {@link Boolean}
   */
  String LOGOUT = BASE_PATH + "user/logout";
  /**
   * @Param
   * phone {@link String}
   * password {@link String}
   * mac {@link String} if needed
   * @return
   * {@link Boolean}
   */
  String REGISTER = BASE_PATH + "user/register";
  /**
   * @Param
   * account {@link String}
   * @return
   * {@link Boolean}
   */
  String CHECK_PHONE_EXISTENT = BASE_PATH + "user/checkPhoneExistent";
  /**
   * @Param
   * null
   * @return
   * {@link String[]} in {@link org.json.JSONArray} as "stockId"
   */
  String GET_SZ_IDS = BASE_PATH + "sz/getStockIds";
  /**
   * @Param
   * null
   * @return
   * {@link String[]} in {@link org.json.JSONArray} as "stockId"
   */
  String GET_SH_IDS = BASE_PATH + "sh/getStockIds";
  /**
   * @Param
   * null
   * @return
   * {@link String[]} in {@link org.json.JSONArray} as "stockId"
   */
  String GET_CHUANG_YE_BAN_IDS = BASE_PATH + "chuangYeBan/getStockIds";
  /**
   * @Param
   * userId as {@link Long}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link Long} by key "favoriteGroupId"
   * {@link String} by key "favoriteGroupName"
   * {@link Integer} by key "stockCount"
   * {@link Integer} by key "rankWeight"
   */
  String GET_FAVORITE_GROUPS = BASE_PATH + "favorite/getFavoriteGroups";
  /**
   * @Param
   * userId as {@link Long}
   * favoriteGroupName {@link String}
   * @return an {@link org.json.JSONObject} containing
   * {@link Long} by key "favoriteGroupId"
   * {@link String} by key "favoriteGroupName"
   * {@link Integer} by key "rankWeight"
   */
  String ADD_FAVORITE_GROUP = BASE_PATH + "favorite/addFavoriteGroup";
  /**
   * @Param
   * userId as {@link Long}
   * favoriteGroupId {@link Long}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link Long} by key "favoriteGroupId"
   * {@link String} by key "favoriteGroupName"
   * {@link Integer} by key "stockCount"
   * {@link Integer} by key "rankWeight"
   */
  String DELETE_FAVORITE_GROUP = BASE_PATH + "favorite/deleteFavoriteGroup";
  /**
   * @Param
   * userId as {@link Long}
   * favoriteGroupId {@link Long[]}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link Long} by key "favoriteGroupId"
   * {@link String} by key "favoriteGroupName"
   * {@link Integer} by key "stockCount"
   * {@link Integer} by key "rankWeight"
   */
  String DELETE_FAVORITE_GROUPS = BASE_PATH + "favorite/deleteFavoriteGroups";
  /**
   * @Param
   * userId {@link Long}
   * oldGroupName {@link String}
   * newGroupName {@link String}
   * @return an {@link org.json.JSONObject} containing
   * {@link Long} by key "favoriteGroupId"
   * {@link Integer} by key "rankWeight"
   */
  String RENAME_FAVORITE_GROUP = BASE_PATH + "favorite/renameFavoriteGroup";
  /**
   * @Param
   * userId {@link Long}
   * favoriteGroupId {@link Long}
   * @return an {@link org.json.JSONObject}  containing
   * an {@link org.json.JSONArray} by key "stockTableId"
   * in which
   * the elements as {@link Long}
   */
  String GET_FAVORITE_STOCKS = BASE_PATH + "favorite/getFavoriteStocks";
  /**
   * @Param
   * userId {@link Long}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link Long} by key "stockTableId"
   * {@link Integer} by key "rankWeight"
   * {@link Boolean} by key "specialAttention"
   */
  String GET_ALL_FAVORITE_STOCKS = BASE_PATH + "favorite/getAllFavoriteStocks";
  /**
   * @Param
   * userId {@link Long}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link Long} by key "stockTableId"
   * {@link Integer} by key "rankWeight"
   * {@link Boolean} by key "specialAttention"
   */
  String GET_SPECIAL_FAVORITE_STOCKS = BASE_PATH + "favorite/getSpecialFavoriteStocks";
  /**
   * @Param
   * userId {@link Long}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link Long} by key "stockTableId"
   * {@link Integer} by key "rankWeight"
   * {@link Boolean} by key "specialAttention"
   */
  String GET_DEFAULT_SH_STOCKS = BASE_PATH + "favorite/getDefaultShStocks";
  /**
   * @Param
   * null
   * @return an {@link org.json.JSONObject}  containing
   * an {@link org.json.JSONArray} by key "stockTableId"
   * in which
   * the elements as {@link Long}
   */
  String GET_DEFAULT_SZ_STOCKS = BASE_PATH + "favorite/getDefaultSzStocks";
  /**
   * @Param
   * userId as {@link Long}
   * favoriteGroupId {@link Long}
   * stockTableId {@link Long[]}
   * @return an {@link org.json.JSONObject} containing
   * an {@link org.json.JSONArray} by key "stockTableId"
   * in which
   * the elements as {@link Long}
   */
  String ADD_FAVORITE_STOCKS = BASE_PATH + "favorite/addFavoriteStocks";
  /**
   * @Param
   * userId as {@link Long}
   * favoriteGroupId {@link Long}
   * stockTableId {@link Long[]}
   * @return an {@link org.json.JSONObject} containing
   * an {@link org.json.JSONArray} by key "stockTableId"
   * in which
   * the elements as {@link Long}
   */
  String DELETE_FAVORITE_STOCKS = BASE_PATH + "favorite/deleteFavoriteStocks";
  /**
   * @Param
   * userId as {@link Long}
   * favoriteGroupId {@link Long}
   * stockTableId {@link Long[]}
   * specialAttention {@link Boolean} with default value "true"
   * @return an {@link org.json.JSONObject} containing
   * an {@link org.json.JSONArray} by key "stockTableId"
   * in which
   * the elements as {@link Long}
   */
  String SET_SPECIAL_FAVORITE_STOCKS = BASE_PATH + "favorite/setSpecialFavoriteStocks";
  /**
   * @Param
   * userId as {@link Long}
   * stockTableId {@link Long}
   * @return an {@link org.json.JSONObject} containing
   * an {@link Integer} by key "rankWeight"
   */
  String SET_FAVORITE_STOCK_TOP = BASE_PATH + "favorite/setFavoriteStockTop";
  /**
   * @Param
   * stockTableId as {@link Long[]}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link String} by key "stockId"
   * {@link Integer} by key "stockType"
   * {@link String[]} by key "values" according to the below
   * [
     * 0：”大秦铁路”，股票名字；
     * 1：”27.55″，今日开盘价；
     * 2：”27.25″，昨日收盘价；
     * 3：”26.91″，当前价格；
     * 4：”27.55″，今日最高价；
     * 5：”26.20″，今日最低价；
     * 6：”26.91″，竞买价，即“买一”报价；
     * 7：”26.92″，竞卖价，即“卖一”报价；
     * 8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
     * 9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
     * 10：”4695″，“买一”申请4695股，即47手；
     * 11：”26.91″，“买一”报价；
     * 12：”57590″，“买二”
     * 13：”26.90″，“买二”
     * 14：”14700″，“买三”
     * 15：”26.89″，“买三”
     * 16：”14300″，“买四”
     * 17：”26.88″，“买四”
     * 18：”15100″，“买五”
     * 19：”26.87″，“买五”
     * 20：”3100″，“卖一”申报3100股，即31手；
     * 21：”26.92″，“卖一”报价
     * (22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
     * 30：”2008-01-11″，日期；
     * 31：”15:05:32″，时间；
     * 32：未知
   * ]
   */
  String GET_STOCKS = BASE_PATH + "stock/getStocks";
  /**
   * @Param
   * stockTableId as {@link Long[]}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link String} by key "stockId"
   * {@link Integer} by key "stockType"
   * {@link String[]} by key "values" according to the below
   * [
   * 0：股票名称
   * 1：当前价格
   * 2：当前点数
   * 3：涨跌率
   * 4：成交量 as "volume"
   * 5：成交额 as "turnover"
   * ]
   */
  String GET_QUOTATIONS = BASE_PATH + "stock/getQuotations";
  /**
   * @Param
   * userId {@link Long}
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link Long} by key "stockTableId"
   * {@link String} by key "stockId"
   * {@link Integer} by key "stockType"
   * {@link Long} by the key "searchTime"
   * {@link Boolean} by key "favorite"
   * ]
   */
  String GET_STOCK_SEARCH_HISTORY = BASE_PATH + "search/getStockSearchHistory";
  /**
   * @Param
   * userId {@link Long}
   * search {@link String}
   * limit {@link Integer} with default value "20"
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONObject} containing
   * {@link Long} by key "stockTableId"
   * {@link String} by key "stockId"
   * {@link String} by key "stockName"
   * {@link Integer} by key "stockType"
   * {@link Boolean} by key "favorite"
   */
  String SEARCH_FOR_MATCHED_STOCKS = BASE_PATH + "search/searchForMatchedStocks";
  /**
   * @Param
   * userId {@link Long}
   * stockTableId {@link String}
   * @return
   * {@link org.json.JSONObject} containing
   * {@link Long} by the key "searchTime"
   */
  String SAVE_STOCK_SEARCH_HISTORY = BASE_PATH + "search/saveStockSearchHistory";
  /**
   * @Param
   * userId {@link Long}
   * @return
   * {@link Boolean}
   */
  String CLEAR_STOCK_SEARCH_HISTORY = BASE_PATH + "search/clearStockSearchHistory";
  /**
   * @Param
   * userId {@link Long}
   * stockTableId {@link Long}
   * @return an {@link org.json.JSONObject} containing
   * {@link Long} by the key "favoriteGroupId"
   * {@link Integer} by the key "rankWeight"
   */
  String ADD_FAVORITE_STOCK_FROM_SEARCH = BASE_PATH + "search/addFavoriteStockFromSearch";
  /**
   * @Param
   * userId {@link Long}
   * stockTableId {@link Long}
   * @return
   * {@link Boolean}
   */
  String DELETE_FAVORITE_STOCK_FROM_SEARCH = BASE_PATH + "search/deleteFavoriteStockFromSearch";
  /**
   * @Param
   * stockTableId {@link Long}
   * kLineType {@link Integer}
   * @return a gif file
   * {@link java.io.File}
   */
  String GET_K_LINE = BASE_PATH + "stock/getKLineChart";
  /**
   * @Param
   * null
   * @return an {@link org.json.JSONArray}
   * in which
   * the elements as {@link org.json.JSONArray} containing the below
   * [
   * 0：股票名称
   * 1：当前价格
   * 2：当前点数
   * 3：涨跌率
   * 4：rankWeight
   * ]
   */
  String GET_FOREIGN_STOCKS = BASE_PATH + "stock/getForeignStocks";
  /**
   * @Param
   * stockTableId {@link Long}
   * kLineType {@link Integer}
   * @return an {@link org.json.JSONObject} containing
   * {@link String} by the key "kLineChartFileName"
   */
  String GET_K_LINE_CHART_FILE_NAME= BASE_PATH + "stock/getKLineChartFileName";
  /**
   * @Param
   * null
   * @return a gif file
   * {@link java.io.File}
   */
  String GET_DEFAULT_PORTRAIT = BASE_PATH + "user/getDefaultPortrait";
  /**
   * @Param
   * DIRECTLY portraitFileName
   * @return a file
   * {@link java.io.File}
   */
  String GET_USER_PORTRAIT = BASE_PATH + "user/getUserPortrait";
  /**
   * @Param
   * userId {@link Long}
   * @return an {@link org.json.JSONObject} containing
   * {@link String} by the key "phone"
   * {@link String} by the key "email"
   * {@link String} by the key "nickname"
   * {@link String} by the key "portraitFileName"
   */
  String GET_USER_INFO = BASE_PATH + "user/getUserInfo";
  /**
   * @Param
   * the portrait file as {@link java.io.File}
   * userId as {@link Long}
   * @return an {@link org.json.JSONObject} containing
   * {@link Boolean} by the key "result"
   */
  String UPLOAD_USER_PORTRAIT = BASE_PATH + "user/uploadUserPortrait";
  /**
   * @Param
   * userId {@link Long}
   * nickname {@link String}
   * @return a {@link Boolean} 
   */
  String CHANGE_NICKNAME = BASE_PATH + "user/changeNickname";
  /**
   * @Param
   * userId {@link Long}
   * email {@link String}
   * @return a {@link Boolean}
   */
  String CHANGE_EMAIL = BASE_PATH + "user/changeEmail";
  /**
   * @Param
   * null
   * @return a {@link org.json.JSONArray}
   * in which the element as {@link org.json.JSONObject} containing
   * {@link Double} by the key "tradePrice"
   * {@link Integer} by the key "tradeCount"
   * {@link java.util.Date} by the key "tradeDate"
   * {@link java.sql.Time} by the key "tradeTime"
   * {@link Long} by the key "stockTableId"
   * {@link String} by the key "stockName"
   * {@link String} by the key "stockId"
   * {@link Integer} by the key "stockType"
   * {@link Long} by the key "userId"
   * {@link String} by the key "nickname"
   */
  String GET_ALL_TRANSACTIONS = BASE_PATH + "transaction/getAllTransactions";
  /**
   * @Param
   * userId {@link Long}
   * @return a {@link org.json.JSONArray}
   * in which the element as {@link org.json.JSONObject} containing
   * {@link Double} by the key "tradePrice"
   * {@link Integer} by the key "tradeCount"
   * {@link java.util.Date} by the key "tradeDate"
   * {@link java.sql.Time} by the key "tradeTime"
   * {@link Long} by the key "stockTableId"
   * {@link String} by the key "stockName"
   * {@link String} by the key "stockId"
   * {@link Integer} by the key "stockType"
   * {@link String} by the key "nickname"
   */
  String GET_USER_TRANSACTIONS = BASE_PATH + "transaction/getUserTransactions";
  /**
   * @Param
   * userId {@link Long}
   * stockTableId {@link Long}
   * tradePrice {@link Double}
   * tradeCount {@link Integer}
   * @return {@link org.json.JSONObject} containing
   * {@link Boolean} by the key "result"
   */
  String ADD_TRANSACTION = BASE_PATH + "transaction/addTransaction";
  /**
   * @Param
   * null
   * @return {@link org.json.JSONObject} containing
   * {@link Integer} by the key "versionCode"
   * {@link String} by the key "versionName"
   * {@link String} by the key "versionDescription"
   */
  String GET_LATEST_APP_VERSION = BASE_PATH + "app/getLatestAppVersion";
  /**
   * @Param
   * null
   * @return {@link org.json.JSONObject} containing
   * {@link Integer} by the key "versionCode"
   */
  String GET_LATEST_APP_VERSION_CODE = BASE_PATH + "app/getLatestAppVersionCode";
  /**
   * @Param
   * null
   * @return an APK file
   */
  String GET_LATEST_APK = BASE_PATH + "app/getLatestApk";
  /**
   * @Param
   * topStockType {@link Integer}
   * topStockOrder {@link Boolean}
   * topStockCount {@link Integer}
   * @return a {@link org.json.JSONArray}
   * in which the element as {@link org.json.JSONObject} containing
   * {@link Long} by the key "stockTableId"
   * {@link String} by the key "stockId"
   * {@link String} by the key "stockName"
   * {@link Double} by the key "currentPrice"
   * {@link Double} by the key "fluctuationRate"
   * {@link Long} by the key "turnover"
   * {@link Long} by the key "volume"
   */
  String GET_TOP_SEVERAL_STOCKS = BASE_PATH + "recommendations/getTopSeveralStocks";
  /**
   * @Param
   * hotStockCount {@link Integer}
   * @return a {@link org.json.JSONArray}
   * in which the element as {@link org.json.JSONObject} containing
   * {@link Long} by the key "stockTableId"
   * {@link String} by the key "stockId"
   * {@link String} by the key "stockName"
   * {@link Double} by the key "currentPrice"
   * {@link Double} by the key "fluctuationRate"
   * {@link Long} by the key "turnover"
   * {@link Long} by the key "volume"
   */
  String GET_HOT_STOCKS = BASE_PATH + "recommendations/getHotStocks";
}
