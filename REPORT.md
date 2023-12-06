# CS3035 – Course Project Description

## Description of Your Project

Below, provide a short description of what your project is, how it works, and why you selected this as your project.

My project is a personal organization app. It was designed to help users to manage their tasks. Our brain was not designed to remember things. It was designed to solve problems. This application will remember tasks for users and help them organize tasks accordingly. User can add/modify/delete items in any list.
Things that a human can do can be categorized into two classes: 1. Actions that can be done anytime such as reading a book or reading an email and 2. actions that can be done at some specific point of time (usually they take specific place as well) such as appointments, taking exams/test and so on.
- Next Action List: this is where you store all the actions that can be done at any point of time: order new laptop, clean room, send email, buy tickets online, do homework. 
- Calendar List: this is where you store any task that requires specific time: go to lecture, appointments to bank, job interview, gym, make a phone call at appropriate time.
- Project List: some tasks cannot be achieved by one action such as Learning a new language, creating a program, completing a course whether online or in university. Therefore, you cannot list those actions in NAL or Calendar. First, Project Widget allows you to store name of your projects, then you can implement your project by breaking it into smaller one actionable steps. If project is too complex you can break it further into smaller projects by adding task back to Project List.
- Someday/Maybe List: some of the things that you may want to do may come to your mind but currently you dont have time for that or bad circumstances. So you may want to add those ideas to Someday/Maybe list and do them in the future. Those ideas can be projects, one time actions or calendar so you may move tasks accordingly in the future.
- Done List: stores tasks that are already completed. It allows you to see how many things you actually do but do not usually remember. The purpose of this list to motivate you and push you forward to do more.

I selected this project because I was using this task management model for almost for four years now. Previously I was using a notebook and I had to provide a page for each list. But now I can see every list in one screen. I am planning to store/save the program's state in the future so this program becomes actually usable.

## Requirements

- How/What different views did you provide for some aspect of your model?

Main view: this view shows 5 list widgets and its the main tool for organizing tasks.

Statistical view: this view shows a Pie Chart. Pie Chart has 5 sections showing each list with its business. It tells the user which section has more or less actions/projects in percent.
- What custom widget did you create in your application?

1.Next Action List Widget

2.Calendar List Widget

3.Project List Widget

4.Someday/Maybe List Widget

5.Done list Widget

6.Project breaker Widget

7.Clock widgets (shows time) but its not important

- What are the different domain objects that can be created/edited in
  your application?

Each list has its own unique list item. Items may consist of buttons, textField, Date, Time but the most important data is users text that stores Actions. Therefore, new item in any list can be created just with text.

NAL item item consists of: Done button (when user finishes the task user needs to press it), text field (user writes his action here), Delete Button (removes item from the list).

Calendar item consists of: Done button, Date (users specifies date for this action here), Time (user specifies time), text field (user stores the action), text field (user stores address where this action will be completed)

Project item consists of: Break button (user uses this button to manage project. This button opens an additional window for project), text field (for name of the project), delete button (remove project)

Someday/Maybe item consist of text field (stores action/Project description), 3 buttons to move item to one of the previously mentioned lists

Done List item consists of: text field (completed action), delete button (remove action for history of completed actions)

ProjectBreaker item consists of text field (can belong to any list) and 4 buttons to add this item to one of the lists. If project is complex user might want to add back some part of the project to Project List and break them further later on.
- What parts of the application/project did you find particularly challenging? And, what would you have liked to improve?

My main view class is not listening to the model changes since my view consists of custom widgets that store data. It would be very hard to manage items in the list if they are outside the widget. I added listeners to my custom widgets in the controller class, so the model class still gets user data from widgets, but the view does not depend on the model.
When I create an item in the list I also attach controllers to some parts of the item (For instance NAL item has two buttons). I did not find a way where I could create this item outside of widget and then pass it to it with all the controllers. I believe my approach is simpler therefore I used mostly widgets to work with lists. For example (controller example feature) when new item is created text field automatically populate with some base text and when user presses text field it erases the default text so the user won't have to erase it manually. Its one of the controllers that I implemented and I thought it's better to manage items inside the widget classes.

My second view class (statistical view) follows MVC architecture. Statistical View listens to changes in the model and draws a Pie Chart accordingly.  
If I had more time I would clean my code. I want to create one widget list class and use EXTEND to create different types of Widget list items (Polymorphism). Also, I want to add sorting to Calendar widget so items always sorted by date and time.
- Any  other comments on the course project?

The application is screen adjustable (if your monitor is small widget will be smaller to fit, if its big widgets will be bigger). Instead of using fixed sizes for each object on the screen I gave some percentage of the screen to each of the object. Screen is not resizable.
Model get data from widget that is implemented in the controller class. When new item gets added to any of the list - item will be also added to the model. In main view - View listens to changes in the widget, not Model. It is hard to manage each widget if view listens to model and then draws widget since my item consist not only of the data but also buttons. My second statistical view is implemented using MVC.

- Provide a link to your video that is easily viewable.

Tutorial video:
https://unbcloud-my.sharepoint.com/:v:/g/personal/p655n_unb_ca/EUfDedmu2WxJrHhVk8z4UR8BCRiLeSDMbQDUquSQaFY7Pw?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D&e=Zl2m0J

3.5min video:
https://unbcloud-my.sharepoint.com/:v:/g/personal/p655n_unb_ca/EWo1ntNHEjhDncufxyjl5QUBpRW0Eol8tt8c8WkpBQdMhg?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D&e=fdJ8GF



