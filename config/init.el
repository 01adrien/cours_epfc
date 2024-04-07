;; Don't show the splash screen
(setq inhibit-startup-message t)

;; Turn off some unneeded UI elements
(menu-bar-mode -1) 5
(tool-bar-mode -1)
(tooltip-mode -1)
(scroll-bar-mode -1)
(set-fringe-mode 10)

;; unbell
(setq ring-bell-function 'ignore)

;; Display line numbers in every buffer
(global-display-line-numbers-mode 1)

;; UI 
(load-theme 'tango-dark t)
(set-face-attribute 'default nil :font "Fira Code Retina" :height 110)

;; full screen at start
(add-to-list 'default-frame-alist '(fullscreen . maximized))

;; cursor style
(setq-default cursor-type '(bar . 2))
(set-cursor-color "green")

;; disable auto save
(setq auto-save-default nil)
(setq backup-directory-alist
      `((".*" . "/tmp")))
(setq auto-save-file-name-transforms
      `((".*" "/tmp" t)))

(electric-pair-mode 1)

;; Make ESC quit prompts
(global-set-key (kbd "<escape>") 'keyboard-escape-quit)
   

;; Window binding
(global-set-key (kbd "S-C-<left>") 'shrink-window-horizontally)
(global-set-key (kbd "S-C-<right>") 'enlarge-window-horizontally)
(global-set-key (kbd "S-C-<down>") 'shrink-window)
(global-set-key (kbd "S-C-<up>") 'enlarge-window)
(global-set-key (kbd "C-x <up>") 'windmove-up)
(global-set-key (kbd "C-x <down>") 'windmove-down)
(global-set-key (kbd "C-x <left>") 'windmove-left)
(global-set-key (kbd "C-x <right>") 'windmove-right)



;; Initialize package sources
(require 'package)

(setq package-archives '(("melpa" . "https://melpa.org/packages/")
                         ("org" . "https://orgmode.org/elpa/")
                         ("elpa" . "https://elpa.gnu.org/packages/")))

(package-initialize)
(unless package-archive-contents
 (package-refresh-contents))

;; Initialize use-package on non-Linux platforms
(unless (package-installed-p 'use-package)
   (package-install 'use-package))

(require 'use-package)
(setq use-package-always-ensure t)

(use-package org)


(custom-set-variables
 ;; custom-set-variables was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(package-selected-packages
   '(lsp-mode magit use-package rainbow-delimiters ivy-rich doom-modeline counsel))
 '(warning-suppress-types '((comp))))
(custom-set-faces
 ;; custom-set-faces was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 )

(use-package lsp-mode
  :config
  (add-hook 'c-mode-hook #'lsp))
