# Farm Harvest - Mini JavaFX Game

**Author:** Omarion Aubert  
**Course:** IST JAV  
**Project:** Game Project Submission  
**Date:** Dec 18th, 2025  

## Description
Farm Harvest is a simple 2D tile-based farming game. Players can move around, till soil, plant crops, and harvest them. Crops grow over in-game days with a day/night cycle.

## Controls
- WASD / Arrow Keys: Movement
- E or SPACE: Use Tool / Harvest
- 1 – 4: Select Tool or Crop
- ENTER: Toggle Menu

## Features
- Day/Night cycle with visual overlay
- Growing crops (Wheat, Tomato)
- Inventory display and toolbar
- Main Menu / Pause Screen
- Pickup animation when harvesting
- Background Music

- ## Tech Stack
- Java 21
- JavaFX
- Maven

## How to Run
1. Clone the repository
2. Run with Maven:
   ```bash
   mvn clean javafx:run

   
## Project Structure
farm_harvest/   
├── .vscode/                    ← IDE settings (safe to include or ignore)   
├── README.md                 ← Build & run instructions   
├── src/   
│   └── main/   
│       ├── java/   
│       │   └── io/github/game/   
│       │       ├── controllers/   
│       │       │   └── App.java   
│       │       ├── crops/   
│       │       │   ├── Crop.java   
│       │       │   ├── CropStage.java   
│       │       │   ├── Growable.java   
│       │       │   ├── Tomato.java   
│       │       │   └── Wheat.java   
│       │       ├── engine/   
│       │       │   ├── FlyingItem.java   
│       │       │   ├── GameLoop.java   
│       │       │   ├── InputHandler.java   
│       │       │   └── Renderer.java   
│       │       ├── entities/   
│       │       │   ├── Direction.java   
│       │       │   ├── HoeTool.java   
│       │       │   ├── Player.java   
│       │       │   ├── PlayerAction.java   
│       │       │   ├── SeedTool.java   
│       │       │   ├── SeedType.java   
│       │       │   ├── Tool.java   
│       │       │   └── ToolType.java   
│       │       ├── util/   
│       │       │   ├── Inventory.java   
│       │       │   └── ResourceManager.java   
│       │       └── world/   
│       │           ├── interact/   
│       │           │   └── Interactable.java   
│       │           ├── tiles/   
│       │           │   ├── AbstractTile.java   
│       │           │   ├── DirtTile.java   
│       │           │   ├── GrassTile.java   
│       │           │   ├── TileType.java   
│       │           │   └── WaterTile.java   
│       │           ├── DayCycle.java   
│       │           └── World.java   
│       └── resources/   
│           ├── audio/                ← music files   
│           ├── crops/                ← crop images   
│           ├── fonts/                ← pixel font files   
│           ├── player/               ← player sprites   
│           ├── tiles/                ← tile images   
│           └── tools/                ← hoe images   
├── target/                     ← Compiled build output (ignored)   
├── .gitignore   
└── pom.xml                    ← Maven build file     
