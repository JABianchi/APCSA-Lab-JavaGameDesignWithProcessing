#!/bin/bash
set -e

# 1. Kill everything cleanly
sudo pkill -9 Xvfb || true
sudo pkill -9 x11vnc || true
sudo pkill -9 fluxbox || true
sudo pkill -f launch.sh || true
sudo rm -f /tmp/.X1-lock
sudo rm -f /tmp/.X11-unix/X1

# 2. Start Virtual Display and wait longer
Xvfb :1 -screen 0 1024x768x24 &
sleep 2

# 3. Start Window Manager
fluxbox &
sleep 1

# 4. Start VNC Server with 'Relaunch' logic
#  -forever and -bg  ensure it stays in the background
x11vnc -display :1 -nopw -forever -shared -bg -rfbport 5900 &
sleep 2

# 5. Start the Web Bridge
# --vnc-path to ensure the webserver finds the index files
/usr/share/novnc/utils/launch.sh --vnc localhost:5900 --listen 6080 --vnc-path /usr/share/novnc &

# 6. The "Monday Morning" Readiness Check
echo "Waiting for Game Screen to wake up..."
for i in {1..10}; do
    if netstat -tuln | grep -q ":6080"; then
        echo "✅ Game Screen is READY on port 6080!"
        exit 0
    fi
    echo "..."
    sleep 1
done

echo "⚠️ Warning: Port 6080 is taking a long time. Check logs."