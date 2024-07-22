@echo off
echo Starting Required Docker services...
docker-compose -f postgres-docker-compose.yml up -d
echo Docker services started.