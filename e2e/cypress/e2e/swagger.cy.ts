describe('Favorite Lyrics E2E Tests', () => {
  it('shows swagger for Command', () => {
    cy.log("Test Disabled");
    let host = Cypress.env('host.command') ?  Cypress.env('host.command') : 'localhost';
    cy.visit(`http://${host}:8080/swagger-ui/index.html`);
    cy.get('h2').contains('OpenAPI definition').should('not.be.null');
    cy.wait(1000);

    cy.get('div[class="servers"] > label > select > option').should('have.value', 'http://localhost:8080')
  });

  it('shows swagger for Query', () => {
    cy.log("Test Disabled");
    let host = Cypress.env('host.query') ?  Cypress.env('host.query') : 'localhost';
    cy.visit(`http://${host}:8090/swagger-ui/index.html`);
    cy.get('h2').contains('OpenAPI definition').should('not.be.null');
    cy.wait(1000);

    cy.get('div[class="servers"] > label > select > option').should('have.value', 'http://localhost:8090')
  });
})