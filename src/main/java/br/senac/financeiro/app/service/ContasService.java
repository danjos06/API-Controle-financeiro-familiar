package br.senac.financeiro.app.service;

import java.util.Map;

import br.senac.financeiro.app.model.entity.ContasEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.senac.financeiro.app.model.request.LoginRequest;
import br.senac.financeiro.app.repository.ContasRepository;
import br.senac.financeiro.app.util.JwtUtil;
import br.senac.financeiro.app.util.Md5Util;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContasService {

    private final ContasRepository contasRepository;
    private final JwtUtil jwtUtil = new JwtUtil();

    public ContasEntity criar(ContasEntity conta) {
        conta.setId(null);
        conta.setSenha(Md5Util.hash(conta.getSenha()));
        return contasRepository.save(conta);
    }

    public Map<String, String> login(LoginRequest req) {
        ContasEntity conta = contasRepository.findByEmail(req.getEmail())
                .filter(c -> c.getSenha().equals(Md5Util.hash(req.getSenha())))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos"));
        return Map.of("token", jwtUtil.gerarToken(conta.getId(), conta.getEmail()));
    }

    public ContasEntity buscar(Integer id) {
        return contasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"));
    }

    public ContasEntity atualizar(Integer id, ContasEntity dados) {
        ContasEntity conta = buscar(id);
        conta.setNome(dados.getNome());
        conta.setEmail(dados.getEmail());
        conta.setSenha(Md5Util.hash(dados.getSenha()));
        return contasRepository.save(conta);
    }

    public void excluir(Integer id) {
        ContasEntity conta = buscar(id);
        contasRepository.delete(conta);
    }
}
