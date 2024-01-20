/* eslint-disable @typescript-eslint/require-await */
/* eslint-disable @typescript-eslint/no-misused-promises */
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import DatePickerComponent from 'app/shared/components/DatePickerComponent';
import MetricCardComponent from 'app/shared/components/MetricCardComponent';
import { formattedCurrentDate, formattedStartDate } from 'app/shared/services/DateService';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import React, { useEffect, useRef, useState } from 'react';
import { Translate, translate } from 'react-jhipster';
import { toast } from 'react-toastify';
import { Button, Col, Container, Row } from 'reactstrap';
import { getChatsMetricsByMonth } from '../reducers/daily-chats-metrics-group-by-month.reducer';
import { getChatsMetricsWithPrevious } from '../reducers/daily-chats-metrics-with-previous.reducer';
import { getChatsMetrics } from '../reducers/daily-chats-metrics.reducer';
import ChatsAttendedPercentage from './ChatsAttendedPercentageChart';
import PdfBody from './PdfBody';
import ReceivedAndAttendedChart from './ReceivedAndAttendedChart';

function DailyChatsDashboard() {
  // State and variables
  const [startDate, setStarDate] = useState<string>(formattedStartDate);
  const [currentDate, setCurrentDate] = useState<string>(formattedCurrentDate);
  const [isSearchDisable, setSearchDisable] = useState(false);

  const dispatch = useAppDispatch();

  useEffect(() => {
    getAllChatsMetricData();
  }, []);

  useEffect(() => {
    if (startDate > currentDate) {
      toast.error(translate('qualitydashboardApp.dailyChats.metrics.validation.startDateLessThanEndDate'));
      setSearchDisable(true);
    } else if (startDate === undefined || startDate.length === 0 || currentDate === undefined || currentDate.length === 0) {
      toast.error(translate('qualitydashboardApp.dailyChats.metrics.validation.canNotBeEmpty'));
      setSearchDisable(true);
    } else {
      setSearchDisable(false);
    }
  }, [startDate]);

  useEffect(() => {
    if (startDate > currentDate) {
      toast.error(translate('qualitydashboardApp.dailyChats.metrics.validation.endDateLessThanStartDate'));
      setSearchDisable(true);
    } else if (startDate === undefined || startDate.length === 0 || currentDate === undefined || currentDate.length === 0) {
      toast.error(translate('qualitydashboardApp.dailyChats.metrics.validation.canNotBeEmpty'));
      setSearchDisable(true);
    } else {
      setSearchDisable(false);
    }
  }, [currentDate]);

  const dailyChatsMetricsList = useAppSelector(state => state.dailyChatsMetrics.entities);
  const dailyChatsList = useAppSelector(state => state.dailyChatsMetricsWithPrevious.entity);
  const dailyChatsThreshold = useAppSelector(state => state.metricThreshold.thresholds);

  const chatsMetricsByMonth = useAppSelector(state => state.dailyChatsMetricsGroupByMonth.entities);

  // dispatch, calls to APIs
  const getAllChatsMetricData = () => {
    dispatch(getChatsMetricsWithPrevious({ startDate, endDate: currentDate }));
    dispatch(getChatsMetrics({ startDate, endDate: currentDate }));
    dispatch(getChatsMetricsByMonth({ startDate, endDate: currentDate }));
  };

  const pdfBodyRef = useRef(null);

  // PDF
  const createPDF = async () => {
    const pdf = new jsPDF("portrait", "pt", "a4"); 
    // Adding the fonts
    pdf.setFont('Inter-Regular');
    pdf.setFontSize(8)

    // data
    const data = await html2canvas(document.querySelector('#pdfChats'));
    const img = data.toDataURL('image/png');
    const imgProperties = pdf.getImageProperties(img);
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = (imgProperties.height * pdfWidth) / imgProperties.width;

    // eslint-disable-next-line no-console
    console.log(`PDF IMG DATA: ${pdfWidth} :: ${pdfHeight}`);

    pdf.addImage(img, 'PNG', 0, 85, pdfWidth, pdfHeight);
    // pdf.save('shipping_label.pdf');
    pdf.html(pdfBodyRef.current, {
      // eslint-disable-next-line @typescript-eslint/no-shadow
      async callback(doc) {
        doc.save(`chats-report-${startDate}-${currentDate}`);
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
            text={'qualitydashboardApp.dailyChats.metrics.startDateInput'}
          ></DatePickerComponent>
          <DatePickerComponent
            date={currentDate}
            setDate={setCurrentDate}
            text={'qualitydashboardApp.dailyChats.metrics.endDateInput'}
          ></DatePickerComponent>
          <Col style={{ display: 'flex', justifyContent: 'space-evenly' }}>
            <Button color="primary" onClick={getAllChatsMetricData} disabled={isSearchDisable}>
              <FontAwesomeIcon icon="sync" />
              {/* <Translate contentKey="qualitydashboardApp.dailyChats.metrics.refreshMetricsLabel">Refresh</Translate> */}
            </Button>
            <Button color="secondary" onClick={createPDF} disabled={isSearchDisable}>
              <FontAwesomeIcon icon="download" />{' '}
              <Translate contentKey="qualitydashboardApp.dailyChats.metrics.downloadPdf">Download PDF</Translate>
            </Button>
          </Col>
        </Row>
      </Container>

      <Container id="pdfChats">
        <Row>
          <Col>
            <MetricCardComponent
              title={translate('qualitydashboardApp.dailyChats.metrics.cards.totalDailyReceivedChats')}
              footer={translate('qualitydashboardApp.dailyChats.metrics.previousPeriodLabel')}
              previousData={dailyChatsList.previous.totalReceivedChats}
              data={dailyChatsList.current.totalReceivedChats}
              thresholds={dailyChatsThreshold.filter(t => t.metricName === 'totalReceivedCalls')}
              icon={'message'}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              title={translate('qualitydashboardApp.dailyChats.metrics.cards.totalDailyAttendedChats')}
              footer={translate('qualitydashboardApp.dailyChats.metrics.previousPeriodLabel')}
              previousData={dailyChatsList.previous.totalAttendedChats}
              data={dailyChatsList.current.totalAttendedChats}
              thresholds={dailyChatsThreshold.filter(t => t.metricName === 'totalAttendedChats')}
              icon={'message'}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              title={translate('qualitydashboardApp.dailyChats.metrics.cards.totalDailyMissedChats')}
              footer={translate('qualitydashboardApp.dailyChats.metrics.previousPeriodLabel')}
              previousData={dailyChatsList.previous.totalMissedChats}
              data={dailyChatsList.current.totalMissedChats}
              thresholds={dailyChatsThreshold.filter(t => t.metricName === 'totalMissedChats')}
              icon={'message'}
            ></MetricCardComponent>
          </Col>
        </Row>
        <Row>
          <Col>
            <MetricCardComponent
              title={translate('qualitydashboardApp.dailyChats.metrics.cards.totalDailyConversationChatsTimeInMin')}
              footer={translate('qualitydashboardApp.dailyChats.metrics.previousPeriodLabel')}
              previousData={dailyChatsList.previous.totalDailyConversationChatsTime}
              data={dailyChatsList.current.totalDailyConversationChatsTime}
              thresholds={dailyChatsThreshold.filter(t => t.metricName === 'totalDailyConversationChatsTime')}
              icon={'message'}
            ></MetricCardComponent>
          </Col>
          <Col>
            <MetricCardComponent
              title={translate('qualitydashboardApp.dailyChats.metrics.cards.avgDailyConversationChatsTimeInMin')}
              footer={translate('qualitydashboardApp.dailyChats.metrics.previousPeriodLabel')}
              previousData={dailyChatsList.previous.avgConversationChats}
              data={dailyChatsList.current.avgConversationChats}
              thresholds={dailyChatsThreshold.filter(t => t.metricName === 'avgConversationChats')}
              icon={'message'}
            ></MetricCardComponent>
          </Col>
          <Col></Col>
        </Row>

        <Row>
          <Col>
            <ReceivedAndAttendedChart
              metricsByMonth={dailyChatsMetricsList}
              startDate={startDate}
              endDate={currentDate}
            ></ReceivedAndAttendedChart>
          </Col>
          <Col>
            <ChatsAttendedPercentage metricsYTD={chatsMetricsByMonth} startDate={startDate} endDate={currentDate}></ChatsAttendedPercentage>
          </Col>
        </Row>
      </Container>

      <div style={{ visibility: 'hidden' }}>
        <div ref={pdfBodyRef}>
          <PdfBody
            startDate={startDate}
            currentDate={currentDate}
           title={"Reporte de chats"}
          ></PdfBody>
        </div>
      </div>
    </>
  );
}

export default DailyChatsDashboard;
