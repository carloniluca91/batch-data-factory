-- create sequence for log table
CREATE SEQUENCE IF NOT EXISTS batch_sample_id;

-- log table
CREATE TABLE IF NOT EXISTS @jdbc.log.table@ (

    sample_id INT NOT NULL DEFAULT NEXTVAL('batch_sample_id'),
    event_ts TIMESTAMP NOT NULL,
    event_dt DATE NOT NULL,
    datasource_id TEXT NOT NULL,
    datasource_class TEXT NOT NULL,
    generation_type TEXT NOT NULL,
    custom_generator_class TEXT,
    batch_size INT NOT NULL,
    datasource_type TEXT NOT NULL,
    file_system_type TEXT NOT NULL,
    file_system_path TEXT NOT NULL,
    generated_file_name TEXT NOT NULL,
    sample_generation_code TEXT NOT NULL,
    exception_class TEXT,
    exception_message TEXT,
    insert_ts TIMESTAMP NOT NULL DEFAULT NOW(),
    insert_dt DATE NOT NULL DEFAULT NOW()::DATE,
    PRIMARY KEY (sample_id)
);

COMMENT ON COLUMN @jdbc.log.table@.sample_id IS 'Id of generated sample';
COMMENT ON COLUMN @jdbc.log.table@.event_ts IS 'Timestamp of generated sample';
COMMENT ON COLUMN @jdbc.log.table@.event_dt IS 'Date of generated sample';
COMMENT ON COLUMN @jdbc.log.table@.datasource_id IS 'Human readable datasource id';
COMMENT ON COLUMN @jdbc.log.table@.datasource_class IS 'FQ name of generated sample class';
COMMENT ON COLUMN @jdbc.log.table@.generation_type IS 'CUSTOM|STANDARD';
COMMENT ON COLUMN @jdbc.log.table@.custom_generator_class IS 'Fq name of class used for custom generation (null if generation_type = STANDARD)';
COMMENT ON COLUMN @jdbc.log.table@.batch_size IS 'Number of generated records';
COMMENT ON COLUMN @jdbc.log.table@.datasource_type IS 'AVRO|CSV';
COMMENT ON COLUMN @jdbc.log.table@.file_system_type IS 'HDFS|LOCAL';
COMMENT ON COLUMN @jdbc.log.table@.file_system_path IS 'Target path on target file system';
COMMENT ON COLUMN @jdbc.log.table@.generated_file_name IS 'Name of file generated at target path';
COMMENT ON COLUMN @jdbc.log.table@.sample_generation_code IS 'OK|KO';
COMMENT ON COLUMN @jdbc.log.table@.exception_class IS 'FQ name of exception raised by sample generation process';
COMMENT ON COLUMN @jdbc.log.table@.exception_message IS 'Message of exception raised by sample generation process';
COMMENT ON COLUMN @jdbc.log.table@.insert_ts IS 'Timestamp of table record';
COMMENT ON COLUMN @jdbc.log.table@.insert_dt IS 'Date of table record';