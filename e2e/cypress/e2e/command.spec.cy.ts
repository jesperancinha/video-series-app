describe('empty spec', () => {
  it('should perform a POST request to Command and send QAF', () => {
    cy.request("POST", "http://localhost:8080/video-series", {
      "name": "Glee",
      "volumes": 121,
      "cashValue": 1000000,
      "genre": "DRAMA"
    })
  })
})