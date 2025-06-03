
import java.util.Random;
import java.util.Scanner;

public class Ludo {

    public static void jogadaUsuario(String jogador, int[] pos, int[] adversario, Scanner e, Random rand, int[] base, int[][] casaSeg, int tam) {
        System.out.println("Jogador " + jogador + ", jogue o dado.");
        e.nextLine();

        int dado = rand.nextInt(6) + 1;
        System.out.println("Jogador " + jogador + " tirou: " + dado);

        mover(pos, dado, tam);

        if (adversario[0] != base[0] || adversario[1] != base[1]) {
            if (colidir(pos, adversario, casaSeg)) {
                System.out.println("Jogador " + jogador + " comeu Jogador " + (jogador.equals("A") ? "B" : "A"));
                adversario[0] = base[0];
                adversario[1] = base[1];
            }
        }
    }

    public static void mover(int[] pos, int dado, int tam) {
        if (pos[0] == -1 && dado == 6) {
            pos[0] = 0;
            pos[1] = 0;
        } else if (pos[0] != -1) {
            int total = pos[0] * tam + pos[1] + dado;
            int finalCasa = (tam * tam) - 1;
            if (total > finalCasa) {
                System.out.println("Precisa tirar o número exato para chegar no final!");
                return;
            }
            pos[0] = total / tam;
            pos[1] = total % tam;
        } else {
            System.out.println("Precisa tirar 6 para sair da base.");
        }
    }

    public static boolean emSeguro(int[] pos, int[][] casaSeg) {
        for (int[] seguro : casaSeg) {
            if (iguais(pos, seguro)) return true;
        }
        return false;
    }

    public static boolean colidir(int[] a, int[] b, int[][] casaSeg) {
        return iguais(a, b) && !emSeguro(a, casaSeg);
    }

    public static boolean venceu(int[] pos, int tam) {
        return pos[0] == tam - 1 && pos[1] == tam - 1;
    }

    public static boolean iguais(int[] a, int[] b) {
        return a[0] == b[0] && a[1] == b[1];
    }

    public static void imprimirTabuleiro(int[] a, int[] b, int[][] casaSeg, int tam) {
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                int[] v = {i, j};
                if (iguais(a, v) && iguais(b, v)) System.out.print("[X]");
                else if (iguais(a, v)) System.out.print("[A]");
                else if (iguais(b, v)) System.out.print("[B]");
                else if (emSeguro(v, casaSeg)) System.out.print("[S]");
                else System.out.print("[ ]");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
         int tam = 7;
         int[] base = {-1, -1};
        int[][] casaSeg = {{0,6}, {1,3}, {2, 0}, {3,5}, {4,2}, {5,5}, {6,4}};

        Scanner e = new Scanner(System.in);
        Random rand = new Random();

        int[] posA = new int[] {base[0], base[1]};
        int[] posB = new int[] {base[0], base[1]};
        boolean jogadorAtual = true;

        while (true) {
            imprimirTabuleiro(posA, posB, casaSeg, tam);
            String jogador = jogadorAtual ? "A" : "B";

            if (jogadorAtual) jogadaUsuario(jogador, posA, posB, e, rand, base, casaSeg, tam);
            else jogadaUsuario(jogador, posB, posA, e, rand, base, casaSeg, tam);

            if (venceu(posA, tam)) {
                System.out.println("Jogador A venceu!");
                break;
            } else if (venceu(posB, tam)) {
                System.out.println("Jogador B venceu!");
                break;
            }

            jogadorAtual = !jogadorAtual;
        }
    }
}
