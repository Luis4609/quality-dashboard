import React, { useEffect, useState } from 'react';

import ReactECharts from 'echarts-for-react';

// Import the echarts core module, which provides the necessary interfaces for using echarts.
import { IDailyChatsMetrics } from 'app/shared/model/daily-chats.model';
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
  title: { text: 'Chats - Recibidos y Atendidos', textStyle: { fontSize: 14 } },
  tooltip: {},
  legend: { top: 'bottom' },
  xAxis: {
    name: 'Día',
    data: [],
  },
  yAxis: [
    {
      type: 'value',
      name: 'Nº de Chats',
      axisLabel: {
        formatter: '{value}',
      },
    },
  ],
  series: [
    {
      name: 'Chats recibidos',
      type: 'line',
      data: [],
    },
    {
      name: 'Chats atendidos',
      type: 'line',
      data: [],
    },
  ],
};

const ReceivedAndAttendedChart = (props: { metricsByMonth: IDailyChatsMetrics[]; startDate: string; endDate: string }) => {

  const [option, setOption] = useState(DEFAULT_OPTIONS);

  const getData = () => {
    const newOption = cloneDeep(option); // immutable

    newOption.title.text = `Llamadas - Recibidas y Atendidas (${props.startDate} - ${props.endDate})`;

    const x: any[] = props.metricsByMonth.map(metric => metric.metricDate);
    const data: any[] = props.metricsByMonth.map(metric => metric.totalReceivedChats);
    const data1: any[] = props.metricsByMonth.map(metric => metric.totalAttendedChats);

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
