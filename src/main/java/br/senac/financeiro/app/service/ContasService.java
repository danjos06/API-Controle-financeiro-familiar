package br.senac.financeiro.app.service;

import java.util.List;
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
        if (conta.getSalario() == null) {
            conta.setSalario(0.0);
        }
        if (conta.getLimiteGastos() == null) {
            conta.setLimiteGastos(0.0);
        }
        if (contasRepository.existsByEmail(conta.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
        }
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

    public ContasEntity buscarPorEmail(String email) {
        return contasRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada com este e-mail"));
    }

    public List<ContasEntity> listar() {
        return contasRepository.findAll();
    }

    public ContasEntity atualizar(Integer id, ContasEntity dados) {
        ContasEntity conta = buscar(id);
        if (dados.getNome() != null && !dados.getNome().isBlank()) {
            conta.setNome(dados.getNome());
        }
        if (dados.getEmail() != null && !dados.getEmail().isBlank()) {
            if (contasRepository.existsByEmailAndIdNot(dados.getEmail(), id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já está em uso");
            }
            conta.setEmail(dados.getEmail());
        }
        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            conta.setSenha(Md5Util.hash(dados.getSenha()));
        }
        if (dados.getSalario() != null) {
            conta.setSalario(dados.getSalario());
        }
        if (dados.getLimiteGastos() != null) {
            conta.setLimiteGastos(dados.getLimiteGastos());
        }
        return contasRepository.save(conta);
    }

    public void excluir(Integer id) {
        ContasEntity conta = buscar(id);
        contasRepository.delete(conta);
    }
}
