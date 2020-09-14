package br.com.cassio.testjavareflection;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@With
@Builder
public class ConfiguracaoDePara {
    String de;
    String para;
}
