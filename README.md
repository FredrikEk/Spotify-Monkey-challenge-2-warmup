# Java starter kit

<img src="http://monkeymusicchallenge.com/images/monkey.png" align="right">

### Prerequisites

First of all, go to http://monkeymusicchallenge.com, sign up and get your API key.

For this starter kit, you will need basic knowledge of:

* [Java](http://youtu.be/kLO1djacsfg)
* [Maven](http://maven.apache.org)

Make sure to have Maven [Maven](http://maven.apache.org) installed on your system.

Also, if you run into trouble, make sure to use Java 1.7 or newer.

### Getting started

Start by [forking](https://github.com/monkey-music-challenge/java-starter-kit/fork)
this repository to your own GitHub user.

Then, open up a terminal and:

```
git clone git@github.com:<username>/java-starter-kit.git
cd java-starter-kit
mvn package
java -jar target/warmup.jar <your-team-name> <your-api-key>
```

Use [Eclipse](http://eclipse.org) or [IDEA](http://www.jetbrains.com/idea)? Run `mvn eclipse:eclipse` or `mvn idea:idea` and then import into your favorite IDE.

These are all UNIX commands. Got Windows? We recommend [Cygwin](https://www.cygwin.com/).

Issues with `maven` or `java`? Try downloading the latest [Java](https://www.java.com/en/) version (1.8).

Make sure to surf to your team page before running the above commands...

Your monkey is waiting for you!

### How to complete the warmup challenge

`Main.java` contains the boilerplate needed to communicate with the server. You should not need to change anything in here unless we have done something wrong.

We suggest you go straight to `AI.java` and start playing around with the code.

Remember, your mission is to:

* guide your monkey through the warmup level
* pick up all the music items
* get them to the eagerly awaiting Spotify user

Have fun!

### Bugs

When you find bugs in our code, please submit an issue or pull request to our [original starter kit repository](https://github.com/monkey-music-challenge/java-starter-kit).
