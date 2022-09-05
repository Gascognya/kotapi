# KotApi Web Tools (Prototype)

***by JiaYin Pan***

## Example

### Test

```kotlin
val app = Application {
    val name = it.queryParam("name", "unknown")
    Response("hello $name")
}
```

###### Native Test

```kotlin
val request = GET("/").addQuery("name", "bob")

val response = app(request)

//|------------------------------------------------------------------------
//|Response 200
//|-------------------------------- Header --------------------------------
//|content-type: text/plain
//|--------------------------------  Body  --------------------------------
//|hello bob
//|------------------------------------------------------------------------

```

###### Server Test

```kotlin
val servlet = KotApiServlet(app)
// Build Server By Servlet

// Send request to http://127.0.0.1?name=bob
```

### Router

```kotlin
val helloApp = Application {
    val name = it.pathParam("name", "unknown")
    val age = it.queryParam("age", -1)
    Response("hello $name ($age)")
}

val router = Router.Builder()
    .get("/hello/{name}", helloApp)
    .build()

val request = GET("/hello/bob").addQuery("age", "18")

val response = router(request)

//|------------------------------------------------------------------------
//|Response 200
//|-------------------------------- Header --------------------------------
//|content-type: text/plain
//|--------------------------------  Body  --------------------------------
//|hello bob (18)
//|------------------------------------------------------------------------

```

### Middleware

###### Functional

```kotlin
val app = Application {
    Response("hello")
}

val echoMiddleware = Middleware { next ->
    Application {
        logger.info("${it.method} ${it.path}")
        next(it)
    }
}

val newApp = echoMiddleware.then(app)

val response = newApp(GET("/"))
 ```

###### Classify

```kotlin
fun Catching<RuntimeException>.handler(): Response {
    logger.warn(exception.message)
    return Response(exception.message ?: "unknown", 500)
}

val app = Application{
    throw RuntimeException("some error")
}

val middleware = ExceptionCatchingMiddleware
    .Builder()
    .add<RuntimeException> { handler() }
    .build()

val newApp = middleware.then(app)
```

### TODO
- Body Parser (Json, FormData)
- Functional Application
- More Sugar