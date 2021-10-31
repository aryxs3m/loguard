# Loguard

Simple Java application that watches your logfiles and send notifications if needed.

Loguard is **watching** log files. It does not block attacks.

At the moment this project supports
- 2 notifiers:
  - Discord Webhook
  - Console
- Very basic rules:
  - string contains

## Configuration

You have two JSON files: **config.json** and **patterns.json**.

### config.json

- Basic configuration
- Log file paths for monitoring
- Notifier configuration per pattern type

### patterns.json

Loguard notifies you when a pattern is matched with a log line. These patterns are configured here.