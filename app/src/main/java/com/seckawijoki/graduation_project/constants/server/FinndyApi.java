package com.seckawijoki.graduation_project.constants.server;

/**
 * Created by 瑶琴频曲羽衣魂 on 2018/3/4 at 15:51.
 */

public interface FinndyApi {
  /**
   * @Param
   * pagesize 文章数，默认20
   * pageindex 服务器数据的开始位置，默认0
   * datatype 默认json
   * sortby 默认desc
   * token
   * @return
   */
  String BASE_PATH = "http://www.finndy.com/api.php?token=";
  /**
   * 投资理财产品点评平台点评数据
   */
  String TOKEN_CRITIQUE = BASE_PATH + "1.0_7SyxjqkXqjfiiNzqgkkP3846cd05";
  /**
   * 理财类型的精品文章采集规则
   */
  String TOKEN_FINANCING = BASE_PATH + "1.0_7SyxjqkXqjfHxNzqqXeWb5529cbb";


}
