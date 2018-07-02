package br.com.jcsw.math.chaosmonkey;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

class ChaosMonkeySettings {

  private ChaosMonkeyType chaosMonkeyType;

  private Integer latencyRangeStart;

  private Integer latencyRangeEnd;

  static ChaosMonkeySettings makeEmptySettings() {
    return new ChaosMonkeySettings(ChaosMonkeyType.NOTHING);
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

  ChaosMonkeyType getChaosMonkeyType() {
    return chaosMonkeyType;
  }

  void setChaosMonkeyType(ChaosMonkeyType chaosMonkeyType) {
    this.chaosMonkeyType = chaosMonkeyType;
  }

  int getLatencyRangeStart() {
    return latencyRangeStart;
  }

  void setLatencyRangeStart(int latencyRangeStart) {
    this.latencyRangeStart = latencyRangeStart;
  }

  int getLatencyRangeEnd() {
    return latencyRangeEnd;
  }

  void setLatencyRangeEnd(int latencyRangeEnd) {
    this.latencyRangeEnd = latencyRangeEnd;
  }
}
