package p06_TirePressureMonitoringSystem;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public
class AlarmTest {




    @Test
    public void testAlarmWithLowPressure_Then_ShouldBeOn(){
        Sensor sensor = Mockito.mock (Sensor.class);
        Mockito.when (sensor.popNextPressurePsiValue ()).thenReturn (15.2);
        Alarm alarm = new Alarm (sensor);
        alarm.check ();
        Assert.assertTrue (alarm.getAlarmOn ());
    }
    @Test
    public void testAlarmWithHiPressure_Then_ShouldBeOn(){
        Sensor sensor = Mockito.mock (Sensor.class);
        Mockito.when (sensor.popNextPressurePsiValue ()).thenReturn (25.5);
        Alarm alarm = new Alarm (sensor);
        alarm.check ();
        Assert.assertTrue (alarm.getAlarmOn ());
    }
    @Test
    public void testAlarmWithNormalPressure_Then_ShouldBeOff(){
        Sensor sensor = Mockito.mock (Sensor.class);
        Mockito.when (sensor.popNextPressurePsiValue ()).thenReturn (19.5);
        Alarm alarm = new Alarm (sensor);
        alarm.check ();
        Assert.assertFalse (alarm.getAlarmOn ());
    }

}