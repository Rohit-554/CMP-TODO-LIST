# Contributing to CMP-TODO-LIST

First off, thank you for considering contributing to CMP-TODO-LIST! 🎉

This is a Kotlin Multiplatform project that aims to demonstrate the power of building cross-platform mobile applications. We welcome contributions from everyone, whether you're fixing a typo, adding a feature, or improving documentation.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
  - [Reporting Bugs](#reporting-bugs)
  - [Suggesting Features](#suggesting-features)
  - [Contributing Code](#contributing-code)
- [Development Setup](#development-setup)
- [Code Style Guidelines](#code-style-guidelines)
- [Commit Message Format](#commit-message-format)
- [Pull Request Process](#pull-request-process)
- [Testing](#testing)

## Code of Conduct

This project adheres to a code of conduct that all contributors are expected to uphold. Please be respectful, inclusive, and considerate in all interactions.

## How Can I Contribute?

### Reporting Bugs

Before creating a bug report, please check the [existing issues](https://github.com/Rohit-554/CMP-TODO-LIST/issues) to avoid duplicates.

When filing a bug report, please include:
- **Clear title and description**: Explain what happened vs. what you expected
- **Steps to reproduce**: Detailed steps to reproduce the issue
- **Environment**: Android/iOS version, device model, app version
- **Screenshots or videos**: If applicable
- **Error logs**: Any relevant crash logs or error messages

### Suggesting Features

We're always open to new ideas! When suggesting a feature:
- Check if it's already been suggested in [issues](https://github.com/Rohit-554/CMP-TODO-LIST/issues)
- Explain the problem your feature would solve
- Describe how you envision the feature working
- Consider how it fits with the project's goals

### Contributing Code

1. **Fork the repository** and create your branch from `TodoApp`
2. **Make your changes** following our code style guidelines
3. **Test your changes** on both Android and iOS if possible
4. **Commit your changes** with clear commit messages
5. **Push to your fork** and submit a pull request

## Development Setup

### Prerequisites

- **Android Studio**: Latest stable version
- **Xcode**: Latest version (for iOS development, macOS only)
- **JDK**: Version 17 or higher
- **Kotlin Multiplatform Mobile (KMM) plugin**: Install from Android Studio plugins

### Getting Started

1. **Clone your fork:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/CMP-TODO-LIST.git
   cd CMP-TODO-LIST
   ```

2. **Open in Android Studio:**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory
   - Wait for Gradle sync to complete

3. **Run on Android:**
   - Select the `androidApp` configuration
   - Click the "Run" button (green triangle)
   - Choose an emulator or connected device

4. **Run on iOS (macOS only):**
   - Option 1: Use the KMM plugin in Android Studio
   - Option 2: Open the `iosApp` folder in Xcode and run from there

### Project Structure

```
CMP-TODO-LIST/
├── androidApp/          # Android-specific code
├── composeApp/          # Shared Compose Multiplatform UI
├── iosApp/              # iOS-specific code
├── gradle/              # Gradle wrapper files
└── README.md
```

## Code Style Guidelines

### Kotlin

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Keep functions small and focused (ideally under 20 lines)
- Add comments for complex logic

### Compose

- Use `@Composable` functions for UI components
- Prefer stateless composables where possible
- Hoist state to parent composables when needed
- Use descriptive names for composable functions (e.g., `TaskListScreen`, `AddTaskButton`)

### Formatting

- **Indentation**: 4 spaces (no tabs)
- **Line length**: Maximum 120 characters
- **Imports**: Remove unused imports, organize alphabetically
- Use Android Studio's built-in formatter (Ctrl+Alt+L / Cmd+Opt+L)

### Example

```kotlin
@Composable
fun TaskItem(
    task: Task,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task) },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = task.description,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
```

## Commit Message Format

We follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>: <subject>

[optional body]

[optional footer]
```

### Types

- **feat**: A new feature
- **fix**: A bug fix
- **docs**: Documentation changes
- **style**: Code style changes (formatting, no logic change)
- **refactor**: Code refactoring
- **test**: Adding or updating tests
- **chore**: Build process, dependency updates

### Examples

```
feat: add dark mode support to settings screen

Implemented dark mode toggle in settings with shared preferences
to persist user choice across app restarts.

Closes #42
```

```
fix: prevent crash when deleting completed tasks

Added null check before accessing task list to prevent
IndexOutOfBoundsException.

Fixes #67
```

```
docs: update installation instructions in README
```

## Pull Request Process

1. **Update documentation**: If you're adding a feature, update the README or relevant docs
2. **Test thoroughly**: Ensure your changes work on both Android and iOS
3. **Keep PRs focused**: One feature/fix per PR makes review easier
4. **Link issues**: Reference related issues using `Fixes #123` or `Closes #456`
5. **Respond to feedback**: Address review comments promptly

### PR Checklist

Before submitting, ensure:
- [ ] Code follows the style guidelines
- [ ] Changes have been tested on Android
- [ ] Changes have been tested on iOS (if applicable)
- [ ] Commit messages follow the format
- [ ] Documentation has been updated
- [ ] No new warnings or errors introduced

### PR Template

When creating a PR, please include:

```markdown
## Description
Brief description of your changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Code refactoring

## Testing
How have you tested this?
- [ ] Android Emulator
- [ ] Android Device
- [ ] iOS Simulator
- [ ] iOS Device

## Screenshots (if applicable)
Add screenshots showing the changes

## Related Issues
Fixes #(issue number)
```

## Testing

### Manual Testing

- **Android**: Test on at least one emulator and one physical device if possible
- **iOS**: Test on simulator and real device (if available)
- **Different scenarios**: Test edge cases, error states, and various data inputs

### Future: Automated Testing

We plan to add automated tests soon. Contributions to testing infrastructure are especially welcome!

## Questions?

If you have questions that aren't covered here:
- Check the [README](README.md)
- Look through [existing issues](https://github.com/Rohit-554/CMP-TODO-LIST/issues)
- Open a new issue with the `question` label

---

Thank you for contributing! 🚀

Made with ❤️ by the CMP-TODO-LIST community
