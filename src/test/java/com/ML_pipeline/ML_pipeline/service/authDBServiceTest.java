package com.ML_pipeline.ML_pipeline.service;

import com.ML_pipeline.ML_pipeline.dto.AuthResponse;
import com.ML_pipeline.ML_pipeline.dto.change_password_DTO;
import com.ML_pipeline.ML_pipeline.model.RefreshToken;
import com.ML_pipeline.ML_pipeline.model.User;
import com.ML_pipeline.ML_pipeline.repository.RefreshTokenRepo;
import com.ML_pipeline.ML_pipeline.repository.authDB_repo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class authDBServiceTest {

    @Mock
    private authDB_repo rdr;

    @Mock
    private RefreshTokenRepo rtr;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private BCryptPasswordEncoder enc;

    @InjectMocks
    private authDB_service_impl rds;

    @Mock
    private JWTService jwts;

    @Mock
    AuthenticationManager am;

    @Mock
    private RefreshTokenServiceImpl rts;

    @Test
    void test_add_user_encodes_password_and_saves_user() {
        // Arrange
        User user = new User();
        user.setUser_name("test_user");
        user.setPassword("12345");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        when(encoder.encode(anyString())).thenReturn("encoded_12345");
        when(encoder.matches(eq("12345"), eq("encoded_12345"))).thenReturn(true);

        // Act
        user.setPassword(encoder.encode(user.getPassword()));
        rdr.save(user);

        // Assert
        verify(rdr, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        // ✅ Corrected line: assert on encoded password
        assertTrue(encoder.matches("12345", capturedUser.getPassword()));
    }

    @Test
    void test_verify_returns_token_when_authenticated() {
        // Arrange
        User user = new User();
        user.setUser_name("testuser");
        user.setPassword("testpass");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(am.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuth);
        when(jwts.generateToken("testuser")).thenReturn("fake-jwt-token");

        RefreshToken fakeRefresh = new RefreshToken();
        fakeRefresh.setToken("fake-refresh-token");
        when(rts.createRefreshToken("testuser")).thenReturn(fakeRefresh);

        // Act
        AuthResponse result = rds.verify(user);

        // Assert
        assertEquals("fake-jwt-token", result.getAccessToken());
        assertEquals("fake-refresh-token", result.getRefreshToken());
        verify(am).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwts).generateToken("testuser");
        verify(rts).createRefreshToken("testuser");
    }

    @Test
    void test_verify_returns_failed_when_not_authenticated() {
        // Arrange
        User user = new User();
        user.setUser_name("testuser");
        user.setPassword("wrongpass");

        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.isAuthenticated()).thenReturn(false);
        when(am.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuth);

        // Act
        AuthResponse result = rds.verify(user);

        // Assert
        assertEquals("Failed", result.getAccessToken());
        assertNull(result.getRefreshToken());
        verify(am).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwts, never()).generateToken(anyString());
    }

    @Test
    void test_editUserPw_success() {
        // Arrange
        User user = new User();
        user.setUser_name("testuser");
        user.setPassword("hashedOldPw");

        change_password_DTO dto = new change_password_DTO();
        dto.setUsername("testuser");
        dto.setOld_pass("oldPw");
        dto.setNew_pass("newPw");

        // Mock repository
        when(rdr.findByUsername("testuser")).thenReturn(user);

        // Mock encoder behavior
        when(enc.matches("oldPw", "hashedOldPw")).thenReturn(true); // simulate password match
        when(enc.encode("newPw")).thenReturn("hashedNewPw");         // simulate new password encoding

        // Act
        rds.edit_user_pw(dto);

        // Assert
        assertEquals("hashedNewPw", user.getPassword()); // user password updated
        verify(rdr).save(user);                          // repository save called
    }

    @Test
    void test_editUserPw_failure_wrongOldPassword() {
        // Arrange
        User user = new User();
        user.setUser_name("testuser");
        user.setPassword("hashedOldPw");

        change_password_DTO dto = new change_password_DTO();
        dto.setUsername("testuser");
        dto.setOld_pass("wrongPw");
        dto.setNew_pass("newPw");

        when(rdr.findByUsername("testuser")).thenReturn(user);

        // Act
        rds.edit_user_pw(dto);

        // Assert
        assertEquals("hashedOldPw", user.getPassword()); // password unchanged
        verify(rdr, never()).save(any());                 // save not called
    }
}