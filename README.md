Asset revision resolve support for Pebble template.

This plugin surpose you already have asset revision mapping file such as

```json

{
  "js/a.js" : "js/a.1a2b3c4d5e6f.js",
  "css/main.css" :  "css/main.7f8e9d0c.css"
}

```
Currently this plugin not support generating such revision file, you may checkout professional front-end solution such as Gulp and its pugin like https://github.com/sindresorhus/gulp-rev.

## How to use

### Setup and register this extension

First you need to setup and register this extension to Pebble engine.

```
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

This asset support three tags : `javascriptInclude` `cssInclude` `imageInclude`

```
{% javascriptInclude 'js/a.js' %}
{% cssInclude 'css/main.css' %}
{% imageInclude 'image/a.png' %}

```

will output as

```html
<script src="xxxxxxxxx/js/a.2a3b23898bef.js" ></script>
<link src="xxxx/css/main.687bdf87.css" ></link>
<img src="xxxx/images/a.378374abef2.png" ></img>
```

### Advantage Usage

When use tags, all tags support passing asset list, for example you can use

```
{% javascriptInclude ['js/a.js', 'js/b.js'] %}
```

And all tags support custom properties, you can use it to set html element's attributes; just use `props` syntax

```
{% javascriptInclude 'js/a.js' props {'async':'true'} %}
```

will output as

```html
<script src="xxxxxxxxx/js/a.2a3b23898bef.js" async ></script>
```

And this syntax also support asset list.

## Advanced usage

For example you can set different host for development and production environment.

```java
PebbleAssetExtensionBuilder.assetsHost("http://assets.example.com")
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

