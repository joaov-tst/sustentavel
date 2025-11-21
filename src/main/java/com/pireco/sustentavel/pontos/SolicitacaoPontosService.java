package com.pireco.sustentavel.pontos;

import com.pireco.sustentavel.pontos.dto.SolicitacaoPontosRequest;
import com.pireco.sustentavel.pontos.dto.SolicitacaoPontosResponse;
import com.pireco.sustentavel.usuario.UsuarioEntity;
import com.pireco.sustentavel.usuario.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.pireco.sustentavel.pontos.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.pireco.sustentavel.pontos.VoucherResgateRepository;


@Service
public class SolicitacaoPontosService {

    private final SolicitacaoPontosRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final VoucherResgateRepository voucherResgateRepository;


    public SolicitacaoPontosService(SolicitacaoPontosRepository repository,
                                    UsuarioRepository usuarioRepository,
                                    VoucherResgateRepository voucherResgateRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.voucherResgateRepository = voucherResgateRepository;
    }


    // ================== USUÁRIO LOGADO ==================

    private UsuarioEntity getUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email);  // seu método já retorna UsuarioEntity
    }

    // ================== ÁREA DO CLIENTE ==================

    public SolicitacaoPontosResponse criar(SolicitacaoPontosRequest request) {

        UsuarioEntity usuario = getUsuarioLogado();

        SolicitacaoPontos solicitacao = new SolicitacaoPontos(
                request.numeroComanda(),
                request.pontosSolicitados(),
                request.descricao(),
                usuario
        );

        SolicitacaoPontos salva = repository.save(solicitacao);

        return toResponse(salva);
    }

    public List<SolicitacaoPontosResponse> listarMinhas() {
        UsuarioEntity usuario = getUsuarioLogado();

        List<SolicitacaoPontos> entidades =
                repository.findByUsuarioOrderByDataSolicitacaoDesc(usuario);

        List<SolicitacaoPontosResponse> respostas = new ArrayList<>();

        for (SolicitacaoPontos s : entidades) {
            respostas.add(toResponse(s));
        }

        return respostas;
    }

    // ================== CONVERSÃO PARA RESPONSE ==================

    private SolicitacaoPontosResponse toResponse(SolicitacaoPontos s) {
        return new SolicitacaoPontosResponse(
                s.getId(),
                s.getNumeroComanda(),
                s.getPontosSolicitados(),
                s.getDescricao(),
                s.getDataSolicitacao(),
                s.getStatus()
        );
    }

    // ============= ÁREA DO ASSISTENTE ADMINISTRATIVO =============

    public List<SolicitacaoPontosResponse> listarPendentes() {

        List<SolicitacaoPontos> entidades =
                repository.findByStatusOrderByDataSolicitacaoAsc(StatusSolicitacao.PENDENTE);

        List<SolicitacaoPontosResponse> respostas = new ArrayList<>();

        for (SolicitacaoPontos s : entidades) {
            respostas.add(toResponse(s));
        }

        return respostas;
    }

    public SolicitacaoPontosResponse alterarStatus(Long id, StatusSolicitacao novoStatus) {

        SolicitacaoPontos solic = repository.findById(id).orElse(null);

        if (solic == null) {
            throw new RuntimeException("Solicitação não encontrada");
        }

        solic.setStatus(novoStatus);

        SolicitacaoPontos salva = repository.save(solic);

        return toResponse(salva);
    }


    // =============== RESUMO "MEUS PONTOS" ===============

    public ResumoPontosResponse resumoPontosCliente() {
        UsuarioEntity usuario = getUsuarioLogado();

        // 1) Solicitações aprovadas (ganhos)
        List<SolicitacaoPontos> aprovadas =
                repository.findByUsuarioAndStatus(usuario, StatusSolicitacao.APROVADA);

        int totalAcumulado = 0;
        List<TransacaoPontosResponse> historico = new ArrayList<>();

        for (SolicitacaoPontos s : aprovadas) {
            totalAcumulado += s.getPontosSolicitados();

            historico.add(new TransacaoPontosResponse(
                    "Não desperdício - Comanda #" + s.getNumeroComanda(),
                    s.getDataSolicitacao(),
                    s.getPontosSolicitados() // positivo
            ));
        }

        // 2) Vouchers resgatados (resgates)
        List<VoucherResgate> vouchers = voucherResgateRepository.findByUsuario(usuario);

        int totalResgatado = 0;

        for (VoucherResgate v : vouchers) {
            totalResgatado += v.getPontosUtilizados();

            historico.add(new TransacaoPontosResponse(
                    "Resgate de recompensa - " + v.getDescricaoRecompensa(),
                    v.getDataResgate(),
                    -v.getPontosUtilizados() // negativo
            ));
        }

        // 3) Saldo atual
        int saldoAtual = totalAcumulado - totalResgatado;

        // 4) Solicitações pendentes
        int pendentes = repository.countByUsuarioAndStatus(usuario, StatusSolicitacao.PENDENTE);

        // (Opcional) ordenar o histórico por data, se quiser:
        historico.sort((a, b) -> b.data().compareTo(a.data())); // mais recente primeiro

        return new ResumoPontosResponse(
                saldoAtual,
                totalAcumulado,
                totalResgatado,
                pendentes,
                historico
        );
    }

    // =============== RESGATAR RECOMPENSA (GERAR VOUCHER) ===============

    public VoucherResponse resgatarRecompensa() {
        UsuarioEntity usuario = getUsuarioLogado();

        ResumoPontosResponse resumo = resumoPontosCliente();
        int saldoAtual = resumo.saldoAtual();

        if (saldoAtual < 100) {
            throw new RuntimeException("Saldo insuficiente para resgatar. É preciso ter pelo menos 100 pontos.");
        }

        String codigo = "GSR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        VoucherResgate voucher = new VoucherResgate(
                codigo,
                100,                          // sempre 100 por resgate
                "Drink ou suco",              // descrição fixa da recompensa
                usuario
        );

        VoucherResgate salvo = voucherResgateRepository.save(voucher);

        return new VoucherResponse(
                salvo.getCodigo(),
                salvo.getDescricaoRecompensa(),
                salvo.getPontosUtilizados(),
                salvo.getDataResgate(),
                salvo.getValidade()   //
        );


    }
}



