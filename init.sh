#start test DB docker container for mvn package
docker run \
    --name testDb \
    -p 5432:5432 \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_DB=albertsongs \
    -e POSTGRES_HOST_AUTH_METHOD=trust \
    -d \
    postgres:alpine

#main logic
mvn clean package
docker-compose -f docker-compose.yml up -d
echo "Please wait 30 seconds (for init DB schema by Hibernate)..."
sleep 30
docker exec -it albertsongs-db psql -U postgres -d albertsongs -f /tmp/db_migration/playlist.sql
docker exec -it albertsongs-db psql -U postgres -d albertsongs -f /tmp/db_migration/video.sql
docker exec -it albertsongs-db psql -U postgres -d albertsongs -f /tmp/db_migration/backup.sql

#remove test DB docker container
docker rm -f testDb