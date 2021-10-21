package org.joystream.kpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A KpiRound.
 */
@Entity
@Table(name = "kpi_round")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KpiRound implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 64)
    @Column(name = "display_name", length = 64, nullable = false, unique = true)
    private String displayName;

    @NotNull
    @Size(min = 4, max = 64)
    @Column(name = "total_possible_rewards", length = 64, nullable = false)
    private String totalPossibleRewards;

    @NotNull
    @Min(value = 1)
    @Column(name = "council_elected_in_round", nullable = false)
    private Integer councilElectedInRound;

    @NotNull
    @Min(value = 2)
    @Column(name = "council_members", nullable = false)
    private Integer councilMembers;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "term_length", length = 64, nullable = false)
    private String termLength;

    @NotNull
    @Min(value = 1)
    @Column(name = "start_block", nullable = false)
    private Integer startBlock;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Size(min = 1)
    @Column(name = "end_block", nullable = false)
    private String endBlock;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "term_summary")
    private String termSummary;

    @Column(name = "summary_submission_deadline")
    private String summarySubmissionDeadline;

    @Column(name = "max_fiat_pool_difference")
    private String maxFiatPoolDifference;

    @Column(name = "number_of_kpis")
    private String numberOfKpis;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "kpiRound")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "kpiRound" }, allowSetters = true)
    private Set<Kpi> kpis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KpiRound id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public KpiRound displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTotalPossibleRewards() {
        return this.totalPossibleRewards;
    }

    public KpiRound totalPossibleRewards(String totalPossibleRewards) {
        this.setTotalPossibleRewards(totalPossibleRewards);
        return this;
    }

    public void setTotalPossibleRewards(String totalPossibleRewards) {
        this.totalPossibleRewards = totalPossibleRewards;
    }

    public Integer getCouncilElectedInRound() {
        return this.councilElectedInRound;
    }

    public KpiRound councilElectedInRound(Integer councilElectedInRound) {
        this.setCouncilElectedInRound(councilElectedInRound);
        return this;
    }

    public void setCouncilElectedInRound(Integer councilElectedInRound) {
        this.councilElectedInRound = councilElectedInRound;
    }

    public Integer getCouncilMembers() {
        return this.councilMembers;
    }

    public KpiRound councilMembers(Integer councilMembers) {
        this.setCouncilMembers(councilMembers);
        return this;
    }

    public void setCouncilMembers(Integer councilMembers) {
        this.councilMembers = councilMembers;
    }

    public String getTermLength() {
        return this.termLength;
    }

    public KpiRound termLength(String termLength) {
        this.setTermLength(termLength);
        return this;
    }

    public void setTermLength(String termLength) {
        this.termLength = termLength;
    }

    public Integer getStartBlock() {
        return this.startBlock;
    }

    public KpiRound startBlock(Integer startBlock) {
        this.setStartBlock(startBlock);
        return this;
    }

    public void setStartBlock(Integer startBlock) {
        this.startBlock = startBlock;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public KpiRound startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getEndBlock() {
        return this.endBlock;
    }

    public KpiRound endBlock(String endBlock) {
        this.setEndBlock(endBlock);
        return this;
    }

    public void setEndBlock(String endBlock) {
        this.endBlock = endBlock;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public KpiRound endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTermSummary() {
        return this.termSummary;
    }

    public KpiRound termSummary(String termSummary) {
        this.setTermSummary(termSummary);
        return this;
    }

    public void setTermSummary(String termSummary) {
        this.termSummary = termSummary;
    }

    public String getSummarySubmissionDeadline() {
        return this.summarySubmissionDeadline;
    }

    public KpiRound summarySubmissionDeadline(String summarySubmissionDeadline) {
        this.setSummarySubmissionDeadline(summarySubmissionDeadline);
        return this;
    }

    public void setSummarySubmissionDeadline(String summarySubmissionDeadline) {
        this.summarySubmissionDeadline = summarySubmissionDeadline;
    }

    public String getMaxFiatPoolDifference() {
        return this.maxFiatPoolDifference;
    }

    public KpiRound maxFiatPoolDifference(String maxFiatPoolDifference) {
        this.setMaxFiatPoolDifference(maxFiatPoolDifference);
        return this;
    }

    public void setMaxFiatPoolDifference(String maxFiatPoolDifference) {
        this.maxFiatPoolDifference = maxFiatPoolDifference;
    }

    public String getNumberOfKpis() {
        return this.numberOfKpis;
    }

    public KpiRound numberOfKpis(String numberOfKpis) {
        this.setNumberOfKpis(numberOfKpis);
        return this;
    }

    public void setNumberOfKpis(String numberOfKpis) {
        this.numberOfKpis = numberOfKpis;
    }

    public String getNotes() {
        return this.notes;
    }

    public KpiRound notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Kpi> getKpis() {
        return this.kpis;
    }

    public void setKpis(Set<Kpi> kpis) {
        if (this.kpis != null) {
            this.kpis.forEach(i -> i.setKpiRound(null));
        }
        if (kpis != null) {
            kpis.forEach(i -> i.setKpiRound(this));
        }
        this.kpis = kpis;
    }

    public KpiRound kpis(Set<Kpi> kpis) {
        this.setKpis(kpis);
        return this;
    }

    public KpiRound addKpis(Kpi kpi) {
        this.kpis.add(kpi);
        kpi.setKpiRound(this);
        return this;
    }

    public KpiRound removeKpis(Kpi kpi) {
        this.kpis.remove(kpi);
        kpi.setKpiRound(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KpiRound)) {
            return false;
        }
        return id != null && id.equals(((KpiRound) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KpiRound{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", totalPossibleRewards='" + getTotalPossibleRewards() + "'" +
            ", councilElectedInRound=" + getCouncilElectedInRound() +
            ", councilMembers=" + getCouncilMembers() +
            ", termLength='" + getTermLength() + "'" +
            ", startBlock=" + getStartBlock() +
            ", startDate='" + getStartDate() + "'" +
            ", endBlock='" + getEndBlock() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", termSummary='" + getTermSummary() + "'" +
            ", summarySubmissionDeadline='" + getSummarySubmissionDeadline() + "'" +
            ", maxFiatPoolDifference='" + getMaxFiatPoolDifference() + "'" +
            ", numberOfKpis='" + getNumberOfKpis() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
