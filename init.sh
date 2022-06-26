mvn clean package
docker-compose -f docker-compose.yml up -d
sleep 15
docker exec -it albertsongs-db psql -U postgres -d albertsongs -f /tmp/db_migration/playlist.sql
docker exec -it albertsongs-db psql -U postgres -d albertsongs -f /tmp/db_migration/video.sql