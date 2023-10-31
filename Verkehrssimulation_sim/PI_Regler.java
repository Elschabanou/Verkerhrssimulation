
public class PI_Regler {

    private double kp; // Proportionaler Anteil
    private double ki; // Integraler Anteil
    private double integral = 0.0; // Speichert die Summe der Fehler über die Zeit

    public PI_Regler(double kp, double ki) {
        this.kp = kp;
        this.ki = ki;
    }

    public double calculate(double setpoint, double currentAcceleration, double timeStep) {
        double error = setpoint - currentAcceleration;

        double proportional = kp * error;
        integral += error;
        double integralTerm = ki * integral;

        double controlSignal = proportional + timeStep * integralTerm;
        
        return controlSignal;
    }

    public void setParameters(double kp, double ki) {
        this.kp = kp;
        this.ki = ki;
    }
}
