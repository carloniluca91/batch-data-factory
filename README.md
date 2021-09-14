### Batch Data Factory

`Spring` application that emulates a batch data source generating 
configurable batches of random data for testing purposes

The application generates a large batch of random `Java` bean
(enriched by some special annotations) which is then serialized
in `avro` or `csv` format and the saved to an external file on either
local or distributed file system. 
For each generated batch, the application also inserts a logging record in a table of a `PostgreSQL` database.

Main modules
* `application` which defines the Spring application
* `data-model` that defines all of dataSource configuration classes, structure of each generated bean
  and functions to be used for random data generation