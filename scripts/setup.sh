#!/bin/bash
set -e # Exit immediately if a command fails

echo "🔧 Starting environment setup..."

# 1. Fix package sources and install tools
sudo rm -f /etc/apt/sources.list.d/yarn.list
sudo apt-get update
sudo apt-get install -y fluxbox novnc x11vnc xvfb

# 2. Make all scripts executable
chmod +x scripts/*.sh

# 3. Set the DISPLAY variable globally so Java always finds it
if ! grep -q "export DISPLAY=:1" ~/.bashrc; then
    echo "export DISPLAY=:1" >> ~/.bashrc
fi

echo "✅ Setup complete!"