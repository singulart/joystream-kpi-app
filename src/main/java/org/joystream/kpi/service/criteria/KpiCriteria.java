package org.joystream.kpi.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link org.joystream.kpi.domain.Kpi} entity. This class is used
 * in {@link org.joystream.kpi.web.rest.KpiResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kpis?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KpiCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter reward;

    private StringFilter rewardDistribution;

    private StringFilter gradingProcess;

    private StringFilter active;

    private StringFilter purpose;

    private StringFilter scopeOfWork;

    private StringFilter rewardDistributionInfo;

    private StringFilter reporting;

    private FloatFilter fiatPoolFactor;

    private StringFilter grading;

    private LongFilter kpiRoundId;

    private Boolean distinct;

    public KpiCriteria() {}

    public KpiCriteria(KpiCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.reward = other.reward == null ? null : other.reward.copy();
        this.rewardDistribution = other.rewardDistribution == null ? null : other.rewardDistribution.copy();
        this.gradingProcess = other.gradingProcess == null ? null : other.gradingProcess.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.purpose = other.purpose == null ? null : other.purpose.copy();
        this.scopeOfWork = other.scopeOfWork == null ? null : other.scopeOfWork.copy();
        this.rewardDistributionInfo = other.rewardDistributionInfo == null ? null : other.rewardDistributionInfo.copy();
        this.reporting = other.reporting == null ? null : other.reporting.copy();
        this.fiatPoolFactor = other.fiatPoolFactor == null ? null : other.fiatPoolFactor.copy();
        this.grading = other.grading == null ? null : other.grading.copy();
        this.kpiRoundId = other.kpiRoundId == null ? null : other.kpiRoundId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KpiCriteria copy() {
        return new KpiCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getReward() {
        return reward;
    }

    public StringFilter reward() {
        if (reward == null) {
            reward = new StringFilter();
        }
        return reward;
    }

    public void setReward(StringFilter reward) {
        this.reward = reward;
    }

    public StringFilter getRewardDistribution() {
        return rewardDistribution;
    }

    public StringFilter rewardDistribution() {
        if (rewardDistribution == null) {
            rewardDistribution = new StringFilter();
        }
        return rewardDistribution;
    }

    public void setRewardDistribution(StringFilter rewardDistribution) {
        this.rewardDistribution = rewardDistribution;
    }

    public StringFilter getGradingProcess() {
        return gradingProcess;
    }

    public StringFilter gradingProcess() {
        if (gradingProcess == null) {
            gradingProcess = new StringFilter();
        }
        return gradingProcess;
    }

    public void setGradingProcess(StringFilter gradingProcess) {
        this.gradingProcess = gradingProcess;
    }

    public StringFilter getActive() {
        return active;
    }

    public StringFilter active() {
        if (active == null) {
            active = new StringFilter();
        }
        return active;
    }

    public void setActive(StringFilter active) {
        this.active = active;
    }

    public StringFilter getPurpose() {
        return purpose;
    }

    public StringFilter purpose() {
        if (purpose == null) {
            purpose = new StringFilter();
        }
        return purpose;
    }

    public void setPurpose(StringFilter purpose) {
        this.purpose = purpose;
    }

    public StringFilter getScopeOfWork() {
        return scopeOfWork;
    }

    public StringFilter scopeOfWork() {
        if (scopeOfWork == null) {
            scopeOfWork = new StringFilter();
        }
        return scopeOfWork;
    }

    public void setScopeOfWork(StringFilter scopeOfWork) {
        this.scopeOfWork = scopeOfWork;
    }

    public StringFilter getRewardDistributionInfo() {
        return rewardDistributionInfo;
    }

    public StringFilter rewardDistributionInfo() {
        if (rewardDistributionInfo == null) {
            rewardDistributionInfo = new StringFilter();
        }
        return rewardDistributionInfo;
    }

    public void setRewardDistributionInfo(StringFilter rewardDistributionInfo) {
        this.rewardDistributionInfo = rewardDistributionInfo;
    }

    public StringFilter getReporting() {
        return reporting;
    }

    public StringFilter reporting() {
        if (reporting == null) {
            reporting = new StringFilter();
        }
        return reporting;
    }

    public void setReporting(StringFilter reporting) {
        this.reporting = reporting;
    }

    public FloatFilter getFiatPoolFactor() {
        return fiatPoolFactor;
    }

    public FloatFilter fiatPoolFactor() {
        if (fiatPoolFactor == null) {
            fiatPoolFactor = new FloatFilter();
        }
        return fiatPoolFactor;
    }

    public void setFiatPoolFactor(FloatFilter fiatPoolFactor) {
        this.fiatPoolFactor = fiatPoolFactor;
    }

    public StringFilter getGrading() {
        return grading;
    }

    public StringFilter grading() {
        if (grading == null) {
            grading = new StringFilter();
        }
        return grading;
    }

    public void setGrading(StringFilter grading) {
        this.grading = grading;
    }

    public LongFilter getKpiRoundId() {
        return kpiRoundId;
    }

    public LongFilter kpiRoundId() {
        if (kpiRoundId == null) {
            kpiRoundId = new LongFilter();
        }
        return kpiRoundId;
    }

    public void setKpiRoundId(LongFilter kpiRoundId) {
        this.kpiRoundId = kpiRoundId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KpiCriteria that = (KpiCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(reward, that.reward) &&
            Objects.equals(rewardDistribution, that.rewardDistribution) &&
            Objects.equals(gradingProcess, that.gradingProcess) &&
            Objects.equals(active, that.active) &&
            Objects.equals(purpose, that.purpose) &&
            Objects.equals(scopeOfWork, that.scopeOfWork) &&
            Objects.equals(rewardDistributionInfo, that.rewardDistributionInfo) &&
            Objects.equals(reporting, that.reporting) &&
            Objects.equals(fiatPoolFactor, that.fiatPoolFactor) &&
            Objects.equals(grading, that.grading) &&
            Objects.equals(kpiRoundId, that.kpiRoundId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            reward,
            rewardDistribution,
            gradingProcess,
            active,
            purpose,
            scopeOfWork,
            rewardDistributionInfo,
            reporting,
            fiatPoolFactor,
            grading,
            kpiRoundId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KpiCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (reward != null ? "reward=" + reward + ", " : "") +
            (rewardDistribution != null ? "rewardDistribution=" + rewardDistribution + ", " : "") +
            (gradingProcess != null ? "gradingProcess=" + gradingProcess + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (purpose != null ? "purpose=" + purpose + ", " : "") +
            (scopeOfWork != null ? "scopeOfWork=" + scopeOfWork + ", " : "") +
            (rewardDistributionInfo != null ? "rewardDistributionInfo=" + rewardDistributionInfo + ", " : "") +
            (reporting != null ? "reporting=" + reporting + ", " : "") +
            (fiatPoolFactor != null ? "fiatPoolFactor=" + fiatPoolFactor + ", " : "") +
            (grading != null ? "grading=" + grading + ", " : "") +
            (kpiRoundId != null ? "kpiRoundId=" + kpiRoundId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
