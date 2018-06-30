package br.com.jcsw.math.chaos;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ChaosMonkeySettings {

  private ChaosType chaosType;

  private int latencyRangeStart;

  private int latencyRangeEnd;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append("chaosType", chaosType)
        .append("latencyRangeStart", latencyRangeStart)
        .append("latencyRangeEnd", latencyRangeEnd)
        .toString();
  }

  public ChaosType getChaosType() {
    return chaosType;
  }

  public void setChaosType(ChaosType chaosType) {
    this.chaosType = chaosType;
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
