FeaturedRecyclerView
------------------------------
**FeaturedRecyclerView** is a custom **ViewGroup** which is made by extending **ReyclerView**. As name suggest **FeaturedRecyclerView**, it features the first item which is at top (by setting its height to **featuredItemHeight**).

[![API](https://img.shields.io/badge/API-11%2B-red.svg)](https://android-arsenal.com/api?level=11)

![SampleGIF](/art/sample_GIF.gif) 

Attributes
----------

* **featuredItemHeight**: Height of first item.
* **defaultItemHeight** : Height of other items.
* **offset** : It is not a xml attribute but a parameter which is received in **FeaturedRecyclerViewAdapter**'s method to animate the property of childs widget.

Diagramatic View
-----------------------
![SampleDiagram](/art/diagram_small.jpg) 

Concept
----------
![Concept](/art/concept.gif) 

FutureWork
---------------
* **Horizontal Orientation** support would be added.
* **Reference Points** would be changed.

Basic Usage
-----------

Add below lines in build.gradle at app level.
```java
compile 'com.github.developer-shivam:featuredrecyclerview:1.0.0'
```

*For a working implementation, see `/app` folder*

```java
FeaturedRecyclerView featuredRecyclerView = (FeaturedRecyclerView) findViewById(R.id.featured_recycler_view);
FeatureLinearLayoutManager layoutManager = new FeatureLinearLayoutManager(this);
featuredRecyclerView.setLayoutManager(layoutManager);
CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(this, dummyData);featuredRecyclerView.setAdapter(adapter);
```

You must use **FeaturedLinearLayoutManager** to avoid flickering.

```xml
<developer.shivam.featuredrecyclerview.FeaturedRecyclerView android:id="@+id/featured_recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:featuredItemHeight="400dp"
    android:defaultItemHeight="200dp" />
```

Adavantages of using **FeaturedRecyclerViewAdapter** is that it contains two more methods which can be used to animate the properties of childs attribute (like textView fading).

```java
    @Override
    public void onSmallItemResize(CustomRecyclerViewHolder holder, int position, float offset) {
       holder.tvHeading.setAlpha(offset / 100f);
    }

    @Override
    public void onBigItemResize(CustomRecyclerViewHolder holder, int position, float offset) {
       holder.tvHeading.setAlpha(offset / 100f);
    }
```
Donations
-------------

This project needs you! If you would like to support this project's further development, the creator of this project or the continuous maintenance of this project, feel free to donate. Your donation is highly appreciated (and I love food, coffee and beer). Thank you!

**PayPal**

* **[Donate $5]**: Thank's for creating this project, here's a coffee (or some beer) for you!
* **[Donate $10]**: Wow, I am stunned. Let me take you to the movies!
* **[Donate $15]**: I really appreciate your work, let's grab some lunch!
* **[Donate $25]**: That's some awesome stuff you did right there, dinner is on me!
* **[Donate $50]**: I really really want to support this project, great job!
* **[Donate $100]**: You are the man! This project saved me hours (if not days) of struggle and hard work, simply awesome!
* **[Donate $2799]**: Go buddy, buy Macbook Pro for yourself!
Of course, you can also choose what you want to donate, all donations are awesome!

Connect With Me
-----------

Shivam Satija (droidsergeant)
I love making new friends, please feel free to connect with me.

<a href="https://plus.google.com/108004024169425288075">
  <img alt="Connect me on Google+" src="/art/gplus.png" />
</a>
<a href="https://www.facebook.com/theShivamSatija">
  <img alt="Connect me on Facebook" src="/art/fb.png" width="64" height="64" />
</a>
<a href="https://in.linkedin.com/in/developershivam">
  <img alt="Connect me on LinkedIn" src="/art/linkedin.png" />
</a> 

Question / Contact Me / Hire Me
---------------------
Please feel free to ping me at **dr.droid27@gmail.com**. Expected package would be **6 lpa**.

License
-------

```
Copyright 2017 Shivam Satija

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[Facebook]:          /art/fb.png
[Google+]:             /art/gplus.png
[LinkedIn]:             /art/linkedin.png

[Donate $5]: 		https://www.paypal.me/developerShivam/5
[Donate $10]:  	https://www.paypal.me/developerShivam/10
[Donate $15]:  	https://www.paypal.me/developerShivam/15
[Donate $25]:  	https://www.paypal.me/developerShivam/25
[Donate $50]: 		https://www.paypal.me/developerShivam/50
[Donate $100]: 	https://www.paypal.me/developerShivam/100
[Donate $2799]: 	https://www.paypal.me/developerShivam/2799