import React, { useEffect, useState } from 'react';

import ReactECharts from 'echarts-for-react';

// Import the echarts core module, which provides the necessary interfaces for using echarts.
import { IDailyCallsMetricsByDate } from 'app/shared/model/daily-calls.model';
import { BarChart } from 'echarts/charts';
import { GridComponent, TitleComponent, TooltipComponent } from 'echarts/components';
import * as echarts from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { cloneDeep } from 'lodash';

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

const ReceivedAndAttendedChart = (props: {
  metricsByMonth: IDailyCallsMetricsByDate[];
  startDate: string;
  endDate: string;
  text: { title: string; xAxisName: string; yAxisName: string; legend: string; legend2: string };
}) => {
  const [option, setOption] = useState(DEFAULT_OPTIONS);

  const getData = () => {
    const newOption = cloneDeep(option); // immutable

    // translate text
    newOption.title.text = `${props.text.title} (${props.startDate} - ${props.endDate})`;
    newOption.xAxis.name = props.text.xAxisName;
    newOption.yAxis[0].name = props.text.yAxisName;
    newOption.series[0].name = props.text.legend;
    newOption.series[0].name = props.text.legend;
    newOption.series[1].name = props.text.legend2;

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
