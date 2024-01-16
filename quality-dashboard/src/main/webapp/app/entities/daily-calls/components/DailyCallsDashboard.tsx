/* eslint-disable @typescript-eslint/no-misused-promises */
import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate, translate } from 'react-jhipster';
import { Button, Col, Container, Row } from 'reactstrap';

import DatePickerComponent from 'app/shared/components/DatePickerComponent';
import MetricCardComponent from 'app/shared/components/MetricCardComponent';

import { getMetrics } from '../reducers/daily-calls-metrics.reducer';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { getEntitiesByName } from 'app/entities/metric-threshold/metric-threshold.reducer';
import { formattedCurrentDate, formattedStartDate } from 'app/shared/services/DateService';
import { toast } from 'react-toastify';
import CallsChart from '../components/CallsChart';
import ReceivedAndAttendedChart from '../components/ReceivedAndAttendedChart';
import { getMetricsByMonth } from '../reducers/daily-calls-metrics-by-month.reducer';
import { getMetricsWithDate } from '../reducers/daily-calls-metrics-date.reducer';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

// const LazyMetricCard = lazy(() => import './ReceivedAndAttendedChart')

export const DailyCallsDashboard = () => {
  // State and variables
  const [startDate, setStarDate] = useState<string>(formattedStartDate);
  const [currentDate, setCurrentDate] = useState<string>(formattedCurrentDate);
  const [isSearchDisable, setSearchDisable] = useState(false);

  // use app context
  const dailyCallsMetrics = useAppSelector(state => state.dailyCallsMetrics.entity);
  const metricsYTD = useAppSelector(state => state.dailyCallsMetricsByDate.entities);
  const metricsByMonth = useAppSelector(state => state.dailyCallsMetricsByMonth.entities);

  const dailyCallsThresholds = useAppSelector(state => state.metricThreshold.thresholds)

  const dispatch = useAppDispatch();

  useEffect(() => {
    getAllMetrics();
  }, []);

  useEffect(() => {
    if (startDate > currentDate) {
      toast.error(translate('qualitydashboardApp.dailyCalls.metrics.validation.startDateLessThanEndDate'));
      setSearchDisable(true);
    } else if (startDate === undefined || startDate.length === 0 || currentDate === undefined || currentDate.length === 0) {
      toast.error(translate('qualitydashboardApp.dailyCalls.metrics.validation.canNotBeEmpty'));
      setSearchDisable(true);
    } else {
      setSearchDisable(false);
    }
  }, [startDate]);

  useEffect(() => {
    if (startDate > currentDate) {
      toast.error(translate('qualitydashboardApp.dailyCalls.metrics.validation.endDateLessThanStartDate'));
      setSearchDisable(true);
    } else if (startDate === undefined || startDate.length === 0 || currentDate === undefined || currentDate.length === 0) {
      toast.error(translate('qualitydashboardApp.dailyCalls.metrics.validation.canNotBeEmpty'));
      setSearchDisable(true);
    } else {
      setSearchDisable(false);
    }
  }, [currentDate]);

  // dispatch, calls to APIs
  const getAllMetrics = () => {
    dispatch(getMetrics({ startDate, endDate: currentDate }));
    dispatch(getMetricsWithDate({ startDate, endDate: currentDate }));
    dispatch(getMetricsByMonth({ startDate, endDate: currentDate }));
    dispatch(getEntitiesByName("dailyCalls"))
  };



  // PDF 
  const createPDF = async () => {   
    const pdf = new jsPDF("portrait", "pt", "a4"); 
    const data = await html2canvas(document.querySelector("#pdf"));
    const img = data.toDataURL("image/png");  
    const imgProperties = pdf.getImageProperties(img);
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = (imgProperties.height * pdfWidth) / imgProperties.width;
    pdf.addImage(img, "PNG", 0, 0, pdfWidth, pdfHeight);
    pdf.save("shipping_label.pdf");
  }


  return (
    <Container style={{ marginTop: '1em' }} id='pdf'>
      {/* Date Pickers section */}
      <Row className="align-items-center mb-3">
        <DatePickerComponent
          date={startDate}
          setDate={setStarDate}
          text={'qualitydashboardApp.dailyCalls.metrics.startDateInput'}
        ></DatePickerComponent>
        <DatePickerComponent
          date={currentDate}
          setDate={setCurrentDate}
          text={'qualitydashboardApp.dailyCalls.metrics.endDateInput'}
        ></DatePickerComponent>
        <Col>
          <Button color="primary" onClick={getAllMetrics} disabled={isSearchDisable}>
            <FontAwesomeIcon icon="sync" />
            <Translate contentKey="qualitydashboardApp.dailyCalls.metrics.refreshMetricsLabel">Refresh</Translate>
          </Button>
        </Col>
        <Col>
          <Button color="secondary" onClick={createPDF}  disabled={isSearchDisable}>
            <FontAwesomeIcon icon="download" />
            <Translate contentKey="qualitydashboardApp.dailyCalls.metrics.downloadPdf">Download PDF</Translate>
          </Button>
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
            thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalReceivedCalls')}
          ></MetricCardComponent>
        </Col>
        <Col>
          <MetricCardComponent
            color="danger"
            title="Llamadas atendidas"
            footer={'año anterior'}
            previousData={dailyCallsMetrics.previous.totalAttendedCalls}
            data={dailyCallsMetrics.current.totalAttendedCalls}
            thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalAttendedCalls')}
          ></MetricCardComponent>
        </Col>
        <Col>
          <MetricCardComponent
            color="success"
            title="Llamadas perdidas"
            footer="año anterior"
            previousData={dailyCallsMetrics.previous.totalLostCalls}
            data={dailyCallsMetrics.current.totalLostCalls}
            thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalLostCalls')}
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
            thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalAttendedCallsExternalAgent')}
          ></MetricCardComponent>
        </Col>
        <Col>
          <MetricCardComponent
            color="danger"
            title="Llamadas atendidas agentes internos"
            footer="año anterior"
            previousData={dailyCallsMetrics.previous.totalAttendedCallsInternalAgent}
            data={dailyCallsMetrics.current.totalAttendedCallsInternalAgent}
            thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalAttendedCallsInternalAgent')}
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
