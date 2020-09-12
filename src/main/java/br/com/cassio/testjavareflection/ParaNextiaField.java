package br.com.cassio.testjavareflection;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Builder
@With
@Data
public class ParaNextiaField {
    String id;
    Object valor;
    Integer index;
}
