/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proto;

/**
 *
 * @author dkx6r0c
 */
public class Test {

    public static void main(String[] args) {

        Data data = new Data();
        Control control = new Control();
        Input input = new Input();
        View view = new View();
        User user = new User();
        Cpu cpu = new Cpu();

        data = data.enter(control.getInput());
        view = data.render();
        control.reccive(user.getInput(view));
        control.reccive(cpu.genInput(data));
        
    }
}
