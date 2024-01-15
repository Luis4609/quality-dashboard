import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './metric-threshold.reducer';

export const MetricThreshold = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const metricThresholdList = useAppSelector(state => state.metricThreshold.entities);
  const loading = useAppSelector(state => state.metricThreshold.loading);
  const totalItems = useAppSelector(state => state.metricThreshold.totalItems);

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
      <h2 id="metric-threshold-heading" data-cy="MetricThresholdHeading">
        <Translate contentKey="qualitydashboardApp.metricThreshold.home.title">Metric Thresholds</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="qualitydashboardApp.metricThreshold.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/metric-threshold/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="qualitydashboardApp.metricThreshold.home.createLabel">Create new Metric Threshold</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {metricThresholdList && metricThresholdList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="qualitydashboardApp.metricThreshold.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('entityName')}>
                  <Translate contentKey="qualitydashboardApp.metricThreshold.entityName">Entity Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('entityName')} />
                </th>
                <th className="hand" onClick={sort('metricName')}>
                  <Translate contentKey="qualitydashboardApp.metricThreshold.metricName">Metric Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('metricName')} />
                </th>
                <th className="hand" onClick={sort('successPercentage')}>
                  <Translate contentKey="qualitydashboardApp.metricThreshold.successPercentage">Success Percentage</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('successPercentage')} />
                </th>
                <th className="hand" onClick={sort('dangerPercentage')}>
                  <Translate contentKey="qualitydashboardApp.metricThreshold.dangerPercentage">Danger Percentage</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dangerPercentage')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {metricThresholdList.map((metricThreshold, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/metric-threshold/${metricThreshold.id}`} color="link" size="sm">
                      {metricThreshold.id}
                    </Button>
                  </td>
                  <td>{metricThreshold.entityName}</td>
                  <td>{metricThreshold.metricName}</td>
                  <td>{metricThreshold.successPercentage}</td>
                  <td>{metricThreshold.dangerPercentage}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/metric-threshold/${metricThreshold.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/metric-threshold/${metricThreshold.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (location.href = `/metric-threshold/${metricThreshold.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="qualitydashboardApp.metricThreshold.home.notFound">No Metric Thresholds found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={metricThresholdList && metricThresholdList.length > 0 ? '' : 'd-none'}>
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

export default MetricThreshold;
