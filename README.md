## How to use

### Create .clasp.clasp.json.xxxx

```sh
mkdir .clasp

touch .clasp/clasp.json.production
touch .clasp/clasp.json.staging
```

### Write script-id to clasp.json.xxx

```
{
  "scriptId":"your-script-id",
  "rootDir":"dist"
}
```

### Install Leiningen

### Install dependency

``lein deps``

### Change permission to deploy_to

```sh
chmod 744 deploy_to
```

### Build and push

```sh
./deploy_to [production|staging]
```

or

```sh
lein exec view/index.clj >| dist/index.html && lein cljsbuild once main && clasp push
```
