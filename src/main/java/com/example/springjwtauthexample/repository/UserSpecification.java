package com.example.springjwtauthexample.repository;

import com.example.springjwtauthexample.entity.User;
import com.example.springjwtauthexample.utils.DateParser;
import com.example.springjwtauthexample.web.filter.UserFilter;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public interface UserSpecification {

    static Specification<User> withFilter(UserFilter filter){
        return Specification.where(byUsername(filter.getUsername()))
                .and(byEmail(filter.getEmail()))
                .and(byPhone(filter.getPhone()))
                .and(byBirthdayDate(filter.getBirthDate()));
    }

    static Specification<User> byBirthdayDate(String birthDate) {
        return (root, query, criteriaBuilder) -> {
            if (birthDate == null || birthDate.isEmpty()) {
                return null;
            }
            LocalDate parsedBirthDate = DateParser.parse(birthDate);
            Date dateOfBirth = Date.from(parsedBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return criteriaBuilder.greaterThan(root.get("birthDate"), dateOfBirth);
        };
    }

    static Specification<User> byPhone(String phone) {
        return (((root, query, criteriaBuilder) -> {
            if (phone != null) {
                return criteriaBuilder.isMember(phone, root.get("phones"));
            }
            return null;
        }));
    }

    static Specification<User> byEmail(String email) {
        return (((root, query, criteriaBuilder) -> {
            if (email != null) {
                return criteriaBuilder.isMember(email, root.get("emails"));
            }
            return null;
        }));
    }

    static Specification<User> byUsername(String username) {
        return (((root, query, criteriaBuilder) -> {
            if (username != null && !username.isEmpty()) {
                return criteriaBuilder.like(root.get("username"), username + "%");
            }
            return null;
        }));
    }
}