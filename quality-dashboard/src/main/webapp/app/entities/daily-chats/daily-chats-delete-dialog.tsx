import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './daily-chats.reducer';
import DailyChatsDetail from './daily-chats-detail';

export const DailyChatsDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const dailyChatsEntity = useAppSelector(state => state.dailyChats.entity);
  const updateSuccess = useAppSelector(state => state.dailyChats.updateSuccess);

  const handleClose = () => {
    navigate('/daily-chats' + pageLocation.search);
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(dailyChatsEntity.id));
  };

  return (
    <>
      <DailyChatsDetail></DailyChatsDetail>
      <Modal isOpen toggle={handleClose}>
        <ModalHeader toggle={handleClose} data-cy="dailyChatsDeleteDialogHeading">
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="qualitydashboardApp.dailyChats.delete.question">
          <Translate contentKey="qualitydashboardApp.dailyChats.delete.question" interpolate={{ id: dailyChatsEntity.id }}>
            Are you sure you want to delete this DailyChats?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-dailyChats" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    </>
  );
};

export default DailyChatsDeleteDialog;
