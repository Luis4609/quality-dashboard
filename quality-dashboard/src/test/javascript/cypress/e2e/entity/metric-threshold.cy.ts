import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('MetricThreshold e2e test', () => {
  const metricThresholdPageUrl = '/metric-threshold';
  const metricThresholdPageUrlPattern = new RegExp('/metric-threshold(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const metricThresholdSample = { entityName: 'fresco excluding baseboard', metricName: 'pole licensing unlawful' };

  let metricThreshold;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/metric-thresholds+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/metric-thresholds').as('postEntityRequest');
    cy.intercept('DELETE', '/api/metric-thresholds/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (metricThreshold) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/metric-thresholds/${metricThreshold.id}`,
      }).then(() => {
        metricThreshold = undefined;
      });
    }
  });

  it('MetricThresholds menu should load MetricThresholds page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('metric-threshold');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MetricThreshold').should('exist');
    cy.url().should('match', metricThresholdPageUrlPattern);
  });

  describe('MetricThreshold page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(metricThresholdPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MetricThreshold page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/metric-threshold/new$'));
        cy.getEntityCreateUpdateHeading('MetricThreshold');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricThresholdPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/metric-thresholds',
          body: metricThresholdSample,
        }).then(({ body }) => {
          metricThreshold = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/metric-thresholds+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/metric-thresholds?page=0&size=20>; rel="last",<http://localhost/api/metric-thresholds?page=0&size=20>; rel="first"',
              },
              body: [metricThreshold],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(metricThresholdPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MetricThreshold page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('metricThreshold');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricThresholdPageUrlPattern);
      });

      it('edit button click should load edit MetricThreshold page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MetricThreshold');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricThresholdPageUrlPattern);
      });

      it('edit button click should load edit MetricThreshold page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MetricThreshold');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricThresholdPageUrlPattern);
      });

      it('last delete button click should delete instance of MetricThreshold', () => {
        cy.intercept('GET', '/api/metric-thresholds/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('metricThreshold').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', metricThresholdPageUrlPattern);

        metricThreshold = undefined;
      });
    });
  });

  describe('new MetricThreshold page', () => {
    beforeEach(() => {
      cy.visit(`${metricThresholdPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MetricThreshold');
    });

    it('should create an instance of MetricThreshold', () => {
      cy.get(`[data-cy="entityName"]`).type('fuzzy astride finally');
      cy.get(`[data-cy="entityName"]`).should('have.value', 'fuzzy astride finally');

      cy.get(`[data-cy="metricName"]`).type('place brr');
      cy.get(`[data-cy="metricName"]`).should('have.value', 'place brr');

      cy.get(`[data-cy="successPercentage"]`).type('557.44');
      cy.get(`[data-cy="successPercentage"]`).should('have.value', '557.44');

      cy.get(`[data-cy="dangerPercentage"]`).type('13668.41');
      cy.get(`[data-cy="dangerPercentage"]`).should('have.value', '13668.41');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        metricThreshold = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', metricThresholdPageUrlPattern);
    });
  });
});
