====================================================================================
+                                                                                  +
+                                     FOODME                                       +
+                        Restaurant Management Application                         +
+                        ---------------------------------                         +
+                                                                                  +
+                                     README                                       +
+                                                                                  +
====================================================================================


====================================================================================
+                                CONTACT INFORMATION                               +
====================================================================================

                Name              | Phone                 | email
                --------------------------------------------------------------------
    Primary   : Henry Truong      | (916) 498-4606        | hetruong@ucsd.edu
                                  |                       |
    Secondary : Alex Tang         | (909) 641-0529        | alt020@ucsd.edu

====================================================================================
+                             INSTALLATION INSTRUCTIONS                            +
====================================================================================

    1) Ensure the latest version of Java is currently installed on your system.

    2) Download the correct Android SDK for your platform from the following link:

               http://developer.android.com/sdk/index.html

    3) Navigate to the downloaded folder and run SDK_Manager.exe

        -- This may take a short time, please be patient.
        -- The Android SDK Manager will begin a search for required files.

    4) Click on "Install <number of packages> package(s)..."

        -- The required software will be automatically downloaded and
           installed by the Android SDK Manager.

    5) Accept the aggreement and click "Install"

    6) Click on "Tools" at the top of the screen.

    7) On the drop-down menu, select "Manage AVDs...".

    8) Click on "New..." to create a new virtual device.

    9) In the "AVD Name:" field, type "Tablet".

    10) In the "Device:" drop-down menu, select "7" WSVGA (Tablet)(1024 x 600: mdpi)".

        -- You may need to scroll down if this option is not in view.

    11) In the "Target:" drop-down menu, select "Android 4.4.2 - API Level 19".

    12) In the "CPU/ABI:" drop-down menu, select the appropriate CPU.

        -- This will vary depending on the machine, however it is recommended to
           use 'ARM (armeabi-v7a)' if it is available.

    13) In the "Skin:" drop-down menu, select "Skin with dynamic hardware controls".

    14) In the VM HEAP field, enter "32".

        -- The rest of the fields should be automatically filled out for you.
           Any of the remaining fields may be modified as desired.

        !WARNING: Any manual changes to these may result in abnormal performance.

    15) Click on "OK".

    16) In the Android Virtual Device Manager, select the newly created device.

    17) Click on "Start...".

    18) The Launch Options window will appear.

    19) Check the box next to "Scale display to real size".

        -- The settings may be modified as desired, however it is recommended
           to preserve the automatically generated ones.

        !WARNING: Any manual changes to these may result in abnormal performance.

    20) Click on "Launch".

    21) Wait for the emulator to load.

        -- This might take a while, please be patient.

    22) If the virtual screen is locked, "Swipe" with your mouse by clicking on
        the "lock" icon, holding, dragging the mouse horizontally, and releasing.
        This will unlock the virtual device.

    23) Click on the home button icon (in the shape of a house).

    24) Using a file explorer, navigate to the SDK folder, and move to the
        'platform-tools' folder.

    25) Download the SplashScreenActivity APK from:

                http://jdelaney.org/SplashScreenActivity.apk

    26) Move this file into the opened SDK/platform-tools folder.

    27) Open a command prompt and navigate to SDK/platform-tools/.

    28) Type in the following command:

            adb install SplashScreenActivity.apk

        -- This will install the application on your emulator.
        -- Wait until the terminal displays "Success" before continuing.

    29) Switch back to the emulator window.

    30) Click on the applications icon (circle with squares inside) and locate
        the FoodMe icon.

    31) Click on the FoodMe icon, and the application will launch.


====================================================================================
+                           LOGIN/PASSWORD INFORMATION                             +
====================================================================================

    A username and password are required for all employees who desire to log into
    the website.  The credentials are as follows:

        email    : ggillespie@ucsd.edu
        password : gary

    No credentials are necessary for using the tablet application.

====================================================================================
+                                 DEFERRED TESTS                                   +
====================================================================================

    The following test cases are deferred:

        Website Test Case # 6: Get Receipt

        Tablet Test Case # 9: Back

====================================================================================
+                              LINK TO PROJECT VIDEO                               +
====================================================================================

                           ** Copyright 2014 SEGFAULT **
