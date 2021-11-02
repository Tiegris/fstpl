# Fstp

## Functions

```text
~loop_COLLECTION_call_CALLNAME
~if_BOOL_FROM_MODEL_call_CALLNAME
~rename_call_CALLNAME
```

## Resolve files

Start with double tilde:

```text
~~loop_COLLECTION_call_CALLNAME
~~if_BOOL_FROM_MODEL_call_CALLNAME
~~rename_call_CALLNAME
~~FILENAME
```

## Call

CALLNAME can be a constant string or an interpolated string.

Use bash env var syntax:

```text
_call_$name
_call_${name}
_call_prefix${name}.txt
```

If the name is "demo" in the model, then the previous outputs these files:

```text
demo
demo
prefixdemo.txt
```
