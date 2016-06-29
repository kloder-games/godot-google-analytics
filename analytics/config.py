def can_build(plat):
    return plat == 'android'

def configure(env):
    if env['platform'] == 'android':
        env.android_add_dependency("compile 'com.google.android.gms:play-services-analytics:8.3.0'")
        env.android_add_java_dir("android")
        env.disable_module()
