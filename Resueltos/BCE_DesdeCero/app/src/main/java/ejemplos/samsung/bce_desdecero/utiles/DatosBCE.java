package ejemplos.samsung.bce_desdecero.utiles;

public class DatosBCE {
    private String moneda;
    private double ratio;

    public DatosBCE(String moneda, double ratio) {
        this.moneda = moneda;
        this.ratio = ratio;
    }

    public String getMoneda() {
        return moneda;
    }

    public double getRatio() {
        return ratio;
    }
}
