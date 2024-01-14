import React, { useState, useEffect, useRef } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './daily-calls.reducer';
import { FileUpload } from 'primereact/fileupload';
import { uploadExcelEntity } from './reducers/daily-calls-upload.reducer';

export const DailyCalls = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'day', 'desc'), pageLocation.search),
  );

  const dailyCallsList = useAppSelector(state => state.dailyCalls.entities);
  const loading = useAppSelector(state => state.dailyCalls.loading);
  const totalItems = useAppSelector(state => state.dailyCalls.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  const fileRef = useRef(null);

  const handleUpload = event => {
    const formData = new FormData();
    formData.append('file', event.files[0]);

    dispatch(uploadExcelEntity(formData));

    // delete current file
    fileRef.current.clear();
  };

  return (
    <div>
      <h2 id="daily-calls-heading" data-cy="DailyCallsHeading">
        <Translate contentKey="qualitydashboardApp.dailyCalls.home.title">Daily Calls</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="qualitydashboardApp.dailyCalls.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/daily-calls/new" className="btn btn-primary jh-create-entity me-2" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="qualitydashboardApp.dailyCalls.home.createLabel">Create new Daily Calls</Translate>
          </Link>
          <Button color="primary">
            <FileUpload
              ref={fileRef}
              mode="basic"
              name="calls-upload"
              accept="/*"
              maxFileSize={1000000}
              customUpload
              uploadHandler={handleUpload}
              chooseLabel={translate('qualitydashboardApp.dailyCalls.home.uploadExcel')}
            />
          </Button>
        </div>
      </h2>
      <div className="table-responsive">
        {dailyCallsList && dailyCallsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('day')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.day">Day</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('day')} />
                </th>
                <th className="hand" onClick={sort('totalDailyReceivedCalls')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyReceivedCalls">Total Daily Received Calls</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyReceivedCalls')} />
                </th>
                <th className="hand" onClick={sort('totalDailyAttendedCalls')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyAttendedCalls">Total Daily Attended Calls</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyAttendedCalls')} />
                </th>
                <th className="hand" onClick={sort('totalDailyMissedCalls')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyMissedCalls">Total Daily Missed Calls</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyMissedCalls')} />
                </th>
                <th className="hand" onClick={sort('totalDailyReceivedCallsExternalAgent')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyReceivedCallsExternalAgent">
                    Total Daily Received Calls External Agent
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyReceivedCallsExternalAgent')} />
                </th>
                <th className="hand" onClick={sort('totalDailyAttendedCallsExternalAgent')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyAttendedCallsExternalAgent">
                    Total Daily Attended Calls External Agent
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyAttendedCallsExternalAgent')} />
                </th>
                <th className="hand" onClick={sort('totalDailyReceivedCallsInternalAgent')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyReceivedCallsInternalAgent">
                    Total Daily Received Calls Internal Agent
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyReceivedCallsInternalAgent')} />
                </th>
                <th className="hand" onClick={sort('totalDailyAttendedCallsInternalAgent')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyAttendedCallsInternalAgent">
                    Total Daily Attended Calls Internal Agent
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyAttendedCallsInternalAgent')} />
                </th>
                <th className="hand" onClick={sort('totalDailyCallsTimeInMin')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyCallsTimeInMin">Total Daily Calls Time In Min</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyCallsTimeInMin')} />
                </th>
                <th className="hand" onClick={sort('totalDailyCallsTimeExternalAgentInMin')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyCallsTimeExternalAgentInMin">
                    Total Daily Calls Time External Agent In Min
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyCallsTimeExternalAgentInMin')} />
                </th>
                <th className="hand" onClick={sort('totalDailyCallsTimeInternalAgentInMin')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.totalDailyCallsTimeInternalAgentInMin">
                    Total Daily Calls Time Internal Agent In Min
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyCallsTimeInternalAgentInMin')} />
                </th>
                <th className="hand" onClick={sort('avgDailyOperationTimeExternalAgentInMin')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.avgDailyOperationTimeExternalAgentInMin">
                    Avg Daily Operation Time External Agent In Min
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('avgDailyOperationTimeExternalAgentInMin')} />
                </th>
                <th className="hand" onClick={sort('avgDailyOperationTimeInternalAgentInMin')}>
                  <Translate contentKey="qualitydashboardApp.dailyCalls.avgDailyOperationTimeInternalAgentInMin">
                    Avg Daily Operation Time Internal Agent In Min
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('avgDailyOperationTimeInternalAgentInMin')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dailyCallsList.map((dailyCalls, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/daily-calls/${dailyCalls.id}`} color="link" size="sm">
                      {dailyCalls.id}
                    </Button>
                  </td>
                  <td>{dailyCalls.day ? <TextFormat type="date" value={dailyCalls.day} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{dailyCalls.totalDailyReceivedCalls}</td>
                  <td>{dailyCalls.totalDailyAttendedCalls}</td>
                  <td>{dailyCalls.totalDailyMissedCalls}</td>
                  <td>{dailyCalls.totalDailyReceivedCallsExternalAgent}</td>
                  <td>{dailyCalls.totalDailyAttendedCallsExternalAgent}</td>
                  <td>{dailyCalls.totalDailyReceivedCallsInternalAgent}</td>
                  <td>{dailyCalls.totalDailyAttendedCallsInternalAgent}</td>
                  <td>{dailyCalls.totalDailyCallsTimeInMin}</td>
                  <td>{dailyCalls.totalDailyCallsTimeExternalAgentInMin}</td>
                  <td>{dailyCalls.totalDailyCallsTimeInternalAgentInMin}</td>
                  <td>{dailyCalls.avgDailyOperationTimeExternalAgentInMin}</td>
                  <td>{dailyCalls.avgDailyOperationTimeInternalAgentInMin}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/daily-calls/${dailyCalls.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/daily-calls/${dailyCalls.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (location.href = `/daily-calls/${dailyCalls.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="qualitydashboardApp.dailyCalls.home.notFound">No Daily Calls found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={dailyCallsList && dailyCallsList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default DailyCalls;
