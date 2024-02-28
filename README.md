# Vatic SDK

Provides ability for VaticAI partners to report back on sucessful conversions with in fintech Android applications.

# How to use

on your build.gradle file declare the vatic sdk dependency as follows

```
implementation("com.vaticai:postback:1.0.0")
```

in your application code simple usage is as follows

```
  val postback = Postback(this, "uuidv6-you-woll-receive-from-integration-team") //this is the Context
  postback.received()
```

# Available methods

## received()
mark loan application as received

## approved()
mark loan application as approved

## disbursed()
mark loan as disbursed

## defaulted()
mark loan as default
