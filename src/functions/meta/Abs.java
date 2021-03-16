package functions.meta;
//модуль
import functions.Function;

public class Abs implements Function {
    private final Function f;
    public Abs(Function f1) {
        f = f1;
    }
    @Override
    public double getValueAt(double x) {
        return Math.abs(f.getValueAt(x));
    }

    @Override
    public Function derivative() {
        return new Mult(f.derivative(), new Signum(f));
    }

    @Override
    public String toString() {
        return "|" + f.toString() + "|";
    }
}
