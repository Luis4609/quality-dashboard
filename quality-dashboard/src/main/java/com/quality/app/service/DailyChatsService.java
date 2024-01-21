package com.quality.app.service;

import com.quality.app.domain.DailyChats;
import com.quality.app.repository.DailyChatsRepository;
import com.quality.app.service.dto.DailyChatsDTO;
import com.quality.app.service.dto.metrics.chats.DailyChatsMetricsDTO;
import com.quality.app.domain.IChatsMetrics;
import com.quality.app.domain.IChatsMetricsSummary;
import com.quality.app.service.mapper.DailyChatsMapper;
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

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.quality.app.domain.DailyChats}.
 */
@Service
@Transactional
public class DailyChatsService {

    private final Logger log = LoggerFactory.getLogger(DailyChatsService.class);

    private final DailyChatsRepository dailyChatsRepository;

    private final DailyChatsMapper dailyChatsMapper;

    /**
     * Instantiates a new Daily chats service.
     *
     * @param dailyChatsRepository the daily chats repository
     * @param dailyChatsMapper     the daily chats mapper
     */
    public DailyChatsService(DailyChatsRepository dailyChatsRepository, DailyChatsMapper dailyChatsMapper) {
        this.dailyChatsRepository = dailyChatsRepository;
        this.dailyChatsMapper = dailyChatsMapper;
    }

    /**
     * Save a dailyChats.
     *
     * @param dailyChatsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyChatsDTO save(DailyChatsDTO dailyChatsDTO) {
        log.debug("Request to save DailyChats : {}", dailyChatsDTO);
        DailyChats dailyChats = dailyChatsMapper.toEntity(dailyChatsDTO);
        dailyChats = dailyChatsRepository.save(dailyChats);
        return dailyChatsMapper.toDto(dailyChats);
    }

    /**
     * Update a dailyChats.
     *
     * @param dailyChatsDTO the entity to save.
     * @return the persisted entity.
     */
    public DailyChatsDTO update(DailyChatsDTO dailyChatsDTO) {
        log.debug("Request to update DailyChats : {}", dailyChatsDTO);
        DailyChats dailyChats = dailyChatsMapper.toEntity(dailyChatsDTO);
        dailyChats = dailyChatsRepository.save(dailyChats);
        return dailyChatsMapper.toDto(dailyChats);
    }

    /**
     * Partially update a dailyChats.
     *
     * @param dailyChatsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DailyChatsDTO> partialUpdate(DailyChatsDTO dailyChatsDTO) {
        log.debug("Request to partially update DailyChats : {}", dailyChatsDTO);

        return dailyChatsRepository
            .findById(dailyChatsDTO.getId())
            .map(existingDailyChats -> {
                dailyChatsMapper.partialUpdate(existingDailyChats, dailyChatsDTO);

                return existingDailyChats;
            })
            .map(dailyChatsRepository::save)
            .map(dailyChatsMapper::toDto);
    }

    /**
     * Get all the dailyChats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DailyChatsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DailyChats");
        return dailyChatsRepository.findAll(pageable).map(dailyChatsMapper::toDto);
    }

    /**
     * Get one dailyChats by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DailyChatsDTO> findOne(Long id) {
        log.debug("Request to get DailyChats : {}", id);
        return dailyChatsRepository.findById(id).map(dailyChatsMapper::toDto);
    }

    /**
     * Delete the dailyChats by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DailyChats : {}", id);
        dailyChatsRepository.deleteById(id);
    }

    /**
     * Update data from file.
     *
     * @param file the file
     * @throws IOException the io exception
     */
    public void updateDataFromFile(MultipartFile file) throws IOException {

        // Load the Excel file
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            if (row.getCell(0) == null || row.getCell(0).toString().isEmpty()) break;

            DailyChats dailyChatsFromExcel;

            Optional<DailyChats> dailyChats = dailyChatsRepository.findByDay(row.getCell(0).getLocalDateTimeCellValue().toLocalDate());
            dailyChatsFromExcel = dailyChats.orElseGet(DailyChats::new);

            dailyChatsFromExcel.setDay(row.getCell(0).getLocalDateTimeCellValue().toLocalDate());
            dailyChatsFromExcel.setTotalDailyReceivedChats((int) row.getCell(1).getNumericCellValue());
            dailyChatsFromExcel.setTotalDailyConversationChatsTimeInMin((int) row.getCell(2).getNumericCellValue());
            dailyChatsFromExcel.setTotalDailyAttendedChats((int) row.getCell(3).getNumericCellValue());
            dailyChatsFromExcel.setTotalDailyMissedChats((int) row.getCell(4).getNumericCellValue());
            dailyChatsFromExcel.setAvgDailyConversationChatsTimeInMin((float) row.getCell(5).getNumericCellValue());

            dailyChatsRepository.save(dailyChatsFromExcel);
        }
        workbook.close();
    }

    /**
     * Gets metrics by date.
     *
     * @param start  the start
     * @param finish the finish
     * @return the metrics by date
     */
    public List<IChatsMetrics> getMetricsByDate(Date start, Date finish) {

        return dailyChatsRepository.getMetricsByDate(start, finish);
    }

    /**
     * Gets metrics summary by date.
     *
     * @param start  the start
     * @param finish the finish
     * @return the metrics summary by date
     */
    public DailyChatsMetricsDTO getMetricsSummaryByDate(Date start, Date finish) {

        IChatsMetricsSummary current = dailyChatsRepository.getMetricsSummaryByDate(start, finish);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        startCalendar.add(Calendar.YEAR, -1);

        Calendar finishCalendar = Calendar.getInstance();
        finishCalendar.setTime(finish);
        finishCalendar.add(Calendar.YEAR, -1);

        IChatsMetricsSummary previous = dailyChatsRepository.getMetricsSummaryByDate(startCalendar.getTime(), finishCalendar.getTime());

        return new DailyChatsMetricsDTO(current, previous);
    }

    /**
     * Gets metrics by date group by month.
     *
     * @param start  the start
     * @param finish the finish
     * @return the metrics by date group by month
     */
    public List<IChatsMetrics> getMetricsByDateGroupByMonth(Date start, Date finish) {

        return dailyChatsRepository.getMetricsByDateGroupByMonth(start, finish);
    }
}
