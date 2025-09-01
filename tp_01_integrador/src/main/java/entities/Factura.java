package entities;

public class Factura {
    private int idCliente;
    private int idFactura;

    public Factura(int idCliente, int idFactura) {
        this.idCliente = idCliente;
        this.idFactura = idFactura;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdFactura() {
        return idFactura;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idCliente=" + idCliente +
                ", idFactura=" + idFactura +
                '}';
    }
}
