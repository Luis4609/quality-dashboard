import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';

import ReactECharts from 'echarts-for-react';

// Import the echarts core module, which provides the necessary interfaces for using echarts.
import { BarChart } from 'echarts/charts';
import { GridComponent, TitleComponent, TooltipComponent } from 'echarts/components';
import * as echarts from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { getMetricsWithDate } from '../daily-calls-metrics-date.reducer';
import { cloneDeep } from 'lodash';

// Register the required components
echarts.use([TitleComponent, TooltipComponent, GridComponent, BarChart, CanvasRenderer]);

echarts.registerTheme('my_theme', {
  backgroundColor: '#f4cccc',
});

const DEFAULT_OPTIONS = {
  title: {},
  tooltip: {},
  legend: { top: 'bottom' },
  xAxis: {
    data: [],
  },
  yAxis: [
    {
      type: 'value',
      name: 'Recibidas',
      min: 0,
      max: 50000,
      interval: 10000,
      axisLabel: {
        formatter: '{value}',
      },
    },
    {
      type: 'value',
      name: 'Atendidas',
      min: 0,
      max: 100,
      interval: 20,
      axisLabel: {
        formatter: '{value} %',
      },
    },
  ],
  series: [
    {
      name: 'Llamadas recibidas',
      type: 'bar',
      label: {
        show: true,
        position: 'inside',
      },
      data: [],
    },
    {
      name: '% Llamadas atendidas',
      type: 'line',
      yAxisIndex: 1,
      label: {
        show: true,
        position: 'up',
      },
      data: [],
    },
  ],
};

const CallsChart = () => {
  const dispatch = useAppDispatch();
  const metricsByDate = useAppSelector(state => state.dailyCallsMetricsByDate.entities);

  const getAllMetricsByDate = () => {
    dispatch(getMetricsWithDate(2023));
  };

  useEffect(() => {
    getAllMetricsByDate();
  }, []);

  const [option, setOption] = useState(DEFAULT_OPTIONS);

  const getData = () => {
    const newOption = cloneDeep(option); // immutable

    const x: any[] = metricsByDate.map(metric => metric.metricDate);
    const data: any[] = metricsByDate.map(metric => metric.totalReceivedCalls);
    const data1: any[] = metricsByDate.map(metric => ((metric.totalAttendedCalls / metric.totalReceivedCalls) * 100).toFixed(2));

    newOption.xAxis.data.push(...x);
    newOption.series[0].data.push(...data);
    newOption.series[1].data.push(...data1);

    // eslint-disable-next-line no-console
    console.log(`'%c REFRESCANDO componente charts ${JSON.stringify(metricsByDate)}`, 'background: #222; color: #bada55');
    // eslint-disable-next-line no-console
    console.log(`'%c REFRESCANDO DATA 1:: ${x} -- ${data1} -- ${newOption.series[1].data}`, 'background: #222; color: #bada55');

    setOption(newOption);
  };

  useEffect(() => {
    getData();
  }, [metricsByDate]);

  return <ReactECharts option={option} lazyUpdate={true} />;
};

export default CallsChart;
