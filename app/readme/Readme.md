# Coroutines

## What are Coroutines

![alt text](https://www.howtodoandroid.com/wp-content/uploads/2021/10/kotlin-coroutines.png)

**Coroutines are nothing but lightweight threads**

**Coroutines do not replace threads, it’s more like a framework to manage it.**

*A framework to manage concurrency in a more performant and simple way with its lightweight thread which is written on top of the actual threading framework to get the most out of it by taking the advantage of cooperative nature of functions.*

![alt text](https://amitshekhar.me/_next/image?url=%2Fstatic%2Fimages%2Fblog%2Fsuspend-function-coroutines.png&w=640&q=75)



## Why should we use Coroutines

### Thread problems

```
// For example, something like this in Main Thread.
val response = Async_operation()  //async operation
if (response.isSuccessful()) doThis() else doThat()
```

We create a new thread and once the task is completed, we pass back the result to UI thread. We can do that right? Yes, for sure but there are few problems with this approach:

1. **Passing data from one thread to another is a headache.** Also, it does not look very clean. Most of the time we end up using callbacks or some sort of notify mechanism
2. Threads are  **expensive**. Creating and stopping them is expensive, involves creating own stack. Threads are managed by OS. Thread scheduler adds extra overhead to schedule the threads.
3. Threads are  **blocking** . If you are performing a task as simple as delaying the execution for a second (Sleep), Thread would be blocked and can not be used for any other operation.
4. Threads are  **not lifecycle aware** . They do not have any knowledge of Lifecycle components (Activity, Fragment, ViewModel). A thread will be running even if UI component is destroyed which requires us to handle clean up and memory leaks.

Coroutines are light and super fast

A coroutine can provide a very high level of concurrency with very small overhead. Multiple threads can also provide parallelism but there is blocking and context switching. **Coroutine suspends the thread and does not block it so that it can switch to another work.**

### Performance comparison between Coroutine and Thread

Create 10000 coroutines

```dart
        val numMax = 10000
        //Create too many coroutines
        try {
            val startTime = System.currentTimeMillis()
            for (i in 1..numMax) {
                GlobalScope.launch {
                    val a = 0
                }
            }

            Log.i(TAG, "Create $numMax coroutines in ${System.currentTimeMillis() - startTime}")
        } catch (oom: OutOfMemoryError) {
            Log.i(TAG, "Out of memory $oom")
        }
```

Create 10000 threads

```dart
        val numMax = 10000
        //Create too many Thread
        try {
            val startTime = System.currentTimeMillis()
            for (i in 1..numMax) {
                thread {
                    val a = 0
                }
            }

            Log.i(TAG, "Create $numMax thread in ${System.currentTimeMillis() - startTime}")
        } catch (oom: OutOfMemoryError) {
            Log.i(TAG, "Out of memory $oom")
        }
```

Output: 
```dart
15:42:24.540 CoroutinesPractice I Create 10000 coroutines in 346
15:42:30.308 CoroutinesPractice I Create 10000 thread in 5768
```

When we change numMax = 1000000
Output:
```dart
15:48:49.391 CoroutinesPractice I Create 1000000 coroutines in 33496
16:05:27.310 CoroutinesPractice I Create 1000000 thread in 564799
```

###Memory usage comparison between Coroutine and Thread 
* Each thread has its own stack, typically 1MB in size. 64k is the least amount of stack space allowed per thread in the JVM

* A simple coroutine in Kotlin occupies only a few dozen bytes of heap memory.

**So. The winner is Coroutines** 

##Conclusion
A coroutine can provide a very high level of concurrency with very small overhead. 
Multiple threads can also provide parallelism but there is blocking and context switching. Coroutine suspends the thread and does not block it so that it can switch to another work.

With a lot of coroutines doing very small bits of work and voluntarily switching between each other, It can provide efficiency and faster execution which can never be achieved from a scheduler which is why you can have thousands of coroutines working together as opposed to tens of threads.

I hope you have found a reason to use coroutine. What we have seen is a small part of, what coroutine has to offer. Additionally, Coroutine works really well with Livedata, Room, Retrofit, etc. So I will encourage you to start using coroutine in your projects. Only if you try it, you would know the power of it