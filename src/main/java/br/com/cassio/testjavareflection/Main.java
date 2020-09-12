package br.com.cassio.testjavareflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Pessoa pessoa = criarPessoaTeste();
        List<ConfiguracaoDePara> configuracaoDePara = recuperarConfiguracoes();

        List<ParaNextiaField> listaFields = new ArrayList<>();

        configuracaoDePara.stream().forEach(c-> {
            String[] de = c.getDe().split("\\.");
            Object resultObject = pessoa;
            for (String deI : de) {
                if (List.class.isAssignableFrom(resultObject.getClass())) {
                    List listObject = ((List)resultObject);
                    for (int index = 0; listObject.size() > index; index++) {
                        Object objectItem = listObject.get(index);
                        listaFields.add(
                                ParaNextiaField.builder()
                                .id(c.getPara())
                                .valor(getPropertyValue(objectItem, deI))
                                .index(index + 1)
                                .build());
                    }
                } else {
                    resultObject = getPropertyValue(resultObject, deI);
                }
            }
            if (!List.class.isAssignableFrom(resultObject.getClass())) {
                listaFields.add(ParaNextiaField.builder()
                        .id(c.getPara())
                        .valor(resultObject)
                        .index(0)
                        .build());
            }
        });

        System.out.println(listaFields);
/*
        System.out.println(getPropertyValue(pessoa, "id"));
        System.out.println(getPropertyValue(pessoa, "nome"));
        ((List) getPropertyValue(pessoa, "dependentes")).stream()
        .forEach(item -> {
            System.out.println(getPropertyValue(item, "id"));
            System.out.println(getPropertyValue(item, "nome"));
            System.out.println(getPropertyValue(item, "parentesco"));
        });
*/
    }

    private static List<ConfiguracaoDePara> recuperarConfiguracoes() {
        return Arrays.asList(ConfiguracaoDePara.builder()
                        .de("cpf")
                        .para("0002-0001-0001-0006")
                        .build(),
                    ConfiguracaoDePara.builder()
                            .de("nome")
                            .para("0002-0001-0001-0001")
                            .build(),
                    ConfiguracaoDePara.builder()
                            .de("dependentes.cpf")
                            .para("0002-0001-0002-0006")
                            .build(),
                    ConfiguracaoDePara.builder()
                            .de("dependentes.nome")
                            .para("0002-0001-0002-0001")
                            .build(),
                    ConfiguracaoDePara.builder()
                            .de("dependentes.parentesco")
                            .para("0002-0001-0002-5876")
                            .build()
                    );
    }

    private static Pessoa criarPessoaTeste() {
        return Pessoa.builder()
                    .cpf("23423423401")
                    .nome("Nome 1")
                    .dependentes(Arrays.asList(Dependente.builder()
                            .cpf("56789056744")
                            .nome("Dependente 1")
                            .parentesco(ParentescoEnum.CONJUGE)
                            .build(), Dependente.builder()
                            .cpf("12312312332")
                            .nome("Dependente 2")
                            .parentesco(ParentescoEnum.FILHO)
                            .build()))
                    .build();
    }

    private static Object getPropertyValue(Object obj, String property) {
        try {
            Method method = obj.getClass().getMethod("get" + capitalizeFirstLetter(property), null);
            return method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String capitalizeFirstLetter(String str) {
        if(str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
