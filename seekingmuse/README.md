

# There be dragons here, caution is required.

## Database startup

This uses mongodb, without an ORM.

The docker-compose file bind mounts the "docker-scripts" directory to the db container.  This dir has a simple script to establish a user and password. The only login that works is "root" - magoo is messed up somehow

It is run via, docker-entrypoint-initdb.d

* schema.js - the schema for the graphql service
* index.js - the resolver and typedef file to bring all of the apollo infrastructure up at once
* .env - define variables for the docker-scripts startup 
* .babelrc - establish the right javascript syntax

## Vismuse

A simple visualizer.  No CSS