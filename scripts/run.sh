#!/bin/bash
# 1. Point to the virtual window
export DISPLAY=:1

# 2. Force the script to run from the project root, even though it's in /scripts
cd "$(dirname "$0")/.."

# 3. Create bin folder at root
mkdir -p bin

# 4. Compile all root .java files into bin
echo "🔨 Compiling classes..."
javac -cp "lib/core/core.jar:." -d bin *.java

# 5. If successful, run
if [ $? -eq 0 ]; then
    echo "🚀 Launching Game..."
    # Note: we include 'bin' in the classpath so it finds the compiled files
    java -cp "lib/core/core.jar:bin:." Main
else
    echo "❌ Compilation failed."
fi