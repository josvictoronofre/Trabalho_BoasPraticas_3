// Sistema de Controle de Estoque e Vendas
// versao 1.2 - DT-01 e DT-02 Quitadas
// autor: José Victor Onofre Sales

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;   // (nao usado)

public class Estoque {

    // Quitado DT-01: Remocao de credencial hardcoded do codigo-fonte
    static String SENHA_ADMIN = System.getenv("ADMIN_PASSWORD") != null 
                                ? System.getenv("ADMIN_PASSWORD") 
                                : "UFRN_DIMAP_BOAS_PRATICAS_2026@";

    static ArrayList<Produto> produtos = new ArrayList<>();
    static ArrayList<String> hist = new ArrayList<>();  // historico

    static class Produto {
        String nome;
        double preco;
        int qtd;
    }

    static double lerDoubleSeguro(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um valor numerico valido (Use '.' para decimais).");
            }
        }
    }

    static int lerIntSeguro(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um numero inteiro valido.");
            }
        }
    }

    static void add(String n, double p, int q) {
        Produto prod = new Produto();
        prod.nome = n;
        prod.preco = p;
        prod.qtd = q;
        produtos.add(prod);
        hist.add(n);
        System.out.println("Produto adicionado!");
    }

    static double vender(String nome, int quantidade) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).nome.equals(nome)) {
                if (produtos.get(i).qtd >= quantidade) {
                    produtos.get(i).qtd = produtos.get(i).qtd - quantidade;
                    double total = produtos.get(i).preco * quantidade;
                    if (total > 100) {
                        total = total - total * 0.1;
                    }
                    System.out.println("Venda realizada. Total: " + total);
                    return total;
                } else {
                    System.out.println("Estoque insuficiente");
                    return 0;
                }
            }
        }
        System.out.println("Produto nao encontrado");
        return 0;
    }

    static double calcular_total(double preco, int quantidade) {
        double t = preco * quantidade;
        if (t > 200) {
            t = t - t * 0.15;
        }
        return t;
    }

    static void listar() {
        System.out.println("=== PRODUTOS ===");
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println(produtos.get(i).nome + " - R$" + produtos.get(i).preco
                    + " - qtd: " + produtos.get(i).qtd);
        }
    }

    static void relatorio_estoque_baixo() {
        System.out.println("=== ESTOQUE BAIXO ===");
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).qtd < 5) {
                System.out.println(produtos.get(i).nome + " esta com estoque baixo ("
                        + produtos.get(i).qtd + ")");
            }
        }
    }

    static void relatorio_vendas() {
        // TODO: implementar de verdade
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1-Cadastrar  2-Vender  3-Listar  4-Estoque baixo  5-Admin  0-Sair");
            System.out.print("Opcao: ");
            String op = sc.next();
            if (op.equals("1")) {
                System.out.print("Nome: ");
                String n = sc.next();
                double p = lerDoubleSeguro(sc, "Preco: ");
                int q = lerIntSeguro(sc, "Qtd: ");
                add(n, p, q);
            } else if (op.equals("2")) {
                System.out.print("Nome do produto: ");
                String n = sc.next();
                int q = lerIntSeguro(sc, "Quantidade: ");
                vender(n, q);
            } else if (op.equals("3")) {
                listar();
            } else if (op.equals("4")) {
                relatorio_estoque_baixo();
            } else if (op.equals("5")) {
                System.out.print("Senha: ");
                String s = sc.next();
                if (s.equals(SENHA_ADMIN)) {
                    System.out.println("Acesso liberado");
                } else {
                    System.out.println("Senha errada");
                }
            } else if (op.equals("0")) {
                break;
            } else {
                System.out.println("Opcao invalida");
            }
        }
    }
}