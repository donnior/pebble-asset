Pebble template extension with asset revision resolve support.

This extension surpose you already have asset revision mapping file such as

```json

{
  "js/a.js" : "js/a.1a2b3c4d5e6f.js",
  "css/main.css" :  "css/main.7f8e9d0c.css"
}

```
Currently this extension not support generating such revision file, you may checkout professional front-end solution such as Gulp and its pugin like https://github.com/sindresorhus/gulp-rev.

## How to use

### Setup and register this extension

First you need to setup and register this extension to Pebble engine.

```java
PebbleEngine.Builder builder = new PebbleEngine.Builder();
PebbleAssetExtensionBuilder extensionBuilder = new PebbleAssetExtensionBuilder();
PebbleAssetExtension extension = 
    extensionBuilder.assetsHost("http://assets.example.com")
                    .basePath("static")
                    .revFile("rev-mapping.json")
                    .build();
builder.extension(extension)
```

You can set `assetsHost`,`basePath` and `revFile`, only `revFile` is required.


After setup you can use it in your view page with the following two ways:

### Use `asset` function

This function just resolve one asset file's path as string value.

```html
<script src="{{ asset('js/a.js') }}"></script>
```

may generate output as

```html
<script src="http://assets.example.com/static/js/a.11aa22bb.js" ></script>
```

### Use Tags

This extension support three tags : `javascriptInclude` `cssInclude` `imageInclude`

```php
{% javascriptInclude 'js/a.js' %}
{% cssInclude 'css/main.css' %}
{% imageInclude 'image/a.png' %}

```

will output as

```html
<script src="http://assets.example.com/static/js/a.2a3b23898bef.js" ></script>
<link src="http://assets.example.com/static/css/main.687bdf87.css" ></link>
<img src="http://assets.example.com/static/images/a.378374abef2.png" ></img>
```

### Extra

When use tags, all tags support passing assets array, for example you can use

```php
{% javascriptInclude ['js/a.js', 'js/b.js'] %}
```

will output as

```html
<script src="http://assets.example.com/static/js/a.11aa22bb.js" ></script>
<script src="http://assets.example.com/static/js/b.33cc44ee.js" ></script>

```

And all tags support custom properties, you can use it to set html element's attributes; just use `props` syntax

```php
{% javascriptInclude 'js/a.js' props {'async':true} %}
```

will output as

```html
<script src="xxxxxxxxx/js/a.2a3b23898bef.js" async ></script>
```

And this syntax also support assets array.

```php
{% javascriptInclude ['js/a.js', 'js/b.js'] props {'async':true} %}
```

## Advanced usage

For example you can set different host for development and production environment.

```java
PebbleAssetExtensionBuilder.assetsHost(isProd ? "http://assets.example.com" : "")
                    .basePath("static")
                    .revFile("rev-mapping.json")
                    .build();

<script src="{{ asset('js/a.js') }}"></script>
```

may generate 

```html
<!-- production env -->
<script src="http://assets.example.com/static/js/a.1fbe2ad.js" ></script>

<!-- dev env -->
<script src="/static/js/a.1fbe2ad.js" ></script>

```

