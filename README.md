GoogleAnalytics
=====
This is the Google Analytics module for Godot Engine (https://github.com/okamstudio/godot)
- Android only
- Register Screens
- Register Events

How to use
----------
Drop the "analytics" directory inside the "modules" directory on the Godot source.

Recompile.

In Example project goto Export->Target->Android:

	Options:
		Custom Package:
			- place your apk from build
		Permissions on:
			- Access Network State
			- Internet

Configuring your game
---------------------

To enable the module on Android, add the path to the module to the "modules" property on the [android] section of your engine.cfg file. It should look like this:

	[android]
	modules="org/godotengine/godot/GodotGoogleAnalytics"

If you have more separete by comma.

API Reference
-------------

The following methods are available:
```python
# Init GoogleAnalytics
# @param string tracker_id The tracker ID (UA-XXXXXXX-X)
init(tracker_id)

# Register screen
# @param string name The name of the screen
screen(name)

# Register event
# @param string category Category of the event
# @param string action Action of the event
event(category, action)
```

References
-------------
Google Analytics Services:
* https://developers.google.com/android/reference/com/google/android/gms/analytics/GoogleAnalytics
* https://developers.google.com/analytics/devguides/collection/android/v4/?hl=es


License
-------------
MIT license
