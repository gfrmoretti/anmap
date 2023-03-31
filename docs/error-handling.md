# Error Handling

When the AnMap fails to convert any field, the AnMap will set the field with error with a `null` value.

The map will return an empty Optional only if the lib can not construct the object or if you try to map a
null value.

## Logging

The lib uses logback to control the logs.

In DEBUG mode the library will specify any problem that can occurs.