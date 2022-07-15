package challenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class ReactiveExampleTest {

    @InjectMocks
    ReactiveExample reactiveExample;


    @Test
    void sumaDePuntajes(){
        Mono<Integer> suma = reactiveExample.sumaDePuntajes();

        StepVerifier.create(suma)
                .expectNext(260)
                .verifyComplete();
    }

    @Test
    void mayorPuntajeDeEstudiante() {
        var mayor = reactiveExample.mayorPuntajeDeEstudiante(2);
        StepVerifier.create(mayor)
                .expectNextMatches(e -> e.getNombre().equals("pedro"))
                .expectNextMatches(e -> e.getNombre().equals("juan"))
                .verifyComplete();
    }

    @Test
    void totalDeAsisntenciasDeEstudiantesComMayorPuntajeDe() {
        var mayorPuntaje = reactiveExample.totalDeAsisntenciasDeEstudiantesConMayorPuntajeDe(75);

        StepVerifier.create(mayorPuntaje)
                .expectNext(43).verifyComplete();
    }

    @Test
    void elEstudianteTieneAsistenciasCorrectas() {
        var estudiante = new Estudiante("raul", 30, List.of(5,2,1,4,5));
        var asistencias = reactiveExample.elEstudianteTieneAsistenciasCorrectas(estudiante);

        StepVerifier.create(asistencias)
                .expectNext(true).verifyComplete();
    }

    @Test
    void promedioDePuntajesPorEstudiantes(){
        var promedio = reactiveExample.promedioDePuntajesPorEstudiantes();

        StepVerifier.create(promedio)
                .expectNext(52.0).verifyComplete();
    }

    @Test
    void estudiantesAprovados(){
        var aprobados = reactiveExample.estudiantesAprovados();
        StepVerifier.create(aprobados)
                .expectNext("juan", "pedro")
                .verifyComplete();
    }
}