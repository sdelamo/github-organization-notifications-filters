package groovycalamari.githuborganizationfilters;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Client("https://api.github.com")
@Header(name = HttpHeaders.ACCEPT, value = "application/vnd.github.v3+json")
@Header(name = HttpHeaders.USER_AGENT, value = "micronaut-cli")
public interface GithubClient {

    @Get("/orgs/{org}/repos")
    List<Repository> repos(@NonNull @NotNull @PathVariable String org,
                           @Nullable @QueryValue @Pattern(regexp = "all|public|private|forks|sources|member|internal") String type,
                           @Nullable @QueryValue(value = "per_page") @Max(100) Integer perPage,
                           @Nullable @QueryValue Integer page,
                           @Nullable @QueryValue @Pattern(regexp = "created|updated|pushed|full_name") String sort,
                           @Nullable @QueryValue @Pattern(regexp = "asc|desc") String direction);

    @Get("/orgs/{org}/repos")
    List<Repository> repos(@Header(HttpHeaders.AUTHORIZATION) String authorization,
                           @NonNull @NotNull @PathVariable String org,
                           @Nullable @QueryValue @Pattern(regexp = "all|public|private|forks|sources|member|internal") String type,
                           @Nullable @QueryValue(value = "per_page") @Max(100) Integer perPage,
                           @Nullable @QueryValue Integer page,
                           @Nullable @QueryValue @Pattern(regexp = "created|updated|pushed|full_name") String sort,
                           @Nullable @QueryValue @Pattern(regexp = "asc|desc") String direction);
}
