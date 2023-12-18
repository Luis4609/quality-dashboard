import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Container, Row } from 'reactstrap';

import { BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip } from 'chart.js';
import { Bar } from 'react-chartjs-2';
import MetricCardComponent from 'app/shared/Components/MetricCardComponent';
import { getMetrics } from './daily-calls.reducer';

import DatePicker from 'react-date-picker';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

// Different configurations options for the Chart
export const options = {
  responsive: true,
  plugins: {
    legend: {
      position: 'top' as const,
    },
    title: {
      display: true,
      text: 'Chart.js Bar Chart',
    },
  },
};

export const DailyCallsMetrics = () => {
  // State and variables

  const [startDate, setStartDate] = useState(new Date());

  const dailyCallsList = useAppSelector(state => state.dailyCalls.entities);

  const labels = dailyCallsList.map((dailyCalls: { day: any }) => dailyCalls.day);

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

  const dispatch = useAppDispatch();

  // const getAllMetrics = () => {
  //   dispatch(
  //     getMetrics({
  //       start: new Date(12-12-2023),
  //     }),
  //   );
  // };

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
        {/* <Row>
          <Col>
            Resumen mensual
            <Bar options={options} data={data} />
          </Col>
        </Row> */}

        <Row>
          <Col>
            <label>Pick start date: </label>
            <input type="date"></input>
          </Col>
        </Row>

        <Row>
          <Col>
            <MetricCardComponent color="primary" title="Llamadas recibidas" footer="-22.717 año anterior"></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent color="danger" title="Llamadas atendidas" footer="-22.717 año anterior"></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent color="success" title="Llamadas perdidas" footer="-22.717 año anterior"></MetricCardComponent>
          </Col>
        </Row>
        <Row>
          <Col>
            <MetricCardComponent
              color="primary"
              title="Llamadas atendidas agentes externos"
              footer="-22.717 año anterior"
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="danger"
              title="Llamadas atendidas agentes internos"
              footer="-22.717 año anterior"
            ></MetricCardComponent>
          </Col>
          <Col></Col>
        </Row>
        <Row>
          <Col>
            <MetricCardComponent
              color="primary"
              title="Llamadas atendidas agentes externos"
              footer="-22.717 año anterior"
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              color="danger"
              title="Llamadas atendidas agentes internos"
              footer="-22.717 año anterior"
            ></MetricCardComponent>
          </Col>
          <Col></Col>
        </Row>
        <Row>
          <Col>
            <MetricCardComponent color="primary" title="Llamadas atendidas (media - agente/dia)" footer="-22.717 año anterior"></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent color="danger" title="Llamadas atendidas" footer="-22.717 año anterior"></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent color="success" title="Llamadas perdidas" footer="-22.717 año anterior"></MetricCardComponent>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default DailyCallsMetrics;
