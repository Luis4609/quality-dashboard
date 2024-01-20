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
  title: { text: 'Chats - Recibidos y Porcentaje atención', textStyle: { fontSize: 14 } },
  tooltip: {},
  legend: { top: 'bottom' },
  xAxis: {
    data: [],
  },
  yAxis: [
    {
      type: 'value',
      name: 'Recibidos',
      axisLabel: {
        formatter: '{value}',
      },
    },
    {
      type: 'value',
      name: 'Atendidos',
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
      name: 'Chats recibidos',
      type: 'bar',
      label: {
        show: true,
        position: 'inside',
      },
      data: [],
    },
    {
      name: '% Chats atendidos',
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

const ChatsAttendedPercentage = (props: { metricsYTD: IDailyChatsMetrics[]; startDate: string; endDate: string }) => {
  const [option, setOption] = useState(DEFAULT_OPTIONS);

  const getData = () => {
    const newOption = cloneDeep(option); // immutable

    // eslint-disable-next-line prefer-const
    let initialDate = new Date(props.startDate);

    newOption.title.text = `Llamadas - Recibidas y Porcentaje atención (${initialDate.toLocaleString('default', {
      month: 'long',
      year: 'numeric',
    })} - ${new Date(props.endDate).toLocaleString('default', { month: 'long', year: 'numeric' })})`;

    const x: any[] = props.metricsYTD.map(metric => {
      let displayDate = (new Date(metric.metricDate).getMonth() + 1).toString();
      displayDate += "/" + (new Date(metric.metricDate).getFullYear()).toString();
      return displayDate
    });
    const data: any[] = props.metricsYTD.map(metric => metric.totalReceivedChats);
    const data1: any[] = props.metricsYTD.map(metric => ((metric.totalAttendedChats / metric.totalReceivedChats) * 100).toFixed(2));

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
  }, [props.metricsYTD]);

  return <ReactECharts option={option} lazyUpdate={true} />;
};

export default ChatsAttendedPercentage;
