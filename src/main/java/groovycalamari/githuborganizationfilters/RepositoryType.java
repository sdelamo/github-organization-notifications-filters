package groovycalamari.githuborganizationfilters;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RepositoryType {
    ALL("all"),
    PUBLIC("public"),
    PRIVATE("private"),
    FORKS("forks"),
    SOURCES("sources"),
    MEMBER("member"),
    INTERNAL("internal");

    private final String type;
    RepositoryType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
