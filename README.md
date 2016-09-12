# ShakingClock
An android TTS time-telling application with a setting GUI

# Overview
Ever tired of having to turn on screen in order to check the current time? Well, don't hesitate to download this application.
Using accelerometer as trigger and TTS as time-telling service, you can know current time with a shake of your phone.

# Help
Turn on the app and a setting gui is shown, where you can modify the sensitivity and minimal time interval between two triggers.
Then send the app to background and do not exit. Since a wake clock is adopted, the app should be functioning correctly even when
the screen turns off.

Please note that since the background service run at the same thread with the gui, exiting and killing the app will cause it not
to function.

# System requirement
* Recommend using android 5.0+
* TTS with local language support
* Accelerometer
