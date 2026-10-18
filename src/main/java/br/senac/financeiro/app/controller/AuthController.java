package br.senac.financeiro.app.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senac.financeiro.app.model.request.LoginRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final br.senac.financeiro.app.service.ContasService contasService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {
        return contasService.login(req);
    }
}
