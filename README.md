# InputSource Auto Converter

![plugin-example](.github/readme/plugin-example-1.gif)

This plugin is helpful when using IdeaVim in a non-english environment. It assists in situations where you forget to switch to english while typing non-english in insert mode.

## Installation

Go to `Settings > Plugins > Marketplace` and searching `InputSource Auto Converter`. 

## Feature

![plugin-setting-panel](.github/readme/plugin-setting-panel.png)

> `Settings > Tools > InputSource Auto Converter`

**Automatically convert input source at condition**

- When opening the project, input source initializes with the set `Project init Input Source`
- When transitioning from Insert Mode to Non-Insert Modes(Command Mode, Visual Mode, Select Mode, ...), this plugin automatically converts to the set `Normal Mode Input Source`
  - If you forget to switch back to english after using non-english in insert mode, this plugin automatically converts to english in non-insert mode.
- Strict check mode configures the input source to fixed in the code editor when not in Insert Mode.
  - If editor receives non-English input source changed from another place, it automatically converts to set input source.

## Supported

- Currently, only supports macOS
- Only supports the default input method provided by OS
