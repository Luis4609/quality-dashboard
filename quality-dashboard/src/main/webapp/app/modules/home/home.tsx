import './home.scss';

import React, { useState } from 'react';
import { Translate, translate } from 'react-jhipster';
import { Col, Nav, NavItem, NavLink, Row, TabContent, TabPane } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import DailyCallsDashboard from 'app/entities/daily-calls/components/DailyCallsDashboard';
import DailyChatsDashboard from 'app/entities/daily-chats/components/DailyChatsDashboard';
import classnames from 'classnames';
import { toast } from 'react-toastify';
import { updateWelcomeMessage } from 'app/shared/reducers/authentication';

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const welcomeMessageShown = useAppSelector(state => state.authentication.welcomeMessageShown);

  const dispatch = useAppDispatch();

  // State for current active Tab
  const [currentActiveTab, setCurrentActiveTab] = useState('1');

  // Toggle active state for Tab
  const toggle = tab => {
    if (currentActiveTab !== tab) setCurrentActiveTab(tab);
  };

  if (account.login !== undefined && !welcomeMessageShown) {
    const welcomeMessage = toast.success(`${translate('home.logged.welcomeMessage')} ${account.login}`, {
      autoClose: 4000,
      toastId: `${account.login}`,
    });
    dispatch(updateWelcomeMessage());
  }

  // eslint-disable-next-line no-console
  console.log(account);

  return (
    <>
      <Row>
        <Col md="8">
          <h2 data-cy="">
            <Translate contentKey="home.title">Dashboard</Translate>
          </h2>
          {/* {account?.login ? (
          ) : (
            <div>
              <Link to="/register" className="btn btn-primary mr-1">
                Log in or Register new account
              </Link>
            </div>
          )} */}
        </Col>
      </Row>
      <Row>
        <Nav tabs>
          <NavItem>
            <NavLink
              className={classnames({
                active: currentActiveTab === '1',
              })}
              onClick={() => {
                toggle('1');
              }}
            >
              <Translate contentKey="home.metrics.callsTabTitle">Métricas de Llamadas</Translate>
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink
              className={classnames({
                active: currentActiveTab === '2',
              })}
              onClick={() => {
                toggle('2');
              }}
            >
              <Translate contentKey="home.metrics.chatsTabTitle">Métricas de Chats</Translate>
            </NavLink>
          </NavItem>
        </Nav>
        <TabContent activeTab={currentActiveTab}>
          <TabPane tabId="1">
            <DailyCallsDashboard></DailyCallsDashboard>
          </TabPane>
          <TabPane tabId="2">
            <DailyChatsDashboard></DailyChatsDashboard>
          </TabPane>
        </TabContent>
      </Row>
    </>
  );
};

export default Home;
