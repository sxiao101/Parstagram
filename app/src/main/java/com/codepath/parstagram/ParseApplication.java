package com.codepath.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("G9krRttd0jGPfjDuIh3LvujfDr26kfenNmQ8lswq")
                .clientKey("jh1qOzE28AO4tAPsNQzVjO6AHnmztL3kUfMlpWxI")
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
}
