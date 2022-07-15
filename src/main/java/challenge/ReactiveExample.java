package challenge;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ReactiveExample {

    public static final int VALOR_PERMITIDO = 15;
    private  Flux<Estudiante> estudianteList;

    public Flux<Estudiante> ReactiveExample() {
        //TODO: convertir los estudiantes a un Flux
        Flux<Estudiante> estudiantesFlux = Flux.just(
                new Estudiante("raul", 30, List.of(1, 2, 1, 4, 5)),
                new Estudiante("andres", 35, List.of(4, 2, 4, 3, 5)),
                new Estudiante("juan", 75, List.of(3, 2, 4, 5, 5)),
                new Estudiante("pedro", 80, List.of(5, 5, 4, 5, 5)),
                new Estudiante("santiago", 40, List.of(4, 5, 4, 5, 5))
        );

        return estudiantesFlux;
    }

    private Predicate<Estudiante> asistenciasPemitidas() {
        return estudiante -> estudiante.getAsistencias()
                .stream()
                .reduce(0, Integer::sum) >= VALOR_PERMITIDO;
    }

    //TODO: suma de puntajes
    public Mono<Integer> sumaDePuntajes() {
        Flux<Estudiante> estudiantes = ReactiveExample();

        return estudiantes.map(this.mapeoDeEstudianteAPuntaje())
                .reduce(0, Integer::sum);
    }

    private Function<Estudiante, Integer> mapeoDeEstudianteAPuntaje() {
        return Estudiante::getPuntaje;
    }

    //TODO: mayor puntaje de estudiante
    public Flux<Estudiante> mayorPuntajeDeEstudiante(int limit) {
        Flux<Estudiante> estudiantes = ReactiveExample();

        return estudiantes.sort(Comparator.comparing(Estudiante::getPuntaje).reversed())
                .take(limit);
    }

    //TODO: total de asisntencias de estudiantes con mayor puntaje basado en un  valor
    public Mono<Integer> totalDeAsisntenciasDeEstudiantesConMayorPuntajeDe(int valor) {
        Flux<Estudiante> estudiantes = ReactiveExample();

        return estudiantes
                .filter(estudiante -> estudiante.getPuntaje() >= valor)
                .flatMap(estudiante -> Flux.fromIterable(estudiante.getAsistencias()))
                .reduce(0, Integer::sum);
    }

    //TODO: el estudiante tiene asistencias correctas
    public Mono<Boolean> elEstudianteTieneAsistenciasCorrectas(Estudiante estudiante) {
        return Mono.just(estudiante.getAsistencias()
                .stream()
                .reduce(0, Integer::sum) >= VALOR_PERMITIDO);
    }

    //TODO: promedio de puntajes por estudiantes
    public Mono<Double> promedioDePuntajesPorEstudiantes() {
        Flux<Estudiante> estudiantes = ReactiveExample();
        Mono<Integer> total = sumaDePuntajes();
        Mono<Long> cantidad = estudiantes.count();

        Double valor = Double.valueOf(total.block());
        Double valor2 = Double.valueOf(cantidad.block());

        return Mono.just(valor / valor2);
    }


    //TODO: los nombres de estudiante con puntaje mayor a un valor
    public Flux<String> losNombresDeEstudianteConPuntajeMayorA(int valor) {
        Flux<Estudiante> estudiantes = ReactiveExample();
        return estudiantes.filter(e -> e.getPuntaje() >= valor)
                .map(Estudiante::getNombre);
    }

    //TODO: estudiantes aprovados
    public Flux<String> estudiantesAprovados() {
        Flux<Estudiante> estudiantes = ReactiveExample();

        return estudiantes.filter(estudiante -> estudiante.getPuntaje() >= 75)
                .map(estudiante -> {
                    var nombre = estudiante.getNombre();
                    return nombre;
                });
    }
}
