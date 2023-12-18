import { useAppSelector } from 'app/config/store';
import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Container, Row } from 'reactstrap';

import { BarElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip } from 'chart.js';
import { Bar } from 'react-chartjs-2';

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

export const DailyChatsMetrics = () => {
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
            Resumen mensual
            <Bar options={options} data={data} />;
          </Col>
          <Col>Total Monthly received calls</Col>
          <Col>Total Monthly missed calls</Col>
        </Row>
      </Container>
    </>
  );
};

export default DailyChatsMetrics;
