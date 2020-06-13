# multiconsumer
A simple Producer-Consumer implementation

Solution desription:

- Will have a single Parser that parses the file line by line
- Each record, parsed, will contain separate fields (Record class)
- With the start of the program will create Producer , that will create a list of independent Consumers (Consumer will be Runnable)
- Within each consumer we will store a Queue of tasks to handle, and also, it will store every ID tasks that is to handle and calculate total length of time needs to be processed.
- Producer will have a method, to choose a best fit consumer, based on record's ID. It traverse every consumer and check if there is such ID in this consumer, or choose minimal total time property.

Data flow:
- Every line is parsed by Parser
- Passed to Producer
- Producer choose best consumer to pass this record.
- Producer calls this consumer.
- Consumer writes this Record info into internal data structure
- On the other Consumer's thread:
  - It checks the queue of the available task
  - Get the task 
  - proceed the task
  - change the internal data structure to change metainfo about record ID and time.
  
I am very sorry, but I didn't have a time to write any tests..
