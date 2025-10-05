DB_NAME = prbd_tuto
DB_USER = postgres
SQL_DIR = backend
FRONTEND_DIR = ./frontend/.

.PHONY: setup_backend init security backend frontend

setup_backend: init security backend

init:
	psql -U $(DB_USER) -d $(DB_NAME) -f $(SQL_DIR)/init.sql

security:
	psql -U $(DB_USER) -d $(DB_NAME) -f $(SQL_DIR)/security.sql

backend:
	psql -U $(DB_USER) -d $(DB_NAME) -f $(SQL_DIR)/backend.sql

frontend:
	cd $(FRONTEND_DIR) && flutter run --debug
