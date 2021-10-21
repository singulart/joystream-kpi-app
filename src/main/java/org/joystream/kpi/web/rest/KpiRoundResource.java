package org.joystream.kpi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.joystream.kpi.domain.KpiRound;
import org.joystream.kpi.repository.KpiRoundRepository;
import org.joystream.kpi.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.joystream.kpi.domain.KpiRound}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KpiRoundResource {

    private final Logger log = LoggerFactory.getLogger(KpiRoundResource.class);

    private static final String ENTITY_NAME = "kpiRound";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpiRoundRepository kpiRoundRepository;

    public KpiRoundResource(KpiRoundRepository kpiRoundRepository) {
        this.kpiRoundRepository = kpiRoundRepository;
    }

    /**
     * {@code POST  /kpi-rounds} : Create a new kpiRound.
     *
     * @param kpiRound the kpiRound to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kpiRound, or with status {@code 400 (Bad Request)} if the kpiRound has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kpi-rounds")
    public ResponseEntity<KpiRound> createKpiRound(@Valid @RequestBody KpiRound kpiRound) throws URISyntaxException {
        log.debug("REST request to save KpiRound : {}", kpiRound);
        if (kpiRound.getId() != null) {
            throw new BadRequestAlertException("A new kpiRound cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KpiRound result = kpiRoundRepository.save(kpiRound);
        return ResponseEntity
            .created(new URI("/api/kpi-rounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kpi-rounds/:id} : Updates an existing kpiRound.
     *
     * @param id the id of the kpiRound to save.
     * @param kpiRound the kpiRound to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpiRound,
     * or with status {@code 400 (Bad Request)} if the kpiRound is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kpiRound couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kpi-rounds/{id}")
    public ResponseEntity<KpiRound> updateKpiRound(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KpiRound kpiRound
    ) throws URISyntaxException {
        log.debug("REST request to update KpiRound : {}, {}", id, kpiRound);
        if (kpiRound.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpiRound.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpiRoundRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KpiRound result = kpiRoundRepository.save(kpiRound);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpiRound.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kpi-rounds/:id} : Partial updates given fields of an existing kpiRound, field will ignore if it is null
     *
     * @param id the id of the kpiRound to save.
     * @param kpiRound the kpiRound to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpiRound,
     * or with status {@code 400 (Bad Request)} if the kpiRound is not valid,
     * or with status {@code 404 (Not Found)} if the kpiRound is not found,
     * or with status {@code 500 (Internal Server Error)} if the kpiRound couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kpi-rounds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KpiRound> partialUpdateKpiRound(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KpiRound kpiRound
    ) throws URISyntaxException {
        log.debug("REST request to partial update KpiRound partially : {}, {}", id, kpiRound);
        if (kpiRound.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpiRound.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpiRoundRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KpiRound> result = kpiRoundRepository
            .findById(kpiRound.getId())
            .map(existingKpiRound -> {
                if (kpiRound.getDisplayName() != null) {
                    existingKpiRound.setDisplayName(kpiRound.getDisplayName());
                }
                if (kpiRound.getTotalPossibleRewards() != null) {
                    existingKpiRound.setTotalPossibleRewards(kpiRound.getTotalPossibleRewards());
                }
                if (kpiRound.getCouncilElectedInRound() != null) {
                    existingKpiRound.setCouncilElectedInRound(kpiRound.getCouncilElectedInRound());
                }
                if (kpiRound.getCouncilMembers() != null) {
                    existingKpiRound.setCouncilMembers(kpiRound.getCouncilMembers());
                }
                if (kpiRound.getTermLength() != null) {
                    existingKpiRound.setTermLength(kpiRound.getTermLength());
                }
                if (kpiRound.getStartBlock() != null) {
                    existingKpiRound.setStartBlock(kpiRound.getStartBlock());
                }
                if (kpiRound.getStartDate() != null) {
                    existingKpiRound.setStartDate(kpiRound.getStartDate());
                }
                if (kpiRound.getEndBlock() != null) {
                    existingKpiRound.setEndBlock(kpiRound.getEndBlock());
                }
                if (kpiRound.getEndDate() != null) {
                    existingKpiRound.setEndDate(kpiRound.getEndDate());
                }
                if (kpiRound.getTermSummary() != null) {
                    existingKpiRound.setTermSummary(kpiRound.getTermSummary());
                }
                if (kpiRound.getSummarySubmissionDeadline() != null) {
                    existingKpiRound.setSummarySubmissionDeadline(kpiRound.getSummarySubmissionDeadline());
                }
                if (kpiRound.getMaxFiatPoolDifference() != null) {
                    existingKpiRound.setMaxFiatPoolDifference(kpiRound.getMaxFiatPoolDifference());
                }
                if (kpiRound.getNumberOfKpis() != null) {
                    existingKpiRound.setNumberOfKpis(kpiRound.getNumberOfKpis());
                }
                if (kpiRound.getNotes() != null) {
                    existingKpiRound.setNotes(kpiRound.getNotes());
                }

                return existingKpiRound;
            })
            .map(kpiRoundRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpiRound.getId().toString())
        );
    }

    /**
     * {@code GET  /kpi-rounds} : get all the kpiRounds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kpiRounds in body.
     */
    @GetMapping("/kpi-rounds")
    public ResponseEntity<List<KpiRound>> getAllKpiRounds(Pageable pageable) {
        log.debug("REST request to get a page of KpiRounds");
        Page<KpiRound> page = kpiRoundRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kpi-rounds/:id} : get the "id" kpiRound.
     *
     * @param id the id of the kpiRound to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kpiRound, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kpi-rounds/{id}")
    public ResponseEntity<KpiRound> getKpiRound(@PathVariable Long id) {
        log.debug("REST request to get KpiRound : {}", id);
        Optional<KpiRound> kpiRound = kpiRoundRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kpiRound);
    }

    /**
     * {@code DELETE  /kpi-rounds/:id} : delete the "id" kpiRound.
     *
     * @param id the id of the kpiRound to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kpi-rounds/{id}")
    public ResponseEntity<Void> deleteKpiRound(@PathVariable Long id) {
        log.debug("REST request to delete KpiRound : {}", id);
        kpiRoundRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
