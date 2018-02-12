package com.seckawijoki.graduation_project.tools;


import com.seckawijoki.graduation_project.constants.server.KLineType;
import com.seckawijoki.graduation_project.constants.server.StockType;
import com.seckawijoki.graduation_project.constants.sina.SinaKLineType;
import com.seckawijoki.graduation_project.constants.sina.SinaServerPath;
import com.seckawijoki.graduation_project.constants.sina.SinaStockType;
import com.seckawijoki.graduation_project.utils.OkHttpUtils;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/5 at 17:17.
 */

public class SinaRequestTools {
  private SinaRequestTools() {
  }
  public static String getSinaKLineType(int kLineType) {
    String sinaKLineType;
    switch ( kLineType ) {
      default:
      case KLineType.MINUTE:
        sinaKLineType = SinaKLineType.MINUTE;
        break;
      case KLineType.DAILY:
        sinaKLineType = SinaKLineType.DAILY;
        break;
      case KLineType.WEEKLY:
        sinaKLineType = SinaKLineType.WEEKLY;
        break;
      case KLineType.MONTHLY:
        sinaKLineType = SinaKLineType.MONTHLY;
        break;
    }
    return sinaKLineType;
  }

  public static String getSinaStockType(int stockType) {
    String sinaStockType;
    switch ( stockType ) {
      default:
      case StockType.SH:
        sinaStockType = SinaStockType.SH;
        break;
      case StockType.SZ:
      case StockType.CHUANG_YE_BAN:
        sinaStockType = SinaStockType.SZ;
        break;
    }
    return sinaStockType;
  }

  public static String getSinaKLineUrl(String stockId, int stockType, int kLineType) {
    String sinaKLineType = getSinaKLineType(kLineType);
    String sinaStockType = getSinaStockType(stockType);
    return getSinaKLineUrl(sinaStockType, stockId, sinaKLineType);
  }
  public static String getSinaKLineUrl(String sinaStockType, String stockId, String sinaKLineType) {
    return String.format(
            SinaServerPath.FORMAT_GET_K_LINE_CHART,
            sinaKLineType, sinaStockType, stockId
    );
  }

  public static String getSinaStockUrl(String stockId, int stockType) {
    String sinaStockType;
    String url;
    switch ( stockType ) {
      default:
      case StockType.SH:
        sinaStockType = SinaStockType.SH;
        break;
      case StockType.SZ:
      case StockType.CHUANG_YE_BAN:
        sinaStockType = SinaStockType.SZ;
        break;
    }
    url = String.format(
            SinaServerPath.FORMAT_GET_STOCK,
            sinaStockType, stockId
    );
    return url;
  }

  public static String getSinaQuotationUrl(String stockId, int stockType) {
    String sinaStockType;
    String url = null;
    switch ( stockType ) {
      default:
      case StockType.SH:
        sinaStockType = SinaStockType.SH;
        break;
      case StockType.SZ:
      case StockType.CHUANG_YE_BAN:
        sinaStockType = SinaStockType.SZ;
        break;
    }
    url = String.format(
            SinaServerPath.GET_MARKET_STOCK_FORMAT,
            sinaStockType, stockId
    );
    return url;
  }

  /**
   * @param stockId  股票代码
   * @param stockType 股票类型
   * @return
   * 数据含义分别为：
      0：”大秦铁路”，股票名字；
      1：”27.55″，今日开盘价；
      2：”27.25″，昨日收盘价；
      3：”26.91″，当前价格；
      4：”27.55″，今日最高价；
      5：”26.20″，今日最低价；
      6：”26.91″，竞买价，即“买一”报价；
      7：”26.92″，竞卖价，即“卖一”报价；
      8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
      9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
      10：”4695″，“买一”申请4695股，即47手；
      11：”26.91″，“买一”报价；
      12：”57590″，“买二”
      13：”26.90″，“买二”
      14：”14700″，“买三”
      15：”26.89″，“买三”
      16：”14300″，“买四”
      17：”26.88″，“买四”
      18：”15100″，“买五”
      19：”26.87″，“买五”
      20：”3100″，“卖一”申报3100股，即31手；
      21：”26.92″，“卖一”报价
      (22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
      30：”2008-01-11″，日期；
      31：”15:05:32″，时间；
   */
  public static String[] getSinaStock(String stockId, int stockType){
    String url = getSinaStockUrl(stockId, stockType);
    return getSinaStockToValues(url);
  }
  /**
   * @param stockId  股票代码
   * @param stockType 股票类型
   * @return
   * 数据含义分别为：
      0：股票名字
      1：当前价格
      2：当前点数
      3：涨跌率
      4：成交量（手）
      5：成交额（万元）
   */
  public static String[] getSinaQuotation(String stockId, int stockType){
    String url = getSinaQuotationUrl(stockId, stockType);
    return getSinaStockToValues(url);
  }
  public static String[] getSinaStockToValues(String url) {
    String result = OkHttpUtils.get()
            .url(url)
            .execute()
            .string();
    return SinaResponseTools.parse(result);
  }

  public static String getSinaStockFromServer(String url) {
    return OkHttpUtils.get()
            .url(url)
            .execute()
            .string();
  }
}
