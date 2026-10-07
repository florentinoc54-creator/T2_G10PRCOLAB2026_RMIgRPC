import java.rmi.Remote;            // Interface base que toda interface remota RMI deve estender
import java.rmi.RemoteException;   // Excepção que todo método remoto deve poder lançar

/**
 * Interface remota do serviço de Registo Académico.
 * Qualquer classe que implemente esta interface pode ter os seus
 * métodos invocados a partir de outra JVM (mesmo noutra máquina),
 * desde que seja publicada num RMI Registry.
 */
public interface RegistoAcademico extends Remote {

    // Consulta a média de um estudante a partir do número de estudante.
    // Lança RemoteException porque a chamada pode falhar por motivos de rede.
    double consultarMedia(String numeroEstudante) throws RemoteException;

    // Regista/actualiza a nota de um estudante numa determinada disciplina.
    // Devolve uma mensagem de confirmação da operação.
    String registarNota(String numeroEstudante, String disciplina, double nota) throws RemoteException;

    // Lista todas as disciplinas já registadas para um estudante.
    String listarDisciplinas(String numeroEstudante) throws RemoteException;
}
