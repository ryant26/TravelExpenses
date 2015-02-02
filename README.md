# TravelExpenses
## Preface
This is **NOT** the system documentation. Please see the doc directory for the Readme deliverable, and other design documents!

## Cmput 301 Assignment 1

This is a simple android app that can be used to track travel expenses.

## Some Notes:
### Code Quality / Style (Self Reflection):
- I did feel that I had some good sepparation between my controllers/views and model. The view and controller boundary got a little bit mucky however.

- The model class (my cache and persistance classes) and its abstractions were well designed, modular, and testable

- That said, my use of a hashtable for my main datastructure for claims and expenses was inappropiate rarily did I leaverage the efficiency of looking up a single key. I often converted it to a list and then sorted that list. The model should have simple maintained a sorted list as its data backing.

- My decision of when to use an inner class and when to create a sepparate class was somewhat arbitrary. I like to think that I made those decisions
based on how much logic was going to be in that class and its potential for reuse. I'm not sure how true that is. Going forward I need to spend more time planning for the reuse of things like listeners.

- The email functionality was a bit rushed and sort of abused the exsiting infastructure to make it work. If there was time I would refactor this to make it more modular.  

- The adapters for my 2 list views were *very* similar, Im sure I could have somehow shared some code between the two. 