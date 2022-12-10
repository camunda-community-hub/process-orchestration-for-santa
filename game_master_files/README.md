# Game Master Files

## Bill of Materials
* [book_of_character.json](./book_of_character.json) - JSON file that contains Santa's information on which kids were naughty and which kids were nice
* [workers](./workers/README.md) - Implementation of workers needed for the challenge
* [solution] - Example of one possible solution

## Setup

* Create a cluster
* Create a client for that cluster
* Enter the client credentials into the `Workers.java` file and start the workers in the background
* Upload the following player files into Web Modeler:
    * `starting-point.bpmn` - This is the starting point for the players
    * `pack-presents.bpmn` - This is a subprocess called by the main process
    * `test-letter-generator.bpmn` - This is a looping process in which players can request letters / message events to be generated which trigger the player's processes
    * `request-letters-form.form` - This is the form used in `test-letter-generator.bpmn` in case players want to customize the form
* Start the following process: `test-letter-generator.bpmn`

## Hints

* This has been play tested by a colleague with a time frame of 45 minutes. He did not complete the challenge in the allotted time. This is not a problem though. It’s more about having fun with the tool and getting the broad direction right than crossing the t’s and dotting the i's.
* The first step is for each team to duplicate the `starting-point.bpmn` process, and give it a **unique** process id and pick a **unique** message name. Otherwise the teams will either overwrite each others processes or steal each others messages.
* FEEL is important for this exercise friend. Also testing complex expressions in [FEEL playground](https://camunda.github.io/feel-scala/docs/playground/) gives players a shorter feedback cycle than doing it in the engine
   * [split()](https://camunda.github.io/feel-scala/docs/reference/builtin-functions/feel-built-in-functions-string#split) can be used to turn a comma separated list of wishes into a list of individual wishes
   * [list filters](https://camunda.github.io/feel-scala/docs/reference/language-guide/feel-list-expressions#filter) can be used to search for a child with a certain name in the `book_of_character.json`. Note however that an expression like `children[name = name]` or `children[item.name = name]` doesn't work. Name is always bound to the name of the item in the collection. Instead you need an expression like `children[name = name_of_child]` to filter for `name of child`
* The challenge can be solved without DMN. Using DMN is a bit contrived in this example, to be honest. But it is a good element to showcase to Santa how easy it is to change rules later on.

## Solution

The solution is just an example. It is not meant as a reference solution or as an example of modeling best practices.

It consists of a form to enter a wishlist. The original letter is displayed in a disabled textfield. And the form has two test fields, one to enter a comma separated list of wishes and one to enter the name of the child. The comma separated list is itemized in an output mapping utilizing the `split()` function.

The DMN is a bit contrived. It takes as inputs the character and the age of the person. It uses a list filter expression to filter the list returned by the rest connector. Adults are classified as naughty.

Then there is an exclusive gateway to decide whether to send gifts or a letter. 

In case gifts are sent, there is an input mapping at the call activity to map variable names.