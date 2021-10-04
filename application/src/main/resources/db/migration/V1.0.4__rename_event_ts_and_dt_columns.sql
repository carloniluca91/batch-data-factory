-- rename and change comment for column event_ts, event_dt
ALTER TABLE @jdbc.log.table@ RENAME COLUMN event_ts TO generation_start_time;
ALTER TABLE @jdbc.log.table@ RENAME COLUMN event_dt TO generation_start_date;

COMMENT ON COLUMN @jdbc.log.table@.generation_start_time IS 'Start time of batch generation process';
COMMENT ON COLUMN @jdbc.log.table@.generation_start_date IS 'Start date of batch generation process';