# play-authz

<a name="readme-top"></a>
<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#Dependency">Dependency</a></li>
        <li><a href="#Configuration">Configuration</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project

This provides **jcasbin** support to __Play Framework__. 

Policy can be defined either in a `.conf` file or through _JDBC_ adapter.

## Getting Started
* [Play framework](https://www.playframework.com/getting-started)
* [Casbin](https://casbin.io/docs/category/the-basics)

## Dependency 
* [jcasbin](https://mvnrepository.com/artifact/org.casbin/jcasbin)
* [jdbc-adapter](https://mvnrepository.com/artifact/org.casbin/jdbc-adapter)
* database of your choice (in case you want to use JDBC Adapter)

_(versions used for the project -`sbt`)_
```
  "org.casbin" % "jcasbin" % "1.24.0",
  "org.casbin" % "jdbc-adapter" % "2.3.3",
  "org.postgresql" % "postgresql" % "42.5.0"

```

## Configuration
1) __Default__:
```
casbin {
  model = conf/casbin/model.conf
  policy = conf/casbin/policy.csv
  storeType = jdbc
  
  ...
  
}
```
2) __To use model, policy files__:
* Make sure to add your model and policy files in `casbin` directory which is under the `conf` directory.
* Set `storeType = file`

_Note: If model file is not provided then default model will be used_.

3) __To use JDBC Adapter for defining policy__:
* Add your rules in `casbin_rule` table (will be created by default if not present).

Config example:
```
db.default {
  driver = org.someDriver.Driver
  url = "jdbc:example:example:play"
  username = user
  password = password
  
  ...
  
}
```

## Usage

You can use enforcer by doing dependency injection in your controller.

```
@Inject
public PostResourceHandler(... other params, CasbinEnforcer enforcer) {
    ...
    this.enforcer = enforcer;
}
```

Then everything else stays the same.

```
if(enforcer.enforce(role, resource, action)) {
    // some logic
}
```


