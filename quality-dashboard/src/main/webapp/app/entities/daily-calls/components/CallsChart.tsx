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
  title: { text: 'Llamadas - Recibidas y Porcentaje atención', textStyle: { fontSize: 14 } },
  tooltip: {},
  legend: { top: 'bottom' },
  xAxis: {
    data: [],
  },
  yAxis: [
    {
      type: 'value',
      name: 'Recibidas',
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

const ReceveidAndAttendedPercentageChart = (props: {
  metricsYTD: IDailyCallsMetricsByDate[];
  startDate: string;
  endDate: string;
  text: { title: string; yAxisName2: string; yAxisName: string; legend: string; legend2: string };
}) => {
  const [option, setOption] = useState(DEFAULT_OPTIONS);

  const getData = () => {
    const newOption = cloneDeep(option);

    // eslint-disable-next-line prefer-const
    let initialDate = new Date(props.startDate);

    newOption.title.text = `${props.text.title} (${initialDate.toLocaleString('default', {
      month: 'long',
      year: 'numeric',
    })} - ${new Date(props.endDate).toLocaleString('default', { month: 'long', year: 'numeric' })})`;

    // translate text
    // newOption.xAxis.name = props.text.xAxisName;
    newOption.yAxis[0].name = props.text.yAxisName;
    newOption.yAxis[1].name = props.text.yAxisName2;

    newOption.series[0].name = props.text.legend;
    newOption.series[0].name = props.text.legend;
    newOption.series[1].name = props.text.legend2;

    const x: any[] = props.metricsYTD.map(metric => {
      let displayDate = (new Date(metric.metricDate).getMonth() + 1).toString();
      displayDate += '/' + new Date(metric.metricDate).getFullYear().toString();
      return displayDate;
    });
    const data: any[] = props.metricsYTD.map(metric => metric.totalReceivedCalls);
    const data1: any[] = props.metricsYTD.map(metric => ((metric.totalAttendedCalls / metric.totalReceivedCalls) * 100).toFixed(2));

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

export default ReceveidAndAttendedPercentageChart;
