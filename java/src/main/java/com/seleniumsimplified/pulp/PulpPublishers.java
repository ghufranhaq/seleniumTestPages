package com.seleniumsimplified.pulp;

import com.seleniumsimplified.pulp.domain.PulpAuthor;
import com.seleniumsimplified.pulp.domain.PulpBook;
import com.seleniumsimplified.pulp.domain.PulpPublisher;

import java.util.ArrayList;
import java.util.List;

public class PulpPublishers {
    private int key;
    private ArrayList<PulpPublisher> publishers;

    public PulpPublishers(){
        publishers = new ArrayList<>();
        key = 1;
    }

    public int count() {
        return publishers.size();
    }

    public void add(String publisherName) {

        if(findByName(publisherName)!=PulpPublisher.UNKNOWN_PUBLISHER){
            return;
        }

        PulpPublisher publisher = getNextPublisher(publisherName);
        publishers.add(publisher);
    }

    private PulpPublisher getNextPublisher(String publisherName) {
        return new PulpPublisher(String.valueOf(key++), publisherName);
    }

    public PulpPublisher get(String key) {
            for(PulpPublisher publisher : publishers){
                if(publisher.getId().contentEquals(key)){
                    return publisher;
                }
            }

            return PulpPublisher.UNKNOWN_PUBLISHER;
    }

    public PulpPublisher findByName(String name) {
        for(PulpPublisher publisher : publishers){
            if(publisher.getName().contentEquals(name)){
                return publisher;
            }
        }

        return PulpPublisher.UNKNOWN_PUBLISHER;
    }

    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        for(PulpPublisher item : publishers){
            keys.add(item.getId());
        }
        return keys;
    }}
