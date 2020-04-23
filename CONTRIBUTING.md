# Contributing to se-carrental

## Contribution Flow

We use a feature branch model to contribute to the se-carrental codebase. In general, separate branches should be used for developement of this project.
If the new feature, enhancement or fix is working in your branch, you should create a merge request (= pull request) for merging your branch in the development (dev) branch.
Your pull request can be then reviewed by one or more of the colleagues. This makes it possible to identify errors at an early state. After confirmation, your code is officially accepted into the dev branch. Keep in mind that you should not create a branch off the master branch.

### Steps

1. Update your dev branch with `git pull`
2. Create your feature or fix branch
3. Write code and tests
4. Commit your code and follow the commit message guidelines
5. Push your code to the server
6. Submit a merge request
7. Combine multiple commits into a few logically organized commits by squashing them at the latest within the merge request

#### Feature branch naming
- feature/name-of-the-new-feature
#### Bugfix branch naming
- bugfix/name-of-the-bugfix

#### Commit and merge request message guidelines

- The message must contain at least 3 describing words
- The message must not end with a period
