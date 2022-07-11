describe('empty spec', () => {
  it('should perform a POST request to Command and send QAF', () => {
    let host = Cypress.env('host.command') ? Cypress.env('host.command') : 'localhost';
    cy.request("POST", `http://${host}:8080/video-series`, {
      "name": "Glee",
      "volumes": 121,
      "cashValue": 1000000,
      "genre": "DRAMA"
    })
  })
})