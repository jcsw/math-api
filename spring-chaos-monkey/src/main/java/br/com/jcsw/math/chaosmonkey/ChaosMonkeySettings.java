package br.com.jcsw.math.chaosmonkey;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

class ChaosMonkeySettings {

  private ChaosMonkeyType chaosMonkeyType;

  private Integer latencyRangeStart;

  private Integer latencyRangeEnd;

  static ChaosMonkeySettings makeEmptySettings() {
    return new ChaosMonkeySettings(ChaosMonkeyType.NOTHING, NumberUtils.INTEGER_ZERO, NumberUtils.INTEGER_ZERO);
  }

  ChaosMonkeySettings(ChaosMonkeyType chaosMonkeyType) {
    this.chaosMonkeyType = chaosMonkeyType;
  }

  ChaosMonkeySettings(ChaosMonkeyType chaosMonkeyType, Integer latencyRangeStart, Integer latencyRangeEnd) {
    this.chaosMonkeyType = chaosMonkeyType;
    this.latencyRangeStart = latencyRangeStart;
    this.latencyRangeEnd = latencyRangeEnd;
  }

  ChaosMonkeySettings() {
    super();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append("chaosMonkeyType", chaosMonkeyType)
        .append("latencyRangeStart", latencyRangeStart)
        .append("latencyRangeEnd", latencyRangeEnd)
        .toString();
  }

  void validate() {
    if(ChaosMonkeyType.LATENCY.equals(chaosMonkeyType) || ChaosMonkeyType.LATENCY_AND_DENIAL.equals(chaosMonkeyType)) {
      Assert.notNull(latencyRangeStart, "latencyRangeStart is required by LATENCY and LATENCY_AND_DENIAL");
      Assert.notNull(latencyRangeEnd, "latencyRangeEnd is required by LATENCY and LATENCY_AND_DENIAL");
    }
  }

  public ChaosMonkeyType getChaosMonkeyType() {
    return chaosMonkeyType;
  }

  public void setChaosMonkeyType(ChaosMonkeyType chaosMonkeyType) {
    this.chaosMonkeyType = chaosMonkeyType;
  }

  public int getLatencyRangeStart() {
    return latencyRangeStart;
  }

  public void setLatencyRangeStart(int latencyRangeStart) {
    this.latencyRangeStart = latencyRangeStart;
  }

  public int getLatencyRangeEnd() {
    return latencyRangeEnd;
  }

  public void setLatencyRangeEnd(int latencyRangeEnd) {
    this.latencyRangeEnd = latencyRangeEnd;
  }
}
