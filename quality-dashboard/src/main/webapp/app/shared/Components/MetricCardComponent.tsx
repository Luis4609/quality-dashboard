import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Card, CardHeader, CardBody, CardTitle, CardText, CardFooter, Row, Col } from 'reactstrap';

// danger, success

function MetricCardComponent(props: { footer: string; title: string; color: string; data: number; previousData: number }) {
  
  function getColor(data: number, previousData: number) {
    const dataDiff = data - previousData;

    if (dataDiff === 0) {
      return 'text-warning mr-2 ml-2';
    }
    if (dataDiff > 0) {
      return 'text-success mr-2 ml-2';
    }
    return 'text-danger mr-2 ml-2';
  }

  function getArrowIcon(data: number, previousData: number) {
    const dataDiff = data - previousData;

    if (dataDiff === 0) {
      return 'circle-dot';
    }
    if (dataDiff > 0) {
      return 'arrow-up';
    }
    return 'arrow-down';
  }

  const getBackgroundColor = (data: number, previousData: number) => {
    const dataDiff = data - previousData;

    if (dataDiff === 0) {
      return { backgroundColor: '#f2a15e' };
    }
    if (dataDiff > 0) {
      return { backgroundColor: '#8df2c3' };
    }
    return { backgroundColor: '#f0788a' };
  };

  return (
    <>
      <Card className="card-stats mb-4" color="#8df2c3" style={getBackgroundColor(props.data, props.previousData)}>
        <CardBody>
          <Row>
            <div className="col">
              <CardTitle className="text-uppercase text-muted mb-0">{props.title}</CardTitle>
              <span className="h4 font-weight-bold mb-0">{props.data}</span>
            </div>
            <Col className="col-auto">
              {/* <div className="icon icon-shape rounded-circle">
                <FontAwesomeIcon icon="phone" size="xl" />
              </div> */}
              <span className="fa-layers fa-fw fa-lg">
                <FontAwesomeIcon icon="square" size="2xl" color="#db405f" />
                <FontAwesomeIcon icon="phone" transform="shrink-6" size="2xl" inverse />
              </span>
            </Col>
          </Row>
          <p className="mt-3 mb-0 text-muted text-sm">
            <span className={getColor(props.data, props.previousData)}>
              <FontAwesomeIcon icon={getArrowIcon(props.data, props.previousData)} className="fa-pull-left" />
              {props.data - props.previousData}
            </span>
            <span className="m-2 text-nowrap">{props.footer}</span>
          </p>
        </CardBody>
      </Card>
    </>
  );
}

export default MetricCardComponent;
