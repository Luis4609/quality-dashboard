import React from 'react';
import { Card, CardHeader, CardBody, CardTitle, CardText, CardFooter } from 'reactstrap';

// danger, success

function MetricCardComponent(props: { footer: string; title: string; color: string }) {
  return (
    <Card
      className="my-2"
      color={props.color}
      inverse
      style={{
        width: '18rem',
      }}
    >
      <CardHeader>{props.title}</CardHeader>
      <CardBody>
        <CardTitle tag="h5">38888</CardTitle>
        <CardText></CardText>
      </CardBody>
      <CardFooter>{props.footer}</CardFooter>
    </Card>
  );
}

export default MetricCardComponent;
