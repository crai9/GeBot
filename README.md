#GeBot

A twitch.tv bot for obtaining information about RuneScape.

Currently in development.

## Commands

Commands are not case-sensitive.

Works best if given twitch moderator status. `/mod ge_bot`

| Command | Channel | Description | Parameters |
| --- | --- | --- | --- |
| `!join` | ge_bot | Tells GeBot to join your channel.| |
| `!leave` | ge_bot | Tells GeBot to leave your channel.| |
| `!info` | any | Returns information about GeBot.| |
| `!help` | any | Returns a list of commands available to the user.| |
| `!time` | any | Returns the current runescape time.| |
| `!date` | any | Returns the current runescape date.| |
| `!runedate` | any | Returns the current Runedate.| |
| `!reset` | any | Returns the amount of time remaining until the daily reset.| |
| `!warbands` | any | Returns the amount of time remaining until the next warband.| |
| `!count` | any | Returns the amount of players playing a specified game. | `<both/rs3/07>` |
| `!pc` | any | Returns the current Grand Exchange price for an item. | `<item name>` |
| `!vos` | any | Returns the current Voice of Seren (usually). | |
| `!araxxor` | any | Returns details about Araxxor's open paths. | |
| `!vorago` | any | Returns the current Vorago rotation. (Coming soon) | |
| `!slots` | any | Rolls three random twitch emotes. | |

=========

Uses pircbot library for networking, code from harha/TwitchAI used as a starting point.
