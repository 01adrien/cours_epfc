# Importe le nom de l'application depuis le ficqhier publish-appname.sh
source publish-appname.sh
# On se place dans le dossier de publication
cd publish
# Arrête et supprime les conteneurs et les images s'ils existent
docker compose -p $DOCKER_APP_NAME down --rmi all --volumes
# Construit l'image Docker sans cache (à partir du Dockerfile dans le répertoire courant)
docker compose build --no-cache
# Démarre les conteneurs avec le nom de projet défini (utilise docker-compose.yml)
docker compose -p $DOCKER_APP_NAME up -d
