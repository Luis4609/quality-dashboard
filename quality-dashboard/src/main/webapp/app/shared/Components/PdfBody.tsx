import React from 'react';
import { Col, Row } from 'reactstrap';

function PdfBody({ startDate, currentDate, title }) {
  const pdfTitle = `Desde: ${startDate}  Hasta: ${currentDate}`;
  return (
    <div style={{ display: 'flex', flexDirection: 'column', marginLeft: '10px' }}>
      <Row>
        <Col>
          <h3>{title}</h3>
        </Col>
      </Row>

      <Row style={{ display: 'flex', flexDirection: 'row' }}>
        <p>{pdfTitle}</p>
      </Row>
    </div>
  );
}

export default PdfBody;
