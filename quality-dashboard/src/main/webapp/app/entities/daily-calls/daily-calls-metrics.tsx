import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
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

  const date = new Date();
  // Get year, month, and day part from the date
  const year = date.toLocaleString('default', { year: 'numeric' });
  const month = date.toLocaleString('default', { month: '2-digit' });
  const day = date.toLocaleString('default', { day: '2-digit' });

  //TODO validate dates
  // Generate yyyy-mm-dd date string
  const formattedCurrentDate = year + '-' + month + '-' + day;

  const formattedStartDate = year + '-' + month + '-' + "01";

  const [startDate, setStarDate] = useState(formattedStartDate);

  const [currentDate, setCurrentDate] = useState(formattedCurrentDate);

  // eslint-disable-next-line no-console
  console.log(`${formattedCurrentDate} -- ${currentDate}`);

  const dailyCallsList = useAppSelector(state => state.dailyCalls.entities);
  const dailyCallsMetrics = useAppSelector(state => state.dailyCallsMetrics.entity);

  const metricsByMonth = useAppSelector(state => state.dailyCallsMetricsByMonth.entities);

  const labels = dailyCallsList.map((dailyCalls: { day: any }) => dailyCalls.day);

  const dispatch = useAppDispatch();

  const getAllMetrics = () => {
    dispatch(getMetrics({startDate, endDate: currentDate}));
  };

  useEffect(() => {
    getAllMetrics();
  }, [startDate, currentDate]);

  const getAllMetricsByDate = () => {
    dispatch(getMetricsWithDate(Number(year)));
    dispatch(getMetricsByMonth(month));
  };

  useEffect(() => {
    getAllMetricsByDate();
  }, []);


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
            <label>Pick start date: </label>
            <Input type="date" value={startDate} onChange={(e) => setStarDate(e.target.value)}/>
          </Col>

          <Col>
            <label>Pick end date: </label>
            <Input type="date" value={currentDate} onChange={(e) => setCurrentDate(e.target.value)}/>
          </Col>
        </Row>
        <Row>
          <Col>
            <MetricCardComponent
              color="primary"
              title="Llamadas recibidas"
              footer="año anterior"
              previousData={dailyCallsMetrics.previous.totalReceivedCalls}
              data={dailyCallsMetrics.current.totalReceivedCalls}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="danger"
              title="Llamadas atendidas"
              footer={''}
              previousData={dailyCallsMetrics.previous.totalAttendedCalls}
              data={dailyCallsMetrics.current.totalAttendedCalls}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="success"
              title="Llamadas perdidas"
              footer="año anterior"
              previousData={dailyCallsMetrics.previous.totalLostCalls}
              data={dailyCallsMetrics.current.totalLostCalls}
            ></MetricCardComponent>
          </Col>
        </Row>
        <Row>
          <Col>
            <MetricCardComponent
              color="primary"
              title="Llamadas atendidas agentes externos"
              footer="año anterior"
              previousData={dailyCallsMetrics.previous.totalAttendedCallsExternalAgent}
              data={dailyCallsMetrics.current.totalAttendedCallsExternalAgent}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="danger"
              title="Llamadas atendidas agentes internos"
              footer="año anterior"
              previousData={dailyCallsMetrics.previous.totalAttendedCallsInternalAgent}
              data={dailyCallsMetrics.current.totalAttendedCallsInternalAgent}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="primary"
              title="Llamadas atendidas (media - agente/dia)"
              footer="año anterior"
              previousData={-100000}
              data={1}
            ></MetricCardComponent>
          </Col>
        </Row>

        <Row>
          {/* <Col> */}
          {/* <ReactECharts
              option={this.getOption()}
              notMerge={true}
              lazyUpdate={true}
              theme={'theme_name'}
              onChartReady={this.onChartReadyCallback}
              onEvents={EventsDict}
              opts={}
            /> */}
          {/* </Col> */}
          <Col>
            <ReceivedAndAttendedChart></ReceivedAndAttendedChart>
          </Col>
          <Col>
            <CallsChart></CallsChart>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default DailyCallsMetrics;
