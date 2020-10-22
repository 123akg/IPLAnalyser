package com.capgemini.workshop.iplanalysis;

//import com.capgemini.workshop.iplanalysis.IPLLeagueAnalyser;
import com.capgemini.exception.IPLLeagueException;
import com.capgemini.iplpojos.MostRunCSV;
import com.capgemini.model.SortedField;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;

public class IPLLeagueAnalyserTest {
	private static IPLLeagueAnalyser cricketLeagueAnalyser;
	 @BeforeClass
	    public static void setUp() throws Exception {
	         cricketLeagueAnalyser = new IPLLeagueAnalyser();
	    }

    private static final String IPL_MOST_RUNS_CSV_PATH="C:\\Users\\abhij\\eclipse-workspace\\IPLAnalysis\\src\\main\\java\\resources\\IPL2019FactsheetMostRuns.csv";

    @Test
    public void givenMostRunsData_WhenSorted_ReturnsTopAverages() throws IPLLeagueException{
    	try {
        cricketLeagueAnalyser.loadIplData(IPL_MOST_RUNS_CSV_PATH);
        String result = cricketLeagueAnalyser.getTopBattingAverage(SortedField.AVERAGE);
        MostRunCSV[] iplMostRunsCSVS=new Gson().fromJson(result, MostRunCSV[].class);
        Assert.assertEquals(83.2, iplMostRunsCSVS[0].avg, 0.0);
    
    }
    	catch(IPLLeagueException e)
    	{
    		e.printStackTrace();
    	}
    }
    @Test
    public void givenIPLMostRunData_WhenSorted_ShouldReturnTopStrikeRate() throws IPLLeagueException {
        try{
        	cricketLeagueAnalyser.loadIplData(IPL_MOST_RUNS_CSV_PATH);
            String sortedStrikeRateData = cricketLeagueAnalyser.getTopBattingStrikeRate(SortedField.STRIKE_RATE);
            MostRunCSV[] iplMostRunsCSVS = new Gson().fromJson(sortedStrikeRateData, MostRunCSV[].class);
            Assert.assertEquals(333.33,iplMostRunsCSVS[0].sr, 0.0);
        }
        catch (IPLLeagueException e){
        	e.printStackTrace();

        } 
    }
    
}
