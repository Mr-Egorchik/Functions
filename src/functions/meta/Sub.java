package functions.meta;

import functions.Function;
// разность функций
public class Sub implements Function {
    private final Function f, g;
    public Sub(Function f1, Function g1) {
        f = f1;
        g = g1;
    }
    @Override
    public double getValueAt(double x) {
        return f.getValueAt(x) - g.getValueAt(x);
    }

    @Override
    public Function derivative() {
        return new Sub (f.derivative(), g.derivative());
    }

    public Function getF() {
        return f;
    }

    public Function getG() {
        return g;
    }

    @Override
    public String toString() {
        return "" + f.toString() + " - " + g.toString();
    }
}
