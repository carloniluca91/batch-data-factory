-- create log table with new column 'generation_end_time', 'generation_end_date' and 'generation_duration_in_minutes'
CREATE TABLE IF NOT EXISTS @jdbc.log.table@_tmp (

    sample_id INT NOT NULL DEFAULT NEXTVAL('batch_sample_id'),
    generation_start_time TIMESTAMP NOT NULL,
    generation_start_date DATE NOT NULL,
    generation_end_time TIMESTAMP NOT NULL,
    generation_end_date DATE NOT NULL,
    generation_duration_in_minutes INT NOT NULL,
    datasource_id TEXT NOT NULL,
    datasource_class TEXT NOT NULL,
    generation_type TEXT NOT NULL,
    custom_generator_class TEXT,
    batch_size_type TEXT NOT NULL,
    batch_size_value INT NOT NULL,
    serialization_format TEXT NOT NULL,
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

COMMENT ON COLUMN @jdbc.log.table@_tmp.sample_id IS 'Id of generated sample';
COMMENT ON COLUMN @jdbc.log.table@_tmp.generation_start_time IS 'Start time of generation process';
COMMENT ON COLUMN @jdbc.log.table@_tmp.generation_start_date IS 'Start date of generation process';
COMMENT ON COLUMN @jdbc.log.table@_tmp.generation_end_time IS 'End time of generation process';
COMMENT ON COLUMN @jdbc.log.table@_tmp.generation_end_date IS 'End date of generation process';
COMMENT ON COLUMN @jdbc.log.table@_tmp.generation_duration_in_minutes IS 'Duration of generation process in minutes (-1 where this computation was not available)';
COMMENT ON COLUMN @jdbc.log.table@_tmp.datasource_id IS 'Human readable datasource id';
COMMENT ON COLUMN @jdbc.log.table@_tmp.datasource_class IS 'FQ name of generated sample class';
COMMENT ON COLUMN @jdbc.log.table@_tmp.generation_type IS 'CUSTOM|STANDARD';
COMMENT ON COLUMN @jdbc.log.table@_tmp.custom_generator_class IS 'Fq name of class used for custom generation (null if generation_type = STANDARD)';
COMMENT ON COLUMN @jdbc.log.table@_tmp.batch_size_type IS 'FIXED|RANDOM';
COMMENT ON COLUMN @jdbc.log.table@_tmp.batch_size_value IS 'Number of generated records';
COMMENT ON COLUMN @jdbc.log.table@_tmp.serialization_format IS 'AVRO|CSV';
COMMENT ON COLUMN @jdbc.log.table@_tmp.file_system_type IS 'HDFS|LOCAL';
COMMENT ON COLUMN @jdbc.log.table@_tmp.file_system_path IS 'Target path on target file system';
COMMENT ON COLUMN @jdbc.log.table@_tmp.generated_file_name IS 'Name of file generated at target path';
COMMENT ON COLUMN @jdbc.log.table@_tmp.sample_generation_code IS 'OK|KO';
COMMENT ON COLUMN @jdbc.log.table@_tmp.exception_class IS 'FQ name of exception raised by sample generation process';
COMMENT ON COLUMN @jdbc.log.table@_tmp.exception_message IS 'Message of exception raised by sample generation process';
COMMENT ON COLUMN @jdbc.log.table@_tmp.insert_ts IS 'Timestamp of table record';
COMMENT ON COLUMN @jdbc.log.table@_tmp.insert_dt IS 'Date of table record';

-- insert old records into new log table
INSERT INTO @jdbc.log.table@_tmp (
sample_id,
generation_start_time,
generation_start_date,
generation_end_time,
generation_end_date,
generation_duration_in_minutes,
datasource_id,
datasource_class,
generation_type,
custom_generator_class,
batch_size_type,
batch_size_value,
serialization_format,
file_system_type,
file_system_path,
generated_file_name,
sample_generation_code,
exception_class,
exception_message,
insert_ts,
insert_dt
) SELECT
sample_id,
generation_start_time,
generation_start_date,
insert_ts,
insert_dt,
CASE WHEN (EXTRACT (MINUTE FROM (insert_ts - generation_start_time))) = 0 THEN -1
     ELSE (EXTRACT (MINUTE FROM (insert_ts - generation_start_time)))::INT END,
datasource_id,
datasource_class,
generation_type,
custom_generator_class,
batch_size_type,
batch_size_value,
serialization_format,
file_system_type,
file_system_path,
generated_file_name,
sample_generation_code,
exception_class,
exception_message,
insert_ts,
insert_dt
FROM @jdbc.log.table@;

-- drop old log table
DROP TABLE IF EXISTS @jdbc.log.table@;

-- rename new log table
ALTER TABLE @jdbc.log.table@_tmp RENAME TO @jdbc.log.table@;
