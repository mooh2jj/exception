package com.example.exception.controller;

import com.example.exception.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/user")
@Validated
public class ApiController {

    @GetMapping("")
    public UserDto get(
            @Size(min = 2)
            @RequestParam String name,

            @NotNull
            @Min(1) // 최소값
            @RequestParam Integer age) {        // MissingServletRequestParameterException
        System.out.println("getget");
        UserDto user = new UserDto();
        user.setName(name);
        user.setAge(age);

        int a = 10 + age;   // exception 테스트용 코드 NPE
        return user;
    }

    @PostMapping("")
    public UserDto post(@Valid @RequestBody UserDto user) {
        System.out.println(user);
        return user;
    }

    // 특정 예외를 잡는 메서드 -> 특정 컨트롤러안에 있으면 우선순위 가장 높음
/*    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(Exception e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }*/
}
