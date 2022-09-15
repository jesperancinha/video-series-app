#!/bin/bash

function checkServiceByNameAndMessage() {
    name=$1
    message=$2
    docker-compose logs "$name" &> "logs"
    string=$(cat logs)
    counter=0
    while [[ "$string" != *"$message"* ]]
    do
      printf "."
      docker-compose logs "$name" &> "logs"
      string=$(cat logs)
      sleep 1
      counter=$((counter+1))
      if [ $counter -eq 200 ]; then
          echo "Failed after $counter tries! Cypress tests mail fail!!"
          exit
      fi
    done
    counter=$((counter+1))
    echo "Succeeded $name Service after $counter tries!"
}

checkServiceByNameAndMessage postgres 'database system is ready to accept connections'
checkServiceByNameAndMessage mongo 'Connection accepted'
checkServiceByNameAndMessage command 'Started VideoAppCommandLauncher in'
checkServiceByNameAndMessage query 'Started VideoAppQueryLauncher in'
