import dayjs from 'dayjs';
import { IKpi } from 'app/shared/model/kpi.model';

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
  kpis?: IKpi[] | null;
}

export const defaultValue: Readonly<IKpiRound> = {};
