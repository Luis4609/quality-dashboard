package com.quality.app.service;

import com.quality.app.domain.DailyCalls;
import com.quality.app.repository.DailyCallsRepository;
import com.quality.app.service.dto.DailyCallsDTO;
import com.quality.app.service.dto.metrics.calls.DailyCallsMetricsDTO;
import com.quality.app.service.dto.metrics.calls.IDailyCallsMetrics;
import com.quality.app.service.dto.metrics.calls.IDailyCallsMetricsByDate;
import com.quality.app.service.mapper.DailyCallsMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link com.quality.app.domain.DailyCalls}.
 */
@Service
@Transactional
public class DailyCallsService {

    private final Logger log = LoggerFactory.getLogger(DailyCallsService.class);

    private final DailyCallsRepository dailyCallsRepository;

    private final DailyCallsMapper dailyCallsMapper;

    /**
     * Instantiates a new Daily calls service.
     *
     * @param dailyCallsRepository the daily calls repository
     * @param dailyCallsMapper     the daily calls mapper
     */
    public DailyCallsService(DailyCallsRepository dailyCallsRepository, DailyCallsMapper dailyCallsMapper) {
        this.dailyCallsRepository = dailyCallsRepository;
        this.dailyCallsMapper = dailyCallsMapper;
    }

    /**
     * Save a dailyCalls.
     *
     * @param dailyCallsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyCallsDTO save(DailyCallsDTO dailyCallsDTO) {
        log.debug("Request to save DailyCalls : {}", dailyCallsDTO);
        DailyCalls dailyCalls = dailyCallsMapper.toEntity(dailyCallsDTO);
        dailyCalls = dailyCallsRepository.save(dailyCalls);
        return dailyCallsMapper.toDto(dailyCalls);
    }

    /**
     * Update a dailyCalls.
     *
     * @param dailyCallsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyCallsDTO update(DailyCallsDTO dailyCallsDTO) {
        log.debug("Request to update DailyCalls : {}", dailyCallsDTO);
        DailyCalls dailyCalls = dailyCallsMapper.toEntity(dailyCallsDTO);
        dailyCalls = dailyCallsRepository.save(dailyCalls);
        return dailyCallsMapper.toDto(dailyCalls);
    }

    /**
     * Partially update a dailyCalls.
     *
     * @param dailyCallsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DailyCallsDTO> partialUpdate(DailyCallsDTO dailyCallsDTO) {
        log.debug("Request to partially update DailyCalls : {}", dailyCallsDTO);

        return dailyCallsRepository
            .findById(dailyCallsDTO.getId())
            .map(existingDailyCalls -> {
                dailyCallsMapper.partialUpdate(existingDailyCalls, dailyCallsDTO);

                return existingDailyCalls;
            })
            .map(dailyCallsRepository::save)
            .map(dailyCallsMapper::toDto);
    }

    /**
     * Get all the dailyCalls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DailyCallsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DailyCalls");
        return dailyCallsRepository.findAll(pageable).map(dailyCallsMapper::toDto);
    }

    /**
     * Get one dailyCalls by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DailyCallsDTO> findOne(Long id) {
        log.debug("Request to get DailyCalls : {}", id);
        return dailyCallsRepository.findById(id).map(dailyCallsMapper::toDto);
    }

    /**
     * Delete the dailyCalls by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DailyCalls : {}", id);
        dailyCallsRepository.deleteById(id);
    }

    /**
     * Update data from file.
     *
     * @param file the file
     * @throws Exception the exception
     */
    public void updateDataFromFile(MultipartFile file) throws Exception {
        // Load the Excel file
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            if (row.getCell(0) == null || row.getCell(0).toString().isEmpty()) break;

            DailyCalls dailyCalls;

            Optional<DailyCalls> dailyCalls1 = dailyCallsRepository
                .findByDay(row.getCell(0)
                    .getLocalDateTimeCellValue()
                    .toLocalDate());
            dailyCalls = dailyCalls1.orElseGet(DailyCalls::new);

            dailyCalls.setDay(row.getCell(0).getLocalDateTimeCellValue().toLocalDate());
            dailyCalls.setTotalDailyReceivedCalls((int) row.getCell(1).getNumericCellValue());
            dailyCalls.setTotalDailyAttendedCalls((int) row.getCell(2).getNumericCellValue());
            dailyCalls.setTotalDailyMissedCalls((int) row.getCell(3).getNumericCellValue());
            dailyCalls.setTotalDailyReceivedCallsExternalAgent((int) row.getCell(4).getNumericCellValue());
            dailyCalls.setTotalDailyAttendedCallsExternalAgent((int) row.getCell(5).getNumericCellValue());
            dailyCalls.setTotalDailyReceivedCallsInternalAgent((int) row.getCell(6).getNumericCellValue());
            dailyCalls.setTotalDailyAttendedCallsInternalAgent((int) row.getCell(7).getNumericCellValue());
            dailyCalls.setTotalDailyCallsTimeInMin((int) row.getCell(8).getNumericCellValue());
            dailyCalls.setTotalDailyCallsTimeExternalAgentInMin((int) row.getCell(9).getNumericCellValue());
            dailyCalls.setTotalDailyCallsTimeInternalAgentInMin((int) row.getCell(10).getNumericCellValue());
            dailyCalls.setAvgDailyOperationTimeExternalAgentInMin((float) row.getCell(11).getNumericCellValue());
            dailyCalls.setAvgDailyOperationTimeInternalAgentInMin((float) row.getCell(12).getNumericCellValue());

            dailyCallsRepository.save(dailyCalls);
        }
        workbook.close();
    }

    /**
     * Gets monthly calls.
     *
     * @return the monthly calls
     */
    public List<DailyCallsDTO> getMonthlyCalls() {

        return dailyCallsRepository.findAll().stream().map(dailyCallsMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Gets metrics by date range.
     *
     * @param start  the start
     * @param finish the finish
     * @return the metrics by date range
     */
    public DailyCallsMetricsDTO getMetricsSummaryByDateRange(Date start, Date finish) {

        IDailyCallsMetrics current = dailyCallsRepository.getDailyCallMetricsSummaryByDate(start, finish);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        startCalendar.add(Calendar.YEAR, -1);

        Calendar finishCalendar = Calendar.getInstance();
        finishCalendar.setTime(finish);
        finishCalendar.add(Calendar.YEAR, -1);

        IDailyCallsMetrics previous = dailyCallsRepository.getDailyCallMetricsSummaryByDate(startCalendar.getTime(), finishCalendar.getTime());

        return new DailyCallsMetricsDTO(current, previous);
    }

    /**
     * Gets metrics by date range and period.
     *
     * @param start      the start
     * @param finish     the finish
     * @param period     the period
     * @param datePeriod the date period
     * @return the metrics by date range and period
     */
    public IDailyCallsMetrics getMetricsByDateRangeAndPeriod(Date start, Date finish, Integer period, String datePeriod) {

        return dailyCallsRepository.getDailyCallMetricsByDateAndPeriod(start, finish, period);
    }

    /**
     * Gets metrics by year group by month.
     *
     * @param start  the start
     * @param finish the finish
     * @return the metrics by year group by month
     */
    public List<IDailyCallsMetricsByDate> getMetricsByDateGroupByMonth(Date start, Date finish) {

        return dailyCallsRepository.getMetricsByDateGroupByMonth(start, finish);
    }

    /**
     * Gets metrics by date.
     *
     * @param start  the start
     * @param finish the finish
     * @return the metrics by date
     */
    public List<IDailyCallsMetricsByDate> getMetricsByDate(Date start, Date finish) {

        return dailyCallsRepository.getDailyCallMetricsByDate(start, finish);
    }
}
