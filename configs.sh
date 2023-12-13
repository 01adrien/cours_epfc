#!/bin/bash

cd /home/adrien/
cat ~/.bashrc > .config/.bashrc
cat ~/.config/Code/User/settings.json > .config/VScode/settings.json
cat ~/.config/Code/User/keybindings.json > .config/VScode/keybindings.json
cd cours_EPFC
git add .
git commit -m "change on configs files"
git push