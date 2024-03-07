package com.example.springjwtauthexample.service.impl;

import com.example.springjwtauthexample.entity.User;
import com.example.springjwtauthexample.exception.EntityNotFoundException;
import com.example.springjwtauthexample.exception.RemoveLastElementException;
import com.example.springjwtauthexample.exception.WrongDataFormatException;
import com.example.springjwtauthexample.repository.UserRepository;
import com.example.springjwtauthexample.repository.UserSpecification;
import com.example.springjwtauthexample.service.UserCommunicationService;
import com.example.springjwtauthexample.service.UserService;
import com.example.springjwtauthexample.web.filter.UserFilter;
import com.example.springjwtauthexample.web.model.request.AddOrRemoveEmailRequest;
import com.example.springjwtauthexample.web.model.request.AddOrRemovePhoneRequest;
import com.example.springjwtauthexample.web.model.request.ChangeEmailRequest;
import com.example.springjwtauthexample.web.model.request.ChangePhoneRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserCommunicationService, UserService {

    private final UserRepository userRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public User updateEmail(String username, ChangeEmailRequest request) {
        User user = findByUsername(username);

        Set<String> emails = user.getEmails();
        String oldEmail = request.getCurrentEmail();
        String newEmail = request.getNewEmail();

        if (emails.contains(oldEmail)) {
            emails.remove(oldEmail);
            emails.add(newEmail);
            user.setEmails(emails);
            userRepository.save(user);
        }

        return user;
    }

    @Override
    public User addEmail(String username, AddOrRemoveEmailRequest request) {
        User user = findByUsername(username);

        user.addEmail(request.getEmail());
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteEmail(String username, AddOrRemoveEmailRequest request) {
        User user = findByUsername(username);

        if(user.getEmails().size() <= 1)
            throw new RemoveLastElementException("cannot remove last element collection");

        user.deleteEmail(request.getEmail());
        userRepository.save(user);
    }

    @Override
    public User updatePhone(String username, ChangePhoneRequest request) {
        User user = findByUsername(username);

        Set<String> phones = user.getPhones();
        String oldPhone = request.getCurrentPhone();
        String newPhone = request.getNewPhone();

        if (phones.contains(oldPhone)) {
            phones.remove(oldPhone);
            phones.add(newPhone);
            user.setEmails(phones);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public void deletePhone(String username, AddOrRemovePhoneRequest request) {
        User user = findByUsername(username);

        if(user.getPhones().size() <= 1)
            throw new RemoveLastElementException("cannot remove last element collection");

        user.deletePhone(request.getPhone());
        userRepository.save(user);
    }

    @Override
    public User addPhone(String username, AddOrRemovePhoneRequest request) {
        User user = findByUsername(username);
        user.addPhone(request.getPhone());
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> filterBy(UserFilter filter) {
        return userRepository.findAll(UserSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize())
        ).getContent();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new EntityNotFoundException(
                MessageFormat.format("user with name {0} not found", username)));
    }
}
