
### Configurable Cash Register

Welcome to the Configurable Cashier Register project! This app offers a range of features to make managing a restaurant easier.

## Features
__User-Features:__

- Customization of the number of tables
- Addition and removal of products from receipts
- Automatic receipt creation and saving
- Payment options including cash and card
- Virtual receipt printing

__Admin-Features:__

- Adjustment of tax rates
- Implementation of a login system with username and password
- The ability to add new users
- Unlimited, customizable product list with Title, Price, Description, and an Image

## How to run
There are two ways to run the Application: From IDE (e.g. IntelliJ) or to install an actual release from [here.](https://github.com/d3vote/XSolutions-Configurable-Cash-Register/releases)

__Set display-scaling to 100%__ and we suggest using the resolution: 1920x1080 ! <br />

How-To change Scaling:
- [Windows](https://support.microsoft.com/en-us/windows/view-display-settings-in-windows-37f0e05e-98a9-474c-317a-e85422daa8bb)
- [Mac](https://support.apple.com/de-at/guide/mac-help/mchl86d72b76/mac)


__Instruction for IDE (e.g. IntelliJ):__

1) Clone the file to your directory.

2) Go to the build.gradle file in your IDE.

3) Try to build the project, if it fails follow the steps bellow.

4) Look in the top left corner and click on "Download coretto-11.0.7".

5) Wait until the JDK has been installed.

6) Press on Elephant with reloading symbol in the right top corner

7) Run App.java to test the Application

If still having some issues, try to manually select corretto-11.0.7 as JDK


Prerequisites for installing the release:
- [Java Runtime Environment](https://www.java.com/de/download/)

Instruction for .zip and .exe can be found in each [Release](https://github.com/d3vote/XSolutions-Configurable-Cash-Register/releases) README (Windows only)


## Info about Configuration Files
When using the app, three configuration files will be created in the AppData/XSolutions-POS directory: __usersList.json__, __productsList.json__, __categoryList.json__ and __config.txt__. It is essential that these files and the directory are not empty, as the app will not function properly without them.

For MacOS: the files will be created in User/username/Library/Application Support/XSolutions-POS

(If you have any Problems with config file, just delete the entire XSolutions-POS folder.

[Config-Files](https://github.com/d3vote/ConfigFiles) <- Github Repository where Files are stored

## User Data (Username, Password, Rights)

| Name | Username | Password | Admin right |
|------|----------|----------|-------------|
| Dmytro Shandra | d3vote | 1111 | Yes |
| Tolga Topal | tolga | 123 | Yes |
| Tora Dotcom | tosha | test | Yes |
| Theresa Sunitsch | theresa | test | Yes |
| Etrit Talla | etrit | test | Yes |
| User | user | user | No |
| Admin User | admin | admin | Yes |

## Authors
- [Tolga Topal](https://github.com/Torsoto) __[Team-Lead]__
- [Dmytro Shandra](https://www.github.com/d3vote)
- [Torgyn Zhamankulova](https://github.com/Tora-dotcom)
- [Etrit Talla](https://github.com/EtrittallaFH)

Copyright (c) 2023 FH Campus Wien

This project, XSolutions-Configurable-Cash-Register, is the property of FH Campus Wien and is protected by copyright laws. It is made available on GitHub for educational and reference purposes only. Any unauthorized use, reproduction, or distribution of this project is strictly prohibited.
