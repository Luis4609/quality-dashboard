import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { Col, Container, Input, Row } from 'reactstrap';

import MetricCardComponent from 'app/shared/Components/MetricCardComponent';
import { BarElement, CategoryScale, Chart as ChartJS, Legend, LineElement, LinearScale, PointElement, Title, Tooltip } from 'chart.js';
import { Bar, Chart } from 'react-chartjs-2';
import { getMetrics } from './daily-calls-metrics.reducer';

import ReactECharts from 'echarts-for-react';
// Import the echarts core module, which provides the necessary interfaces for using echarts.
import { BarChart } from 'echarts/charts';
import { GridComponent, TitleComponent, TooltipComponent } from 'echarts/components';
import * as echarts from 'echarts/core';

import { CanvasRenderer } from 'echarts/renderers';
import CallsChart from './components/CallsChart';
import { getMetricsWithDate } from './daily-calls-metrics-date.reducer';
import { atendidasSVDIOption } from './metrics/ChartsOptions';
import { getMetricsByMonth } from './daily-calls-metrics-by-month.reducer';
import ReceivedAndAttendedChart from './components/ReceivedAndAttendedChart';
import { toast } from 'react-toastify';
import DailyCallsDashboard from './components/DailyCallsDashboard';

// Register the required components
echarts.use([TitleComponent, TooltipComponent, GridComponent, BarChart, CanvasRenderer]);

echarts.registerTheme('my_theme', {
  backgroundColor: '#f4cccc',
});

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, PointElement, LineElement);

// Different configurations options for the Chart
export const options = {
  responsive: true,
  plugins: {
    legend: {
      position: 'top' as const,
    },
    title: {
      display: true,
      text: 'Total de llamadas recibidas - atendidas',
    },
  },
};

export const optionsMultiAxis = {
  responsive: true,
  interaction: {
    mode: 'index' as const,
    intersect: false,
  },
  stacked: false,
  plugins: {
    title: {
      display: true,
      text: 'Total de llamadas recibidas - atendidas',
    },
  },
  scales: {
    y: {
      type: 'linear' as const,
      display: true,
      position: 'left' as const,
    },
    y1: {
      type: 'linear' as const,
      display: true,
      position: 'right' as const,
      grid: {
        drawOnChartArea: false,
      },
    },
  },
};

//Llamadas - Distribución Tiempo de operación

export const DailyCallsMetrics = () => {
  // State and variables

  return (
    <>
      <Row>
        <Col md="8">
          <h2 data-cy="dailyCallsMetricsExcel">
            <Translate contentKey="qualitydashboardApp.dailyCalls.metrics.title">Daily Calls Metrics</Translate>
          </h2>
        </Col>
      </Row>
        <DailyCallsDashboard></DailyCallsDashboard>
    </>
  );
};

export default DailyCallsMetrics;
