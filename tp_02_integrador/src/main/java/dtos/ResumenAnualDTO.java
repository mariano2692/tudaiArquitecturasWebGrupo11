package dtos;

public class ResumenAnualDTO {
    private int anio;
    private long inscriptos;
    private long egresados;

    public ResumenAnualDTO(int anio, long inscriptos, long egresados) {
        this.anio = anio;
        this.inscriptos = inscriptos;
        this.egresados = egresados;
    }

    public int getAnio() {
        return anio;
    }

    public long getInscriptos() {
        return inscriptos;
    }

    public long getEgresados() {
        return egresados;
    }

    @Override
    public String toString() {
        return "Año: " + anio + " | Inscriptos: " + inscriptos + " | Egresados: " + egresados;
    }
}
