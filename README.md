# Batch Data Factory

`Spring` application that emulates a batch data source generating 
configurable batches of random data for testing purposes

The application generates a large batch of random `Java` bean
(enriched by some special annotations) which is then serialized
in `avro` or `csv` format and the saved to an external file on either
local or distributed file system. 
For each generated batch, the application also inserts a record in a table of 
a `PostgreSQL` database for logging purposes.

Main modules
* `application` which defines the Spring application
* `configuration` which defines all of dataSource configuration classes 
* `data-model` which defines structure for beans to generated and functions to be used for populating such beans