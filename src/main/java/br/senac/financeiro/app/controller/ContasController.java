package br.senac.financeiro.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.senac.financeiro.app.model.entity.ContasEntity;
import br.senac.financeiro.app.service.ContasService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContasController {

    private final ContasService service;

    // cadastro público (não exige token)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContasEntity criar(@RequestBody ContasEntity conta) {
        return service.criar(conta);
    }

    // as rotas abaixo atuam sempre sobre a conta do token
    @GetMapping("/me")
    public ContasEntity buscar(@RequestAttribute Integer contaId) {
        return service.buscar(contaId);
    }

    @PutMapping("/me")
    public ContasEntity atualizar(@RequestAttribute Integer contaId, @RequestBody ContasEntity conta) {
        return service.atualizar(contaId, conta);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@RequestAttribute Integer contaId) {
        service.excluir(contaId);
    }
}
