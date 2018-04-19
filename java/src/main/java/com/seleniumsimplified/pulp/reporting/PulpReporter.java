package com.seleniumsimplified.pulp.reporting;

import com.seleniumsimplified.pulp.PulpData;
import com.seleniumsimplified.pulp.domain.PulpAuthor;
import com.seleniumsimplified.pulp.domain.PulpBook;
import com.seleniumsimplified.pulp.domain.PulpPublisher;
import com.seleniumsimplified.pulp.domain.PulpSeries;

import java.util.*;

public class PulpReporter {
    private final PulpData data;
    private ReportConfig reportConfig;

    public PulpReporter(PulpData books) {
        this.data = books;
        this.reportConfig = ReportConfig.justStrings();
    }

    public PulpData data() {
        return data;
    }

    public Collection<String> getBooksAsStrings() {

        List<String> report = new ArrayList<>();

        StringBuilder line;

        List<String> keys = data.books().keys();

        for(String key : keys){
            line = new StringBuilder();
            PulpBook book = data.books().get(key);
            line.append(book.getTitle());
            line.append(" | ");

            for(String authorId : book.getAllAuthorIndexes()){
                line.append(getAuthorName(data.authors().get(authorId)));
                line.append(", ");
            }
            line.replace(line.lastIndexOf(","), line.lastIndexOf(",")+1, "");
            line.append(" | ");

            line.append(book.getPublicationYear());
            line.append(" | ");

            line.append(data.series().get(book.getSeriesIndex()).getName());

            report.add(line.toString());
        }

        return report;
    }

    public Collection<String> getBooksByAuthorAsStrings(String specificAuthorId) {
        List<String> report = new ArrayList<>();

        StringBuilder line;

        List<String> keys = data.books().keys();

        for(String key : keys){

            PulpBook book = data.books().get(key);

            if(book.isAuthoredBy(specificAuthorId)){

                line = new StringBuilder();

                line.append(book.getTitle());
                line.append(" | ");

                for(String authorId : book.getAllAuthorIndexes()){
                    line.append(getAuthorName(data.authors().get(authorId)));
                    line.append(", ");
                }
                line.replace(line.lastIndexOf(","), line.lastIndexOf(",")+1, "");
                line.append(" | ");

                line.append(book.getPublicationYear());
                line.append(" | ");

                line.append(data.series().get(book.getSeriesIndex()).getName());

                report.add(line.toString());
            }
        }

        return report;
    }

    private String getAuthorName(PulpAuthor author) {

        // TODO: need to add an object that prevents me from hardcoding the URLs otherwise it won't work for different versions of the app

        if(reportConfig!=null && reportConfig.areAuthorNamesLinks()){
            return String.format("<a href='/apps/pulp/gui/reports/books?author=%s'>%s</a>", author.getId(),author.getName());
        }else{
            return author.getName();
        }

    }

    public Collection<String> getAuthorsAsStrings() {
        List<String> report = new ArrayList<>();
        StringBuilder line;

        List<String> keys = data.authors().keys();

        for(String key : keys) {
            line = new StringBuilder();
            PulpAuthor author = data.authors().get(key);
            line.append(getAuthorName(author));

            report.add(line.toString());
        }

        return report;

    }

    public Collection<String> getPublishersAsStrings() {
            List<String> report = new ArrayList<>();
            StringBuilder line;

            List<String> keys = data.publishers().keys();

            for(String key : keys) {
                line = new StringBuilder();
                PulpPublisher item = data.publishers().get(key);
                line.append(item.getName());

                report.add(line.toString());
            }

            return report;

    }

    public Collection<String> getYearsAsStrings() {
        List<String> report = new ArrayList<>();

        List<String> keys = data.books().keys();

        Set<Integer> years = new TreeSet<>();

        for(String key : keys) {
            PulpBook book = data.books().get(key);
            years.add(Integer.valueOf(book.getPublicationYear()));
        }


        for(Integer year : years){
            report.add(year.toString());
        }

        return report;
    }

    public Collection<String> getSeriesNamesAsStrings() {

        List<String> keys = data.series().keys();

        Set<String> itemNames = new TreeSet<>();

        for(String key : keys) {
            PulpSeries item = data.series().get(key);
            itemNames.add(item.getName());
        }

        return itemNames;
    }


    public void configure(ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }
}
