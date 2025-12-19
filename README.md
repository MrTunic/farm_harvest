# 🌱 Farm Harvest — Mini JavaFX Game

**Author:** Omarion Aubert  
**Course:** IST JAV  
**Project:** Game Project Submission  
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

## 5. How to Build and Run (Command Line)

### Prerequisites
- Java JDK 21 or newer
- Maven installed and available in your PATH

### Build & Run
```bash
git clone https://github.com/MrTunic/farm_harvest.git
cd farm_harvest
mvn clean javafx:run 

```
```text
farm_harvest/   
├── .vscode/                    ← IDE settings (optional, not required to build)   
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
├── target/                      ← Compiled build output (ignored)
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

## 8. Conclusion

**Farm Harvest** fulfills all requirements of the Java mini-game project.  
It demonstrates a clean object-oriented design, correct use of Java language features, and a complete, playable JavaFX game.
