# On part d'une image de base qui contient le SDK de dotnet et ASP.Net Core et qui constitue la base pour construire notre image.
# On donne le nom de 'final' à cet étage pour pouvoir éventuellement le référencer plus tard.
FROM mcr.microsoft.com/dotnet/aspnet:9.0 AS final
# Installer le client PostgreSQL pour pg_isready
RUN apt-get update && apt-get install -y postgresql-client && rm -rf /var/lib/apt/lists/*
# On se place dans un (nouveau) dossier app de l'image
WORKDIR /app
# On copie dans le dossier courant de l'image le contenu du dossier courant (publish)
COPY . .
# Copie le script d'attente pour PostgreSQL
COPY wait-for-postgres.sh /app/wait-for-postgres.sh
RUN chmod +x /app/wait-for-postgres.sh
# On définit la commande qui doit être exécutée au démarrage du conteneur : dotnet backend.dll
# La variable d'environnement ASPNETCORE_URLS permet d'indiquer au backend quel est le port interne qui lui est
# fourni (via la variable d'environnement PORT) et sur lequel il doit tourner.
CMD ASPNETCORE_URLS=http://*:$PORT dotnet backend.dll
