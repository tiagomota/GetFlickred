# GetFlickred

This is a simple android app that allows one to search for a Flickr username an see it's public photos and some of their details.

## Usage

Just import the project to the latest Android Studio version and run it.
**Note:** A user with cool pictures is "_Jerry Fryer_", have a look!

Regarding tests, this project has some. 

- UI Tests
```bash
./gradlew connectedDebugAndroidTest
```

- Unit Tests
```bash
./gradlew testDebugUnitTest
```

- Coverage report
```bash
./gradlew createDebugCoverageReport
```

## Third Party Libraries

#### Picasso
The images in the app are handled by [Picasso](http://square.github.io/picasso/) , since it is a hassle-free image loading library, that can handle all the caching and optimizations for image re-usability.

#### Retrofit & OkHttp
The communication layer is built using [OkHttp](http://square.github.io/okhttp/), since it provides a simple and easy client to perform HTTP and HTTP/2 requests, without having to worry about usual problems like redirects and services with multiple IP addresses.
Together with [Retrofit](http://square.github.io/retrofit/), it can abstract this communication layer into a type-safe interface with our API endpoint translated to plain old Java methods.
 
#### RxJava
[Retrofit](http://square.github.io/retrofit/) can returns Observables as the responses of the defined requests, which allows to use the Reactive paradigm, in our applications.
Here RxJava is used to easily chain together requests to the Flickr API, without the need to manage a nasty callback chain, but instead apply functional directives like map and flatmap to bundle together our requests and make them return the most suitable object we require.

# License
Copyright 2016 Tiago Mota

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.