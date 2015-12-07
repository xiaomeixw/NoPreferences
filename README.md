# NoPreferences
Deconstruct Dynamic proxy , Use SharedPreferences demo for my blog 's article

#Screen
<img src="http://i.imgur.com/ltBY2Ko.png" width="45%">

#Use SharedPreferences in Android

	private void setSp() {
	    SharedPreferences sp = getSharedPreferences("my", Context.MODE_PRIVATE);
	    SharedPreferences.Editor edit = sp.edit();
	    edit.putString("mykey", "myvalue");
	    edit.commit();
	}


	private void getSp() {
		SharedPreferences sp = getSharedPreferences("my", Context.MODE_PRIVATE);
		String value = sp.getString("mykey","defalutvalue");
    }

The code smell not good and write Duplicate code again and again...


# NoPreferences Usage
Use NoPreferences So easy , API style just like Retrofit:

NoPreferences turns your set/get API into a Java interface.

    @SharePreference(name = "person",Mode = Context.MODE_PRIVATE)
    public interface PersonProxy {

        String key = "MYNAME";

        @SpKey(key = key)
        void setMyName(String name);

        @SpKey(key = key)
        String getMyName();

    }


The NoPreferences class generates an implementation of the Proxy interface.

 	PersonProxy proxy = new ProxyGenerator().create(application, PersonProxy.class);

Storage then get From SharedPreferences.

	proxy.setMyName("HappyKandy");

	proxy.getMyName()

#License

Copyright 2015 xiaomeixw

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


