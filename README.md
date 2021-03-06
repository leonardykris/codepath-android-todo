# Pre-work - Todo (v1.0)

**Todo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Leonardy Kristianto**

Time spent: **>21** hours spent in total (development + design)

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file (Sugar ORM)
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items (material-dialog)
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] Sync with Firebase to establish user accounts

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/u8KZmqb.gif' title='Video Walkthrough' width='300' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Challenges:

1. For the dropdown icons, I wanted to use Expandable List at first but after researching it for a while I felt that extending/implementing it is an overkill since I only needed the sub-item to be fixed icons. I have to configure the visibility options myself.
1. After realizing that ActiveAndroid is no longer maintained (Oct 2014) and facing number of issues that comes up while configuring it, I searched for another ORM and found Sugar to be really easy to use (although it has conflicting documentations since 1.3/1.4 was obsolete after 1.5 update rolled out)

## License

    Copyright 2016 Leonardy Kristianto

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.