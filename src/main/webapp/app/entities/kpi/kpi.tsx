import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './kpi.reducer';
import { IKpi } from 'app/shared/model/kpi.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Kpi = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const kpiList = useAppSelector(state => state.kpi.entities);
  const loading = useAppSelector(state => state.kpi.loading);
  const totalItems = useAppSelector(state => state.kpi.totalItems);
  const links = useAppSelector(state => state.kpi.links);
  const entity = useAppSelector(state => state.kpi.entity);
  const updateSuccess = useAppSelector(state => state.kpi.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="kpi-heading" data-cy="KpiHeading">
        Kpis
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Kpi
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {kpiList && kpiList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('title')}>
                    Title <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('reward')}>
                    Reward <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('rewardDistribution')}>
                    Reward Distribution <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('gradingProcess')}>
                    Grading Process <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('active')}>
                    Active <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('purpose')}>
                    Purpose <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('scopeOfWork')}>
                    Scope Of Work <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('rewardDistributionInfo')}>
                    Reward Distribution Info <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('reporting')}>
                    Reporting <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('fiatPoolFactor')}>
                    Fiat Pool Factor <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('grading')}>
                    Grading <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Kpi Round <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {kpiList.map((kpi, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${kpi.id}`} color="link" size="sm">
                        {kpi.id}
                      </Button>
                    </td>
                    <td>{kpi.title}</td>
                    <td>{kpi.reward}</td>
                    <td>{kpi.rewardDistribution}</td>
                    <td>{kpi.gradingProcess}</td>
                    <td>{kpi.active}</td>
                    <td>{kpi.purpose}</td>
                    <td>{kpi.scopeOfWork}</td>
                    <td>{kpi.rewardDistributionInfo}</td>
                    <td>{kpi.reporting}</td>
                    <td>{kpi.fiatPoolFactor}</td>
                    <td>{kpi.grading}</td>
                    <td>{kpi.kpiRound ? <Link to={`kpi-round/${kpi.kpiRound.id}`}>{kpi.kpiRound.displayName}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${kpi.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${kpi.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${kpi.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Kpis found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Kpi;
