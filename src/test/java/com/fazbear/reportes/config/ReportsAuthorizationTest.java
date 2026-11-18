package com.fazbear.reportes.config;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;

class ReportsAuthorizationTest {
    private final SecurityConfig config = new SecurityConfig("Reports.Test");

    @Test
    void requiresTheConfiguredRoleAndDelegatedScope() {
        assertThat(config.canReadReports(jwt(List.of("Reports.Test"), "SCOPE_access_as_user"))).isTrue();
        assertThat(config.canReadReports(jwt(List.of("Other.Role"), "SCOPE_access_as_user"))).isFalse();
        assertThat(config.canReadReports(jwt(List.of("Reports.Test"), "SCOPE_other"))).isFalse();
        assertThat(config.canReadReports(jwt("Reports.Test", "SCOPE_access_as_user"))).isFalse();
        assertThat(new SecurityConfig("").canReadReports(jwt(List.of("Reports.Test"), "SCOPE_access_as_user"))).isFalse();
    }

    private JwtAuthenticationToken jwt(Object roles, String authority) {
        Jwt jwt = Jwt.withTokenValue("test").header("alg", "RS256").claim("roles", roles).build();
        return new JwtAuthenticationToken(jwt, List.of(new SimpleGrantedAuthority(authority)));
    }
}
