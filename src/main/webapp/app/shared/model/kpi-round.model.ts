import dayjs from 'dayjs';

export interface IKpiRound {
  id?: number;
  displayName?: string;
  totalPossibleRewards?: string;
  councilElectedInRound?: number;
  councilMembers?: number;
  termLength?: string;
  startBlock?: number;
  startDate?: string;
  endBlock?: string;
  endDate?: string;
  termSummary?: string | null;
  summarySubmissionDeadline?: string | null;
  maxFiatPoolDifference?: string | null;
  numberOfKpis?: string | null;
  notes?: string | null;
}

export const defaultValue: Readonly<IKpiRound> = {};
