### TDT4186 Explanation of practical exercise P2

In my solution of the multiple sleeping barbers problem, I used ```synchronized``` methods in the ```CustomerQueue``` to implement the producer/consumer model.

Both the ```Doorman``` and the ```Barber``` classes implement the ```Runnable``` interface.

The ```Doorman``` acts as the producer and adds customers to the fixed size queue in ```CustomerQueue``` in the method called ```addCustomer```.
The ```Barber``` on the other hand acts as the consumer and will take customers from the queue in ```CustomerQueue``` in the method called ```popCustomer```.