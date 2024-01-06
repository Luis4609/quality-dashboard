import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { ValidatedField, translate } from 'react-jhipster';
import { Col, Container, Row } from 'reactstrap';

import MetricCardComponent from 'app/shared/Components/MetricCardComponent';
import { BarElement, CategoryScale, Chart as ChartJS, Legend, LineElement, LinearScale, PointElement, Title, Tooltip } from 'chart.js';
import { getMetrics } from '../daily-calls-metrics.reducer';

// Import the echarts core module, which provides the necessary interfaces for using echarts.
import { BarChart } from 'echarts/charts';
import { GridComponent, TitleComponent, TooltipComponent } from 'echarts/components';
import * as echarts from 'echarts/core';

import { CanvasRenderer } from 'echarts/renderers';
import { toast } from 'react-toastify';
import CallsChart from '../components/CallsChart';
import ReceivedAndAttendedChart from '../components/ReceivedAndAttendedChart';
import { getMetricsByMonth } from '../daily-calls-metrics-by-month.reducer';
import { getMetricsWithDate } from '../daily-calls-metrics-date.reducer';

// Register the required components
echarts.use([TitleComponent, TooltipComponent, GridComponent, BarChart, CanvasRenderer]);

echarts.registerTheme('my_theme', {
  backgroundColor: '#f4cccc',
});

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, PointElement, LineElement);

export const DailyCallsDashboard = () => {
  // Get year, month, and day part from the date
  const date = new Date();
  const year = date.toLocaleString('default', { year: 'numeric' });
  const month = date.toLocaleString('default', { month: '2-digit' });
  const day = date.toLocaleString('default', { day: '2-digit' });

  // Generate yyyy-mm-dd date string
  const formattedCurrentDate = year + '-' + month + '-' + day;
  const formattedStartDate = year + '-' + month + '-' + '01';

  // State and variables
  const [startDate, setStarDate] = useState<string>(formattedStartDate);
  const [currentDate, setCurrentDate] = useState<string>(formattedCurrentDate);

  // use app context
  const dailyCallsMetrics = useAppSelector(state => state.dailyCallsMetrics.entity);
  const metricsYTD = useAppSelector(state => state.dailyCallsMetricsByDate.entities);
  const metricsByMonth = useAppSelector(state => state.dailyCallsMetricsByMonth.entities);

  const dispatch = useAppDispatch();

  useEffect(() => {
    if (startDate > currentDate) {
      toast.error(translate('qualitydashboardApp.dailyCalls.metrics.validation.startDateLessThanEndDate'));
    } else if (startDate === undefined || startDate.length === 0 || currentDate === undefined || currentDate.length === 0) {
      toast.error(translate('qualitydashboardApp.dailyCalls.metrics.validation.canNotBeEmpty'));
    } else {
      getAllMetrics();
      toast.success(translate('qualitydashboardApp.dailyCalls.metrics.dashboardUpdated'), { autoClose: 4000 });
    }
  }, [startDate, currentDate]);

  // dispatch, calls to APIs
  const getAllMetrics = () => {
    dispatch(getMetrics({ startDate, endDate: currentDate }));
    dispatch(getMetricsWithDate({ startDate, endDate: currentDate }));
    dispatch(getMetricsByMonth({ startDate, endDate: currentDate }));
  };

  return (
    <>
      <Container>
        <Row>
          <Col>
            <ValidatedField
              label={translate('qualitydashboardApp.dailyCalls.metrics.startDateInput')}
              id="daily-calls-metrics-startDateInput"
              value={startDate}
              name="startDateInput"
              data-cy="startDateInput"
              type="date"
              validate={{
                required: { value: true, message: translate('entity.validation.required') },
              }}
              onChange={e => setStarDate(e.target.value)}
            />
          </Col>
          <Col>
            <ValidatedField
              label={translate('qualitydashboardApp.dailyCalls.metrics.endDateInput')}
              value={currentDate}
              id="daily-calls-metrics-endDateInput"
              name="endDateInput"
              data-cy="endDateInput"
              type="date"
              validate={{
                required: { value: true, message: translate('entity.validation.required') },
              }}
              onChange={e => setCurrentDate(e.target.value)}
            />
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
              footer={'año anterior'}
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
          <Col></Col>
        </Row>

        <Row>
          <Col>
            <ReceivedAndAttendedChart
              metricsByMonth={metricsByMonth}
              startDate={startDate}
              endDate={currentDate}
            ></ReceivedAndAttendedChart>
          </Col>
          <Col>
            <CallsChart metricsYTD={metricsYTD} startDate={startDate} endDate={currentDate}></CallsChart>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default DailyCallsDashboard;
