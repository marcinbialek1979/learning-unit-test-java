package pl.mb.testing.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mb.testing.order.Order;
import pl.mb.testing.order.OrderBackup;

import java.io.IOException;

public class OrderBackupExecutionOrderTest {
    @Test
    void callingBackupOrderWithoutCreatingAFIleShouldThrowException() throws IOException {
        //given
        Order order = new Order();
        OrderBackup orderBackup = new OrderBackup();
        //when
        //then
        Assertions.assertThrows(IOException.class, () -> orderBackup.backupOrder(order));

    }
}
