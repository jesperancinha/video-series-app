describe('empty spec', () => {
    it('should perform a GET request to Query and receive QAF', () => {
        let host = Cypress.env('host.query') ? Cypress.env('host.query') : 'localhost';
        cy.request("GET", `http://${host.command}:8090/video-series`).its("body").should(body => {
            const expected = {
                "name": "Glee",
                "volumes": 121,
                "cashValue": 1000000,
                "genre": "DRAMA"
            }
            expect(body).to.have.length(4);
            expect(body[3].name).to.be.eq(expected.name);
            expect(body[3].volumes).to.be.eq(expected.volumes);
            expect(body[3].cashValue).to.be.eq(expected.cashValue);
            expect(body[3].genre).to.be.eq(expected.genre);
        })
    })
})