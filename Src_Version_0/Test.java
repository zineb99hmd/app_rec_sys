import Data_Split.Sort;

import static db.DAO.connection_mongo;

public class Test {
    public static void main(String[] args) {
        connection_mongo("sys_rec");
        Sort s=new Sort();
       System.out.println(s.read_load_file());
       s.Sort_By_time(s.read_load_file());
    }
}
