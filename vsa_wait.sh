#!/bin/bash
GITHUB_RUN_ID=${GITHUB_RUN_ID:-123}

function checkServiceByNameAndMessage() {
    name=$1
    message=$2
    messageLog=$(echo $message | tr '[:upper:]' '[:lower:]')
    docker-compose -p "${GITHUB_RUN_ID}" -f docker-compose.yml -f docker-compose-db.yml logs "$name" > "logs"
    string=$(cat logs)
    stringLog=$(echo $string | tr '[:upper:]' '[:lower:]')
    counter=0
    echo "Project $GITHUB_RUN_ID"
    echo -n "Starting service $name "
    while [[ "$stringLog" != *"$messageLog"* ]]
    do
      echo -e -n "\e[93m-\e[39m"
      docker-compose -p "${GITHUB_RUN_ID}" -f docker-compose.yml -f docker-compose-db.yml logs "$name" > "logs"
      string=$(cat logs)
      stringLog=$(echo $string | tr '[:upper:]' '[:lower:]')
      sleep 1
      counter=$((counter+1))
      if [ $counter -eq 200 ]; then
          echo -e "\e[91mFailed after $counter tries! Cypress tests may fail!!\e[39m"
          echo "$string"
          exit 1
      fi
    done
    counter=$((counter+1))
    echo -e "\e[92m Succeeded starting $name Service after $counter tries!\e[39m"
}

checkServiceByNameAndMessage postgres 'database system is ready to accept connections'
#checkServiceByNameAndMessage mongo 'Connection accepted'
checkServiceByNameAndMessage mongo 'waiting for connections'
checkServiceByNameAndMessage command 'Started VideoAppCommandLauncherKt in'
checkServiceByNameAndMessage query 'Started VideoAppQueryLauncherKt in'
