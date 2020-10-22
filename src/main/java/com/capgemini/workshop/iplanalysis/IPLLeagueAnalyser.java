package com.capgemini.workshop.iplanalysis;

import com.capgemini.iplpojos.MostRunCSV;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import com.capgemini.exception.*;
import com.capgemini.model.*;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class IPLLeagueAnalyser {
    List<MostRunCSV> mostRunsCSVList;
    Map<SortedField, Comparator<MostRunCSV>> sortedMap = new HashMap<>();

    public IPLLeagueAnalyser() {
        mostRunsCSVList=new ArrayList<>();
        this.sortedMap.put(SortedField.STRIKE_RATE,Comparator.comparing(ipldata -> ipldata.sr));
    }

    public int loadIplData(String csvFilePath) {
        int noOfPlayers=0;
        try(Reader reader= Files.newBufferedReader(Paths.get(csvFilePath))) {
            CsvToBeanBuilder<MostRunCSV> csvCsvToBeanBuilder=new CsvToBeanBuilder<>(reader);
            csvCsvToBeanBuilder.withType(MostRunCSV.class);
            csvCsvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<MostRunCSV> csvToBean = csvCsvToBeanBuilder.build();
            Iterator<MostRunCSV> iteratorload=csvToBean.iterator();
            while (iteratorload.hasNext())
            {
                MostRunCSV iplMostRunsCSV=iteratorload.next();
                mostRunsCSVList.add(iplMostRunsCSV);
                noOfPlayers++;
            }
            return noOfPlayers;
        } catch (IOException e) {
            throw new IPLLeagueException("Wrong File Path",IPLLeagueException.ExceptionType.IPL_DATA_NOT_FOUND);
        }

    }

    public String getTopBattingAverage(SortedField sortedField) {
        if (mostRunsCSVList==null || mostRunsCSVList.size()==0)
        {
            throw new IPLLeagueException("No Records",IPLLeagueException.ExceptionType.IPL_FILE_PROBLEM);
        }
        Comparator<MostRunCSV> mostRunsCSVComparator=Comparator.comparing(census-> census.avg);
        
        sort(mostRunsCSVComparator);
        Collections.reverse(mostRunsCSVList);
        String sortedAverage=new Gson().toJson(mostRunsCSVList);
        return sortedAverage;
    }

    public String getTopBattingStrikeRate(SortedField sortedField) {
        if (mostRunsCSVList==null || mostRunsCSVList.size()==0)
        {
            throw new IPLLeagueException("No Records",IPLLeagueException.ExceptionType.IPL_FILE_PROBLEM);
        }
        Comparator<MostRunCSV> mostRunsCSVComparator=Comparator.comparing(census-> census.sr);
        
        sort(mostRunsCSVComparator);
        Collections.reverse(mostRunsCSVList);
        String sortedStrikeRate =new Gson().toJson(mostRunsCSVList);
        return sortedStrikeRate;
    }
    
    
    public void sort(Comparator<MostRunCSV> mostRunsCSVComparator) {
        for (int i = 0; i< this.mostRunsCSVList.size()-1; i++)
        {
            for (int j = 0; j< this.mostRunsCSVList.size()-i-1; j++)
            {
                MostRunCSV census1= this.mostRunsCSVList.get(j);
                MostRunCSV census2= this.mostRunsCSVList.get(j+1);
                if (mostRunsCSVComparator.compare(census1,census2)>0) {
                    this.mostRunsCSVList.set(j, census2);
                    this.mostRunsCSVList.set(j + 1, census1);
                }
            }
        }
    }
}
