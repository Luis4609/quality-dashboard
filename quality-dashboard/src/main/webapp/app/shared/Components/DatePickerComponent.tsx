import React from 'react';
import { translate } from 'react-jhipster';
import { Input, Label } from 'reactstrap';

function DatePickerComponent({ date, setDate, text }) {
  return (
    <>
      <Label for="dateInput" style={{fontSize: '1.10em'}}>{translate(text)}</Label>
      <Input
        value={date}
        id={text.substring(0, 20)}
        name="dateInput"
        data-cy="dateInput"
        type="date"
        onChange={e => setDate(e.target.value)}
        style={{marginBottom: '1em'}}
      />
    </>
  );
}

export default DatePickerComponent;
