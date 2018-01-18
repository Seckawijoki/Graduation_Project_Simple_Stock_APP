package com.seckawijoki.graduation_project.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 瑶琴频曲羽衣魂 on 2017/11/13 at 16:09.
 */

public class Quotation extends DataSupport{
  public static final String QUOTATION_ID = "quotationId";
  long stockTableId;
  private int quotationType;
  String quotationId;
  String quotationName;
  private float priceDifference;
  float currentPrice;
  float fluctuationRate;
  /**
   * 成交的股票数
   * 单位：股
   * 由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百。
   * 一手为100股
   */
  protected long volume;
  /**
   * 成交金额
   * 单位为“元”
   * 为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万。
   */
  double turnover;
  public Quotation setQuotationType(int quotationType) {
    this.quotationType = quotationType;
    return this;
  }
  public Quotation setQuotationId(String quotationId) {
    this.quotationId = quotationId;
    return this;
  }

  public Quotation setValues(String[] values){
    if (values.length <= 0)
      return this;
    quotationName = values[0];
    currentPrice = Float.valueOf(values[1]);
    priceDifference = Float.valueOf(values[2]);
    fluctuationRate = Float.valueOf(values[3]);
    volume = Integer.valueOf(values[4]);
    turnover = Integer.valueOf(values[5]);
    return this;
  }

  public int getQuotationType() {
    return quotationType;
  }

  public String getQuotationId() {
    return quotationId;
  }

  public String getQuotationName() {
    return quotationName;
  }

  public float getPriceDifference() {
    return priceDifference;
  }

  public float getCurrentPrice() {
    return currentPrice;
  }

  public float getFluctuationRate() {
    return fluctuationRate;
  }

  public long getVolume() {
    return volume;
  }

  public double getVolumeInHundredMillionHands() {
    return volume / 100000000.00;
  }

  public double getTurnover() {
    return turnover;
  }

  public double getTurnoverInHundredMillion() {
    return turnover / 100000000.00;
  }

  @Override
  public String toString() {
    return quotationName + quotationId;
  }

  @Override
  public boolean equals(Object o) {
    if ( this == o ) return true;
    if ( o == null || getClass() != o.getClass() ) return false;

    Quotation quotation = (Quotation) o;

    if ( !getQuotationId().equals(quotation.getQuotationId()) ) return false;
    return getQuotationName().equals(quotation.getQuotationName());

  }

  @Override
  public int hashCode() {
    int result = getQuotationId().hashCode();
    result = 31 * result + getQuotationName().hashCode();
    return result;
  }
}
