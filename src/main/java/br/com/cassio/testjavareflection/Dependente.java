package br.com.cassio.testjavareflection;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@With
@Data
@Builder
public class Dependente {
    String cpf;
    String nome;
    ParentescoEnum parentesco;
}
