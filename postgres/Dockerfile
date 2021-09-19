FROM postgres:11.13-alpine

COPY ./scripts/0_init-databases.sql /docker-entrypoint-initdb.d/0_init-databases.sql
COPY ./scripts/1_set-optimized-config.sh /docker-entrypoint-initdb.d/1_set-optimized-config.sh
COPY ./scripts/sample.sql sample.sql
