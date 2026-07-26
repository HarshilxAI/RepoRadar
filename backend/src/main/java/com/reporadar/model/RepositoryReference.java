package com.reporadar.model;

public record RepositoryReference(String owner, String name) {
    public String fullName() { return owner + "/" + name; }
}

