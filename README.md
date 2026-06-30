# 🌱 Farm Harvest — Mini JavaFX Game

**Author:** Omarion Aubert  
**Course:** IST JAV  
**Project:** Game Project  
**Date:** December 18, 2025  

GitHub Repository:  
https://github.com/MrTunic/farm_harvest

---

## 1. Project Description

**Farm Harvest** is a simple 2D tile-based farming game written in Java using JavaFX.  
The player can move around a grid-based world, till soil, plant crops, and harvest them over time. Crops grow across multiple in-game days managed by a day/night cycle.

This project was developed to demonstrate correct and relevant use of **object-oriented programming concepts** studied in class, including inheritance, interfaces, abstraction, and package organization.

---

## 2. Controls

- **WASD / Arrow Keys** — Move player  
- **E or SPACE** — Use tool / interact / harvest  
- **1 – 4** — Select tool or seed  
- **ENTER** — Toggle controls / pause menu  

---

## 3. Features

- Tile-based world with walkable and non-walkable tiles  
- Day / night cycle with progress display  
- Growable crops (Wheat, Tomato) with multiple growth stages  
- Tool system (Hoe, Seeds)  
- Inventory and toolbar display  
- Pickup animation when harvesting crops  
- Background music  
- Main menu / pause overlay  

---

## 4. Technologies Used

- **Java 21**
- **JavaFX**
- **Maven**

---

## 5. How to Build and Run the Game (Command Line)

### Prerequisites
- Java JDK 21 or newer
- Maven installed and available in your PATH
- JavaFX 21 or newer (only needed if running the JAR manually)

### Build & Run from Source
Clone the repository, install dependencies, and run the game using Maven:

```bash
git clone https://github.com/MrTunic/farm_harvest.git
cd farm_harvest
mvn install
mvn clean javafx:run 
```

### Build and Run the JAR Locally
```bash
mvn clean package

java \
--module-path /path/to/javafx/lib \
--add-modules javafx.controls,javafx.fxml,javafx.media \
-jar target/farm_harvest-1.0-SNAPSHOT.jar
```



## 6. Project Structure
```text
farm_harvest/     
├── README.md                   ← Build & run instructions   
├── src/   
│   └── main/   
│       ├── java/   
│       │   └── io/github/game/   
│       │       ├── controllers/   
│       │       │   └── App.java   
│       │       │
│       │       ├── crops/
│       │       │   ├── Crop.java            ← Abstract base class
│       │       │   ├── CropStage.java       ← Growth state enum
│       │       │   ├── Growable.java        ← Interface
│       │       │   ├── Tomato.java
│       │       │   └── Wheat.java
│       │       │
│       │       ├── engine/
│       │       │   ├── FlyingItem.java      ← Harvest animation
│       │       │   ├── GameLoop.java
│       │       │   ├── InputHandler.java
│       │       │   └── Renderer.java
│       │       │
│       │       ├── entities/
│       │       │   ├── Direction.java
│       │       │   ├── HoeTool.java
│       │       │   ├── Player.java
│       │       │   ├── PlayerAction.java
│       │       │   ├── SeedTool.java
│       │       │   ├── SeedType.java
│       │       │   ├── Tool.java            ← Abstract base class
│       │       │   └── ToolType.java
│       │       │
│       │       ├── util/
│       │       │   ├── Inventory.java
│       │       │   └── ResourceManager.java
│       │       │
│       │       └── world/
│       │           ├── interact/
│       │           │   └── Interactable.java ← Interface
│       │           │
│       │           ├── tiles/
│       │           │   ├── AbstractTile.java ← Abstract base class
│       │           │   ├── DirtTile.java
│       │           │   ├── GrassTile.java
│       │           │   ├── TileType.java
│       │           │   └── WaterTile.java
│       │           │
│       │           ├── DayCycle.java
│       │           └── World.java
│       │
│       └── resources/
│           ├── audio/            ← Background music
│           ├── crops/            ← Crop sprites
│           ├── fonts/            ← Pixel font
│           ├── player/           ← Player sprites
│           ├── tiles/            ← Tile sprites
│           └── tools/            ← Tool sprites
│
├── target/                      ← Compiled build output
├── .gitignore
└── pom.xml                      ← Maven build configuration
```


## 7. Rubric Objectives — How They Are Satisfied

### 7.1 Reuse of Object-Oriented Programming Concepts

This project makes **correct and relevant use** of the object-oriented programming principles studied in class.

#### Inheritance
- `AbstractTile` → `DirtTile`, `GrassTile`, `WaterTile`
- `Tool` → `HoeTool`, `SeedTool`
- `Crop` → `Wheat`, `Tomato`

Shared logic is implemented in base classes, while specialized behavior is defined in subclasses.

#### Interfaces
- `Growable` — implemented by crops to support growth over time
- `Interactable` — implemented by tiles that respond to player interaction

Interfaces are used to define behavior contracts without enforcing implementation details.

#### Encapsulation and Typing
- Core game state is encapsulated within `World`, `Player`, and `Inventory`
- Enums such as `ToolType`, `SeedType`, `Direction`, and `PlayerAction` ensure strong typing and prevent invalid states

---

### 7.2 Technical Quality of the Project

- The project builds and runs from the command line using **Maven**
- No compiled binaries are included in the repository
- The README documents:
  - Compilation and execution steps
  - Gameplay controls
  - Project structure and design
- The game runs without unhandled runtime errors under normal use

---

### 7.3 Quality of the Code

- Consistent naming conventions across classes, methods, and variables
- Clear package separation and modular design
- Consistent indentation and formatting throughout the codebase
- Comments explain intent and behavior rather than restating obvious code
- Classes have focused responsibilities and limited coupling

---

### 7.4 Potential For Future Features

Here are some ideas for features that could be added in future versions of Farm Harvest to enhance gameplay and depth:

- NPCs and Animals
  - Chickens, cows, and other farm animals
  - Collectible items: eggs, milk, etc.
- Save / Load System
  - Ability to save your farm progress and load it later
- Expanded Crop / Plant Logic
  - More crops with unique growth patterns
  - Trees, flowers, lily pads, and rocks
- Additional Tools
  - Axe for chopping trees
  - Watering can to boost crop growth
- Farm Infrastructure
  - Bridges, paths, fences, doors, and other wooden structures
  - Buildings such as houses, chicken coops, barns
- Decorations
  - Paintings, carpets, furniture, and other decorative objects
- More Robust Game Mechanics
  - Advanced growth cycles, crop interactions, and seasonal changes
  - Events, quests, or goals to guide gameplay

## 8. Documentation

Detailed API documentation has been generated using **JavaDocs** and is available in the `docs/` folder of this repository.

To view the documentation:

1. Open the `docs/index.html` file in a web browser.
2. Browse classes, methods, and fields along with their descriptions.

This documentation provides additional insight into class responsibilities, public APIs, and method behavior.

[![JavaDocs](https://img.shields.io/badge/JavaDocs-available-brightgreen)](https://MrTunic.github.io/farm_harvest/)


## 9. Conclusion

**Farm Harvest** fulfills all requirements of the Java project.  
It demonstrates a clean object-oriented design, correct use of Java language features, and a complete, playable JavaFX game.
