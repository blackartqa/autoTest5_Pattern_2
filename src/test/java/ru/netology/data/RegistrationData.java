package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor


public class RegistrationData {
    private final String login;
    private final String pass;
    private final String status;
}
