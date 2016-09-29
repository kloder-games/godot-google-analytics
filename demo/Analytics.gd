extends Node

var analytics = null

func _ready():
	if Globals.has_singleton("GoogleAnalytics"):
		analytics = Globals.get_singleton("GoogleAnalytics")
		# Universal Analytics Code, Version, Name, Package
		analytics.init("UA-xxxxxxxx-x", "x.x.x", "Name", "com.package.app")

# Google Analytics Dispatch
# ------------------------------------------------------------------------------

func registerScreen(screen_name):
	if analytics != null:
		analytics.screen(screen_name)

func registerEvent(category_name, action_name):
	if analytics != null:
		analytics.event(category_name, action_name)
