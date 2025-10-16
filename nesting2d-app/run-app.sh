#!/bin/bash

# Script to run the Nesting 2D application
# This script sets up a virtual display for headless environments

echo "Starting Nesting 2D Application..."

# Check if we're in a headless environment
if [ -z "$DISPLAY" ]; then
    echo "No display found, starting virtual display..."
    
    # Start Xvfb in background
    Xvfb :99 -screen 0 1024x768x24 &
    export DISPLAY=:99
    
    # Wait a moment for Xvfb to start
    sleep 2
fi

# Run the application
echo "Launching application..."
./gradlew run

# Clean up virtual display if we started it
if [ -n "$XVFB_PID" ]; then
    kill $XVFB_PID
fi