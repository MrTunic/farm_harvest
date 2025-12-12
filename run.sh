#!/bin/bash

# Clean previous build
if [ -d out ]; then
    echo "Cleaning old build..."
    rm -rf out
fi

# Recreate out folder
mkdir -p out

# Compile all Java files
echo "Compiling Java files..."
javac --module-path /path/to/javafx/lib \
      --add-modules javafx.controls \
      -d out $(find src/main/java -name "*.java")

# Run the game
echo "Running the game..."
java --module-path /path/to/javafx/lib \
     --add-modules javafx.controls \
     --enable-native-access=javafx.graphics \
     -cp out:. io.github.game.controllers.App
