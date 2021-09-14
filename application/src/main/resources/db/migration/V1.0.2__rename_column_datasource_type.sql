-- alter table rename column
ALTER TABLE @jdbc.log.table@ RENAME COLUMN datasource_type TO serialization_format;