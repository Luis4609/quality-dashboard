import { IconProp } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Card, CardBody, CardTitle, Col, Row } from 'reactstrap';
import { IMetricThreshold } from '../model/metric-threshold.model';
import { getBackgroundColor, getIcon } from '../services/MetricColorService';

function MetricCardComponent(props: {
  footer: string;
  title: string;
  color?: string;
  data: number;
  previousData: number;
  thresholds: IMetricThreshold;
  icon: IconProp;
}) {
  return (
    <>
      {props.thresholds !== undefined ? (
        <Card
          className="card-stats mb-4"
          color="#8df2c3"
          style={getBackgroundColor(
            props.data,
            props.previousData,
            props.thresholds[0]?.successPercentage,
            props.thresholds[0]?.dangerPercentage,
          )}
        >
          <CardBody>
            <Row>
              <div className="col">
                <CardTitle className="text-uppercase mb-0">{props.title}</CardTitle>
                <span className="h4 font-weight-bold mb-0">{props.data}</span>
              </div>
              <Col className="col-auto">
                <span className="fa-layers fa-fw fa-xl ">
                  <FontAwesomeIcon icon={props.icon} transform="shrink-6" size="2xl" inverse />
                </span>
              </Col>
            </Row>
            <p className="mt-3 mb-0 text-sm">
              <span>
                <FontAwesomeIcon
                  icon={getIcon(props.data, props.previousData)}
                  className="fa-pull-left fa-border"
                  style={{ borderColor: 'rgba(0,0,0,0)' }}
                  inverse
                />
                {props.data - props.previousData}
              </span>
              <span className="m-2 text-nowrap">{props.footer}</span>
            </p>
          </CardBody>
        </Card>
      ) : (
        <p>loading</p>
      )}
    </>
  );
}

export default MetricCardComponent;
