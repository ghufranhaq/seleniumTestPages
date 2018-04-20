package com.seleniumsimplified.pulp.reporting.reporters;

import com.seleniumsimplified.pulp.domain.objects.PulpPublisher;
import com.seleniumsimplified.pulp.reporting.ReportConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PublisherReporter {
    private final ReportConfig reportConfig;

    public PublisherReporter(ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }

    public Collection<String> getAsStrings(Collection<PulpPublisher> publishers) {
        List<String> report = new ArrayList<>();

        for(PulpPublisher publisher : publishers) {
            report.add(getPublisher(publisher));
        }

        return report;
    }

    public String getPublisher(PulpPublisher item) {
        if(reportConfig!=null && reportConfig.arePublishersLinks()){
            return String.format("<a href='%sbooks?publisher=%s'>%s</a>", reportConfig.getReportPath(), item.getId(), item.getName());
        }else{
            return item.getName();
        }
    }
}
