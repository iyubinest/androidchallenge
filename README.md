[![Build Status](https://travis-ci.org/iyubinest/androidchallenge.svg?branch=master)](https://travis-ci.org/iyubinest/androidchallenge)
[![Coverage Status](https://coveralls.io/repos/github/iyubinest/androidchallenge/badge.svg?branch=master)](https://coveralls.io/github/iyubinest/androidchallenge?branch=master)

WEATHER CLIENT
======

A sample Android app which showcases common open source libraries.
The App tries to use a clean OOP structure that's focused on tests, the first design decision was 
to take divide the app in two big components `data` & `ui`, the first one contains all the data 
suppliers like http, or components to save images, the main reason for this to exist is based on the 
`Repository Pattern`.
 
The UI components are based on the `MVP` pattern without using the same names, where a `Source` is 
a components that provides the data for a UI trough the `View` and the `Repositories` handles the 
way of retains and gets the data. 

This project doesn't contains any comments because it tries to be self-commented, this approach 
encourages that the code is the documentation and it requires to be updated as it evolves.

## Questions to Answer
Please answer the following in about a paragraph.

1.  How long did you spend on completing this challenge? 8 hours
2.  How far were you at 3 hours? get working tests, for all the business logic, gradle configuration
and basic architecture. 
3.  What needs to be finished/fixed for this to be use in a production app? some elements can be 
cleaned in the organization of the project and somo separation of concepts in some classes that are 
doing to much
4.  What did you think of this challenge? fun, easy, like it.
5.  What part was unclear? I think was pretty clear
6.  What could we do to make this challenge better? show a detail for a weather
7.  What did we forget to test you on? Probably on Android components or storage management, but are
not required for every app.

Screenshots
---------

![alt text](https://github.com/iyubinest/androidchallenge/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png?raw=true "App Icon")
![alt text](https://github.com/iyubinest/androidchallenge/blob/master/art/mobile.png "Weather")
![alt text](https://github.com/iyubinest/androidchallenge/blob/master/art/tablet.png "Weather")


Libraries
---------

 * Dagger
 * ButterKnife
 * Retrofit
 * Auto-Parcel
 * Gson
 * Glide
 * Espresso
 * JUnit
 * Mockito
 * MockWebServer
 
 
TO-DO
---------

 * Check for fixes


License
-------

    Copyright 2016 Cristian Gómez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
