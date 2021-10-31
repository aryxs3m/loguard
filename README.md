# Loguard

Simple Java application that watches your logfiles and send notifications if needed.

Loguard is **watching** log files. It does not block attacks.

At the moment this project supports
- 2 notifiers:
  - Discord Webhook
  - Console
- Very basic rules:
  - string contains

## Installation

- Build `shadowJar` or download release
- Copy to your server (ex. /opt/loguard/)
- Rename config.json.example to config.json, configure as you like
- Copy [loguard.service](loguard.service) to `/etc/systemd/system`, change working directory if needed
- Create `loguard` user, ensure that this user can read log files
- `systemctl daemon-reload`
- `systemctl enable loguard`
- `service loguard start`

## Configuration

You have two JSON files: **config.json** and **patterns.json**.

### config.json

- Basic configuration
- Log file paths for monitoring
- Notifier configuration per pattern type

### patterns.json

Loguard notifies you when a pattern is matched with a log line. These patterns are configured here.