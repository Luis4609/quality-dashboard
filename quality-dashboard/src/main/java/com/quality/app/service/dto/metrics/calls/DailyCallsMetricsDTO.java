package com.quality.app.service.dto.metrics.calls;

import com.quality.app.domain.IDailyCallsMetrics;

public class DailyCallsMetricsDTO {

    private IDailyCallsMetrics current;
    private IDailyCallsMetrics previous;

    public DailyCallsMetricsDTO() {
    }

    public DailyCallsMetricsDTO(IDailyCallsMetrics current, IDailyCallsMetrics previous) {
        this.current = current;
        this.previous = previous;
    }

    public IDailyCallsMetrics getCurrent() {
        return current;
    }

    public void setCurrent(IDailyCallsMetrics current) {
        this.current = current;
    }

    public IDailyCallsMetrics getPrevious() {
        return previous;
    }

    public void setPrevious(IDailyCallsMetrics previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "DailyCallsMetricsDTO{" +
            "current=" + current +
            ", previous=" + previous +
            '}';
    }
}
