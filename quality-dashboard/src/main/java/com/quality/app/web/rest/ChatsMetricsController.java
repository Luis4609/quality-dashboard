package com.quality.app.web.rest;


import com.quality.app.service.DailyChatsService;
import com.quality.app.service.dto.metrics.chats.DailyChatsMetricsDTO;
import com.quality.app.service.dto.metrics.chats.IChatsMetrics;
import com.quality.app.service.dto.metrics.chats.IChatsMetricsSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/daily-chats/metrics")
public class ChatsMetricsController {

    private final Logger LOGGER = LoggerFactory.getLogger(ChatsMetricsController.class);
    private final DailyChatsService dailyChatsService;

    public ChatsMetricsController(DailyChatsService dailyChatsService) {
        this.dailyChatsService = dailyChatsService;
    }

    /**
     * Gets chats metrics group by month.
     *
     * @param start  the start
     * @param finish the finish
     * @return the calls metrics by month
     */
    @GetMapping("")
    public ResponseEntity<List<IChatsMetrics>> getChatsMetricsGroupByMonth(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date finish) {

        return ResponseEntity.ok().body(dailyChatsService.getMetricsByDate(start, finish));
    }

    /**
     * Gets chats metrics by year group by month.
     *
     * @param start  the start
     * @param finish the finish
     * @return the calls metrics by year group by month
     */
    @GetMapping("/group-by-month")
    public ResponseEntity<List<IChatsMetrics>> getChatsMetricsByDateRangeGroupByMonth(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date finish) {

        return ResponseEntity.ok().body(dailyChatsService.getMetricsByDateGroupByMonth(start, finish));
    }

    /**
     * Gets daily chats metrics summary by date range.
     *
     * @param start  the start
     * @param finish the finish
     * @return the daily calls metrics
     */
    @GetMapping("/summary")
    public ResponseEntity<DailyChatsMetricsDTO> getSummaryMetricsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date finish) {

        return ResponseEntity.ok().body(dailyChatsService.getMetricsSummaryByDate(start, finish));
    }
}
