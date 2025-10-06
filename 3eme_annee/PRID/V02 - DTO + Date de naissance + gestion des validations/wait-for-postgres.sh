#!/bin/sh
# Attendre que PostgreSQL soit prêt
until pg_isready -h db -p 5432 -U postgres; do
  echo "Waiting for postgres..."
  sleep 1
done
exec "$@"
