package org.joystream.kpi.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Kpi.
 */
@Entity
@Table(name = "kpi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Kpi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 10, max = 128)
    @Column(name = "title", length = 128, nullable = false, unique = true)
    private String title;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "reward", length = 10, nullable = false)
    private String reward;

    @NotNull
    @Column(name = "reward_distribution", nullable = false)
    private String rewardDistribution;

    @NotNull
    @Column(name = "grading_process", nullable = false)
    private String gradingProcess;

    @NotNull
    @Column(name = "active", nullable = false)
    private String active;

    @Size(max = 2048)
    @Column(name = "purpose", length = 2048)
    private String purpose;

    @Size(max = 2048)
    @Column(name = "scope_of_work", length = 2048)
    private String scopeOfWork;

    @Size(max = 2048)
    @Column(name = "reward_distribution_info", length = 2048)
    private String rewardDistributionInfo;

    @Size(max = 2048)
    @Column(name = "reporting", length = 2048)
    private String reporting;

    @Column(name = "fiat_pool_factor")
    private Float fiatPoolFactor;

    @Size(max = 2048)
    @Column(name = "grading", length = 2048)
    private String grading;

    @ManyToOne
    private KpiRound kpiRound;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kpi id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Kpi title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReward() {
        return this.reward;
    }

    public Kpi reward(String reward) {
        this.setReward(reward);
        return this;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getRewardDistribution() {
        return this.rewardDistribution;
    }

    public Kpi rewardDistribution(String rewardDistribution) {
        this.setRewardDistribution(rewardDistribution);
        return this;
    }

    public void setRewardDistribution(String rewardDistribution) {
        this.rewardDistribution = rewardDistribution;
    }

    public String getGradingProcess() {
        return this.gradingProcess;
    }

    public Kpi gradingProcess(String gradingProcess) {
        this.setGradingProcess(gradingProcess);
        return this;
    }

    public void setGradingProcess(String gradingProcess) {
        this.gradingProcess = gradingProcess;
    }

    public String getActive() {
        return this.active;
    }

    public Kpi active(String active) {
        this.setActive(active);
        return this;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public Kpi purpose(String purpose) {
        this.setPurpose(purpose);
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getScopeOfWork() {
        return this.scopeOfWork;
    }

    public Kpi scopeOfWork(String scopeOfWork) {
        this.setScopeOfWork(scopeOfWork);
        return this;
    }

    public void setScopeOfWork(String scopeOfWork) {
        this.scopeOfWork = scopeOfWork;
    }

    public String getRewardDistributionInfo() {
        return this.rewardDistributionInfo;
    }

    public Kpi rewardDistributionInfo(String rewardDistributionInfo) {
        this.setRewardDistributionInfo(rewardDistributionInfo);
        return this;
    }

    public void setRewardDistributionInfo(String rewardDistributionInfo) {
        this.rewardDistributionInfo = rewardDistributionInfo;
    }

    public String getReporting() {
        return this.reporting;
    }

    public Kpi reporting(String reporting) {
        this.setReporting(reporting);
        return this;
    }

    public void setReporting(String reporting) {
        this.reporting = reporting;
    }

    public Float getFiatPoolFactor() {
        return this.fiatPoolFactor;
    }

    public Kpi fiatPoolFactor(Float fiatPoolFactor) {
        this.setFiatPoolFactor(fiatPoolFactor);
        return this;
    }

    public void setFiatPoolFactor(Float fiatPoolFactor) {
        this.fiatPoolFactor = fiatPoolFactor;
    }

    public String getGrading() {
        return this.grading;
    }

    public Kpi grading(String grading) {
        this.setGrading(grading);
        return this;
    }

    public void setGrading(String grading) {
        this.grading = grading;
    }

    public KpiRound getKpiRound() {
        return this.kpiRound;
    }

    public void setKpiRound(KpiRound kpiRound) {
        this.kpiRound = kpiRound;
    }

    public Kpi kpiRound(KpiRound kpiRound) {
        this.setKpiRound(kpiRound);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kpi)) {
            return false;
        }
        return id != null && id.equals(((Kpi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kpi{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", reward='" + getReward() + "'" +
            ", rewardDistribution='" + getRewardDistribution() + "'" +
            ", gradingProcess='" + getGradingProcess() + "'" +
            ", active='" + getActive() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", scopeOfWork='" + getScopeOfWork() + "'" +
            ", rewardDistributionInfo='" + getRewardDistributionInfo() + "'" +
            ", reporting='" + getReporting() + "'" +
            ", fiatPoolFactor=" + getFiatPoolFactor() +
            ", grading='" + getGrading() + "'" +
            "}";
    }
}
