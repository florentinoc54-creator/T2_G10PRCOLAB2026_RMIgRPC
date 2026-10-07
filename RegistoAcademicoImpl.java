import java.rmi.RemoteException;                 // Excepção obrigatória em métodos remotos
import java.rmi.server.UnicastRemoteObject;       // Classe base que torna o objecto acessível remotamente
import java.util.HashMap;                          // Estrutura para guardar notas por estudante
import java.util.Map;

/**
 * Implementação concreta do serviço remoto.
 * Estender UnicastRemoteObject faz com que, ao ser instanciado,
 * o objecto seja automaticamente "exportado": o RMI cria o stub
 * (proxy do lado do cliente) e o skeleton (código que recebe as
 * chamadas do lado do servidor) de forma transparente.
 */
public class RegistoAcademicoImpl extends UnicastRemoteObject implements RegistoAcademico {

    // Mapa: numeroEstudante -> (mapa: disciplina -> nota)
    // Simula uma base de dados de notas guardada em memória no servidor.
    private final Map<String, Map<String, Double>> baseDados = new HashMap<>();

    // O construtor tem de declarar RemoteException porque o construtor
    // de UnicastRemoteObject pode lançar essa excepção ao exportar o objecto.
    protected RegistoAcademicoImpl() throws RemoteException {
        super(); // Chama o construtor da superclasse, que faz a exportação RMI
    }

    @Override
    public double consultarMedia(String numeroEstudante) throws RemoteException {
        // Vai buscar as notas do estudante; se não existir, devolve mapa vazio
        Map<String, Double> notas = baseDados.getOrDefault(numeroEstudante, new HashMap<>());

        if (notas.isEmpty()) {
            return 0.0; // Estudante sem notas registadas
        }

        double soma = 0; // Acumulador da soma das notas
        for (double nota : notas.values()) {
            soma += nota; // Soma cada nota registada
        }

        return soma / notas.size(); // Média aritmética simples
    }

    @Override
    public String registarNota(String numeroEstudante, String disciplina, double nota) throws RemoteException {
        // Garante que existe um mapa de disciplinas para este estudante
        baseDados.putIfAbsent(numeroEstudante, new HashMap<>());

        // Regista (ou substitui) a nota da disciplina indicada
        baseDados.get(numeroEstudante).put(disciplina, nota);

        // Devolve confirmação — esta String viaja de volta pela rede até ao cliente
        return "Nota " + nota + " registada em " + disciplina + " para o estudante " + numeroEstudante;
    }

    @Override
    public String listarDisciplinas(String numeroEstudante) throws RemoteException {
        Map<String, Double> notas = baseDados.get(numeroEstudante);

        if (notas == null || notas.isEmpty()) {
            return "Nenhuma disciplina registada para " + numeroEstudante;
        }

        // Constrói uma String legível com todas as disciplinas e respectivas notas
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entrada : notas.entrySet()) {
            sb.append(entrada.getKey()).append(": ").append(entrada.getValue()).append("\n");
        }

        return sb.toString();
    }
}
