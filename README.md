MKBot
===
Skype Bot written in Java

* [Disclaimer](#disclaimer)
* [Goals and Features](#goals)
* [Requirements](#requirements)
* [Usage](#usage)
* [Configuration](#configuration)
* [Roadmap](#roadmap)
* [License](#license)

<a name="disclaimer"/>Disclaimer
---
Still in very alpha phase :)
Use at your own risk, sometimes improper behavior may cause creating additional chats and/or unnecessary spamming people from your contacts. 

## <a name="goals"/>Goals and Features

TODO

## <a name="requirements"/>Requirements
* Java 8
* Maven
* Skype account and desktop client (DUH!)

## <a name="usage"/>Usage
Clone repo, modify *config.properties* file accordingly, build jar with dependencies by invoking

> mvn package
 
then run it

> java -jar MKBot-0.1-jar-with-dependencies.jar

## <a name="configuration"/>Configuration (config.properties file)
 * *skype_db_path* - path to db.main skype database file (will probably be auto-configured in future)
 * *skype_username* - username of skype user that is running bot
 * *chat_name* - name of the chat that bot is running on
 * *polling_frequency* - (in millis) how often bot is scanning for new messages
 * *reply_tasks_to_load* - names of reply tasks to load (from pl.mkrystek.mkbot.task.impl package)
 * *scheduled_tasks_to_load* - names of scheduled tasks to load (from pl.mkrystek.mkbot.task.impl package)

## <a name="roadmap"/>Roadmap

v0.1 - Simple ping-pong reply functionality

## <a name="license"/>License
MIT