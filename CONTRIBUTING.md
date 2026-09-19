# Contributing to PutLegalHeaders (GUI)

Thanks for your interest! This is a legacy project kept for posterity, but
issues and pull requests are welcome.

## Before you start

Read [`AGENTS.md`](AGENTS.md) — it documents this codebase's architecture and
hard rules. In short:

- UI in `graphics`, logic in `controller`, state in `model`, strings in
  `Texts`/constants in `Finals`.
- No new third-party dependencies.
- Match the existing Java 8 style.

## Build & verify

```bash
mvn clean package
```

The [Build workflow](.github/workflows/build.yml) compiles the project on every
push and pull request.

## Workflow

1. Fork the repo and create a branch: `git checkout -b feature/your-feature`.
2. Make your change and confirm it builds.
3. Commit with a clear message, push, and open a pull request.

## License

Released under the [MIT License](LICENSE) © 2018 hmmaros. By contributing, you
agree your changes are distributed under the same license.
