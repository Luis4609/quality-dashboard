import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Form, Input, Label, Row } from 'reactstrap';
import { useNavigate } from 'react-router';
import { uploadExcelEntity } from './reducers/daily-calls-upload.reducer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const DailyCallsUpload = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const updateSuccess = useAppSelector(state => state.dailyCalls.updateSuccess);

  const handleClose = () => {
    navigate('/daily-calls' + location.search);
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const [file, setFile] = useState<any>();

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFile(e.target.files[0]);
    }
  };

  const handleUpload = () => {
    const formData = new FormData();
    formData.append('file', file);

    dispatch(uploadExcelEntity(formData));
  };

  return (
    <>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 data-cy="dailyCallsUploadExcel">
            <Translate contentKey="qualitydashboardApp.dailyCalls.upload.title">DailyCalls Upload data from Excel</Translate>
          </h2>
        </Col>
      </Row>

      <Row className="justify-content-center">
        <Col md="8">
          <Form encType="multipart/form">
            <Label className="visually-hidden" for="excelUpload">
              Select DailyCalls Excel
            </Label>
            <Input id="excelUpload" name="excel" type="file" onChange={handleFileChange} style={{ marginBottom: '1em' }} />

            <Button color="primary" type="submit" onClick={handleUpload} data-cy="entityCreateSaveButton">
              <FontAwesomeIcon icon="file-arrow-up" />
              &nbsp;
              <Translate contentKey="qualitydashboardApp.dailyCalls.upload.button">Upload file</Translate>
            </Button>
          </Form>
        </Col>
      </Row>
    </>
  );
};

export default DailyCallsUpload;
