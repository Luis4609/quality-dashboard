import './home.scss';

import React, { useState } from 'react';
import { Translate } from 'react-jhipster';
import { Button, Card, CardText, CardTitle, Col, Nav, NavItem, NavLink, Row, TabContent, TabPane } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import DailyCallsMetrics from 'app/entities/daily-calls/daily-calls-metrics';
import classnames from 'classnames';
import DailyCallsDashboard from 'app/entities/daily-calls/components/DailyCallsDashboard';

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  // State for current active Tab
  const [currentActiveTab, setCurrentActiveTab] = useState('1');

  // Toggle active state for Tab
  const toggle = tab => {
    if (currentActiveTab !== tab) setCurrentActiveTab(tab);
  };

  return (
    <>
      <Row>
        <Col md="8">
          <h2 data-cy="">
            <Translate contentKey="home.title">Dashboard</Translate>
          </h2>
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
              Métricas de llamadas
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
              Métricas de chats
            </NavLink>
          </NavItem>
        </Nav>
        <TabContent activeTab={currentActiveTab}>
          <TabPane tabId="1">
            {/* <DailyCallsMetrics></DailyCallsMetrics> */}
            <DailyCallsDashboard></DailyCallsDashboard>
          </TabPane>
          <TabPane tabId="2">
            <Row>
              <Col sm="6">
                <Card body>
                  <CardTitle>Special Title Treatment</CardTitle>
                  <CardText>With supporting text below as a natural lead-in to additional content.</CardText>
                  <Button>Go somewhere</Button>
                </Card>
              </Col>
              <Col sm="6">
                <Card body>
                  <CardTitle>Special Title Treatment</CardTitle>
                  <CardText>With supporting text below as a natural lead-in to additional content.</CardText>
                  <Button>Go somewhere</Button>
                </Card>
              </Col>
            </Row>
          </TabPane>
        </TabContent>
      </Row>
    </>
  );
};

export default Home;
