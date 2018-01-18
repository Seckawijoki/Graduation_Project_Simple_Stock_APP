package com.seckawijoki.graduation_project.constants.sina;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/1/12 at 22:06.
 */

public interface SinaForeignStock {
  /**
   * 道琼斯
   * US
   */
  String DOW_JONES = "int_dji";
  /**
   * 纳斯达克
   * US
   */
  String NASDAQ = "int_nasdaq";
  /**
   * 恒生指数
   * HK
   */
  String HANG_SENG = "int_hangseng";
  /**
   * 日经指数
   * HK
   */
  String NIKKEI = "int_nikkei";
  /**
   * 台湾台北指数
   * HK
   */
  String TWSE = "b_TWSE";
  /**
   * 富时新加坡海峡时报指数
   * HK
   */
  String FSSTI = "b_FSSTI";
  String[] ids = {
          DOW_JONES,
          NASDAQ,
          HANG_SENG,
          NIKKEI,
          TWSE,
          FSSTI
  };
}
