import React from 'react';
import { translate } from 'react-jhipster';
import { Col, Input, Label } from 'reactstrap';

function DatePickerComponent({ date, setDate, text }) {
  return (
    <>
      <Col >
        <Label for="dateInput" style={{marginBottom: '0px'}}>
          {translate(text)}
        </Label>
      </Col>
      <Col className='col-4'>
        <Input
          value={date}
          id={text.substring(0, 20)}
          name="dateInput"
          data-cy="dateInput"
          type="date"
          onChange={e => setDate(e.target.value)}
        />
      </Col>
    </>
  );
}

export default DatePickerComponent;
