export interface IMetricThreshold {
  id?: number;
  entityName?: string;
  metricName?: string;
  successPercentage?: number | null;
  dangerPercentage?: number | null;
}

export const defaultValue: Readonly<IMetricThreshold> = {};

export interface ICallsMetricThreshold {
  id?: number;
  entityName?: string;
  metricName?: string;
  successPercentage?: number | null;
  dangerPercentage?: number | null;
}

export const defaultValueCalls: Readonly<ICallsMetricThreshold> = {};

export interface IChatsMetricThreshold {
  id?: number;
  entityName?: string;
  metricName?: string;
  successPercentage?: number | null;
  dangerPercentage?: number | null;
}

export const defaultValueChats: Readonly<IChatsMetricThreshold> = {};
