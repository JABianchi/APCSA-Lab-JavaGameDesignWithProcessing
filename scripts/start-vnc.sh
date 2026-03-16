#!/bin/bash
# 1. Cleanup
# 1. Kill everything cleanly
sudo pkill -9 Xvfb || true
sudo pkill -9 x11vnc || true
sudo pkill -9 fluxbox || true
sudo pkill -f launch.sh || true
sudo rm -f /tmp/.X1-lock
sudo rm -f /tmp/.X11-unix/X1

# 2. Start Virtual Display and wait longer
Xvfb :1 -screen 0 1024x768x24 &
sleep 3

# 3. Start Window Manager
fluxbox &
sleep 1

# 4. Start VNC Server with 'Relaunch' logic
#  -forever and -bg  ensure it stays in the background
x11vnc -display :1 -nopw -forever -shared -bg -rfbport 5900 &
sleep 2

# 5. Start the Web Bridge
/usr/share/novnc/utils/launch.sh --vnc localhost:5900 --listen 6080