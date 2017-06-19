# Endpoint2mock

[![Build Status](https://travis-ci.org/car2go/Endpoint2mock.svg?branch=master)](https://travis-ci.org/car2go/Endpoint2mock)

Sometimes when working with REST APIs there is a need to return some mock data. Maybe for demo purposes, or real endpoint is just not ready yet. Endpoint2mock aims to help with that by integrating with Retrofit and allowing you to easily redirect some (but not all!) of your requests to your mock server.

Here is how it goes.

## Add it to your project

### Step one

Add `jitpack.io` to the list of your repositories.

```groovy
repositories {
  maven { url 'https://jitpack.io' }
}
```

### Step two

Add dependencies.

```groovy
compile 'com.github.car2go.Endpoint2mock:endpoint2mock:1.0.1'

// If you use Kotlin
kapt 'com.github.car2go.Endpoint2mock:endpoint2mock-compiler:1.0.1'

// If you do not use Kotlin
apt 'com.github.car2go.Endpoint2mock:endpoint2mock-compiler:1.0.1'
```

### Step three

Add `@MockedEndpoint` annotation to your Retrofit interface. For the sake of example let's use Github API.

```kotlin
interface GithubApi {

    /**
     * Will call mocked server if MockableClient is enabled.
     */
    @MockedEndpoint
    @GET("/users/car2go/repos")
    fun getCompanyRepositories(): Observable<List<Repository>>

    /**
     * This endpoint will not be mocked and requests will be sent to the real server.
     */
    @GET("/users/dmitry-zaitsev/repos")
    fun getUserRepositories(): Observable<List<Repository>>

}
```

### Step four

Use `MockableClient` as client for your Retrofit builder.

```kotlin
RestAdapter.Builder()
  .setClient(
          MockableClient.build(mockServerUrl)   // Assuming that you have something running at this URL
                  .mockWhen { shouldMockRequests() }
                  .build()
  )
  .setEndpoint("https://api.github.com")  // In this case we are using Github API
  .build()
  .create(GithubApi::class.java)
```

You can check [json-server](https://github.com/car2go/Endpoint2mock/tree/master/json-server) folder for an example of mock server.

And you are ready to go!

## License

```
Copyright 2017 car2go group GmbH

Released under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
