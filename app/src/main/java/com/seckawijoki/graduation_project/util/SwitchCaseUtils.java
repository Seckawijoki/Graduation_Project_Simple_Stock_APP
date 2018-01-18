package com.seckawijoki.graduation_project.util;


import com.seckawijoki.graduation_project.constants.server.KLineType;
import com.seckawijoki.graduation_project.constants.server.StockType;
import com.seckawijoki.graduation_project.constants.sina.SinaKLineType;
import com.seckawijoki.graduation_project.constants.sina.SinaServerPath;
import com.seckawijoki.graduation_project.constants.sina.SinaStockType;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/12/5 at 17:17.
 */

public class SwitchCaseUtils {
  private SwitchCaseUtils() {
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
}
