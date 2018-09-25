## How to use

### Create .clasp.json

```
{
  "scriptId":"your-script-id",
  "rootDir":"dist"
}
```

### Install dependency

``lein deps``

### Build and push

``lein exec view/index.clj >| dist/index.html && lein cljsbuild once main && clasp push``
