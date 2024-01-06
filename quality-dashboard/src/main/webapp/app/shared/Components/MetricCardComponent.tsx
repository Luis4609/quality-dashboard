import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Card, CardBody, CardTitle, Col, Row } from 'reactstrap';

const getColor = (data: number, previousData: number) => {
  const dataDiff = data - previousData;

  if (dataDiff === 0) {
    return 'text-warning mr-2 ml-2';
  }
  if (dataDiff > 0) {
    return 'text-success mr-2 ml-2';
  }
  return 'text-danger mr-2 ml-2';
};

const getArrowIcon = (data: number, previousData: number) => {
  const dataDiff = data - previousData;

  if (dataDiff === 0) {
    return 'equals';
  }
  if (dataDiff > 0) {
    return 'arrow-up';
  }
  return 'arrow-down';
};

const getBackgroundColor = (data: number, previousData: number) => {
  const dataDiff = data - previousData;

  if (dataDiff === 0) {
    // ffc107 -f2a15e
    return { backgroundColor: '#ffc107' };
  }
  if (dataDiff > 0) {
    // 6AB187 - 1BCFB4 - 198754 - 00d284
    return { backgroundColor: '#00d284' };
  }
  // AB2E3C
  return { backgroundColor: '#AB2E3C' };
};

function MetricCardComponent(props: { footer: string; title: string; color: string; data: number; previousData: number }) {
  return (
    <Card className="card-stats mb-4" color="#8df2c3" style={getBackgroundColor(props.data, props.previousData)}>
      <CardBody>
        <Row>
          <div className="col">
            <CardTitle className="text-uppercase mb-0">{props.title}</CardTitle>
            <span className="h4 font-weight-bold mb-0">{props.data}</span>
          </div>
          <Col className="col-auto">
            <span className="fa-layers fa-fw fa-xl ">
              <FontAwesomeIcon icon="square" size="2xl" color="#353d47" opacity={0.65} />
              <FontAwesomeIcon icon="phone" transform="shrink-6" size="2xl" inverse rotation={90} />
            </span>
          </Col>
        </Row>
        <p className="mt-3 mb-0 text-sm">
          <span>
            <FontAwesomeIcon
              icon={getArrowIcon(props.data, props.previousData)}
              className="fa-pull-left fa-border"
              style={{ borderColor: 'rgba(0,0,0,0)' }}
            />
            {props.data - props.previousData}
          </span>
          <span className="m-2 text-nowrap">{props.footer}</span>
        </p>
      </CardBody>
    </Card>
  );
}

export default MetricCardComponent;
