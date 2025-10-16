#!/bin/bash

echo "🚀 Starting Nesting 2D - Simple Console Version"
echo "=============================================="
echo ""

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
echo ""

# Compile and run
echo "🔨 Compiling..."
javac -d build/classes/java/main -cp src/main/java src/main/java/com/nesting2d/SimpleConsoleMain.java src/main/java/com/nesting2d/model/*.java

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed"
    exit 1
fi

echo "✅ Compilation successful"
echo ""

# Run the application
echo "🎯 Starting application..."
echo ""

java -cp build/classes/java/main com.nesting2d.SimpleConsoleMain