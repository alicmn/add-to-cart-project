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

    public UserDetails(String name, UserTypes userType, LocalDateTime userSince, String... contacts) {
        this.uid = UUID.randomUUID().toString();
        this.name = name;
        this.contacts = Arrays.asList(contacts);
        this.userType = userType;
        this.userSince = userSince;
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
