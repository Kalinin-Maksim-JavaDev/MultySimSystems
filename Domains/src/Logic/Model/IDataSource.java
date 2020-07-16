/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.Model;

/**
 *
 * @author User
 */
public interface IDataSource {

    static class Func {

        public static IDataSource convert(Object dataSource_) {
            return (IDataSource) dataSource_;
        }
    };

    public Object GetData();
}
