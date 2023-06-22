import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.expenses.exceptions.InvalidEmployeeIdException;
import com.capgemini.expenses.exceptions.NameTooShortException;
import com.capgemini.expenses.utilities.EmployeeUtilities;
import org.junit.jupiter.api.Test;

public class SampleTest {


    @Test
    public void testSomething(){

        int a = 4;
        int b = 8;
        int total = a +b;

        assertEquals(12,total);

    }
    @Test
    public void testEmployeeIdNumberIsConvertedCorrectly() throws InvalidEmployeeIdException {
        int result = EmployeeUtilities.validEmployeeID("416");

        assertEquals(416,result);
    }
    @Test
    public void testThatExceptionIsThrownIfEmployeeIdIsNotValidNumber(){

        assertThrows(InvalidEmployeeIdException.class,() ->{
            int result = EmployeeUtilities.validEmployeeID("hello");
        });

    }
    @Test
    public void testValidEmployeeName(){
        assertThrows(NameTooShortException.class,() ->{
            EmployeeUtilities.validateEmployeeName("To","Wi");
        });

    }

}
