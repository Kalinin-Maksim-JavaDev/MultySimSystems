package Platform.Calculation;

import Platform.Concurrent.Monitor_;

class Monitor_Calculator extends Monitor_ {

    final Calculator calculator;
    public int calcnum = 0;

    Monitor_Calculator(Calculator calculator_) {
        calculator = calculator_;
    }

    public synchronized void synchronizedWait() {
        while (calculator.getCalcCounter().get() != 0) {
            Wait();
        }
        calculator.getCalcCounter().addAndGet(calcnum);
    }

    public synchronized void synchronizedRelease() {
        calculator.getCalcCounter().decrementAndGet();
        if (calculator.getCalcCounter().get() == 0) {
            Release();
        }
        // System.out.println("  " + ":" + currentTimeMillis() + " " + sysName + taskMotions.getName() + " notify");
    }
}
