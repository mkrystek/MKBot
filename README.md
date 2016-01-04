MKBot
===

MKBot is a Skype bot written in Java. Since there is no library to integrate with Skype desktop client (at least to my knowledge), I've set myself a goal to write such a bot from scratch.  
MKBot uses somewhat "weird" combination of ways to do its job (but it works!). To learn more about how it works, see [this](#how) section.
To learn how to use it, visit [this](#usage) section.

Have fun!

* [Disclaimer](#disclaimer)
* [How It Works](#how)
* [Goals and Features](#goals)
* [Requirements](#requirements)
* [Usage](#usage)
* [Configuration](#configuration)
* [Roadmap](#roadmap)
* [License](#license)

<a name="disclaimer"/>Disclaimer
---
 Still in very alpha phase, use at your own risk :)  
 Sometimes improper behavior may cause creating additional chats and/or unnecessary spamming people from your contacts.  
 People might not like when someone spams their Skype window.  

## <a name="how"/>How It Works

 Functionality of MKBot is split into 4 main features:
 * Switching focus to Skype window and opening correct chat window
 * Reading incoming messages
 * Running tasks (based on message content) and generating reply message (or scheduling reply in the future)
 * Typing answer back into Skype chat

Quick description of the above:

**Switching focus**  
Although not as functional as advertised, [Skype URI](https://msdn.microsoft.com/en-us/library/dn745878.aspx) proved to be able to open Skype window on requested chat. One major drawback is speed - bringing Skype window to foreground takes quite a lot of time (sometimes even 2s) so wait period is needed after invoking URI.
I've been experimenting with [JNA](https://github.com/java-native-access/jna) for some old-style WinAPI window manipulation but I need to spend more time on it to make it work.

**Reading incoming messages**  
As incoming messages source, MKBot is using Skype's SQLite database (located in user files).  
Bot periodically (frequency is configurable) checks if there are any new messages. If so, it checks if message starts with special character sequence (soon to be configurable) and then parses it (extracting such info as username, taskname and message body).  
This approach works fine but it has 2 drawbacks: consistency and (once again) speed. When analyzing Skype database structure, I've counted 3 different ways Skype has been storing information about chats and participants throughout the time. When Skype decides once again to rethink their aproach, currently used algorithm in bot will become deprecated and more research will need to be done to restore this functionality. And for a topic of speed, Skype is slow to update its database which results in delay between receiving message and reacting to it.

**Running tasks**  
MKBot periodically parses incoming messages looking for specific pattern. If it finds it, it executes corresponding task accordingly (thus producing answer immediately or scheduling one to be sent in future)

**Typing messages**  
To write messages in (already focused on proper chat) Skype window, MKBot is using [Robot](https://docs.oracle.com/javase/8/docs/api/java/awt/Robot.html) class, essentially emulating user-generated keyboard events.

## <a name="goals"/>Goals and Features

TODO

## <a name="requirements"/>Requirements
* Java 8
* Maven
* Skype account and desktop client (DUH!)

Since bot needs to control desktop, it is best to run it inside virtual machine. So far, it has been tested to run correctly on Windows XP guest. You can just run it inside VM and minimize the VM (as long as you change setting to never turn screen off when inactive on guest).

## <a name="usage"/>Usage
Clone repo, modify *config.properties* file accordingly, build jar with dependencies by invoking

> mvn package
 
then run it

> java -jar MKBot-0.1-jar-with-dependencies.jar

## <a name="configuration"/>Configuration (config.properties file)
 * *skype_username* - username of skype user that is running bot
 * *chat_name* - name of the chat that bot is running on
 * *polling_frequency* - (in millis) how often bot is scanning for new messages
 * *reply_tasks_to_load* - names of reply tasks to load (from pl.mkrystek.mkbot.task.impl package)
 * *scheduled_tasks_to_load* - names of scheduled tasks to load (from pl.mkrystek.mkbot.task.impl package)

## <a name="roadmap"/>Roadmap

v0.1 - Simple ping-pong reply functionality

## <a name="license"/>License
MIT
