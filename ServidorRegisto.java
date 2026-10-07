import java.rmi.registry.LocateRegistry; // Permite criar/localizar o RMI Registry
import java.rmi.registry.Registry;       // Representa o próprio registo (uma espécie de "DNS" de objectos remotos

public class ServidorRegisto {

    public static void main(String[] args) {
        try {
            // Cria o RMI Registry na porta 1099 (porta padrão do RMI).
            // O registry é o serviço que guarda a associação "nome -> stub remoto".
            Registry registry = LocateRegistry.createRegistry(1099);

            // Instancia o objecto remoto; o construtor já o exporta automaticamente.
            RegistoAcademico servico = new RegistoAcademicoImpl();

            // Publica o objecto no registry com o nome "RegistoAcademico".
            // É este nome que o cliente vai usar para localizar o serviço.
            registry.rebind("RegistoAcademico", servico);

            // Mensagem local apenas para confirmar que o servidor está pronto.
            System.out.println("Servidor RMI iniciado. Aguardando pedidos na porta 1099...");

        } catch (Exception e) {
            // Qualquer falha (ex.: porta ocupada) é impressa para diagnóstico.
            e.printStackTrace();
        }
    }
}
