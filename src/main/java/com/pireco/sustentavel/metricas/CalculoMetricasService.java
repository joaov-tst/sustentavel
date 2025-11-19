package com.pireco.sustentavel.metricas;

import com.pireco.sustentavel.material.Material;
import com.pireco.sustentavel.material.MaterialRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CalculoMetricasService {

    private final MaterialRepository materialRepository;
    private final FatorConversaoRepository fatorConversaoRepository;
    private final FatorFinanceiroRepository fatorFinanceiroRepository;

    public CalculoMetricasService(MaterialRepository materialRepository,
                                  FatorConversaoRepository fatorConversaoRepository,
                                  FatorFinanceiroRepository fatorFinanceiroRepository) {
        this.materialRepository = materialRepository;
        this.fatorConversaoRepository = fatorConversaoRepository;
        this.fatorFinanceiroRepository = fatorFinanceiroRepository;
    }

    public ResultadoMetricas calcular() {
        List<Material> materiais = materialRepository.findAll();

        BigDecimal residuo = zero();
        BigDecimal co2 = zero();
        BigDecimal agua = zero();
        BigDecimal energia = zero();
        BigDecimal economiaDescarte = zero();
        BigDecimal economiaSubstituicao = zero();

        for (Material m : materiais) {
            double qtdDouble = m.getQuantidade() == null ? 0.0 : m.getQuantidade();
            BigDecimal quantidade = BigDecimal.valueOf(qtdDouble);

            // fatores ambientais
            residuo = residuo.add( quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.RESIDUO)) );

            co2 = co2.add( quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.CO2)) );

            agua = agua.add( quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.AGUA)) );

            energia = energia.add( quantidade.multiply(
                    fator(m, FatorConversao.TipoMetrica.ENERGIA)) );

            // fatores financeiros
            var fatorFinOpt = fatorFinanceiroRepository.findByMaterialId(m.getId());
            if (fatorFinOpt.isPresent()) {
                var f = fatorFinOpt.get();
                if (f.getEconomiaDescarte() != null) {
                    economiaDescarte = economiaDescarte.add(
                            quantidade.multiply(f.getEconomiaDescarte())
                    );
                }
                if (f.getEconomiaSubstituicao() != null) {
                    economiaSubstituicao = economiaSubstituicao.add(
                            quantidade.multiply(f.getEconomiaSubstituicao())
                    );
                }
            }
        }

        BigDecimal economiaTotal = economiaDescarte.add(economiaSubstituicao);

        return new ResultadoMetricas(
                arred(residuo),
                arred(co2),
                arred(agua),
                arred(energia),
                arred(economiaDescarte),
                arred(economiaSubstituicao),
                arred(economiaTotal)
        );
    }

    private BigDecimal fator(Material material, FatorConversao.TipoMetrica tipo) {
        return fatorConversaoRepository
                .findByMaterialIdAndTipo(material.getId(), tipo)
                .map(FatorConversao::getFator)
                .orElse(zero());
    }

    private BigDecimal zero() {
        return BigDecimal.ZERO;
    }

    private BigDecimal arred(BigDecimal valor) {
        return valor.setScale(4, RoundingMode.HALF_UP);
    }
}
