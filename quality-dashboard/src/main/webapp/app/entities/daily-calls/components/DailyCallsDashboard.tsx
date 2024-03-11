/* eslint-disable @typescript-eslint/require-await */
/* eslint-disable @typescript-eslint/no-misused-promises */
import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useRef, useState } from 'react';
import { Translate, translate } from 'react-jhipster';
import { Button, Col, Container, Row } from 'reactstrap';

import DatePickerComponent from 'app/shared/components/DatePickerComponent';
import MetricCardComponent from 'app/shared/components/MetricCardComponent';

import { getMetrics } from '../reducers/daily-calls-metrics.reducer';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { getCallsThresholds } from 'app/entities/metric-threshold/metric-threshold-calls.reducer';
import PdfBody from 'app/shared/components/PdfBody';
import { formattedCurrentDate, formattedStartDate } from 'app/shared/services/DateService';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import { toast } from 'react-toastify';
import ReceveidAndAttendedPercentageChart from '../components/CallsChart';
import ReceivedAndAttendedChart from '../components/ReceivedAndAttendedChart';
import { getMetricsByMonth } from '../reducers/daily-calls-metrics-by-month.reducer';
import { getMetricsWithDate } from '../reducers/daily-calls-metrics-date.reducer';

export const DailyCallsDashboard = () => {
  // State and variables
  const [startDate, setStarDate] = useState<string>(formattedStartDate);
  const [currentDate, setCurrentDate] = useState<string>(formattedCurrentDate);
  const [isSearchDisable, setSearchDisable] = useState(false);

  // use app context
  const dailyCallsMetrics = useAppSelector(state => state.dailyCallsMetrics.entity);
  const metricsYTD = useAppSelector(state => state.dailyCallsMetricsByDate.entities);
  const metricsByMonth = useAppSelector(state => state.dailyCallsMetricsByMonth.entities);
  const dailyCallsThresholds = useAppSelector(state => state.metricThresholdCalls.entities);

  // loading state
  const getMetricsLoading = useAppSelector(state => state.dailyCallsMetrics.loading);
  const metricsYTDLoading = useAppSelector(state => state.dailyCallsMetricsByDate.loading);
  const metricsByMonthLoading = useAppSelector(state => state.dailyCallsMetricsByMonth.loading);
  const thresholdsLoading = useAppSelector(state => state.metricThresholdCalls.loading);

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
    dispatch(getCallsThresholds('dailyCalls'));
  };

  const pdfBodyRef = useRef(null);

  // generate pdf
  const createPDF = async () => {
    const pdf = new jsPDF('portrait', 'pt', 'a4');
    // Adding the fonts
    pdf.setFont('Inter-Regular');
    pdf.setFontSize(8);

    // dashboard data
    const data = await html2canvas(document.querySelector('#pdfCalls'));
    const img = data.toDataURL('image/png');
    const imgProperties = pdf.getImageProperties(img);
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = (imgProperties.height * pdfWidth) / imgProperties.width;

    pdf.addImage(img, 'PNG', 0, 85, pdfWidth, pdfHeight);
    // pdf.save('shipping_label.pdf');
    pdf.html(pdfBodyRef.current, {
      // eslint-disable-next-line @typescript-eslint/no-shadow
      async callback(doc) {
        doc.save(`calls-report-${startDate}-${currentDate}`);
      },
    });
  };

  return (
    <>
      <Container style={{ marginTop: '1em' }}>
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
          <Col style={{ display: 'flex', justifyContent: 'space-evenly' }}>
            <Button color="primary" onClick={getAllMetrics} disabled={isSearchDisable}>
              <FontAwesomeIcon icon="sync" />
            </Button>
            <Button color="secondary" onClick={createPDF} disabled={isSearchDisable}>
              <FontAwesomeIcon icon="download" />{' '}
              <Translate contentKey="qualitydashboardApp.dailyCalls.metrics.downloadPdf">Download PDF</Translate>
            </Button>
          </Col>
        </Row>
        <Container id="pdfCalls">
          {!(getMetricsLoading || metricsYTDLoading || metricsByMonthLoading || thresholdsLoading) ? (
            <>
              <Row>
                <Col>
                  <MetricCardComponent
                    title={translate('qualitydashboardApp.dailyCalls.metrics.cards.totalDailyReceivedCalls')}
                    footer={translate('qualitydashboardApp.dailyCalls.metrics.previousPeriodLabel')}
                    previousData={dailyCallsMetrics.previous.totalReceivedCalls}
                    data={dailyCallsMetrics.current.totalReceivedCalls}
                    thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalReceivedCalls')}
                    icon={'phone'}
                  ></MetricCardComponent>
                </Col>
                <Col>
                  <MetricCardComponent
                    title={translate('qualitydashboardApp.dailyCalls.metrics.cards.totalDailyAttendedCalls')}
                    footer={translate('qualitydashboardApp.dailyCalls.metrics.previousPeriodLabel')}
                    previousData={dailyCallsMetrics.previous.totalAttendedCalls}
                    data={dailyCallsMetrics.current.totalAttendedCalls}
                    thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalAttendedCalls')}
                    icon={'phone'}
                  ></MetricCardComponent>
                </Col>
                <Col>
                  <MetricCardComponent
                    title={translate('qualitydashboardApp.dailyCalls.metrics.cards.totalDailyMissedCalls')}
                    footer={translate('qualitydashboardApp.dailyCalls.metrics.previousPeriodLabel')}
                    previousData={dailyCallsMetrics.previous.totalLostCalls}
                    data={dailyCallsMetrics.current.totalLostCalls}
                    thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalLostCalls')}
                    icon={'phone'}
                  ></MetricCardComponent>
                </Col>
              </Row>
              <Row>
                <Col>
                  <MetricCardComponent
                    title={translate('qualitydashboardApp.dailyCalls.metrics.cards.totalDailyAttendedCallsExternalAgent')}
                    footer={translate('qualitydashboardApp.dailyCalls.metrics.previousPeriodLabel')}
                    previousData={dailyCallsMetrics.previous.totalAttendedCallsExternalAgent}
                    data={dailyCallsMetrics.current.totalAttendedCallsExternalAgent}
                    thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalAttendedCallsExternalAgent')}
                    icon={'phone'}
                  ></MetricCardComponent>
                </Col>
                <Col>
                  <MetricCardComponent
                    title={translate('qualitydashboardApp.dailyCalls.metrics.cards.totalDailyAttendedCallsInternalAgent')}
                    footer={translate('qualitydashboardApp.dailyCalls.metrics.previousPeriodLabel')}
                    previousData={dailyCallsMetrics.previous.totalAttendedCallsInternalAgent}
                    data={dailyCallsMetrics.current.totalAttendedCallsInternalAgent}
                    thresholds={dailyCallsThresholds.filter(t => t.metricName === 'totalAttendedCallsInternalAgent')}
                    icon={'phone'}
                  ></MetricCardComponent>
                </Col>
                <Col></Col>
              </Row>
            </>
          ) : (
            <p>loading</p>
          )}

          {/* CHARTS section */}
          <Row>
            <Col>
              <ReceivedAndAttendedChart
                metricsByMonth={metricsByMonth}
                startDate={startDate}
                endDate={currentDate}
                text={{
                  title: translate('qualitydashboardApp.dailyCalls.metrics.charts.receivedAndAttendedTitle'),
                  xAxisName: translate('qualitydashboardApp.dailyCalls.metrics.charts.xAxisName'),
                  yAxisName: translate('qualitydashboardApp.dailyCalls.metrics.charts.yAxisName'),
                  legend: translate('qualitydashboardApp.dailyCalls.metrics.charts.legend'),
                  legend2: translate('qualitydashboardApp.dailyCalls.metrics.charts.legend2'),
                }}
              ></ReceivedAndAttendedChart>
            </Col>
            <Col>
              <ReceveidAndAttendedPercentageChart
                metricsYTD={metricsYTD}
                startDate={startDate}
                endDate={currentDate}
                text={{
                  title: translate('qualitydashboardApp.dailyCalls.metrics.percentageCharts.attendedPercentageTitle'),
                  yAxisName2: translate('qualitydashboardApp.dailyCalls.metrics.percentageCharts.yAxisName2'),
                  yAxisName: translate('qualitydashboardApp.dailyCalls.metrics.percentageCharts.yAxisName'),
                  legend: translate('qualitydashboardApp.dailyCalls.metrics.percentageCharts.legend'),
                  legend2: translate('qualitydashboardApp.dailyCalls.metrics.percentageCharts.legend2'),
                }}
              ></ReceveidAndAttendedPercentageChart>
            </Col>
          </Row>
        </Container>
      </Container>

      <div style={{ visibility: 'hidden' }}>
        <div ref={pdfBodyRef}>
          <PdfBody startDate={startDate} currentDate={currentDate} title={'Reporte de llamadas'}></PdfBody>
        </div>
      </div>
    </>
  );
};

export default DailyCallsDashboard;
