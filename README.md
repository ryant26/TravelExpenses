# TravelExpenses
## Cmput 301 Assignment 1

This is a simple android app that can be used to track travel expenses.

## Some Notes:
### Code Quality / Style (Self Reflection):
- This app abused Fragments, I used them as pseudo activites when that is not appropiate
	- I did not realise that my cache class (my model, a good candidate for the singleton pattern) 
	could be set as a global variable in the Application class. This would have allowed me to use activites
	in place of many of my fragments 

	- This abuse lead to the abuse of other UI elemtents to make my fragment use appropiate. 
		- eg Weird stateful conditionals to add appropiate buttons to the action bar.

- I did feel that I had some good sepparation between my controllers/views and model. The view and controller boundary got a little bit mucky however.

- The model class (my cache and persistance classes) and its abstractions were well designed, modular, and testablAe

- That said, my use of a hashtable for my main datastructure for claims and expenses was inappropiate ** Will finish later **

- My decision of when to use an inner class and when to create a sepparate class was somewhat arbitrary. I like to think that I made those decisions
based on how much logic was going to be in that class. I'm not sure how true that is. Going forward I need **Will finish later **