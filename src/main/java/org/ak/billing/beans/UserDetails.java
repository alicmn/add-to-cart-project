package org.ak.billing.beans;

import org.ak.billing.constants.UserTypes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//Immutable
public final class UserDetails {
    private final String uid;
    private final String name;
    private final List<String> contacts;
    private final UserTypes userType;
    private final LocalDateTime userSince;

    private UserDetails(Builder builder) {
        this.uid = builder.uid != null ? builder.uid : UUID.randomUUID().toString();
        this.name = builder.name;
        this.contacts = builder.contacts;
        this.userType = builder.userType;
        this.userSince = builder.userSince;
    }

    public static class Builder {
        private String uid;
        private String name;
        private List<String> contacts;
        private UserTypes userType;
        private LocalDateTime userSince;

        public Builder uid(String uid) {
            this.uid = uid;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder contacts(String... contacts) {
            this.contacts = Arrays.asList(contacts);
            return this;
        }

        public Builder userType(UserTypes userType) {
            this.userType = userType;
            return this;
        }

        public Builder userSince(LocalDateTime userSince) {
            this.userSince = userSince;
            return this;
        }

        public UserDetails build() {
            return new UserDetails(this);
        }
    }

    public UserTypes getUserType() {
        return userType;
    }

    public LocalDateTime getUserSince() {
        return userSince;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public List<String> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                ", userType=" + userType +
                ", userSince=" + userSince +
                '}';
    }
}
