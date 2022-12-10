# Workers for Process Orchestration for Santa

## Usage

* Create Zeebe Client in Camunda
  Cloud: [Create a Client](https://docs.camunda.io/docs/components/console/manage-clusters/manage-api-clients/#create-a-client)
* Copy the connection credentials into [Workers.java](src/main/java/Workers.java)
* Start `Workers.java` from your IDE

This will connect to the cluster. Upon successful connection, it will print out the topology of the cluster. This is
mainly a check that the connection was successful.

Later, when the workers are called, they will also print out lines to system out.

The program runs until it is killed. 

