package gui;

import dao.DaoUsuarios;
import dao.modelo.Usuario;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import servicios.ServiciosMarvel;

import java.time.LocalDateTime;

public class ProbandoRxJava {


    public static void main(String[] args) throws InterruptedException {

        ServiciosMarvel sm = new ServiciosMarvel();
        System.out.println(sm.getCharacteres());

        DaoUsuarios dao = new DaoUsuarios();
        Disposable d = dao.updateUsuario(new Usuario(null, "nombre", LocalDateTime.now()))
                .observeOn(Schedulers.io())
                .doFinally(() -> System.out.println("FIN"))
                .subscribe(lists -> lists
                                .peek(System.out::println)
                                .peekLeft(System.out::println),
                        throwable -> {
                            System.out.println("Error" + throwable.getMessage());
                        });


        while(!d.isDisposed()){
            Thread.sleep(100);
        }
        System.out.println(d.isDisposed());
        Schedulers.shutdown();

    }
}
