package com.example.authdemo.service;

import com.example.authdemo.dto.IncomeRequest;
import com.example.authdemo.model.Income;
import com.example.authdemo.model.User;
import com.example.authdemo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addIncomeToUser(User user, IncomeRequest request) {
        String month = request.month() != null ?
                request.month() :
                LocalDate.now().getYear() + "-" + String.format("%02d", LocalDate.now().getMonthValue());

        Optional<Income> existingIncome = user.getIncomeEntries().stream()
                .filter(income -> income.getMonth().equals(month))
                .findFirst();

        if (existingIncome.isPresent()) {
            existingIncome.get().setValue(existingIncome.get().getValue() + request.value());
        } else {
            user.getIncomeEntries().add(new Income(month, request.value()));
        }

        return userRepository.save(user);
    }
}