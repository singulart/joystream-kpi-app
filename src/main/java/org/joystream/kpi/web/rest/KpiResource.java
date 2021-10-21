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
import org.joystream.kpi.service.KpiQueryService;
import org.joystream.kpi.service.KpiService;
import org.joystream.kpi.service.criteria.KpiCriteria;
import org.joystream.kpi.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class KpiResource {

    private final Logger log = LoggerFactory.getLogger(KpiResource.class);

    private static final String ENTITY_NAME = "kpi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KpiService kpiService;

    private final KpiRepository kpiRepository;

    private final KpiQueryService kpiQueryService;

    public KpiResource(KpiService kpiService, KpiRepository kpiRepository, KpiQueryService kpiQueryService) {
        this.kpiService = kpiService;
        this.kpiRepository = kpiRepository;
        this.kpiQueryService = kpiQueryService;
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
        Kpi result = kpiService.save(kpi);
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

        Kpi result = kpiService.save(kpi);
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

        Optional<Kpi> result = kpiService.partialUpdate(kpi);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kpi.getId().toString())
        );
    }

    /**
     * {@code GET  /kpis} : get all the kpis.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kpis in body.
     */
    @GetMapping("/kpis")
    public ResponseEntity<List<Kpi>> getAllKpis(KpiCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Kpis by criteria: {}", criteria);
        Page<Kpi> page = kpiQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kpis/count} : count all the kpis.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/kpis/count")
    public ResponseEntity<Long> countKpis(KpiCriteria criteria) {
        log.debug("REST request to count Kpis by criteria: {}", criteria);
        return ResponseEntity.ok().body(kpiQueryService.countByCriteria(criteria));
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
        Optional<Kpi> kpi = kpiService.findOne(id);
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
        kpiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
