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

describe('DailyChats e2e test', () => {
  const dailyChatsPageUrl = '/daily-chats';
  const dailyChatsPageUrlPattern = new RegExp('/daily-chats(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const dailyChatsSample = {
    totalDailyReceivedChats: 32396,
    totalDailyConversationChatsTimeInMin: 25795,
    totalDailyAttendedChats: 25550,
    totalDailyMissedChats: 6791,
    avgDailyConversationChatsTimeInMin: 4624.82,
  };

  let dailyChats;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/daily-chats+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/daily-chats').as('postEntityRequest');
    cy.intercept('DELETE', '/api/daily-chats/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (dailyChats) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/daily-chats/${dailyChats.id}`,
      }).then(() => {
        dailyChats = undefined;
      });
    }
  });

  it('DailyChats menu should load DailyChats page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('daily-chats');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DailyChats').should('exist');
    cy.url().should('match', dailyChatsPageUrlPattern);
  });

  describe('DailyChats page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(dailyChatsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DailyChats page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/daily-chats/new$'));
        cy.getEntityCreateUpdateHeading('DailyChats');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyChatsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/daily-chats',
          body: dailyChatsSample,
        }).then(({ body }) => {
          dailyChats = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/daily-chats+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/daily-chats?page=0&size=20>; rel="last",<http://localhost/api/daily-chats?page=0&size=20>; rel="first"',
              },
              body: [dailyChats],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(dailyChatsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DailyChats page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('dailyChats');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyChatsPageUrlPattern);
      });

      it('edit button click should load edit DailyChats page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DailyChats');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyChatsPageUrlPattern);
      });

      it('edit button click should load edit DailyChats page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DailyChats');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyChatsPageUrlPattern);
      });

      it('last delete button click should delete instance of DailyChats', () => {
        cy.intercept('GET', '/api/daily-chats/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('dailyChats').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyChatsPageUrlPattern);

        dailyChats = undefined;
      });
    });
  });

  describe('new DailyChats page', () => {
    beforeEach(() => {
      cy.visit(`${dailyChatsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DailyChats');
    });

    it('should create an instance of DailyChats', () => {
      cy.get(`[data-cy="totalDailyReceivedChats"]`).type('22836');
      cy.get(`[data-cy="totalDailyReceivedChats"]`).should('have.value', '22836');

      cy.get(`[data-cy="totalDailyConversationChatsTimeInMin"]`).type('6579');
      cy.get(`[data-cy="totalDailyConversationChatsTimeInMin"]`).should('have.value', '6579');

      cy.get(`[data-cy="totalDailyAttendedChats"]`).type('31397');
      cy.get(`[data-cy="totalDailyAttendedChats"]`).should('have.value', '31397');

      cy.get(`[data-cy="totalDailyMissedChats"]`).type('4836');
      cy.get(`[data-cy="totalDailyMissedChats"]`).should('have.value', '4836');

      cy.get(`[data-cy="avgDailyConversationChatsTimeInMin"]`).type('4731.16');
      cy.get(`[data-cy="avgDailyConversationChatsTimeInMin"]`).should('have.value', '4731.16');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        dailyChats = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', dailyChatsPageUrlPattern);
    });
  });
});
