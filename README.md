# Github Notification Filters for Github Organizations

Command Line application to generate a Github Notifications filter string given a github organization

```bash
% ./gradlew build
```

```
% java -jar build/libs/github-organization-filters-0.1-all.jar -o=micronaut-projects
...
repo:micronaut-projects/static-website repo:micronaut-projects/presentations repo:micronaut-projects/micronaut-core repo:micronaut-projects/micronaut-profiles repo:micronaut-projects/micronaut-guides repo:micronaut-projects/micronaut-examples repo:micronaut-projects/static-website-test repo:micronaut-projects/micronaut-docs repo:micronaut-projects/micronaut-test repo:micronaut-projects/micronaut-kotlin repo:micronaut-projects/micronaut-spring repo:micronaut-projects/micronaut-oauth2 repo:micronaut-projects/micronaut-liquibase repo:micronaut-projects/micronaut-flyway ... 
```

To get private repositories you will need to supply a [Personal Access token](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/creating-a-personal-access-token) with `repo` scope. 

```
java -jar build/libs/github-organization-filters-0.1-all.jar -o=mycompany -t=xxxyyyyzzz