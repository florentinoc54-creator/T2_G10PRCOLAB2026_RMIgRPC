import java.rmi.registry.LocateRegistry; // Para localizar o registry do servidor
import java.rmi.registry.Registry;       // Tipo que representa o registry remoto

public class ClienteRegisto {

    public static void main(String[] args) {
        try {
            // Localiza o registry do servidor.
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Procura, pelo nome, o stub do objecto remoto publicado pelo servidor.
            RegistoAcademico servico = (RegistoAcademico) registry.lookup("RegistoAcademico");

            // ---- Teste com o estudante 2023001 (dados anteriores) ----
            servico.registarNota("2023001", "Programacao de Redes", 15.5);
            servico.registarNota("2023001", "Bases de Dados", 17.0);

            // ---- Teste com um novo estudante: 2023045 ----
            System.out.println(servico.registarNota("2023045", "Electronica Digital", 12.0));
            System.out.println(servico.registarNota("2023045", "Economia e Legislacao", 18.5));
            System.out.println(servico.registarNota("2023045", "Processamento de Sinal", 14.0));

            // Consulta a média do novo estudante
            double media2 = servico.consultarMedia("2023045");
            System.out.println("Média do estudante 2023045: " + media2);

            // Lista as disciplinas do novo estudante
            String disciplinas2 = servico.listarDisciplinas("2023045");
            System.out.println("Disciplinas do estudante 2023045:\n" + disciplinas2);

            // ---- Confirma que o estudante original continua lá (dados independentes) ----
            double media1 = servico.consultarMedia("2023001");
            System.out.println("Média do estudante 2023001 (confirmação): " + media1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
