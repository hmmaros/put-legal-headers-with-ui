# AGENTS.md

Guidelines for AI agents and collaborators working on **PutLegalHeaders** (GUI).

## Project at a glance

- **Windows-only** Java 8 Swing desktop app built with Maven. `json-simple` is
  declared in `pom.xml` but is **not used**.
- The GUI searches a selected folder for files matching given suffixes
  (`*.ts;*.html`) that contain or lack a marker text, then prepends a legal
  header (from `inputText.txt`) to each chosen file.
- Discovery uses `cmd.exe` (`@for /r %f in (...) do @find ...`); header insertion
  rewrites each file through a `tempFile.txt` buffer.

## Architecture & conventions

| Directory | Responsibility |
| --- | --- |
| `src/main/java/main` | Entry point (`Main`) — launches `MainFrame`. |
| `src/main/java/graphics` | **All** Swing UI. A `TextAreaOutputStream` re-routes `System.out`/`System.err` into the results console. |
| `src/main/java/controller` | `Functions` — file discovery + header insertion. |
| `src/main/java/model` | `TextModel` (search text, suffix, WITH/WITHOUT) and `PathsModel` (selected folder). |
| `src/main/java/finals` | `Finals` (constants) and `Texts` (user-facing labels/messages). |
| `src/main/java/logger` | Logging setup. |

Hard rules contributors must follow:

1. **No new third-party dependencies.** Java 8 / Swing / Maven only; the
   `json-simple` dependency should be removed, not extended.
2. **No UI literals.** Labels and messages go in `finals.Texts`; layout
   constants go in `finals.Finals`.
3. **Enforce the layering.** UI code must not contain file logic; `controller`
   must not contain Swing layout.
4. **Windows-aware.** Discovery depends on `cmd.exe` syntax. Only replace it with
   a portable alternative (`java.nio.file.Files.walk`) as part of a deliberate
   cross-platform effort.
5. **Preserve UTF-8** encoding on every read/write.
6. **Console redirect caveat.** `System.out` is re-routed to the results window
   by `OutputFrame`; be careful logging long output or adding new `println`s.

## Code style

- Java 8 syntax.
- `java.util.logging` for diagnostics; `System.out` for user-facing console text.
- Comment markers are suffix-driven (`Finals`: HTML `<!-- -->`, ADOC `////`,
  common `/* */`).

## Known weak spots (candidates for improvement)

- Discovery is `cmd.exe`-dependent → not portable. Prefer `Files.walk`.
- `json-simple` is an unused dependency — remove it.
- Log file is named `%T/ReWarMe.log` (copied from another project) — rename it.
- `tempFile.txt` rewrite flow: consider in-memory rewrite.
- No dry-run or undo; "Add Text to Files" modifies files immediately.
- Global `System.setOut/setErr` re-routing can swallow output after the results
  window closes.

## Assets

- `assets/header.svg` and `assets/mockup.svg` are README showcase graphics.
  Keep them in sync with the README; do not remove without updating it.

## How to run

```bash
mvn clean package
java -jar target/PutLegalHeaders-1.0-jar-with-dependencies.jar
```

> Requires a Windows shell for the discovery command. Do not test against
> production/source-of-truth folders without a backup.

## Workflow

- Work on `main` with small, clearly-messaged commits.
- When asked to "optimize/update", prefer behavior-preserving refactors and
  confirm before touching the file-modification logic.
- Update `README.md` and `AGENTS.md` whenever structure or conventions change.