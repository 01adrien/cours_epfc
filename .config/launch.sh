#!/bin/bash

cd /home/adrien/Bureau/cours_epfc
git pull
cat .config/.bashrc > ~/.bashrc
cat .config/VScode/settings.json > ~/.config/Code/User/settings.json
cat .config/VScode/keybindings.json > ~/.config/Code/User/keybindings.json