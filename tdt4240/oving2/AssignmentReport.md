## TDT4240 Pattern Exercise

### Implementation: Singleton
I changed how I handle the different screens such as the main menu and level screens.
The screen manager ```ScreenManager``` is now an Singleton with lazy initalization and enums ```ScreenEnum``` for the different screens.
After initializing the screen manager with the main game object, the screens can be changed like so:
```ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);```

N.B. I used the following article as guide, [PixNB Blog - How to manage screens in LibGDX](http://www.pixnbgames.com/blog/libgdx/how-to-manage-screens-in-libgdx/).

### Theory
##### 3.a) Which are architectural patterns and which are design patterns? What are the relationships and differences of architectural patterns and design patterns?
* Observer - design pattern
* State - design pattern
* Template Method - design pattern
* Model View Controller - architectural pattern
* Abstract Factory - design pattern
* Pipe and filter - architecture pattern

The main difference between architectural patterns and design patterns are that the former has a broader scope. Architectural patterns aim to be a general solution to higher level issues faced when engineering software. Design patterns on the other hand aim to solve issues at module levels. 

##### 3.b) How is the pattern you chose realized in your code? (Which class(es) works as the pattern you chose?)
I chose the Template Method to create an abstract screen class called ```AbstractScreen``` which implements a regular screen in LibGDX.

##### 3.c) Is there any advantages in using this pattern in this program? (What are the advantages/disadvantages?)
Since implementing a ```Screen``` requires an amount of methods such as ```show()```, ```render()``` and ```resize()```, you end up with a lot of code duplication.
Using an abstract class that you extend from helps to lessen the amount of duplication as you can just add any variations to the specific methods.
An disadvantage is that the code can be more complex to reason and to comprehend the program flow.
It also makes things more difficult to maintain in order to make sure any changes in the abstract class do not break functionality in the extended classes.

### Self evaluation
##### 1) How much hours do you spend on this assignment? 
5 hours

##### 2) If available, how much hours do you spend on each step?
* Step 2: 2 hours
* Step 3: 2 hours
* Step 4: 1 hour
