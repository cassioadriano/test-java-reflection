package br.com.cassio.testjavareflection;


import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.List;

@With
@Data
@Builder
public class Pessoa {
    String cpf;
    String nome;
    List<Dependente> dependentes;
}
