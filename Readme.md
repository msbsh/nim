# The nim game

## Installation
* Check out sources
* `mvn clean package`

## Run
* `java -jar target/nim-0.0.1-SNAPSHOT.jar`

## How to play
Visit [localhost:8081/swagger-ui.html](localhost:8081/swagger-ui.html) with your favorite web browser.

Currently there is only a simple interface containing of two _POST_ endpoints. One can be used to start a new game for a specified _username_.

The other is for playing a round of nim by providing the _gameId_, _playerName_ and the number of matches to take. The latter one only allows for a minimum of 1 and a maximum of 3 matches.

## Features to come
* Player management
* Metrics endpoints for game statistics
* User Interface
* Possibility to configure computer's strategy
* Multiplayer mode
* Mapstruct for more intuitive DTO's