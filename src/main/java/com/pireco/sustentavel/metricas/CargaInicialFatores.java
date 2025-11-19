package com.pireco.sustentavel.metricas;

import com.pireco.sustentavel.material.Material;
import com.pireco.sustentavel.material.MaterialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class CargaInicialFatores implements CommandLineRunner {

    private final MaterialRepository materialRepository;
    private final FatorConversaoRepository fatorConversaoRepository;
    private final FatorFinanceiroRepository fatorFinanceiroRepository;

    public CargaInicialFatores(MaterialRepository materialRepository,
                               FatorConversaoRepository fatorConversaoRepository,
                               FatorFinanceiroRepository fatorFinanceiroRepository) {
        this.materialRepository = materialRepository;
        this.fatorConversaoRepository = fatorConversaoRepository;
        this.fatorFinanceiroRepository = fatorFinanceiroRepository;
    }

    @Override
    public void run(String... args) {

        Optional<Material> oleoOpt  = materialRepository.findByNomeIgnoreCase("Óleo usado");
        Optional<Material> papelOpt = materialRepository.findByNomeIgnoreCase("Papel / papelão");
        Optional<Material> lataOpt  = materialRepository.findByNomeIgnoreCase("Lata de alumínio");

        oleoOpt.ifPresent(this::criarFatoresOleo);
        papelOpt.ifPresent(this::criarFatoresPapel);
        lataOpt.ifPresent(this::criarFatoresLata);
    }

    private void criarFatoresOleo(Material material) {
        conv(material, FatorConversao.TipoMetrica.RESIDUO, 0.92);
        conv(material, FatorConversao.TipoMetrica.CO2,     0.0001);
        conv(material, FatorConversao.TipoMetrica.AGUA,    0.001);
        conv(material, FatorConversao.TipoMetrica.ENERGIA, 0.5);

        fin(material, 0.50, 2.00);
    }

    private void criarFatoresPapel(Material material) {
        conv(material, FatorConversao.TipoMetrica.RESIDUO, 1.0);
        conv(material, FatorConversao.TipoMetrica.CO2,     0.0010);
        conv(material, FatorConversao.TipoMetrica.AGUA,    0.026);
        conv(material, FatorConversao.TipoMetrica.ENERGIA, 4.0);

        fin(material, 0.40, 0.20);
    }

    private void criarFatoresLata(Material material) {
        double kgPorUn = 0.016; // 16 g por lata

        conv(material, FatorConversao.TipoMetrica.RESIDUO, kgPorUn);
        conv(material, FatorConversao.TipoMetrica.CO2,     0.0090 * kgPorUn);
        conv(material, FatorConversao.TipoMetrica.AGUA,    0.010 * kgPorUn);
        conv(material, FatorConversao.TipoMetrica.ENERGIA, 14.0 * kgPorUn);

        fin(material, 0.30, null);
    }

    private void conv(Material m, FatorConversao.TipoMetrica tipo, double fator) {
        fatorConversaoRepository
                .findByMaterialIdAndTipo(m.getId(), tipo)
                .orElseGet(() -> {
                    FatorConversao fc = new FatorConversao();
                    fc.setMaterial(m);
                    fc.setTipo(tipo);
                    fc.setFator(BigDecimal.valueOf(fator));
                    fc.setFonte("Tabela de métricas do projeto");
                    return fatorConversaoRepository.save(fc);
                });
    }

    private void fin(Material m, Double desc, Double subst) {
        fatorFinanceiroRepository
                .findByMaterialId(m.getId())
                .orElseGet(() -> {
                    FatorFinanceiro f = new FatorFinanceiro();
                    f.setMaterial(m);
                    f.setEconomiaDescarte(BigDecimal.valueOf(desc));
                    f.setEconomiaSubstituicao(
                            subst == null ? null : BigDecimal.valueOf(subst)
                    );
                    return fatorFinanceiroRepository.save(f);
                });
    }
}
