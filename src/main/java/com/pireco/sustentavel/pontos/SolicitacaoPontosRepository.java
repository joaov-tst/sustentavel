package com.pireco.sustentavel.pontos;

import com.pireco.sustentavel.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoPontosRepository extends JpaRepository<SolicitacaoPontos, Long> {

    List<SolicitacaoPontos> findByUsuarioOrderByDataSolicitacaoDesc(UsuarioEntity usuario);

    List<SolicitacaoPontos> findByStatusOrderByDataSolicitacaoAsc(StatusSolicitacao status);

    // usados no resumo de pontos:
    List<SolicitacaoPontos> findByUsuarioAndStatus(UsuarioEntity usuario, StatusSolicitacao status);

    int countByUsuarioAndStatus(UsuarioEntity usuario, StatusSolicitacao status);
}



