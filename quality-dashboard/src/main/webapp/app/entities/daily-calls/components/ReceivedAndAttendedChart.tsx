import { useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';

import ReactECharts from 'echarts-for-react';

// Import the echarts core module, which provides the necessary interfaces for using echarts.
import { BarChart } from 'echarts/charts';
import { GridComponent, TitleComponent, TooltipComponent } from 'echarts/components';
import * as echarts from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { cloneDeep } from 'lodash';
import { IDailyCallsMetricsByDate } from 'app/shared/model/daily-calls.model';
import { text } from '@fortawesome/fontawesome-svg-core';

// Register the required components
echarts.use([TitleComponent, TooltipComponent, GridComponent, BarChart, CanvasRenderer]);

echarts.registerTheme('my_theme', {
  backgroundColor: '#f4cccc',
});

const DEFAULT_OPTIONS = {
  title: { text: 'Llamadas - Recibidas y Atendidas', textStyle: { fontSize: 14 } },
  tooltip: {},
  legend: { top: 'bottom' },
  xAxis: {
    name: 'Día',
    data: [],
  },
  yAxis: [
    {
      type: 'value',
      name: 'Nº de Llamadas',
      axisLabel: {
        formatter: '{value}',
      },
    },
  ],
  series: [
    {
      name: 'Llamadas recibidas',
      type: 'line',
      data: [],
    },
    {
      name: 'Llamadas atendidas',
      type: 'line',
      data: [],
    },
  ],
};

const ReceivedAndAttendedChart = (props: { metricsByMonth: IDailyCallsMetricsByDate[]; startDate: string; endDate: string }) => {
  // const metricsByMonth = useAppSelector(state => state.dailyCallsMetricsByMonth.entities);

  // eslint-disable-next-line no-console
  console.log(`metricsByMonth ${props.metricsByMonth}`);

  const [option, setOption] = useState(DEFAULT_OPTIONS);

  const getData = () => {
    const newOption = cloneDeep(option); // immutable

    newOption.title.text = `Llamadas - Recibidas y Atendidas (${props.startDate} - ${props.endDate})`;

    const x: any[] = props.metricsByMonth.map(metric => metric.metricDate);
    const data: any[] = props.metricsByMonth.map(metric => metric.totalReceivedCalls);
    const data1: any[] = props.metricsByMonth.map(metric => metric.totalAttendedCalls);

    newOption.xAxis.data.length = 0;
    newOption.xAxis.data.push(...x);

    newOption.series[0].data.length = 0;
    newOption.series[0].data.push(...data);

    newOption.series[1].data.length = 0;
    newOption.series[1].data.push(...data1);

    setOption(newOption);
  };

  useEffect(() => {
    getData();
  }, [props.metricsByMonth]);

  return <ReactECharts option={option} lazyUpdate={true} />;
};

export default ReceivedAndAttendedChart;
