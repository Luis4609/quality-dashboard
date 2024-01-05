import React from 'react';
import { Card, CardHeader, CardBody, CardTitle, CardText, CardFooter } from 'reactstrap';

// danger, success

function MetricCardComponent(props: { footer: string; title: string; color: string; data: number; previousData: number }) {
  function getColor(data: number, previousData: number) {
    const dataDiff = data - previousData;

    if(dataDiff > 0) {
      return "success"  
    } 
    return 'danger';
  }

  return (
    <Card
      className="my-2"
      color={getColor(props.data, props.previousData)}
      inverse
      style={{
        width: '18rem',
      }}
    >
      <CardHeader>{props.title}</CardHeader>
      <CardBody>
        <CardTitle tag="h5">{props.data}</CardTitle>
        <CardText></CardText>
      </CardBody>
      <CardFooter>{props.data - props.previousData} {props.footer}</CardFooter>
    </Card>
  );
}

export default MetricCardComponent;
