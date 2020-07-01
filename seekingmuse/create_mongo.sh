
#!/usr/bin/env bash
echo "Creating mongo users..."
mongo admin --host localhost -u root -p mysql /docker-entrypoint-initdb.d/create_user.js --authenticationDatabase admin
echo "Mongo users created."
