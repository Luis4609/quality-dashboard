import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Button, Col, Form, Input, Label, Row } from 'reactstrap';
import { useNavigate } from 'react-router';

export const DailyChatsUpload = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const updateSuccess = useAppSelector(state => state.dailyChats.updateSuccess);

  const handleClose = () => {
    navigate('/daily-chats' + location.search);
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

    // dispatch(uploadExcelEntity(formData));
  };

  return (
    <>
      <Row className="justify-content-center">
        <Col>
          <h2 data-cy="dailyChatsUploadExcel">
            <Translate contentKey="qualitydashboardApp.dailyChats.upload.title">DailyChats Upload data from Excel</Translate>
          </h2>
        </Col>
      </Row>
      <Form encType="multipart/form">
        <Row className="row-cols-lg-auto g-3 align-items-center">
          <Col>
            <Label className="visually-hidden" for="excelUpload">
              Select DailyChats Excel
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

export default DailyChatsUpload;
