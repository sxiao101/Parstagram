package com.codepath.parstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("G9krRttd0jGPfjDuIh3LvujfDr26kfenNmQ8lswq")
                .clientKey("jh1qOzE28AO4tAPsNQzVjO6AHnmztL3kUfMlpWxI")
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
}
