package com.quality.app.service.dto.metrics.chats;

public class DailyChatsMetricsDTO {

    private IChatsMetricsSummary current;
    private IChatsMetricsSummary previous;

    public DailyChatsMetricsDTO(IChatsMetricsSummary current, IChatsMetricsSummary previous) {
        this.current = current;
        this.previous = previous;
    }

    public IChatsMetricsSummary getCurrent() {
        return current;
    }

    public void setCurrent(IChatsMetricsSummary current) {
        this.current = current;
    }

    public IChatsMetricsSummary getPrevious() {
        return previous;
    }

    public void setPrevious(IChatsMetricsSummary previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "DailyChatsMetricsDTO{" +
            "current=" + current +
            ", previous=" + previous +
            '}';
    }
}
