# Player Files

## Message from Santa

Dear process orchestrators,

As you can imagine I am a busy man, especially around Christmas. Everyone just sees the presents, but there is a lot more going on behind the scenes:

* Reading all wish lists, 
* Compiling the naughty and nice lists
* Procurement of presents, 
* Rein deer health monitoring
* Packaging, and last but not least
* scheduling of Santa's trips.

We try to keep up with upcoming technology and have been looking into process orchestration. However, I need your help to convince my elfs to adopt this technology. 

I would like you to help me automate the _letter processing_, which is currently a manual task.

This process takes in a letter, digitizes it, then looks up whether the sender is actually a child and whether the child was naughty or nice.
Nice children get their wishes fulfilled. Naughty children and adults just get a letter from Santa.

I did a bit of brainstorming with my elfs and we drafted actions that need to be done in this process. Please have a look at _Starting Point_.

You don't need to use everything that is there, but would be cool if you could showcase the breadth, depth and strength of process orchestration.

Good luck!

## Bill of Materials

* [starting-point.bpmn](./starting-point.bpmn) - Start here
* [pack-presents.bpmn](./pack-presents.bpmn) - Subprocess to pack presents. Should be called from the process you design
* [test-letter-generator.bpmn](./test-letter-generator.bpmn) - Endless loop process to generate some letters so you can test your processes
* [request-letters-form.form](./request-letters-form.form) - The form used in `test-letter-generator.bpmn`