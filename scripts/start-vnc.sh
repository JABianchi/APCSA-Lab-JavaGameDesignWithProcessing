#!/bin/bash

echo "🛠️ STEP 1: Cleaning up old sessions..."
sudo pkill -9 Xvfb || true
sudo pkill -9 x11vnc || true
sudo pkill -9 fluxbox || true
sudo pkill -f launch.sh || true
sudo rm -f /tmp/.X1-lock /tmp/.X11-unix/X1 || true

echo "🛠️ STEP 2: Starting Virtual Display..."
Xvfb :1 -screen 0 1024x768x24 &
sleep 2

echo "🛠️ STEP 3: Starting Window Manager..."
DISPLAY=:1 fluxbox &
sleep 1

echo "🛠️ STEP 4: Starting VNC Server..."
x11vnc -display :1 -nopw -forever -shared -bg -rfbport 5900
sleep 2

echo "🛠️ STEP 5: Starting noVNC Bridge..."
# We move to the directory first so launch.sh can find its files
cd /usr/share/novnc/utils
./launch.sh --vnc localhost:5900 --listen 6080 &

echo "🛠️ STEP 6: Waiting for Game Screen to wake up..."
for i in {1..10}; do
    if netstat -tuln | grep -q ":6080"; then
        echo "✅ SUCCESS: Game Screen is READY!"
        exit 0
    fi
    echo "...waiting..."
    sleep 1
done

echo "❌ ERROR: Port 6080 failed to start."
exit 1