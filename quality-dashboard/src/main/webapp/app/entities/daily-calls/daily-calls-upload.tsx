import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useState } from 'react';
import { Translate } from 'react-jhipster';
import { useNavigate } from 'react-router-dom';
import { Button, Col, Form, FormGroup, FormText, Input, Label, Row } from 'reactstrap';
import { uploadExcelEntity } from './daily-calls.reducer';

export const DailyCallsUpload = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const uploading = useAppSelector(state => state.dailyCalls.uploading);
  const uploadingSuccess = useAppSelector(state => state.dailyCalls.uploadingSuccess);

  const [file, setFile] = useState<any>();

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFile(e.target.files[0]);
    }
  };

  const handleUpload = () => {
    // eslint-disable-next-line no-console
    console.log('Uploading file...');

    const formData = new FormData();
    formData.append('file', file);

    dispatch(uploadExcelEntity(formData));
  };

  return (
    <>
      <Row className="justify-content-center">
        <Col>
          <h2 data-cy="dailyCallsUploadExcel">
            <Translate contentKey="qualitydashboardApp.dailyCalls.upload.title">DailyCalls Upload data from Excel</Translate>
          </h2>
        </Col>
      </Row>
      <Form encType="multipart/form">
        <Row className="row-cols-lg-auto g-3 align-items-center">
          <Col>
            <Label className="visually-hidden" for="excelUpload">
              Select DailyCalls Excel
            </Label>
            <Input id="excelUpload" name="excel" type="file" onChange={handleFileChange}/>
          </Col>
          <Col>
            <Button color="primary" type="submit" onClick={handleUpload}>
              Upload File
            </Button>
          </Col>
        </Row>
      </Form>
    </>
  );
};

export default DailyCallsUpload;
