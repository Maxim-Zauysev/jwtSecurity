package com.example.springjwtauthexample.mapper;

import com.example.springjwtauthexample.entity.User;
import com.example.springjwtauthexample.web.model.response.UserListResponse;
import com.example.springjwtauthexample.web.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper{
    UserResponse userToResponse(User user);

    default UserListResponse userListToUserListResponse(List<User> users){
        UserListResponse response = new UserListResponse();
        for (User n : users) {
            response.getUsers().add(userToResponse(n));
        }
        return response;
    }
}