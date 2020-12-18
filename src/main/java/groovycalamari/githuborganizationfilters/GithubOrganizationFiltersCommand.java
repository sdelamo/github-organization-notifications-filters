package groovycalamari.githuborganizationfilters;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.uri.UriBuilder;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (CollectionUtils.isNotEmpty(repositoryList)) {
            output(org, repositoryList);
        } else {
            System.out.println("Could not retrieve a list of github repositories for organization " + org);
        }
    }

    private void output( @NonNull String org, @NonNull List<Repository> repositoryList) {
        List<String> filters = repositoryList.stream()
                .map(repo -> new GithubNotificationRepositoryFilter(org, repo.getName()))
                .map(GithubNotificationRepositoryFilter::toString)
                .collect(Collectors.toList());
        output(filters);
        System.out.println("Github Notifications URL with every repository in the  organization " + org);
        System.out.println(UriBuilder.of("https://github.com/notifications").queryParam("query", String.join(" ", filters)).build().toString());
    }

    private void output( @NonNull List<String> filters) {
        StringBuilder sb = new StringBuilder();
        boolean requiredSplit = false;
        for (String filter : filters) {
            if ((sb.length() + filter.length()) > 1000) {
                if (!requiredSplit) {
                    System.out.println("Github filters have a maxium length of 1000 characters. The output is split in blocks:");
                    requiredSplit = true;
                }
                output(sb);
                sb = new StringBuilder();
            }
            sb.append(filter);
            sb.append(" ");
        }
        output(sb);
    }

    private void output(StringBuilder sb) {
        System.out.println(sb.toString());
        System.out.println("\n");
    }
}
