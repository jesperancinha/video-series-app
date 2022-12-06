SHELL := /bin/bash
GITHUB_RUN_ID ?=123

cypress-open:
	yarn && npm run cypress
cypress-electron:
	yarn && docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-electron e2e-electron
cypress-chrome:
	yarn && docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-chrome e2e-chrome
cypress-firefox:
	yarn && docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-firefox e2e-firefox
cypress-edge:
	yarn && docker-compose -p ${GITHUB_RUN_ID} -f ../docker-compose.yml -f docker-compose.yml up --exit-code-from e2e-edge e2e-edge
