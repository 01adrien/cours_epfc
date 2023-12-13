#!/bin/bash

cd /home/bob/Bureau/cours_EPFC
git pull
cat .config/.bashrc > ~/.bashrc
cat .config/VScode/settings.json > ~/.config/Code/User/settings.json
cat .config/VScode/keybindings.json > ~/.config/Code/User/keybindings.json