# JSON Output Event Schema

The CLI can report a conversion run as JSON so scripts and CI jobs can read it
without parsing human-readable text. Turn it on with `--json`:

```console
s2c --json \
  -o ./output \
  -p com.app.icons \
  -t com.app.theme.AppTheme \
  ./icons
```

## Line format

Output is JSONL: one JSON object per line, written to standard output. Each
line is complete on its own, so you can process the stream as it arrives.

Every object has an `event` field that says which event it is. The other
fields depend on the event.

`--json` turns the dashboard off, and it also silences the regular log lines,
so standard output contains nothing but JSON. Warnings about a failed log write
are suppressed for the same reason.

Fields that are empty are left out of the line instead of being sent as `null`.
Today this only affects `error_code` and `message` on `file_complete`, which are
present only when a file failed.

## Event order

1. `start`, once, before any file is processed.
2. For each file, in order: `file_start`, one `file_step` per phase, then
   `file_complete`.
3. `done`, once, after the last file.
4. `error_report`, after `done`, only when at least one file failed.
5. `update_available`, after `done`, only when a newer release was found.

Files are processed one at a time by default. With `--parallel`, several files
are converted at the same time, so the events of different files can be
interleaved. Use the `file` field to group them.

## Events

### `start`

Sent once when the run begins.

| Field         | Type   | Description                                  |
|---------------|--------|----------------------------------------------|
| `event`       | string | Always `start`.                              |
| `version`     | string | Version of the tool that is running.         |
| `input`       | string | Input path, as passed on the command line.   |
| `output`      | string | Output path, as passed on the command line.  |
| `total_files` | number | Number of files the run will process.        |

```json
{"event":"start","version":"3.0.0","input":"/icons","output":"/output","total_files":2}
```

### `file_start`

Sent when a file starts converting.

| Field   | Type   | Description                                     |
|---------|--------|-------------------------------------------------|
| `event` | string | Always `file_start`.                            |
| `file`  | string | File name, without the directory.               |
| `index` | number | Position of the file in the run, starting at 0. |

```json
{"event":"file_start","file":"first.svg","index":0}
```

### `file_step`

Sent when a file moves to the next phase of the conversion.

| Field   | Type   | Description                                                       |
|---------|--------|-------------------------------------------------------------------|
| `event` | string | Always `file_step`.                                               |
| `file`  | string | File name, without the directory.                                 |
| `step`  | string | `optimizing`, `parsing`, `generating`, or `writing`.              |

`optimizing` is only sent when optimization is on. It is on by default and is
turned off with `-opt false`.

```json
{"event":"file_step","file":"first.svg","step":"parsing"}
```

### `file_complete`

Sent when a file finishes, whether it worked or not.

| Field         | Type   | Description                                            |
|---------------|--------|--------------------------------------------------------|
| `event`       | string | Always `file_complete`.                                |
| `file`        | string | File name, without the directory.                      |
| `status`      | string | `success` or `failed`.                                 |
| `duration_ms` | number | How long the file took, in milliseconds.               |
| `error_code`  | string | Error code name. Only present when `status` is `failed`. |
| `message`     | string | Error message. Only present when `status` is `failed`. |

`error_code` is the name of an internal error code. The values you are most
likely to see are `FileNotFoundError`, `NotSupportedFileError`, `ParseSvgError`,
`SvgoOptimizationError`, `AvocadoOptimizationError`, `TemplateEmissionError`,
and `FailedToParseIconError`.

```json
{"event":"file_complete","file":"first.svg","status":"success","duration_ms":2}
{"event":"file_complete","file":"second.svg","status":"failed","duration_ms":0,"error_code":"FailedToParseIconError","message":"not-a-number is not a Number"}
```

### `done`

Sent once when the run ends.

| Field         | Type   | Description                                          |
|---------------|--------|------------------------------------------------------|
| `event`       | string | Always `done`.                                       |
| `succeeded`   | number | Number of files that converted.                      |
| `failed`      | number | Number of files that failed.                         |
| `duration_ms` | number | Wall clock time of the whole run, in milliseconds.   |
| `throughput`  | number | Converted files per second. `0.0` for runs under 1 ms. |

```json
{"event":"done","succeeded":1,"failed":1,"duration_ms":2,"throughput":500.0}
```

### `error_report`

Sent after `done` when at least one file failed and the report file was
written. The report holds the run configuration and the failures, and it does
not contain any SVG content.

| Field            | Type   | Description                                                   |
|------------------|--------|---------------------------------------------------------------|
| `event`          | string | Always `error_report`.                                        |
| `report_file`    | string | Path of the saved report, relative to the working directory.  |
| `bug_report_url` | string | Pre-filled GitHub issue URL for reporting the failure.        |

```json
{"event":"error_report","report_file":"s2c-errors-2026-08-16T16-39-45.log","bug_report_url":"https://github.com/rafaeltonholo/svg-to-compose/issues/new?template=bug_report.yml&title=..."}
```

### `update_available`

Sent after `done` when the update check finds a newer release. The release
lookup is not wired to a live source yet, so current builds do not send this
event.

| Field     | Type   | Description                           |
|-----------|--------|---------------------------------------|
| `event`   | string | Always `update_available`.            |
| `current` | string | Version that is running.              |
| `latest`  | string | Newest version that was found.        |
| `url`     | string | Link to the release page.             |

## Full example

```console
$ s2c --json -o ./output -p com.app.icons -t com.app.theme.AppTheme ./icons
{"event":"start","version":"3.0.0","input":"./icons","output":"./output","total_files":2}
{"event":"file_start","file":"first.svg","index":0}
{"event":"file_step","file":"first.svg","step":"parsing"}
{"event":"file_step","file":"first.svg","step":"generating"}
{"event":"file_step","file":"first.svg","step":"writing"}
{"event":"file_complete","file":"first.svg","status":"success","duration_ms":2}
{"event":"file_start","file":"second.svg","index":1}
{"event":"file_step","file":"second.svg","step":"parsing"}
{"event":"file_step","file":"second.svg","step":"generating"}
{"event":"file_step","file":"second.svg","step":"writing"}
{"event":"file_complete","file":"second.svg","status":"success","duration_ms":2}
{"event":"done","succeeded":2,"failed":0,"duration_ms":4,"throughput":500.0}
```

## Reading the stream

List the files that failed:

```console
s2c --json <args> ./icons \
  | jq -r 'select(.event == "file_complete" and .status == "failed") | .file'
```

Print the final counters:

```console
s2c --json <args> ./icons | jq -c 'select(.event == "done")'
```

Group failures by error code:

```console
s2c --json <args> ./icons \
  | jq -r 'select(.event == "file_complete" and .status == "failed") | .error_code' \
  | sort | uniq -c
```

## Exit status

The exit status does not depend on the output mode. The CLI exits with a
non-zero status when any file fails, so a CI job can check the status and use
the JSON stream only for the details.

## Other output modes

`--json` cannot be used together with `--silent`. The CLI stops with a usage
error when both are set.

See the [command-line tool documentation](../svg-to-compose/README.md) for the
dashboard, the plain text mode, and `--silent`.
