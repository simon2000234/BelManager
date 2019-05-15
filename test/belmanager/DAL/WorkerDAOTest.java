/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belmanager.DAL;

import belmanager.BE.Worker;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Melchertsen
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WorkerDAOTest
{

    private Worker expectedWorker;
    private Worker actualWorker;
    private WorkerDAO wd;

    public WorkerDAOTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp() throws Exception
    {
        expectedWorker = new Worker("WT", "WorkerTest", 1);
        wd = new WorkerDAO();
    }

    /**
     * Test of createWorker method, of class WorkerDAO.
     */
    @Test
    public void AtestCreateWorker() throws Exception
    {
       wd.createWorker("WT", "WorkerTest", 1);
    }

    /**
     * Test of getWorker method, of class WorkerDAO.
     */
    @Test
    public void BtestGetWorker() throws Exception
    {
        boolean areTheyTheSame = false;
        actualWorker = wd.getWorker(1);
        if(actualWorker.toString().equals(expectedWorker.toString()))
        {
            areTheyTheSame = true;
        }
        assertEquals(true, areTheyTheSame);
    }

    /**
     * Test of deleteWorker method, of class WorkerDAO.
     */
    @Test
    public void CtestDeleteWorker() throws Exception
    {
        wd.deleteWorker(1);
        actualWorker = wd.getWorker(1);
        assertEquals(null, actualWorker);
    }

    /**
     * Test of getAllWorkers method, of class WorkerDAO.
     */
    @Test
    public void DtestGetAllWorkers() throws Exception
    {
        ArrayList<Worker> list = wd.getAllWorkers();
        assertEquals(7, list.size());
    }

}
