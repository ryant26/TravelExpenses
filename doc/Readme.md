# TravelExpenses
## Video Deliverable
I recorded the video 10 – 15 times to try and get it under the 2min mark. The emulator was simply too slow (specifically when choosing dates) and there was too many dialogs to traverse when adding claims / expenses in my app, to complete the list in under 2 min. On a physical device it might be possible.

https://archive.org/details/ThornhilTravelExpensesFinal

##System Design
The design of this system was based on observable MVC (Please see the UML for visual). The goal at the beginning of the project was to build the system with high levels of separation between components. I strived to create classes that had a small number of responsibilities. Unfortunately, as things got busier I didn't have time to constantly refactor when something wasn't being done right.
I think that a little more planning in the beginning would have helped. But the main issue was my domain knowledge. When you don't know the best practices or typical design patterns it is difficult to design a system well.
Another point that seemed to really break my good separation ideology was the “Activity” classes. It’s very easy to put a lot of logic into these classes and they become bloated very easily. This was especially true when I started adding adapters as nested classes to any activities that had lists. Next time I would try to extract more of my logic into other classes. This way classes with similar view setups could reuse some view setup code. 
I think this was especially true for my two classes that contained lists, I felt that I was *almost* copying code from one of the adapters to the other. This to me is always an opportunity to extract the commonalities into another class.

##Limitations
-	The system will allow an expense to be outside the date range of its parent claim.

-	The system might be slightly overzealous in persistence. There are some 
update paths that would write to the file in storage without any actual changes. This is bad for performance.

-	On the other side of this coin, the model will probably notify it’s observing views that there has been a change in certain cases where there hasn’t been a change
	- Both of these issues would be solved with a better chain (preferably a single path) of method calls when something in the model was updated. 

##Usage
**Add a Claim:**
On the main screen use the + in the upper right hand corner

**Delete a Claim:**
Long click an item in the claims list (Main Screen) and press the x on the context menu

**View Claim Summary:**
Click on a claim in the Claims list.

**Edit a Claim:**
Click on a Claim in the Claims list to enter its summary view. Select the pencil icon in the upper right hand corner from the summary view.

**Email a Claim:**
From the Claims List long select a Claim you want to email. When the context menu appears, select any other claims you wish to email and then select the mail icon from the context menu.

**Add an Expense:**
From the Claim summary view click on the button that says add / edit Expenses. Once in the expense list view, use the + in the upper right hand corner to add an expense.

**Delete an Expense:**
From the Expense List, long click on any item. When the context menu appears select any more Expenses you wish to delete. Then press the x button on the context menu

**Edit an Expense:**
 Click on an Expense from the Expense List. 