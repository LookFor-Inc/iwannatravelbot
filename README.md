<h1 align="center">I Wanna Travel Telegram Bot</h1>

<div align="center">
Traveling restrictions during the covid pandemic (17.03.2021 v.1)

[![API Version](https://img.shields.io/badge/telegrambots-5.0.1-blue)](https://github.com/rubenlagus/TelegramBots)
[![Build Status](https://img.shields.io/badge/jdk-15-orange)](https://www.oracle.com/java/technologies/javase/15-relnote-issues.html)
[![Build Status](https://img.shields.io/badge/spring-2.4.3-brightgreen)](https://spring.io)

[![https://telegram.me/node_telegram_bot_api](https://img.shields.io/badge/Telegram-Bot-blue)](https://t.me/IWannaTravelBot)
</div>

## Table of Contents

- [Introduction](#introduction)
- [Technology stack](#technology-stack)
- [Deploying](#deploying)
- [Configuration](#configuration)

## Introduction

This is a pure Spring Boot Telegram Bot for tracking traveling restrictions
during the covid-19 pandemic.

This bot is based on Long Polling.

The Bot can:

- Save user's country
- Save countries the user would like to travel to
- Show travel restrictions from his country to a foreign
- Notify the user when the status of restrictions has changed
- Interact with inline keyboard
- Handle messages & maintain communication

## Technology stack

- Java 15
- TelegramBots API
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Liquibase
- Lombok
- Maven

## Deploying

## Configuration

To set up a configuration you have to add values to Environment variables.

### Configuration Values

- `TELEGRAM_BOT_USERNAME` - Create a bot via [BotFather](https://t.me/botfather)
- `TELEGRAM_BOT_TOKEN` - Get token by contacting to [BotFather](https://t.me/botfather)
- `SPRING_DATASOURCE_URL` - Postgres database url
- `SPRING_DATASOURCE_USERNAME` - Postgres database username
- `SPRING_DATASOURCE_PASSWORD` - Postgres database password

## Copyright
Copyright © 2021 by [LookFor Inc](https://github.com/LookFor-Inc)
