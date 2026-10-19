package br.senac.financeiro.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.financeiro.app.model.entity.ContasEntity;

public interface ContasRepository extends JpaRepository<ContasEntity, Integer> {

    Optional<ContasEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);

    List<ContasEntity> findByFamiliaId(Integer familiaId);
}
