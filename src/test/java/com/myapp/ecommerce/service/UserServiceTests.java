package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.User;
import com.myapp.ecommerce.repository.UserRepository;
import lombok.extern.log4j.Log4j2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;

import static com.myapp.ecommerce.util.UserCreator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Log4j2
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() throws ParseException {
        BDDMockito.when(userRepository.saveAndReturn(any(User.class))).thenReturn(createUserToBeSaved());
        BDDMockito.when(userRepository.saveAndReturnUpdatedUser(any(User.class), any())).thenReturn(createValidUpdatedUser());
        BDDMockito.when(userRepository.findUserById(any())).thenReturn(createValidUser());
    }


    @Test
    @DisplayName("Success - saves and returns a User")
    void saveAndReturnUser_WhenSuccessful() throws ParseException {
        User created = userService.register(createUserToBeSaved()).getBody();
        assertThat(created.getName()).isSameAs(createUserToBeSaved().getName());
        Assertions.assertThat(created.getUserID()).isEqualTo("123").isNotNull();
        verify(userRepository).saveAndReturn(createUserToBeSaved());
    }

    @Test
    @DisplayName("Success - returns a User using id ")
    void findUserById_ReturnUser_WhenSuccessful() throws ParseException {
        String expectedId = createValidUser().getUserID();
        User user = userService.findUserById(expectedId);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUserID()).isNotNull().isEqualTo(expectedId);
        verify(userRepository).findUserById("123");
    }

    @Test
    @DisplayName("Success - updates and returns a User using id")
    void update_ReturnUser_WhenSuccessful() throws ParseException {

        User user = userService.updateUser( createValidUpdatedUser(), createValidUpdatedUser().getUserID()).getBody();

        Assertions.assertThat(user).isNotNull().isEqualTo(createValidUpdatedUser());
        verify(userRepository).saveAndReturnUpdatedUser(createValidUpdatedUser(), createValidUpdatedUser().getUserID());
    }



    @Test
    @DisplayName("Success - removes a User using id")
    void delete_RemoveUser_WhenSuccessful() throws ParseException {
        Assertions.assertThatCode(() -> userService.deleteUserById("123")).doesNotThrowAnyException();
        verify(userRepository, times(1)).deleteUserById(createValidUser().getUserID());
    }

}
