# TETRIS
Tetris game to play alone or versus computer or to see computer vs computer play

## How to run
### Prerequisites
JRE 17+ installed on the system

### To run standalone
1. Download latest build from [releases](https://github.com/k8en/tetris/releases) page
2. Run downloaded file from command line with the next command<br>`java -jar tetris.jar`

### To run from IDE
1. Clone repository with the next command<br>`git clone https://github.com/k8en/tetris.git`
2. Run the next class<br>`org.kdepo.games.tetris.desktop.Launcher.main`

## How to play
Game contains two fields for play - at the left and at the right sides of the screen. Configure necessary play mode, select _START GAME_ and press _ENTER_:
- To play standalone select _HUMAN_ for the left field
- To not use some game field select _NOT USED_
- To play versus computer or to see computer vs computer play select any bot for the left and/or right fields

## Project structure
`desktop` contains main code logic for playing tetris game<br>
`bot` contains code for bots development<br>
`shared` contains constants, utils and other useful functions shared between all modules<br>
`k2d` graphic engine
