import DatePickerComponent from 'app/shared/components/DatePickerComponent'
import { formattedStartDate, formattedCurrentDate } from 'app/shared/services/DateService';
import React, { useEffect, useState } from 'react'
import { translate } from 'react-jhipster';
import { toast } from 'react-toastify';
import { Col, Container, Row } from 'reactstrap'

function DailyChatsDashboard() {

   // State and variables
   const [startDate, setStarDate] = useState<string>(formattedStartDate);
   const [currentDate, setCurrentDate] = useState<string>(formattedCurrentDate);
 
   useEffect(() => {
    if (startDate > currentDate) {
      toast.error(translate('qualitydashboardApp.dailyCalls.metrics.validation.startDateLessThanEndDate'));
    } else if (startDate === undefined || startDate.length === 0 || currentDate === undefined || currentDate.length === 0) {
      toast.error(translate('qualitydashboardApp.dailyCalls.metrics.validation.canNotBeEmpty'));
    } else {
      // getAllMetrics();                                                                                                                                                                                               
      // toast.success(translate('qualitydashboardApp.dailyCalls.metrics.dashboardUpdated'), { autoClose: 4000 });
    }
  }, [startDate, currentDate]);
  
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
    </Container>
  )
}

export default DailyChatsDashboard