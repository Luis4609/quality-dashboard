import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './daily-chats.reducer';

export const DailyChats = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const dailyChatsList = useAppSelector(state => state.dailyChats.entities);
  const loading = useAppSelector(state => state.dailyChats.loading);
  const totalItems = useAppSelector(state => state.dailyChats.totalItems);

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

  return (
    <div>
      <h2 id="daily-chats-heading" data-cy="DailyChatsHeading">
        <Translate contentKey="qualitydashboardApp.dailyChats.home.title">Daily Chats</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="qualitydashboardApp.dailyChats.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/daily-chats/new" className="btn btn-primary jh-create-entity me-2" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="qualitydashboardApp.dailyChats.home.createLabel">Create new Daily Chats</Translate>
          </Link>
          <Link to="/daily-chats/upload" className="btn btn-primary jh-create-entity me-2" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="file-arrow-up" />
            &nbsp;
            <Translate contentKey="qualitydashboardApp.dailyCalls.home.uploadExcel">Upload data</Translate>
          </Link>
          <Link to="/daily-chats/metrics" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="chart-simple" />
            &nbsp;
            <Translate contentKey="qualitydashboardApp.dailyCalls.home.metrics">Go to metrics</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {dailyChatsList && dailyChatsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="qualitydashboardApp.dailyChats.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('day')}>
                  <Translate contentKey="qualitydashboardApp.dailyChats.day">Day</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('day')} />
                </th>
                <th className="hand" onClick={sort('totalDailyReceivedChats')}>
                  <Translate contentKey="qualitydashboardApp.dailyChats.totalDailyReceivedChats">Total Daily Received Chats</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyReceivedChats')} />
                </th>
                <th className="hand" onClick={sort('totalDailyConversationChatsTimeInMin')}>
                  <Translate contentKey="qualitydashboardApp.dailyChats.totalDailyConversationChatsTimeInMin">
                    Total Daily Conversation Chats Time In Min
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyConversationChatsTimeInMin')} />
                </th>
                <th className="hand" onClick={sort('totalDailyAttendedChats')}>
                  <Translate contentKey="qualitydashboardApp.dailyChats.totalDailyAttendedChats">Total Daily Attended Chats</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyAttendedChats')} />
                </th>
                <th className="hand" onClick={sort('totalDailyMissedChats')}>
                  <Translate contentKey="qualitydashboardApp.dailyChats.totalDailyMissedChats">Total Daily Missed Chats</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('totalDailyMissedChats')} />
                </th>
                <th className="hand" onClick={sort('avgDailyConversationChatsTimeInMin')}>
                  <Translate contentKey="qualitydashboardApp.dailyChats.avgDailyConversationChatsTimeInMin">
                    Avg Daily Conversation Chats Time In Min
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('avgDailyConversationChatsTimeInMin')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dailyChatsList.map((dailyChats, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/daily-chats/${dailyChats.id}`} color="link" size="sm">
                      {dailyChats.id}
                    </Button>
                  </td>
                  <td>{dailyChats.day ? <TextFormat type="date" value={dailyChats.day} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{dailyChats.totalDailyReceivedChats}</td>
                  <td>{dailyChats.totalDailyConversationChatsTimeInMin}</td>
                  <td>{dailyChats.totalDailyAttendedChats}</td>
                  <td>{dailyChats.totalDailyMissedChats}</td>
                  <td>{dailyChats.avgDailyConversationChatsTimeInMin}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/daily-chats/${dailyChats.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/daily-chats/${dailyChats.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (location.href = `/daily-chats/${dailyChats.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="qualitydashboardApp.dailyChats.home.notFound">No Daily Chats found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={dailyChatsList && dailyChatsList.length > 0 ? '' : 'd-none'}>
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

export default DailyChats;
