import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { translate } from 'react-jhipster';
import { Col, Container, Row } from 'reactstrap';

import MetricCardComponent from 'app/shared/components/MetricCardComponent';
import DatePickerComponent from 'app/shared/components/DatePickerComponent';

import { getMetrics } from '../reducers/daily-calls-metrics.reducer';

import { toast } from 'react-toastify';
import CallsChart from '../components/CallsChart';
import ReceivedAndAttendedChart from '../components/ReceivedAndAttendedChart';
import { getMetricsByMonth } from '../reducers/daily-calls-metrics-by-month.reducer';
import { getMetricsWithDate } from '../reducers/daily-calls-metrics-date.reducer';

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
      // toast.success(translate('qualitydashboardApp.dailyCalls.metrics.dashboardUpdated'), { autoClose: 4000 });
    }
  }, [startDate, currentDate]);

  // dispatch, calls to APIs
  const getAllMetrics = () => {
    dispatch(getMetrics({ startDate, endDate: currentDate }));
    dispatch(getMetricsWithDate({ startDate, endDate: currentDate }));
    dispatch(getMetricsByMonth({ startDate, endDate: currentDate }));
  };

  return (
    <Container style={{ marginTop: '1em' }}>
      {/* Date Pickers section */}
      <Row>
        <Col>
          <DatePickerComponent
            date={startDate}
            setDate={setStarDate}
            text={'qualitydashboardApp.dailyCalls.metrics.startDateInput'}
          ></DatePickerComponent>
        </Col>
        <Col>
          <DatePickerComponent
            date={currentDate}
            setDate={setCurrentDate}
            text={'qualitydashboardApp.dailyCalls.metrics.endDateInput'}
          ></DatePickerComponent>
        </Col>
      </Row>
      {/* METRICS section */}
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
      {/* CHARTS section */}
      <Row>
        <Col>
          <ReceivedAndAttendedChart metricsByMonth={metricsByMonth} startDate={startDate} endDate={currentDate}></ReceivedAndAttendedChart>
        </Col>
        <Col>
          <CallsChart metricsYTD={metricsYTD} startDate={startDate} endDate={currentDate}></CallsChart>
        </Col>
      </Row>
    </Container>
  );
};

export default DailyCallsDashboard;
