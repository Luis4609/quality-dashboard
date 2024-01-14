package com.quality.app.web.rest;

import com.quality.app.repository.DailyChatsRepository;
import com.quality.app.service.DailyChatsQueryService;
import com.quality.app.service.DailyChatsService;
import com.quality.app.service.criteria.DailyChatsCriteria;
import com.quality.app.service.dto.DailyChatsDTO;
import com.quality.app.web.rest.errors.BadRequestAlertException;
import com.quality.app.web.rest.errors.UploadFileAlertException;
import com.quality.app.web.rest.util.QualityHeaderUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.quality.app.domain.DailyChats}.
 */
@RestController
@RequestMapping("/api/daily-chats")
public class DailyChatsResource {

    private static final String ENTITY_NAME = "dailyChats";
    private final Logger log = LoggerFactory.getLogger(DailyChatsResource.class);
    private final DailyChatsService dailyChatsService;
    private final DailyChatsRepository dailyChatsRepository;
    private final DailyChatsQueryService dailyChatsQueryService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public DailyChatsResource(
        DailyChatsService dailyChatsService,
        DailyChatsRepository dailyChatsRepository,
        DailyChatsQueryService dailyChatsQueryService
    ) {
        this.dailyChatsService = dailyChatsService;
        this.dailyChatsRepository = dailyChatsRepository;
        this.dailyChatsQueryService = dailyChatsQueryService;
    }

    /**
     * {@code POST  /daily-chats} : Create a new dailyChats.
     *
     * @param dailyChatsDTO the dailyChatsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyChatsDTO, or with status {@code 400 (Bad Request)} if the dailyChats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DailyChatsDTO> createDailyChats(@Valid @RequestBody DailyChatsDTO dailyChatsDTO) throws URISyntaxException {
        log.debug("REST request to save DailyChats : {}", dailyChatsDTO);
        if (dailyChatsDTO.getId() != null) {
            throw new BadRequestAlertException("A new dailyChats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyChatsDTO result = dailyChatsService.save(dailyChatsDTO);
        return ResponseEntity
            .created(new URI("/api/daily-chats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-chats/:id} : Updates an existing dailyChats.
     *
     * @param id            the id of the dailyChatsDTO to save.
     * @param dailyChatsDTO the dailyChatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyChatsDTO,
     * or with status {@code 400 (Bad Request)} if the dailyChatsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyChatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DailyChatsDTO> updateDailyChats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DailyChatsDTO dailyChatsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DailyChats : {}, {}", id, dailyChatsDTO);
        if (dailyChatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyChatsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyChatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DailyChatsDTO result = dailyChatsService.update(dailyChatsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyChatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /daily-chats/:id} : Partial updates given fields of an existing dailyChats, field will ignore if it is null
     *
     * @param id            the id of the dailyChatsDTO to save.
     * @param dailyChatsDTO the dailyChatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyChatsDTO,
     * or with status {@code 400 (Bad Request)} if the dailyChatsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dailyChatsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dailyChatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<DailyChatsDTO> partialUpdateDailyChats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DailyChatsDTO dailyChatsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DailyChats partially : {}, {}", id, dailyChatsDTO);
        if (dailyChatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dailyChatsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dailyChatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DailyChatsDTO> result = dailyChatsService.partialUpdate(dailyChatsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyChatsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /daily-chats} : get all the dailyChats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyChats in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DailyChatsDTO>> getAllDailyChats(
        DailyChatsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DailyChats by criteria: {}", criteria);

        Page<DailyChatsDTO> page = dailyChatsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /daily-chats/count} : count all the dailyChats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDailyChats(DailyChatsCriteria criteria) {
        log.debug("REST request to count DailyChats by criteria: {}", criteria);
        return ResponseEntity.ok().body(dailyChatsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /daily-chats/:id} : get the "id" dailyChats.
     *
     * @param id the id of the dailyChatsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyChatsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DailyChatsDTO> getDailyChats(@PathVariable Long id) {
        log.debug("REST request to get DailyChats : {}", id);
        Optional<DailyChatsDTO> dailyChatsDTO = dailyChatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyChatsDTO);
    }

    /**
     * {@code DELETE  /daily-chats/:id} : delete the "id" dailyChats.
     *
     * @param id the id of the dailyChatsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyChats(@PathVariable Long id) {
        log.debug("REST request to delete DailyChats : {}", id);
        dailyChatsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Upload excel file response entity.
     *
     * @param file the file
     * @return the response entity
     */
    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadExcelFile(@RequestParam MultipartFile file) {
        try {
            dailyChatsService.updateDataFromFile(file);
        } catch (Exception e) {
            throw new UploadFileAlertException("Invalid file", ENTITY_NAME, "fileinvalid");
        }
        return ResponseEntity
            .ok()
            .headers(QualityHeaderUtil.createEntityUploadAlert(applicationName, true, ENTITY_NAME, file.getOriginalFilename()))
            .body("File successfully uploaded");
    }
}
