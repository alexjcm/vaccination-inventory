package com.superapp.demo.validator;

import com.superapp.demo.exceptions.Apiunauthorized;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Component
public class AuthValidator {

    private static final String ClIENT_CREDENTIALS = "client_credentials";

    public void validate(MultiValueMap<String, String> paramMap, String grantType) throws Apiunauthorized {
        if (grantType.isEmpty() || !grantType.equals(ClIENT_CREDENTIALS)) {
            message("La variable grant_type es invalida, por favor revisar.");
        }
        if (Objects.isNull(paramMap) || paramMap.getFirst("client_id").isEmpty() || paramMap.getFirst("client_secret").isEmpty()) {
            message("Las variables client_id y o client_secret son invalidas, por favor revisar");
        }
    }

    private void message(String message) throws Apiunauthorized {
        throw new Apiunauthorized(message);
    }
}
