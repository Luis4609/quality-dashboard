export interface IMetricThreshold {
  id?: number;
  entityName?: string;
  metricName?: string;
  successPercentage?: number | null;
  dangerPercentage?: number | null;
}

export const defaultValue: Readonly<IMetricThreshold> = {};
