#!/bin/bash

# Set required `max_prepared_transactions` setting required by Bonita.
sed -i "s/^.*max_prepared_transactions\s*=\s*\(.*\)$/max_prepared_transactions = 100/" "$PGDATA"/postgresql.conf
sed -i "s/^.*shared_buffers\s*=\s*\(.*\)$/shared_buffers = 256MB/" "$PGDATA"/postgresql.conf
sed -i "s/^.*maintenance_work_mem\s*=\s*\(.*\)$/maintenance_work_mem = 256MB/" "$PGDATA"/postgresql.conf
sed -i "s/^# *work_mem\s*=\s*\(.*\)$/work_mem = 16MB/" "$PGDATA"/postgresql.conf
