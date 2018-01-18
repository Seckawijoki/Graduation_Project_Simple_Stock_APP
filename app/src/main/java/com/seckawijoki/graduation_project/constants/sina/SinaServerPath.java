package com.seckawijoki.graduation_project.constants.sina;

import com.seckawijoki.graduation_project.constants.server.KLineType;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/10 at 9:31.
 */

public interface SinaServerPath {
  String STOCK_BASE_PATH = "http://hq.sinajs.cn/list=";
  /**
   * 大盘股票数据
   * @Param
   * sinaStockType as {@link SinaStockType}
   * stockId
   * @return 7项数据类型
   */
  String GET_MARKET_STOCK_FORMAT = STOCK_BASE_PATH + "s_%s%s";
  /**
   * 股票详情数据
   * @Param
   * sinaStockType as {@link SinaStockType}
   * stockId
   * @return 32项数据类型
   */
  String FORMAT_GET_STOCK = STOCK_BASE_PATH + "%s%s";
  /**
   * 新浪K线图
   * @Param
   * kLineType as {@link KLineType}
   * sinaStockType as {@link SinaStockType}
   * stockId
   * 股票曲线图
   * @return GIF文件
   */
  String FORMAT_GET_K_LINE_CHART = "http://image.sinajs.cn/newchart/%s/n/%s%s.gif";
  /**
   * 新浪K线小图
   * @Param
   * sinaStockType as {@link SinaStockType}
   * stockId
   * 股票曲线图
   * @return GIF文件
   */
  String FORMAT_GET_K_LINE_SMALL_CHART = "http://image.sinajs.cn/newchart/small/i%s%s.gif";


}
