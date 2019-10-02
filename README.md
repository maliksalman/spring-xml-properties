# Spring framework web-app to demonstrate properties loading behavior

The app tries to demonstrate the precedence of sources from which properties are loaded in a spring-framework app (tested on version `5.1.5`). The order might be a bit different on the spring-boot app. Another problem it tries to show is the effects of using a `org.springframework.beans.factory.config.PropertyPlaceholderConfigurer` bean in a context XML file directly instead of relying on `<context:property-placeholder />` - the later will consult all the `PropertySource` objects configured in the spring `Environment`. 

## The setup 

To demonstrate the precedence and placeholder issues with the properties, six properties have been setup at various levels. They are configured like so:

| Precedence Order | Source | Properties Defined | Properties Value |
| ---------------- | ------ | ----- | ------ |
| 1 | `ServletConfig` init parameters (`<servlet><init-param>` in your `web.xml` |  |   |
| 2 | `ServletContext` parameters (`<context-param>` in your `web.xml` | `A` | `servlet` |
| 3 | JNDI properties |  |   |
| 4 | Java `System` properties, (`-D` switches or `System.setProperty()`) | `A,B` | `system` |
| 4 | system ENV variables | `A,B,C` | `env` |
| 5 | custom `PropertySource` (like config-server, see `com.smalik.config.MyEnvironment` class) | `A,B,C,D` | `custom` |
| 6 | Placeholder mechanism (see `some.properties`) | `A,B,C,D,E` | `file` |
 

There are two ways of running the WAR in tomcat (pick one):

## 1. Using `<context:property-placeholder ...` bean

### JVM `-D` switches

| Parameter | value |
| ------- | ----- |
| `message.a` | `system` | 
| `message.b` | `system` | 

### Environment Variables

| Variable | value |
| ------- | ----- |
| `MESSAGE_A` | `env` | 
| `MESSAGE_B` | `env` | 
| `MESSAGE_C` | `env` | 

## 2. Using `PropertyPlaceholderConfigurer` bean explicitly

### JVM `-D` switches

| Parameter | value |
| ------- | ----- |
| `message.a` | `system` | 
| `message.b` | `system` | 
| `spring.profiles.active` | `old` | 

### Environment Variables

| Variable | value |
| ------- | ----- |
| `MESSAGE_A` | `env` | 
| `MESSAGE_B` | `env` | 
| `MESSAGE_C` | `env` | 

## See the result

After the app is running in tomcat, use `http://localhost:8080/{CONTEXT-ROOT}/` to see the results:

### When using `<context:property-placeholder ...` bean

```xml
{
  "environment": {
    "messageA": "servlet",
    "messageB": "system",
    "messageC": "env",
    "messageD": "custom",
    "messageE": "java-default",
    "messageF": "java-default"
  },
  "placeholder": {
    "messageA": "servlet",
    "messageB": "system",
    "messageC": "env",
    "messageD": "custom",
    "messageE": "file",
    "messageF": "xml-default"
  }
}
```

### When `PropertyPlaceholderConfigurer` bean explicitly

```xml
{
  "environment": {
    "messageA": "servlet",
    "messageB": "system",
    "messageC": "env",
    "messageD": "custom",
    "messageE": "java-default",
    "messageF": "java-default"
  },
  "placeholder": {
    "messageA": "system",
    "messageB": "system",
    "messageC": "file",
    "messageD": "file",
    "messageE": "file",
    "messageF": "xml-default"
  }
}
```