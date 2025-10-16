#!/bin/bash

echo "🚀 Starting Nesting 2D Application..."
echo "=================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java version $JAVA_VERSION is too old. Please install Java 17 or higher."
    exit 1
fi

echo "✅ Java version: $(java -version 2>&1 | head -n 1)"

# Check if JAR file exists
JAR_FILE="build/libs/nesting2d-app-1.0.0.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR file not found. Building application..."
    ./gradlew jar
    if [ $? -ne 0 ]; then
        echo "❌ Failed to build application"
        exit 1
    fi
fi

echo "✅ JAR file found: $JAR_FILE"

# Check if we're in a headless environment
if [ -z "$DISPLAY" ]; then
    echo "🖥️  No display found, starting virtual display..."
    
    # Check if Xvfb is available
    if ! command -v Xvfb &> /dev/null; then
        echo "❌ Xvfb is not installed. Installing..."
        sudo apt-get update && sudo apt-get install -y xvfb
    fi
    
    # Start Xvfb in background
    echo "Starting Xvfb on display :99..."
    Xvfb :99 -screen 0 1024x768x24 -ac &
    XVFB_PID=$!
    export DISPLAY=:99
    
    # Wait for Xvfb to start
    sleep 3
    
    echo "✅ Virtual display started (PID: $XVFB_PID)"
else
    echo "✅ Display found: $DISPLAY"
fi

# Run the application
echo "🎯 Launching Nesting 2D Application..."
echo "=================================="
echo ""
echo "📋 Instructions:"
echo "1. Click 'Import CAD File' button to import .dwg or .dxf files"
echo "2. Use 'Show Details' to view detailed information"
echo "3. Close the application when done"
echo ""

java -jar "$JAR_FILE"

# Clean up virtual display if we started it
if [ -n "$XVFB_PID" ]; then
    echo ""
    echo "🧹 Cleaning up virtual display..."
    kill $XVFB_PID 2>/dev/null
    echo "✅ Application closed successfully"
fi