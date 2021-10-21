import { IKpiRound } from 'app/shared/model/kpi-round.model';

export interface IKpi {
  id?: number;
  title?: string;
  reward?: string;
  rewardDistribution?: string;
  gradingProcess?: string;
  active?: string;
  purpose?: string | null;
  scopeOfWork?: string | null;
  rewardDistributionInfo?: string | null;
  reporting?: string | null;
  fiatPoolFactor?: number | null;
  grading?: string | null;
  kpiRound?: IKpiRound | null;
}

export const defaultValue: Readonly<IKpi> = {};
