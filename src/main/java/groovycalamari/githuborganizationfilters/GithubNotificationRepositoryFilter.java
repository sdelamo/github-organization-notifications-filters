package groovycalamari.githuborganizationfilters;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class GithubNotificationRepositoryFilter {

    @NonNull
    @NotBlank
    private String org;

    @NonNull
    @NotBlank
    private String name;

    public GithubNotificationRepositoryFilter() {
    }

    public GithubNotificationRepositoryFilter(@NonNull @NotBlank String org,
                                              @NonNull @NotBlank String name) {
        this.org = org;
        this.name = name;
    }

    @NonNull
    public String getOrg() {
        return org;
    }

    public void setOrg(@NonNull String org) {
        this.org = org;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "repo:"+ org + '/' + name;
    }
}
