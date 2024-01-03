import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Container, Row } from 'reactstrap';

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

  const [startDate, setStartDate] = useState(new Date());

  const dailyCallsList = useAppSelector(state => state.dailyCalls.entities);
  const metricsWithDate = useAppSelector(state => state.dailyCallsMetricsByDate.entities);
  const dailyCallsMetrics = useAppSelector(state => state.dailyCallsMetrics.entity);

  const loading = useAppSelector(state => state.dailyCalls.loading);

  const labels = dailyCallsList.map((dailyCalls: { day: any }) => dailyCalls.day);

  const dispatch = useAppDispatch();

  const getAllMetrics = () => {
    dispatch(getMetrics('2023-10-10'));
  };

  useEffect(() => {
    getAllMetrics();
  }, [startDate]);

  const getAllMetricsByDate = () => {
    dispatch(getMetricsWithDate(2023));
  };

  useEffect(() => {
    getAllMetricsByDate();
  }, []);

  const data = {
    labels,
    datasets: [
      {
        label: 'Received Calls',
        data: dailyCallsList.map(dailyCalls => dailyCalls.totalDailyReceivedCalls),
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
      },
      {
        label: 'Attended Calls',
        data: dailyCallsList.map(dailyCalls => dailyCalls.totalDailyAttendedCalls),
        backgroundColor: 'rgba(53, 162, 235, 0.5)',
      },
    ],
  };

  const dataPercentage = {
    labels,
    datasets: [
      {
        type: 'bar' as const,
        label: 'Received Calls',
        data: dailyCallsList.map(dailyCalls => dailyCalls.totalDailyReceivedCalls),
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
        borderColor: 'white',
        borderWidth: 2,
        yAxisID: 'y',
      },
      {
        type: 'line' as const,
        label: 'Percentege of Attended Calls',
        data: dailyCallsList.map(dailyCalls => (dailyCalls.totalDailyAttendedCalls / dailyCalls.totalDailyReceivedCalls) * 100),
        borderColor: 'rgba(53, 162, 235, 0.5)',
        borderWidth: 2,
        fill: false,
        yAxisID: 'y1',
      },
    ],
  };

  return (
    <>
      <Row>
        <Col md="8">
          <h2 data-cy="dailyCallsMetricsExcel">
            <Translate contentKey="qualitydashboardApp.dailyCalls.metrics.title">Daily Calls Metrics</Translate>
          </h2>
        </Col>
      </Row>
      <Container>
        <Row>
          <Col>
            <Bar options={options} data={data} />
          </Col>
          <Col>
            <Chart type="bar" options={optionsMultiAxis} data={dataPercentage} />
          </Col>
        </Row>

        <Row>
          <Col>
            {/* <ReactECharts
              option={this.getOption()}
              notMerge={true}
              lazyUpdate={true}
              theme={'theme_name'}
              onChartReady={this.onChartReadyCallback}
              onEvents={EventsDict}
              opts={}
            /> */}
            <ReactECharts option={atendidasSVDIOption} />
          </Col>
          <Col>
            <CallsChart></CallsChart>
          </Col>
        </Row>

        <Row>
          <Col>
            <label>Pick start date: </label>
            <input type="date"></input>
          </Col>
        </Row>

        <Row>
          <Col>
            <MetricCardComponent
              color="primary"
              title="Llamadas recibidas"
              footer="-22.717 año anterior"
              data={dailyCallsMetrics.totalReceivedCalls}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="danger"
              title="Llamadas atendidas"
              footer={''}
              data={dailyCallsMetrics.totalAttendedCalls}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="success"
              title="Llamadas perdidas"
              footer="-22.717 año anterior"
              data={dailyCallsMetrics.totalLostCalls}
            ></MetricCardComponent>
          </Col>
        </Row>
        <Row>
          <Col>
            <MetricCardComponent
              color="primary"
              title="Llamadas atendidas agentes externos"
              footer="-22.717 año anterior"
              data={dailyCallsMetrics.totalAttendedCallsExternalAgent}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="danger"
              title="Llamadas atendidas agentes internos"
              footer="-22.717 año anterior"
              data={dailyCallsMetrics.totalAttendedCallsInternalAgent}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="primary"
              title="Llamadas atendidas (media - agente/dia)"
              footer="-22.717 año anterior"
              data={1}
            ></MetricCardComponent>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default DailyCallsMetrics;
