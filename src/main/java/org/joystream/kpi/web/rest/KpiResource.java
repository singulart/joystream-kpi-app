package org.joystream.kpi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.joystream.kpi.domain.Kpi;
import org.joystream.kpi.repository.KpiRepository;
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
 * REST controller for managing {@link org.joystream.kpi.domain.Kpi}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KpiResource {

    private final Logger log = LoggerFactory.getLogger(KpiResource.class);

    private static final String ENTITY_NAME = "kpi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpiRepository kpiRepository;

    public KpiResource(KpiRepository kpiRepository) {
        this.kpiRepository = kpiRepository;
    }

    /**
     * {@code POST  /kpis} : Create a new kpi.
     *
     * @param kpi the kpi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kpi, or with status {@code 400 (Bad Request)} if the kpi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kpis")
    public ResponseEntity<Kpi> createKpi(@Valid @RequestBody Kpi kpi) throws URISyntaxException {
        log.debug("REST request to save Kpi : {}", kpi);
        if (kpi.getId() != null) {
            throw new BadRequestAlertException("A new kpi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kpi result = kpiRepository.save(kpi);
        return ResponseEntity
            .created(new URI("/api/kpis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kpis/:id} : Updates an existing kpi.
     *
     * @param id the id of the kpi to save.
     * @param kpi the kpi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpi,
     * or with status {@code 400 (Bad Request)} if the kpi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kpi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kpis/{id}")
    public ResponseEntity<Kpi> updateKpi(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Kpi kpi)
        throws URISyntaxException {
        log.debug("REST request to update Kpi : {}, {}", id, kpi);
        if (kpi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Kpi result = kpiRepository.save(kpi);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpi.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kpis/:id} : Partial updates given fields of an existing kpi, field will ignore if it is null
     *
     * @param id the id of the kpi to save.
     * @param kpi the kpi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kpi,
     * or with status {@code 400 (Bad Request)} if the kpi is not valid,
     * or with status {@code 404 (Not Found)} if the kpi is not found,
     * or with status {@code 500 (Internal Server Error)} if the kpi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kpis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kpi> partialUpdateKpi(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Kpi kpi)
        throws URISyntaxException {
        log.debug("REST request to partial update Kpi partially : {}, {}", id, kpi);
        if (kpi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kpi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kpiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kpi> result = kpiRepository
            .findById(kpi.getId())
            .map(existingKpi -> {
                if (kpi.getTitle() != null) {
                    existingKpi.setTitle(kpi.getTitle());
                }
                if (kpi.getReward() != null) {
                    existingKpi.setReward(kpi.getReward());
                }
                if (kpi.getRewardDistribution() != null) {
                    existingKpi.setRewardDistribution(kpi.getRewardDistribution());
                }
                if (kpi.getGradingProcess() != null) {
                    existingKpi.setGradingProcess(kpi.getGradingProcess());
                }
                if (kpi.getActive() != null) {
                    existingKpi.setActive(kpi.getActive());
                }
                if (kpi.getPurpose() != null) {
                    existingKpi.setPurpose(kpi.getPurpose());
                }
                if (kpi.getScopeOfWork() != null) {
                    existingKpi.setScopeOfWork(kpi.getScopeOfWork());
                }
                if (kpi.getRewardDistributionInfo() != null) {
                    existingKpi.setRewardDistributionInfo(kpi.getRewardDistributionInfo());
                }
                if (kpi.getReporting() != null) {
                    existingKpi.setReporting(kpi.getReporting());
                }
                if (kpi.getFiatPoolFactor() != null) {
                    existingKpi.setFiatPoolFactor(kpi.getFiatPoolFactor());
                }
                if (kpi.getGrading() != null) {
                    existingKpi.setGrading(kpi.getGrading());
                }

                return existingKpi;
            })
            .map(kpiRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpi.getId().toString())
        );
    }

    /**
     * {@code GET  /kpis} : get all the kpis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kpis in body.
     */
    @GetMapping("/kpis")
    public ResponseEntity<List<Kpi>> getAllKpis(Pageable pageable) {
        log.debug("REST request to get a page of Kpis");
        Page<Kpi> page = kpiRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kpis/:id} : get the "id" kpi.
     *
     * @param id the id of the kpi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kpi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kpis/{id}")
    public ResponseEntity<Kpi> getKpi(@PathVariable Long id) {
        log.debug("REST request to get Kpi : {}", id);
        Optional<Kpi> kpi = kpiRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kpi);
    }

    /**
     * {@code DELETE  /kpis/:id} : delete the "id" kpi.
     *
     * @param id the id of the kpi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kpis/{id}")
    public ResponseEntity<Void> deleteKpi(@PathVariable Long id) {
        log.debug("REST request to delete Kpi : {}", id);
        kpiRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
