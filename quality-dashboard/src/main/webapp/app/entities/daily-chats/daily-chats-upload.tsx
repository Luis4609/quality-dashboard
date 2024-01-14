import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect, useState } from 'react';
import { Translate, translate } from 'react-jhipster';
import { Button, Card, Col, Form, Input, Label, Row } from 'reactstrap';
import { useNavigate } from 'react-router';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { uploadExcelEntity } from './reducers/daily-chats-upload.reducer';

import { FileUpload } from 'primereact/fileupload';
import { toast } from 'react-toastify';

export const DailyChatsUpload = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const updateSuccess = useAppSelector(state => state.dailyChats.updateSuccess);

  // const handleClose = () => {
  //   navigate('/daily-chats' + location.search);
  // };

  // useEffect(() => {
  //   if (updateSuccess) {
  //     handleClose();
  //   }
  // }, [updateSuccess]);

  const [file, setFile] = useState<any>();

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFile(e.target.files[0]);
    }
  };

  // const handleUpload = () => {
  //   const formData = new FormData();
  //   formData.append('file', file);

  //   dispatch(uploadExcelEntity(formData));
  // };

  const onUpload = () => {
    // eslint-disable-next-line no-console
    console.log('Uploading file');
  };

  const handleUpload = event => {
    const file2 = event.files[0];

    const formData = new FormData();
    formData.append('file', file2);

    dispatch(uploadExcelEntity(formData));
  };

  return (
    <>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 data-cy="dailyChatsUploadExcel">
            <Translate contentKey="qualitydashboardApp.dailyChats.upload.title">DailyChats Upload data from Excel</Translate>
          </h2>
        </Col>
      </Row>

      <Row className="justify-content-center">
        <Col md="8">
          <Form encType="multipart/form" onSubmit={handleUpload}>
            <Label className="visually-hidden" for="excelUpload">
              Select Daily chats Excel
            </Label>
            <Input id="excelUpload" name="excel" type="file" onChange={handleFileChange} style={{ marginBottom: '1em' }} />

            <Button color="primary" type="submit" data-cy="entityCreateSaveButton">
              <FontAwesomeIcon icon="file-arrow-up" />
              &nbsp;
              <Translate contentKey="qualitydashboardApp.dailyChats.upload.button">Upload file</Translate>
            </Button>
          </Form>
        </Col>
      </Row>

      <Button color="primary">
        <FileUpload
          mode="basic"
          name="upload-chats"
          url="/api/daily-chats/upload-file"
          accept="/*"
          maxFileSize={1000000}
          customUpload
          uploadHandler={handleUpload}
          emptyTemplate={<p className="m-0">Drag and drop files to here to upload.</p>}
          auto
          chooseLabel={translate('qualitydashboardApp.dailyChats.home.uploadExcel')}
        />
      </Button>
    </>
  );
};

export default DailyChatsUpload;
