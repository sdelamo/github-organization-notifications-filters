package groovycalamari.githuborganizationfilters;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Command(name = "github-organization-filters", description = "...",
        mixinStandardHelpOptions = true)
public class GithubOrganizationFiltersCommand implements Runnable {

    private static final String REPO = "repo:";
    private static final String SLASH = "/";
    private static final String TOKEN = "token ";

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    @Option(names = {"-o", "-org"}, required = true, description = "Github organization name")
    String org;

    @Option(names = {"-t"}, description = "Github Personal Access Token. Token must have repo scope. see https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/creating-a-personal-access-token")
    String token;

    @Option(names = {"-n"}, description = "number of repositories per page", defaultValue = "100")
    Integer perPage;

    @Inject
    public GithubClient githubClient;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(GithubOrganizationFiltersCommand.class, args);
    }

    public void run() {
        if (token != null && verbose) {
            System.out.println("Authenticating with token");
        }

        List<Repository> repositoryList = token != null ? githubClient.repos(TOKEN + token, org, RepositoryType.ALL.toString(), perPage, null, null, null) : githubClient.repos(org, RepositoryType.ALL.toString(), perPage, null, null, null);

        if (verbose) {
            System.out.println("Github notification filter for Github organization " + org + " :\n");
        }
        System.out.println(repositoryList.stream().map(repo -> REPO + org + SLASH + repo.getName()).collect(Collectors.joining(" ")));

    }
}
