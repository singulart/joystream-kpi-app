package org.joystream.kpi.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link org.joystream.kpi.domain.KpiRound} entity. This class is used
 * in {@link org.joystream.kpi.web.rest.KpiRoundResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /kpi-rounds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KpiRoundCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter displayName;

    private StringFilter totalPossibleRewards;

    private IntegerFilter councilElectedInRound;

    private IntegerFilter councilMembers;

    private StringFilter termLength;

    private IntegerFilter startBlock;

    private LocalDateFilter startDate;

    private StringFilter endBlock;

    private LocalDateFilter endDate;

    private StringFilter termSummary;

    private StringFilter summarySubmissionDeadline;

    private StringFilter maxFiatPoolDifference;

    private StringFilter numberOfKpis;

    private StringFilter notes;

    private LongFilter kpisId;

    private Boolean distinct;

    public KpiRoundCriteria() {}

    public KpiRoundCriteria(KpiRoundCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.displayName = other.displayName == null ? null : other.displayName.copy();
        this.totalPossibleRewards = other.totalPossibleRewards == null ? null : other.totalPossibleRewards.copy();
        this.councilElectedInRound = other.councilElectedInRound == null ? null : other.councilElectedInRound.copy();
        this.councilMembers = other.councilMembers == null ? null : other.councilMembers.copy();
        this.termLength = other.termLength == null ? null : other.termLength.copy();
        this.startBlock = other.startBlock == null ? null : other.startBlock.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endBlock = other.endBlock == null ? null : other.endBlock.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.termSummary = other.termSummary == null ? null : other.termSummary.copy();
        this.summarySubmissionDeadline = other.summarySubmissionDeadline == null ? null : other.summarySubmissionDeadline.copy();
        this.maxFiatPoolDifference = other.maxFiatPoolDifference == null ? null : other.maxFiatPoolDifference.copy();
        this.numberOfKpis = other.numberOfKpis == null ? null : other.numberOfKpis.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.kpisId = other.kpisId == null ? null : other.kpisId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public KpiRoundCriteria copy() {
        return new KpiRoundCriteria(this);
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

    public StringFilter getDisplayName() {
        return displayName;
    }

    public StringFilter displayName() {
        if (displayName == null) {
            displayName = new StringFilter();
        }
        return displayName;
    }

    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public StringFilter getTotalPossibleRewards() {
        return totalPossibleRewards;
    }

    public StringFilter totalPossibleRewards() {
        if (totalPossibleRewards == null) {
            totalPossibleRewards = new StringFilter();
        }
        return totalPossibleRewards;
    }

    public void setTotalPossibleRewards(StringFilter totalPossibleRewards) {
        this.totalPossibleRewards = totalPossibleRewards;
    }

    public IntegerFilter getCouncilElectedInRound() {
        return councilElectedInRound;
    }

    public IntegerFilter councilElectedInRound() {
        if (councilElectedInRound == null) {
            councilElectedInRound = new IntegerFilter();
        }
        return councilElectedInRound;
    }

    public void setCouncilElectedInRound(IntegerFilter councilElectedInRound) {
        this.councilElectedInRound = councilElectedInRound;
    }

    public IntegerFilter getCouncilMembers() {
        return councilMembers;
    }

    public IntegerFilter councilMembers() {
        if (councilMembers == null) {
            councilMembers = new IntegerFilter();
        }
        return councilMembers;
    }

    public void setCouncilMembers(IntegerFilter councilMembers) {
        this.councilMembers = councilMembers;
    }

    public StringFilter getTermLength() {
        return termLength;
    }

    public StringFilter termLength() {
        if (termLength == null) {
            termLength = new StringFilter();
        }
        return termLength;
    }

    public void setTermLength(StringFilter termLength) {
        this.termLength = termLength;
    }

    public IntegerFilter getStartBlock() {
        return startBlock;
    }

    public IntegerFilter startBlock() {
        if (startBlock == null) {
            startBlock = new IntegerFilter();
        }
        return startBlock;
    }

    public void setStartBlock(IntegerFilter startBlock) {
        this.startBlock = startBlock;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            startDate = new LocalDateFilter();
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public StringFilter getEndBlock() {
        return endBlock;
    }

    public StringFilter endBlock() {
        if (endBlock == null) {
            endBlock = new StringFilter();
        }
        return endBlock;
    }

    public void setEndBlock(StringFilter endBlock) {
        this.endBlock = endBlock;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public LocalDateFilter endDate() {
        if (endDate == null) {
            endDate = new LocalDateFilter();
        }
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getTermSummary() {
        return termSummary;
    }

    public StringFilter termSummary() {
        if (termSummary == null) {
            termSummary = new StringFilter();
        }
        return termSummary;
    }

    public void setTermSummary(StringFilter termSummary) {
        this.termSummary = termSummary;
    }

    public StringFilter getSummarySubmissionDeadline() {
        return summarySubmissionDeadline;
    }

    public StringFilter summarySubmissionDeadline() {
        if (summarySubmissionDeadline == null) {
            summarySubmissionDeadline = new StringFilter();
        }
        return summarySubmissionDeadline;
    }

    public void setSummarySubmissionDeadline(StringFilter summarySubmissionDeadline) {
        this.summarySubmissionDeadline = summarySubmissionDeadline;
    }

    public StringFilter getMaxFiatPoolDifference() {
        return maxFiatPoolDifference;
    }

    public StringFilter maxFiatPoolDifference() {
        if (maxFiatPoolDifference == null) {
            maxFiatPoolDifference = new StringFilter();
        }
        return maxFiatPoolDifference;
    }

    public void setMaxFiatPoolDifference(StringFilter maxFiatPoolDifference) {
        this.maxFiatPoolDifference = maxFiatPoolDifference;
    }

    public StringFilter getNumberOfKpis() {
        return numberOfKpis;
    }

    public StringFilter numberOfKpis() {
        if (numberOfKpis == null) {
            numberOfKpis = new StringFilter();
        }
        return numberOfKpis;
    }

    public void setNumberOfKpis(StringFilter numberOfKpis) {
        this.numberOfKpis = numberOfKpis;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public LongFilter getKpisId() {
        return kpisId;
    }

    public LongFilter kpisId() {
        if (kpisId == null) {
            kpisId = new LongFilter();
        }
        return kpisId;
    }

    public void setKpisId(LongFilter kpisId) {
        this.kpisId = kpisId;
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
        final KpiRoundCriteria that = (KpiRoundCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(displayName, that.displayName) &&
            Objects.equals(totalPossibleRewards, that.totalPossibleRewards) &&
            Objects.equals(councilElectedInRound, that.councilElectedInRound) &&
            Objects.equals(councilMembers, that.councilMembers) &&
            Objects.equals(termLength, that.termLength) &&
            Objects.equals(startBlock, that.startBlock) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endBlock, that.endBlock) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(termSummary, that.termSummary) &&
            Objects.equals(summarySubmissionDeadline, that.summarySubmissionDeadline) &&
            Objects.equals(maxFiatPoolDifference, that.maxFiatPoolDifference) &&
            Objects.equals(numberOfKpis, that.numberOfKpis) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(kpisId, that.kpisId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            displayName,
            totalPossibleRewards,
            councilElectedInRound,
            councilMembers,
            termLength,
            startBlock,
            startDate,
            endBlock,
            endDate,
            termSummary,
            summarySubmissionDeadline,
            maxFiatPoolDifference,
            numberOfKpis,
            notes,
            kpisId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KpiRoundCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (displayName != null ? "displayName=" + displayName + ", " : "") +
            (totalPossibleRewards != null ? "totalPossibleRewards=" + totalPossibleRewards + ", " : "") +
            (councilElectedInRound != null ? "councilElectedInRound=" + councilElectedInRound + ", " : "") +
            (councilMembers != null ? "councilMembers=" + councilMembers + ", " : "") +
            (termLength != null ? "termLength=" + termLength + ", " : "") +
            (startBlock != null ? "startBlock=" + startBlock + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endBlock != null ? "endBlock=" + endBlock + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (termSummary != null ? "termSummary=" + termSummary + ", " : "") +
            (summarySubmissionDeadline != null ? "summarySubmissionDeadline=" + summarySubmissionDeadline + ", " : "") +
            (maxFiatPoolDifference != null ? "maxFiatPoolDifference=" + maxFiatPoolDifference + ", " : "") +
            (numberOfKpis != null ? "numberOfKpis=" + numberOfKpis + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (kpisId != null ? "kpisId=" + kpisId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
