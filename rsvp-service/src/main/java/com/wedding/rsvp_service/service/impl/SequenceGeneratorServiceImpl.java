package com.wedding.rsvp_service.service.impl;

import com.wedding.rsvp_service.model.DbSeq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SequenceGeneratorServiceImpl {
    private final MongoOperations mongoOperations;

    //method for incrementing the RsvpId automatically
    public long getNextId(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        Update update = new Update().inc("seq", 1);
        DbSeq counter = mongoOperations.findAndModify(
                query, update,
                FindAndModifyOptions.options().returnNew(true).upsert(true)
                , DbSeq.class);

        return counter != null ? counter.getSeq() : 1;
    }
}
